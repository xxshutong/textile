package org.opentaps.notes.services.impl;

import org.opentaps.notes.services.GetNoteByIdService;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.repository.NoteRepository;

public class GetNoteByIdServiceImpl implements GetNoteByIdService {

    private String noteId;
    private Note note;

    private NoteRepository repository;

    public GetNoteByIdServiceImpl() { }

    public void setNoteRepository(NoteRepository noteRepository) {
        this.repository = noteRepository;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public Note getNote() {
        return this.note;
    }

    public void getNoteById() {
        note = repository.getNoteById(noteId);
    }

}
