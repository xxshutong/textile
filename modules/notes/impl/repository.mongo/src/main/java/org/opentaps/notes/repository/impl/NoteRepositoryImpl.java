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
import java.util.List;

import org.bson.types.ObjectId;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.repository.NoteRepository;
import org.opentaps.notes.repository.RepositoryException;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;


/**
 * The implementation of the NoteRepository using the javax.persistence.EntityManager.
 */
public class NoteRepositoryImpl implements NoteRepository {
    private volatile Mongo mongo;
    private static final String DB = "notedb";

    public void setMongo(Mongo mongo) {
        if (this.mongo == null) {
            synchronized (this) {
                if (this.mongo == null) {
                    this.mongo = mongo;
                }
            }
        }
    }

    /** {@inheritDoc} */
    public Note getNoteById(String noteId) {
        if (mongo == null) {
            throw new IllegalStateException();
        }

        DB db = mongo.getDB(DB);
        DBCollection coll = db.getCollection("notes");

        BasicDBObject query = new BasicDBObject("_id", new ObjectId(noteId));
        DBObject noteDoc = coll.findOne(query);

        Note note = null;
        if (noteDoc != null) {
            note = new Note();
            note.setNoteId(noteDoc.get("_id").toString());
            note.setNoteText((String) noteDoc.get("noteText"));
            note.setCreatedByUserId((String) noteDoc.get("createdByUserId"));
            note.setUserIdType((String) noteDoc.get("userIdType"));
            note.setClientDomain((String) noteDoc.get("clientDomain"));
            note.setSequenceNum((Long) noteDoc.get("sequenceNum"));
            Date dateTimeCreated = (Date) noteDoc.get("dateTimeCreated");
            if (dateTimeCreated != null) {
                note.setDateTimeCreated(new Timestamp(dateTimeCreated.getTime()));
            }
        }

        return note;
    }

    /** {@inheritDoc} 
     * @throws Exception */
    public List<Note> getNotesPaginated(Long fromSequence, Integer numberOfNotes) {
        return getNotesPaginated(fromSequence, numberOfNotes, null);
    }

    /** {@inheritDoc} 
     * @throws MongoException 
     * @throws  */
    public List<Note> getNotesPaginated(Long fromSequence, Integer numberOfNotes, Integer order) {
        if (mongo == null) {
            throw new IllegalStateException();
        }

        DB db = mongo.getDB(DB);

        DBCollection coll = db.getCollection("notes");

        BasicDBObject query = new BasicDBObject();
        if (order == null || order >= 0) {
            query.put("sequenceNum", new BasicDBObject("$gte", fromSequence == null ? 0L : fromSequence));
        } else {
            query.put("sequenceNum", new BasicDBObject("$lte", fromSequence == null ? 0L : fromSequence));
        }

        if (numberOfNotes == null || numberOfNotes <= 0 || numberOfNotes > 100) {
            numberOfNotes = 100;
        }

        List<DBObject> notes = coll.find(query).sort(new BasicDBObject("sequenceNum", (order == null || order >= 0) ? 1 : -1)).limit(numberOfNotes).toArray();
        List<Note> result = new ArrayList<Note>();
        if (notes != null && notes.size() > 0) {
            for (DBObject noteDoc : notes ) {
                Note note = new Note();
                note.setNoteId(noteDoc.get("_id").toString());
                note.setNoteText((String) noteDoc.get("noteText"));
                note.setCreatedByUserId((String) noteDoc.get("createdByUserId"));
                note.setUserIdType((String) noteDoc.get("userIdType"));
                note.setClientDomain((String) noteDoc.get("clientDomain"));
                note.setSequenceNum((Long) noteDoc.get("sequenceNum"));
                Date dateTimeCreated = (Date) noteDoc.get("dateTimeCreated");
                if (dateTimeCreated != null) {
                    note.setDateTimeCreated(new Timestamp(dateTimeCreated.getTime()));
                }
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
        if (mongo == null) {
            throw new IllegalStateException();
        }
        if (notes == null) {
            throw new IllegalArgumentException();
        }

        DB db = mongo.getDB(DB);

        DBCollection coll = db.getCollection("notes");

        for (Note note : notes) {

            // transform POJO into BSON
            BasicDBObject noteDoc = (BasicDBObject) BasicDBObjectBuilder.start()
                    .add("noteId", note.getNoteId())
                    .add("noteText", note.getNoteText())
                    .add("createdByUserId", note.getCreatedByUserId())
                    .add("userIdType", note.getUserIdType())
                    .add("clientDomain", note.getClientDomain()).get();

            // for creation set the created date
            if (note.getDateTimeCreated() == null) {
                note.setDateTimeCreated(new Timestamp(System.currentTimeMillis()));
            }
            noteDoc.put("dateTimeCreated", note.getDateTimeCreated());
            if (note.getSequenceNum() == null) {
                note.setSequenceNum(nextSequenceNum(coll));
            }
            noteDoc.put("sequenceNum", note.getSequenceNum());

            coll.insert(noteDoc);
            note.setNoteId(noteDoc.getObjectId("_id").toString());
        }
    }

    private Long nextSequenceNum(DBCollection c) {
        BasicDBObject query = new BasicDBObject("sequenceNum", new BasicDBObject("$exists", true));
        List<DBObject> last = c.find(query).sort(new BasicDBObject("sequenceNum", -1)).limit(1).toArray();
        Long lastSequenceNum = null;
        if (last != null && last.size() > 0) {
            lastSequenceNum = (Long) last.get(0).get("sequenceNum");
            lastSequenceNum++;
        }
        
        return lastSequenceNum != null ? lastSequenceNum : 1L;
    }
}