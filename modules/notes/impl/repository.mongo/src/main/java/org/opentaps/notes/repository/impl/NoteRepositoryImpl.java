/*
 * Copyright (c) Open Source Strategies, Inc.
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opentaps.notes.repository.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import org.apache.commons.validator.GenericValidator;
import org.bson.BasicBSONObject;
import org.bson.types.ObjectId;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.domain.NoteFactory;
import org.opentaps.notes.domain.impl.NoteMongo;
import org.opentaps.notes.repository.NoteRepository;
import org.opentaps.notes.security.NoteUser;
import org.osgi.service.useradmin.User;


/**
 * The implementation of the NoteRepository using the javax.persistence.EntityManager.
 */
public class NoteRepositoryImpl implements NoteRepository {
    private volatile Mongo mongo;
    private volatile NoteFactory factory;
    private static final String DB = "notedb";
    private static final String NOTES_COLLECTION = "notes";

    public void setMongo(Mongo mongo) {
        if (this.mongo == null) {
            synchronized (this) {
                if (this.mongo == null) {
                    this.mongo = mongo;
                }
            }
        }
    }

    public void setNoteFactory(NoteFactory factory) {
        if (this.factory  == null) {
            synchronized (this) {
                if (this.factory == null) {
                    this.factory = factory;
                }
            }
        }
    }

    private DBCollection getNotesCollection() {
        if (mongo == null) {
            throw new IllegalStateException();
        }
        DB db = mongo.getDB(DB);
        return db.getCollection(NOTES_COLLECTION);
    }

    /** {@inheritDoc} */
    public Note getNoteById(String noteId) {
        DBCollection coll = getNotesCollection();
        BasicDBObject query = new BasicDBObject(NoteMongo.MONGO_ID_FIELD, new ObjectId(noteId));
        DBObject noteDoc = coll.findOne(query);
        return dbObjectToNote(noteDoc);
    }

    /** {@inheritDoc} */
    public List<Note> getNotesPaginated(Long fromSequence, Integer numberOfNotes) {
        return getNotesPaginated(fromSequence, numberOfNotes, null);
    }

    /** {@inheritDoc} */
    public List<Note> getNotesPaginated(Long fromSequence, Integer numberOfNotes, Integer order) {
        DBCollection coll = getNotesCollection();

        BasicDBObject query = new BasicDBObject();
        if (order == null || order >= 0) {
            order = 1;
        } else {
            order = -1;
        }

        if (fromSequence != null) {
            if (order > 0) {
                query.put(Note.Fields.sequenceNum.getName(), new BasicDBObject("$gte", fromSequence));
            } else {
                query.put(Note.Fields.sequenceNum.getName(), new BasicDBObject("$lte", fromSequence));
            }
        }

        if (numberOfNotes == null || numberOfNotes <= 0 || numberOfNotes > 100) {
            numberOfNotes = 100;
        }

        List<DBObject> notes = coll.find(query).sort(new BasicDBObject(Note.Fields.sequenceNum.getName(), order)).limit(numberOfNotes).toArray();
        List<Note> result = new ArrayList<Note>();
        if (notes != null && notes.size() > 0) {
            for (DBObject noteDoc : notes) {
                Note note = dbObjectToNote(noteDoc);
                result.add(note);
            }
        }

        return result;
    }

    /** {@inheritDoc} */
    public void persist(Note note) {
        if (note == null) {
            throw new IllegalArgumentException();
        }

        persist(Arrays.asList(note));
    }

    /** {@inheritDoc} */
    public void persist(List<Note> notes) {
        if (notes == null) {
            throw new IllegalArgumentException();
        }
        DBCollection coll = getNotesCollection();

        for (Note note : notes) {

            // transform POJO into BSON
            BasicDBObject noteDoc = noteToDbObject(note);

            // for creation set the created date
            if (note.getDateTimeCreated() == null) {
                note.setDateTimeCreated(new Timestamp(System.currentTimeMillis()));
            }
            noteDoc.put(Note.Fields.dateTimeCreated.getName(), note.getDateTimeCreated());

            // and the sequence
            if (note.getSequenceNum() == null) {
                note.setSequenceNum(nextSequenceNum(coll));
            }
            noteDoc.put(Note.Fields.sequenceNum.getName(), note.getSequenceNum());

            coll.insert(noteDoc);
            note.setNoteId(noteDoc.getObjectId(NoteMongo.MONGO_ID_FIELD).toString());
        }
    }

