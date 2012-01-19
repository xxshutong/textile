package org.opentaps.notes.services.impl;

import org.opentaps.notes.services.GetNoteByIdService;
import org.opentaps.notes.services.GetNoteByIdServiceInput;
import org.opentaps.notes.services.GetNoteByIdServiceOutput;
import org.opentaps.notes.repository.NoteRepository;

public class GetNoteByIdServiceImpl implements GetNoteByIdService {

    private NoteRepository repository;

    public GetNoteByIdServiceImpl() { }

    public void setNoteRepository(NoteRepository noteRepository) {
        this.repository = noteRepository;
    }

    public GetNoteByIdServiceOutput getNoteById(GetNoteByIdServiceInput input) {
        GetNoteByIdServiceOutput out = new GetNoteByIdServiceOutput();
        out.setNote(repository.getNoteById(input.getNoteId()));
        return out;
    }

}
