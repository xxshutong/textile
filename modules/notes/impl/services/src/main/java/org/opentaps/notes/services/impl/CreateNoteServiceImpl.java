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

import java.util.Set;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.opentaps.core.log.Log;
import org.opentaps.core.service.ServiceException;
import org.opentaps.core.service.ServiceValidationException;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.repository.NoteRepository;
import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.services.CreateNoteServiceInput;
import org.opentaps.notes.services.CreateNoteServiceOutput;
import org.opentaps.validation.services.ValidationService;

public class CreateNoteServiceImpl implements CreateNoteService {

    private volatile NoteRepository repository = null;
    private volatile ValidationService validationService = null;

    public CreateNoteServiceImpl() { }

    public void setNoteRepository(NoteRepository noteRepository) {
        if (repository == null && noteRepository != null) {
            synchronized (this) {
                if (repository == null) {
                    repository = noteRepository;
                }
            }
        }
    }

    public void setValidationService(ValidationService validationService) {
        if (this.validationService == null && validationService != null) {
            synchronized (this) {
                if (this.validationService == null) {
                    this.validationService = validationService;
                }
            }
        }
    }

    public CreateNoteServiceOutput createNote(CreateNoteServiceInput input) throws ServiceException {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        if (repository == null) {
            throw new IllegalStateException("No NoteRepository implementation was set");
        }
        if (validationService == null) {
            throw new IllegalStateException("No ValidationService implementation was set");
        }

        Set<ConstraintViolation<CreateNoteServiceInput>> inputViolations = validationService.getValidator().validate(input);
        if (inputViolations.size() > 0) {
            throw new ServiceValidationException("Cannot create note.", (Set) inputViolations);
        }


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

        try {
            repository.persist(note);
        } catch (ConstraintViolationException e) {
            throw new ServiceValidationException("Cannot create note.", e);
        } catch (PersistenceException e) {
            throw new ServiceException("Cannot create note.", e);
        }

        CreateNoteServiceOutput out = new CreateNoteServiceOutput();
        out.setNoteId(note.getId());

        Set<ConstraintViolation<CreateNoteServiceOutput>> outputViolations = validationService.getValidator().validate(out);
        if (outputViolations.size() > 0) {
            throw new ServiceValidationException("Could not create note.", (Set) outputViolations);
        }

        Log.logDebug("Note created with id [" + note.getId() + "]");
        return out;
    }

}
