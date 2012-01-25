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
import org.opentaps.notes.entity.NoteData;
import org.opentaps.validation.services.ValidationService;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

@RunWith(JUnit4TestRunner.class)
public class ValidationTest extends NotesTestConfig {

    @Inject
    private ValidationService validationService;


    @Test
    public void testValidation() throws Exception {

        assertNotNull("ValidationService should have been initialized", validationService);

        log("ValidationTest :: testValidation : Got validator " + validationService.getValidator().getClass().getName());

        BeanDescriptor desc = validationService.getValidator().getConstraintsForClass(NoteData.class);
        assertTrue("NoteData should have validation setup", desc.isBeanConstrained());
        assertEquals("Should have 2 constraints on NoteData", desc.getConstrainedProperties().size(), 2);

        log("ValidationTest :: testValidation : Got all NoteData constraints on " + desc.getConstrainedProperties());

        NoteData note = new NoteData();
        Set<ConstraintViolation<NoteData>> constraintViolations = validationService.getValidator().validate(note);

        log("ValidationTest :: testValidation : Got note constraint violations " + constraintViolations);

        // text, and created date time cannot be null
        assertEquals("Should have 2 validation errors on an empty NoteData", constraintViolations.size(), 2);
    }
}
