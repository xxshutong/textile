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
import javax.validation.ConstraintViolation;

import org.opentaps.core.service.ServiceException;
import org.opentaps.core.service.ServiceValidationException;
import org.opentaps.notes.repository.NoteRepository;
import org.opentaps.notes.services.GetNotesService;
import org.opentaps.notes.services.GetNotesServiceInput;
import org.opentaps.notes.services.GetNotesServiceOutput;
import org.opentaps.validation.services.ValidationService;


public class GetNotesServiceImpl implements GetNotesService {

    private volatile NoteRepository repository = null;
    private volatile ValidationService validationService = null;

    public GetNotesServiceImpl() { }

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

    public GetNotesServiceOutput getNotes(GetNotesServiceInput input) throws ServiceException  {
        if (input == null) {
            throw new IllegalArgumentException();
        }
        if (repository == null) {
            throw new IllegalStateException();
        }
        if (validationService == null) {
            throw new IllegalStateException("No ValidationService implementation was set");
        }

        Set<ConstraintViolation<GetNotesServiceInput>> inputViolations = validationService.getValidator().validate(input);
        if (inputViolations.size() > 0) {
            throw new ServiceValidationException("Cannot get note", (Set) inputViolations);
        }

        GetNotesServiceOutput out = new GetNotesServiceOutput();
        out.setNotes(repository.getNotesPaginated(input.getFromSequence(), input.getNumberOfNotes(), input.getOrderDirection()));

        Set<ConstraintViolation<GetNotesServiceOutput>> outputViolations = validationService.getValidator().validate(out);
        if (outputViolations.size() > 0) {
            throw new ServiceValidationException("Could not get note", (Set) outputViolations);
        }

        return out;
    }

}
