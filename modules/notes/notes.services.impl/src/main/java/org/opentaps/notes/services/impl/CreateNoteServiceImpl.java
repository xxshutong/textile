package org.opentaps.notes.services.impl;

import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.repository.NoteRepository;

public class CreateNoteServiceImpl implements CreateNoteService {

    private String noteId;
    private String text;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private String attribute6;
    private String attribute7;
    private String attribute8;
    private String attribute9;
    private String attribute10;

    private NoteRepository repository;

    public CreateNoteServiceImpl() { }

    public void setNoteRepository(NoteRepository noteRepository) {
        this.repository = noteRepository;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAttribute1(String value) {
        this.attribute1 = value;
    }
    public void setAttribute2(String value) {
        this.attribute2 = value;
    }
    public void setAttribute3(String value) {
        this.attribute3 = value;
    }
    public void setAttribute4(String value) {
        this.attribute4 = value;
    }
    public void setAttribute5(String value) {
        this.attribute5 = value;
    }
    public void setAttribute6(String value) {
        this.attribute6 = value;
    }
    public void setAttribute7(String value) {
        this.attribute7 = value;
    }
    public void setAttribute8(String value) {
        this.attribute8 = value;
    }
    public void setAttribute9(String value) {
        this.attribute9 = value;
    }
    public void setAttribute10(String value) {
        this.attribute10 = value;
    }

    public String getNoteId() {
        return this.noteId;
    }

    public void createNote() {
        Note note = new Note();
        note.setText(text);
        note.setAttribute1(attribute1);
        note.setAttribute2(attribute2);
        note.setAttribute3(attribute3);
        note.setAttribute4(attribute4);
        note.setAttribute5(attribute5);
        note.setAttribute6(attribute6);
        note.setAttribute7(attribute7);
        note.setAttribute8(attribute8);
        note.setAttribute9(attribute9);
        note.setAttribute10(attribute10);

        repository.persist(note);

        noteId = note.getId();
    }

}
