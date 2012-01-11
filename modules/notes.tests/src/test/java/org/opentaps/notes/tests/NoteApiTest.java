package org.opentaps.notes.tests;

import org.junit.Test;

import org.opentaps.tests.OpentapsTestCase;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.services.GetNoteByIdService;

public class NoteApiTest extends OpentapsTestCase {

    private CreateNoteService createNoteService;
    private GetNoteByIdService getNoteByIdService;

    @Test
    public void testCreateNote() throws Exception {

        assertNotNull("CreateNoteService should have been initialized", createNoteService);
        assertNotNull("GetNoteByIdService should have been initialized", getNoteByIdService);

        // create a note
        createNoteService.setText("This is the note text");
        createNoteService.setAttribute1("attribute 1");
        createNoteService.setAttribute2("attribute 2");
        createNoteService.setAttribute3("attribute 3");
        createNoteService.setAttribute4("attribute 4");
        createNoteService.setAttribute5("attribute 5");
        createNoteService.setAttribute6("attribute 6");
        createNoteService.setAttribute7("attribute 7");
        createNoteService.setAttribute8("attribute 8");
        createNoteService.setAttribute9("attribute 9");
        createNoteService.setAttribute10("attribute 10");

        createNoteService.createNote();

        String noteId = createNoteService.getNoteId();

        assertNotNull("CreateNoteService should have succeeded and returned a noteId", noteId);

        // get the note
        getNoteByIdService.setNoteId(noteId);
        getNoteByIdService.getNoteById();
        Note note = getNoteByIdService.getNote();

        assertNotNull("The created note [" + noteId + "] should have been found.", note);
    }
}
