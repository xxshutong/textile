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
        createNoteInput.setAttribute("field1", "value1");
        createNoteInput.setAttribute("field2", "value2");
        createNoteInput.setAttribute("field3", "value3");
        createNoteInput.setAttribute("field4", "value4");
        createNoteInput.setAttribute("field5", "value5");


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

        assertEquals("note id mismatch", noteId, note.getNoteId());
        assertEquals("note text mismatch", "This is the note text", note.getNoteText());
        assertEquals("custom field 1 mismatch", "value1", note.getAttribute("field1"));
        assertEquals("custom field 2 mismatch", "value2", note.getAttribute("field2"));
        assertEquals("custom field 3 mismatch", "value3", note.getAttribute("field3"));
        assertEquals("custom field 4 mismatch", "value4", note.getAttribute("field4"));
        assertEquals("custom field 5 mismatch", "value5", note.getAttribute("field5"));

        // create a second note
        createNoteInput = new CreateNoteServiceInput();
        createNoteInput.setText("This is another note text");
        createNoteInput.setAttribute("field1", "value1");
        createNoteInput.setAttribute("field2", "value2");

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

        assertEquals("note id mismatch", noteId, note.getNoteId());
        assertEquals("note text mismatch", "This is another note text", note.getNoteText());
        assertEquals("custom field 1 mismatch", "value1", note.getAttribute("field1"));
        assertEquals("custom field 2 mismatch", "value2", note.getAttribute("field2"));
        assertEquals("custom field 3 mismatch", null, note.getAttribute("field3"));
        assertEquals("custom field 4 mismatch", null, note.getAttribute("field4"));
        assertEquals("custom field 5 mismatch", null, note.getAttribute("field5"));
    }

    @Test
    public void testCreateNoteSequence() throws Exception {
        log("NoteApiTest :: testCreateNoteSequence : Create 100 notes in sequence");

        assertNotNull("CreateNoteService should have been initialized", createNoteService);
        assertNotNull("GetNoteByIdService should have been initialized", getNoteByIdService);

        Long previousSequenceNum = 0L;

        for (int i = 1; i <= 100; i++) {
            CreateNoteServiceInput createNoteInput = new CreateNoteServiceInput();
            createNoteInput.setText("Test note sequence " + i);
            createNoteInput.setAttribute("field1", "value1");
            createNoteInput.setAttribute("field2", "value2");
            createNoteInput.setAttribute("field3", "value3");
            createNoteInput.setAttribute("field4", "value4");
            createNoteInput.setAttribute("field5", "value5");

            log("NoteApiTest :: testCreateNoteSequence : Creating note : Iteration : " + i);

            String noteId = createNoteService.createNote(createNoteInput).getNoteId();

            assertNotNull("CreateNoteService should have succeeded and returned a noteId", noteId);

            log("NoteApiTest :: testCreateNoteSequence : Note [" + noteId + "] was created.");

            // get the note
            GetNoteByIdServiceInput getNoteByIdServiceInput = new GetNoteByIdServiceInput();
            getNoteByIdServiceInput.setNoteId(noteId);
            Note note = getNoteByIdService.getNoteById(getNoteByIdServiceInput).getNote();

            assertNotNull("The created note [" + noteId + "] should have been found.", note);

            log("NoteApiTest :: testCreateNoteSequence : Found Note [" + noteId + "], checking sequence Number ...");

            assertEquals("note id mismatch", noteId, note.getNoteId());
            Long sequenceNum = note.getSequenceNum();
            log("NoteApiTest :: testCreateNoteSequence : Current sequence number [" + sequenceNum + "], previous sequence number [" + previousSequenceNum + "]");

            assertTrue("Current sequence number [" + sequenceNum + "] should be > then previous [" + previousSequenceNum + "]", sequenceNum > previousSequenceNum);
            previousSequenceNum = sequenceNum;
        }
    }
}
