package org.opentaps.notes.services.impl;

import org.opentaps.notes.services.NoteServices;
import org.opentaps.notes.services.GetNoteByIdService;
import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.repository.NoteRepository;

public class NoteServicesImpl implements NoteServices {

    private NoteRepository repository;

    public NoteServicesImpl() { }

    public void setNoteRepository(NoteRepository noteRepository) {
        this.repository = noteRepository;
    }

    public CreateNoteService getCreateNoteService() {
        CreateNoteServiceImpl ser = new CreateNoteServiceImpl();
        ser.setNoteRepository(repository);
        return ser;
    }

    public GetNoteByIdService getGetNoteByIdService() {
        GetNoteByIdServiceImpl ser = new GetNoteByIdServiceImpl();
        ser.setNoteRepository(repository);
        return ser;
    }
}