    private Long nextSequenceNum(DBCollection c) {
        BasicDBObject query = new BasicDBObject(Note.Fields.sequenceNum.getName(), new BasicDBObject("$exists", true));
        List<DBObject> last = c.find(query).sort(new BasicDBObject(Note.Fields.sequenceNum.getName(), -1)).limit(1).toArray();
        Long lastSequenceNum = null;
        if (last != null && last.size() > 0) {
            lastSequenceNum = (Long) last.get(0).get(Note.Fields.sequenceNum.getName());
            lastSequenceNum++;
        }

        if (lastSequenceNum == null) {
            lastSequenceNum = 1L;
        }

        return lastSequenceNum;
    }

    private Note dbObjectToNote(DBObject noteDoc) {
        if (noteDoc == null) {
            return null;
        }

        Note note = factory.newInstance();
        note.setNoteId(noteDoc.get(NoteMongo.MONGO_ID_FIELD).toString());
        note.setNoteText((String) noteDoc.get(Note.Fields.noteText.getName()));
        if (noteDoc.containsField("createdByUserId")) {
            String userId = (String) noteDoc.get("createdByUserId");
            if (!GenericValidator.isBlankOrNull(userId)) {
                note.setCreatedByUser(new NoteUser(userId, (String) noteDoc.get("userIdType")));
            }
        } else if (noteDoc.containsField(Note.Fields.createdByUser.getName())) {
            BasicDBObject userDoc = (BasicDBObject) noteDoc.get(Note.Fields.createdByUser.getName());
            if (userDoc != null) {
                User user = new NoteUser();
                Set<Entry<String, Object>> entries = userDoc.entrySet();
                for (Entry<String, Object> entry : entries) {
                    user.getProperties().put(entry.getKey(), entry.getValue());
                }
                note.setCreatedByUser(user);
            }
        }
        note.setClientDomain((String) noteDoc.get(Note.Fields.clientDomain.getName()));
        note.setSequenceNum((Long) noteDoc.get(Note.Fields.sequenceNum.getName()));
        Date dateTimeCreated = (Date) noteDoc.get(Note.Fields.dateTimeCreated.getName());
        if (dateTimeCreated != null) {
            note.setDateTimeCreated(new Timestamp(dateTimeCreated.getTime()));
        }
        // look for custom fields
        for (String field : noteDoc.keySet()) {
            if (!note.isBaseField(field)) {
                note.setAttribute(field, (String) noteDoc.get(field));
            }
        }

        return note;
    }

    private static BasicDBObject noteToDbObject(Note note) {
        if (note == null) {
            return null;
        }
        BasicDBObject noteDoc = (BasicDBObject) BasicDBObjectBuilder.start()
            .add(Note.Fields.noteId.getName(), note.getNoteId())
            .add(Note.Fields.noteText.getName(), note.getNoteText())
            .add(Note.Fields.clientDomain.getName(), note.getClientDomain()).get();

        User user = note.getCreatedByUser();
        if (user != null) {
          BasicDBObject userDoc = (BasicDBObject) BasicDBObjectBuilder.start().get();
          @SuppressWarnings("unchecked")
          Dictionary<String, Object> props = user.getProperties();
          if (props != null && !props.isEmpty()) {
              Enumeration<String> keys = props.keys();
              while (keys.hasMoreElements()) {
                  String key = keys.nextElement();
                  userDoc.append(key, props.get(key));
              }
          }
          noteDoc.append(Note.Fields.createdByUser.getName(), userDoc);
        }

        // look for custom fields
        for (String field : note.getAttributeNames()) {
            noteDoc.put(field, note.getAttribute(field));
        }

        return noteDoc;
    }
}
