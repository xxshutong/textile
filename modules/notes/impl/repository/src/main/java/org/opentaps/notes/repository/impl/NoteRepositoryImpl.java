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
import org.opentaps.notes.repository.NoteRepository;

/**
 * The implementation of the NoteRepository using the javax.persistence.EntityManager.
 */
public class NoteRepositoryImpl implements NoteRepository {

    private volatile EntityManager em;

    public void setEntityManager(EntityManager em) {
        if (this.em == null) {
            synchronized (this) {
                if (this.em == null) {
                    this.em = em;
                }
            }
        }
    }

    /** {@inheritDoc} */
    public Note getNoteById(String noteId) {
        if (em == null) {
            throw new IllegalStateException();
        }
        return em.find(Note.class, noteId);
    }

    /** {@inheritDoc} */
    public List<Note> getNotesPaginated(Long fromSequence, Integer numberOfNotes) {
        TypedQuery<Note> query = em.createQuery("SELECT o FROM NoteData o WHERE o.sequenceNum >= :sequence", Note.class);
        if (numberOfNotes == null || numberOfNotes <= 0 || numberOfNotes > 100) {
            numberOfNotes = 100;
        }
        if (fromSequence == null) {
            fromSequence = 0L;
        }
        query.setMaxResults(numberOfNotes);
        query.setParameter("sequence", fromSequence);
        return query.getResultList();
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
