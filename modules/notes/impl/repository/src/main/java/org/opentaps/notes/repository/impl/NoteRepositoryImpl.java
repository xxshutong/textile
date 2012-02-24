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
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.opentaps.notes.domain.Note;
import org.opentaps.notes.entity.NoteData;
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

    private static Note makeNote(NoteData noteData) {
        if (noteData == null) {
            return null;
        }

        Note note = new Note();
        note.setId(noteData.getNoteId());
        note.setText(noteData.getNoteText());
        note.setAttribute1(noteData.getAttribute1());
        note.setAttribute2(noteData.getAttribute2());
        note.setAttribute3(noteData.getAttribute3());
        note.setAttribute4(noteData.getAttribute4());
        note.setAttribute5(noteData.getAttribute5());
        note.setAttribute6(noteData.getAttribute6());
        note.setAttribute7(noteData.getAttribute7());
        note.setAttribute8(noteData.getAttribute8());
        note.setAttribute9(noteData.getAttribute9());
        note.setAttribute10(noteData.getAttribute10());
        note.setDateTimeCreated(noteData.getDateTimeCreated());
        note.setCreatedByUserId(noteData.getCreatedByUserId());
        note.setUserIdType(noteData.getUserIdType());
        note.setSequenceNum(noteData.getSequenceNum());
        note.setClientDomain(noteData.getClientDomain());
        return note;
    }

    private static NoteData makeNoteData(Note note) {
        NoteData noteData = new NoteData();
        noteData.setNoteId(note.getId());
        noteData.setNoteText(note.getText());
        noteData.setAttribute1(note.getAttribute1());
        noteData.setAttribute2(note.getAttribute2());
        noteData.setAttribute3(note.getAttribute3());
        noteData.setAttribute4(note.getAttribute4());
        noteData.setAttribute5(note.getAttribute5());
        noteData.setAttribute6(note.getAttribute6());
        noteData.setAttribute7(note.getAttribute7());
        noteData.setAttribute8(note.getAttribute8());
        noteData.setAttribute9(note.getAttribute9());
        noteData.setAttribute10(note.getAttribute10());
        noteData.setDateTimeCreated(note.getDateTimeCreated());
        noteData.setCreatedByUserId(note.getCreatedByUserId());
        noteData.setUserIdType(note.getUserIdType());
        noteData.setSequenceNum(note.getSequenceNum());
        noteData.setClientDomain(note.getClientDomain());
        return noteData;
    }

    /** {@inheritDoc} */
    public Note getNoteById(String noteId) {
        if (em == null) {
            throw new IllegalStateException();
        }
        NoteData noteData = em.find(NoteData.class, noteId);
        return makeNote(noteData);
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
            NoteData noteData = makeNoteData(note);

            // for creation set the created date
            if (noteData.getDateTimeCreated() == null) {
                noteData.setDateTimeCreated(new Timestamp(System.currentTimeMillis()));
            }

            em.persist(noteData);
            note.setId(noteData.getNoteId());
        }

        em.flush();
    }

}
