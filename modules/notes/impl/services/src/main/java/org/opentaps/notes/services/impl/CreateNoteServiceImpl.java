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
