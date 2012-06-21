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
package org.opentaps.notes.tests;


import java.util.Set;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.metadata.BeanDescriptor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.domain.NoteFactory;
import org.opentaps.validation.services.ValidationService;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

@RunWith(JUnit4TestRunner.class)
public class ValidationTest extends NotesTestConfig {

    @Inject
    private ValidationService validationService;

    @Inject
    private NoteFactory factory;

    @Test
    public void testValidation() throws Exception {

        assertNotNull("ValidationService should have been initialized", validationService);

        log("ValidationTest :: testValidation : Got validator " + validationService.getValidator().getClass().getName());

        BeanDescriptor desc = validationService.getValidator().getConstraintsForClass(Note.class);
        assertTrue("Note should have validation setup", desc.isBeanConstrained());
        assertEquals("Should have 2 constraints on Note", desc.getConstrainedProperties().size(), 2);

        log("ValidationTest :: testValidation : Got all Note constraints on " + desc.getConstrainedProperties());

        Note note = factory.newInstance();
        Set<ConstraintViolation<Note>> constraintViolations = validationService.getValidator().validate(note);

        log("ValidationTest :: testValidation : Got note constraint violations " + constraintViolations);

        // text, and created date time cannot be null
        assertEquals("Should have 2 validation errors on an empty NoteData", constraintViolations.size(), 2);

        // check for empty text
        note.setNoteText("");
        constraintViolations = validationService.getValidator().validate(note);
        log("ValidationTest :: testValidation : Got note constraint violations " + constraintViolations);
        assertEquals("Should have 2 validation errors on an empty NoteData with empty text", constraintViolations.size(), 2);

        // check for blank text (having whitespace)
        note.setNoteText("  ");
        constraintViolations = validationService.getValidator().validate(note);
        log("ValidationTest :: testValidation : Got note constraint violations " + constraintViolations);
        assertEquals("Should have 2 validation errors on an empty NoteData with white space only text", constraintViolations.size(), 2);
    }
}
