package org.opentaps.notes.services.impl;

import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.services.CreateNoteServiceInput;
import org.opentaps.notes.services.CreateNoteServiceOutput;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.repository.NoteRepository;

public class CreateNoteServiceImpl implements CreateNoteService {

    private NoteRepository repository;

    public CreateNoteServiceImpl() { }

    public void setNoteRepository(NoteRepository noteRepository) {
        this.repository = noteRepository;
    }

    public CreateNoteServiceOutput createNote(CreateNoteServiceInput input) {
        Note note = new Note();
        note.setText(input.getText());
        note.setAttribute1(input.getAttribute1());
        note.setAttribute2(input.getAttribute2());
        note.setAttribute3(input.getAttribute3());
        note.setAttribute4(input.getAttribute4());
        note.setAttribute5(input.getAttribute5());
        note.setAttribute6(input.getAttribute6());
        note.setAttribute7(input.getAttribute7());
        note.setAttribute8(input.getAttribute8());
        note.setAttribute9(input.getAttribute9());
        note.setAttribute10(input.getAttribute10());

        repository.persist(note);

        CreateNoteServiceOutput out = new CreateNoteServiceOutput();
        out.setNoteId(note.getId());
        return out;
    }

}
