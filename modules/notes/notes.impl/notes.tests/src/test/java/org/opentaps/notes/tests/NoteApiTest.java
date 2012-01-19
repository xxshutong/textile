package org.opentaps.notes.tests;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.services.GetNoteByIdService;
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


        log("NoteApiTest :: testCreateNote : Creating note ...");

        createNoteService.createNote();

        String noteId = createNoteService.getNoteId();

        assertNotNull("CreateNoteService should have succeeded and returned a noteId", noteId);

        log("NoteApiTest :: testCreateNote : Note [" + noteId + "] was created.");

        // get the note
        getNoteByIdService.setNoteId(noteId);
        getNoteByIdService.getNoteById();
        Note note = getNoteByIdService.getNote();

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
    }
}
