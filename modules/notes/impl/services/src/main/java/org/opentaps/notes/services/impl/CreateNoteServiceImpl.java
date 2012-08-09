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
import org.opentaps.notes.domain.NoteFactory;
import org.opentaps.notes.repository.NoteRepository;
import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.services.CreateNoteServiceInput;
import org.opentaps.notes.services.CreateNoteServiceOutput;
import org.opentaps.notes.security.NoteSecurity;
import org.opentaps.validation.services.ValidationService;

public class CreateNoteServiceImpl implements CreateNoteService {

    private volatile NoteRepository repository = null;
    private volatile NoteSecurity security = null;
    private volatile ValidationService validationService = null;
    private volatile NoteFactory factory = null;

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

    public void setNoteSecurity(NoteSecurity noteSecurity) {
        if (security == null && noteSecurity != null) {
            synchronized (this) {
                if (security == null) {
                    security = noteSecurity;
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

    public void setNoteFactory(NoteFactory factory) {
        if (this.factory == null && factory != null) {
            synchronized (this) {
                if (this.factory == null) {
                    this.factory = factory;
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

        Note note = factory.newInstance();
        note.setNoteText(input.getText());
        note.setAttributes(input.getAttributes());
        note.setCreatedByUser(input.getCreatedByUser());
        note.setClientDomain(input.getClientDomain());

        if (security != null) {
            if (!security.hasPermission(note, NoteSecurity.Operation.CREATE)) {
                throw new ServiceException(security.getErrorMessage());
            }
        } else {
            throw new ServiceException("Security subsystem in broken.");
        }

        try {
            repository.persist(note);
        } catch (ConstraintViolationException e) {
            throw new ServiceValidationException("Cannot create note.", e);
        } catch (PersistenceException e) {
            throw new ServiceException("Cannot create note.", e);
        }

        CreateNoteServiceOutput out = new CreateNoteServiceOutput();
        out.setNoteId(note.getNoteId());

        Set<ConstraintViolation<CreateNoteServiceOutput>> outputViolations = validationService.getValidator().validate(out);
        if (outputViolations.size() > 0) {
            throw new ServiceValidationException("Could not create note.", (Set) outputViolations);
        }

        Log.logDebug("Note created with id [" + note.getNoteId() + "]");
        return out;
    }

}
