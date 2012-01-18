package org.opentaps.notes.services;

import org.opentaps.notes.domain.Note;

public interface GetNoteByIdService {

    public void setNoteId(String noteId);
    public Note getNote();

    public void getNoteById();

}
