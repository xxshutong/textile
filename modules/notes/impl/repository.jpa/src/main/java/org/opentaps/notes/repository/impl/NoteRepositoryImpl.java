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
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.opentaps.notes.domain.Note;
import org.opentaps.notes.domain.NoteFactory;
import org.opentaps.notes.domain.impl.NoteJPA;
import org.opentaps.notes.repository.NoteRepository;

/**
 * The implementation of the NoteRepository using the javax.persistence.EntityManager.
 */
public class NoteRepositoryImpl implements NoteRepository {

    private volatile EntityManager em;
    private volatile NoteFactory factory;

    public void setEntityManager(EntityManager em) {
        if (this.em == null) {
            synchronized (this) {
                if (this.em == null) {
                    this.em = em;
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

    /** {@inheritDoc} */
    public Note getNoteById(String noteId) {
        if (em == null) {
            throw new IllegalStateException();
        }
        return em.find(NoteJPA.class, noteId);
    }

    /** {@inheritDoc} */
    public List<Note> getNotesPaginated(Long fromSequence, Integer numberOfNotes) {
        return getNotesPaginated(fromSequence, numberOfNotes, null);
    }

    /** {@inheritDoc} */
    public List<Note> getNotesPaginated(Long fromSequence, Integer numberOfNotes, Integer order) {
        StringBuilder sb = new StringBuilder("SELECT o FROM NoteJPA o");
        if (fromSequence != null) {
            sb.append(" WHERE o.sequenceNum ");
            if (order == null || order >= 0) {
                sb.append(">=");
            } else {
                sb.append("<=");
            }
            sb.append(" :sequence ");
        }
        sb.append(" ORDER BY o.sequenceNum ");
        if (order == null || order >= 0) {
            sb.append("ASC");
        } else {
            sb.append("DESC");
        }
        TypedQuery<NoteJPA> query = em.createQuery(sb.toString(), NoteJPA.class);
        if (numberOfNotes == null || numberOfNotes <= 0 || numberOfNotes > 100) {
            numberOfNotes = 100;
        }
        if (fromSequence != null) {
            query.setParameter("sequence", fromSequence);
        }
        query.setMaxResults(numberOfNotes);
        List<Note> results = new ArrayList<Note>(query.getResultList());
        return results;
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
        if (em == null) {
            throw new IllegalStateException();
        }
        if (notes == null) {
            throw new IllegalArgumentException();
        }

        for (Note note : notes) {

            // for creation set the created date
            if (note.getDateTimeCreated() == null) {
                note.setDateTimeCreated(new Timestamp(System.currentTimeMillis()));
            }

            em.persist(note);
            note.setNoteId(note.getNoteId());
        }

        em.flush();
    }

}
