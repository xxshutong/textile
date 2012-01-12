package org.opentaps.notes.repository.impl;

import org.opentaps.notes.entity.NoteData;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.repository.NoteRepository;

import javax.persistence.EntityManager;

/**
 * The implementation of the NoteRepository using the javax.persistence.EntityManager.
 */
public class NoteRepositoryImpl implements NoteRepository {

    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    private static Note makeNote(NoteData noteData) {
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
        return noteData;
    }

    /* {@inheritDoc} */
    public Note getNoteById(String noteId) {
        NoteData noteData = em.find(NoteData.class, noteId);
        return makeNote(noteData);
    }

    /* {@inheritDoc} */
    public void persist(Note note) {
        NoteData noteData = makeNoteData(note);
        em.persist(noteData);
        em.flush();
        em.clear();

        // for creation the id was not set and is now available in noteData
        note.setId(noteData.getNoteId());
    }
    

}
