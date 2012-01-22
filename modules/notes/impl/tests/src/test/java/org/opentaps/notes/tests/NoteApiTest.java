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

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.services.CreateNoteServiceInput;
import org.opentaps.notes.services.GetNoteByIdService;
import org.opentaps.notes.services.GetNoteByIdServiceInput;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

@RunWith(JUnit4TestRunner.class)
public class NoteApiTest extends NotesTestConfig {

    @Inject
    private CreateNoteService createNoteService;
    @Inject
    private GetNoteByIdService getNoteByIdService;

    @Test
    public void testCreateNote() throws Exception {

        assertNotNull("CreateNoteService should have been initialized", createNoteService);
        assertNotNull("GetNoteByIdService should have been initialized", getNoteByIdService);

        log("NoteApiTest :: testCreateNote : Got all the notes OSGI services");

        // create a note
        CreateNoteServiceInput createNoteInput = new CreateNoteServiceInput();
        createNoteInput.setText("This is the note text");
        createNoteInput.setAttribute1("attribute 1");
        createNoteInput.setAttribute2("attribute 2");
        createNoteInput.setAttribute3("attribute 3");
        createNoteInput.setAttribute4("attribute 4");
        createNoteInput.setAttribute5("attribute 5");
        createNoteInput.setAttribute6("attribute 6");
        createNoteInput.setAttribute7("attribute 7");
        createNoteInput.setAttribute8("attribute 8");
        createNoteInput.setAttribute9("attribute 9");
        createNoteInput.setAttribute10("attribute 10");


        log("NoteApiTest :: testCreateNote : Creating note ...");

        String noteId = createNoteService.createNote(createNoteInput).getNoteId();

        assertNotNull("CreateNoteService should have succeeded and returned a noteId", noteId);

        log("NoteApiTest :: testCreateNote : Note [" + noteId + "] was created.");

        // get the note
        GetNoteByIdServiceInput getNoteByIdServiceInput = new GetNoteByIdServiceInput();
        getNoteByIdServiceInput.setNoteId(noteId);
        Note note = getNoteByIdService.getNoteById(getNoteByIdServiceInput).getNote();

        assertNotNull("The created note [" + noteId + "] should have been found.", note);

        log("NoteApiTest :: testCreateNote : Found Note [" + noteId + "], checking values ...");

        assertEquals("note id mismatch", noteId, note.getId());
        assertEquals("note text mismatch", "This is the note text", note.getText());
        assertEquals("attribute 1 mismatch", "attribute 1", note.getAttribute1());
        assertEquals("attribute 2 mismatch", "attribute 2", note.getAttribute2());
        assertEquals("attribute 3 mismatch", "attribute 3", note.getAttribute3());
        assertEquals("attribute 4 mismatch", "attribute 4", note.getAttribute4());
        assertEquals("attribute 5 mismatch", "attribute 5", note.getAttribute5());
        assertEquals("attribute 6 mismatch", "attribute 6", note.getAttribute6());
        assertEquals("attribute 7 mismatch", "attribute 7", note.getAttribute7());
        assertEquals("attribute 8 mismatch", "attribute 8", note.getAttribute8());
        assertEquals("attribute 9 mismatch", "attribute 9", note.getAttribute9());
        assertEquals("attribute 10 mismatch", "attribute 10", note.getAttribute10());

        // create a second note
        createNoteInput = new CreateNoteServiceInput();
        createNoteInput.setText("This is another note text");
        createNoteInput.setAttribute1("attribute 1");
        createNoteInput.setAttribute3("attribute 3");
        createNoteInput.setAttribute5("attribute 5");
        createNoteInput.setAttribute7("attribute 7");
        createNoteInput.setAttribute9("attribute 9");

        log("NoteApiTest :: testCreateNote : Creating note 2 ...");

        noteId = createNoteService.createNote(createNoteInput).getNoteId();

        assertNotNull("CreateNoteService should have succeeded and returned a noteId", noteId);

        log("NoteApiTest :: testCreateNote : Note [" + noteId + "] was created.");

        // get the note
        getNoteByIdServiceInput = new GetNoteByIdServiceInput();
        getNoteByIdServiceInput.setNoteId(noteId);
        note = getNoteByIdService.getNoteById(getNoteByIdServiceInput).getNote();

        assertNotNull("The created note [" + noteId + "] should have been found.", note);

        log("NoteApiTest :: testCreateNote : Found Note [" + noteId + "], checking values ...");

        assertEquals("note id mismatch", noteId, note.getId());
        assertEquals("note text mismatch", "This is another note text", note.getText());
        assertEquals("attribute 1 mismatch", "attribute 1", note.getAttribute1());
        assertEquals("attribute 2 mismatch", null, note.getAttribute2());
        assertEquals("attribute 3 mismatch", "attribute 3", note.getAttribute3());
        assertEquals("attribute 4 mismatch", null, note.getAttribute4());
        assertEquals("attribute 5 mismatch", "attribute 5", note.getAttribute5());
        assertEquals("attribute 6 mismatch", null, note.getAttribute6());
        assertEquals("attribute 7 mismatch", "attribute 7", note.getAttribute7());
        assertEquals("attribute 8 mismatch", null, note.getAttribute8());
        assertEquals("attribute 9 mismatch", "attribute 9", note.getAttribute9());
        assertEquals("attribute 10 mismatch", null, note.getAttribute10());
    }
}
