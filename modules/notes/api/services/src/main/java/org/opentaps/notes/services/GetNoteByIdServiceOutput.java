package org.opentaps.notes.services;

import org.opentaps.notes.domain.Note;

public class GetNoteByIdServiceOutput {

    private Note note;

    public void setNote(Note note) { this.note = note; }
    public Note getNote() { return note; }

}
