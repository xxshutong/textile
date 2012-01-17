package org.opentaps.testsuit.notes.impl;

import javax.naming.InitialContext;

import org.opentaps.notes.domain.Note;
import org.opentaps.notes.services.*;
import org.opentaps.tests.OpentapsTestCase;
import org.opentaps.testsuit.notes.NotesTestService;


public class NotesTestServiceImpl extends OpentapsTestCase implements NotesTestService {

    /* (non-Javadoc)
     * @see org.opentaps.testsuit.notes.NotesTestService#createNote()
     */
    public void createNote() throws Exception {
        final String NOTE_TEXT  = "This is the note text";
        final String ATTR1      = "attribute 1";
        final String ATTR2      = "attribute 2";
        final String ATTR3      = "attribute 3";
        final String ATTR4      = "attribute 4";
        final String ATTR5      = "attribute 5";
        final String ATTR6      = "attribute 6";
        final String ATTR7      = "attribute 7";
        final String ATTR8      = "attribute 8";
        final String ATTR9      = "attribute 9";
        final String ATTR10     = "attribute 10";

        InitialContext ctx = new InitialContext();
        CreateNoteService createNoteSrvc = (CreateNoteService) ctx.lookup("osgi:service/org.opentaps.notes.services.CreateNoteService");
        GetNoteByIdService getNoteByIdSrvc = (GetNoteByIdService) ctx.lookupLink("osgi:service/org.opentaps.notes.services.GetNoteByIdService");
        assertNotNull("CreateNoteService should have been initialized", createNoteSrvc);
        assertNotNull("GetNoteByIdService should have been initialized", getNoteByIdSrvc);

        // create a note
        createNoteSrvc.setText(NOTE_TEXT);
        createNoteSrvc.setAttribute1(ATTR1);
        createNoteSrvc.setAttribute2(ATTR2);
        createNoteSrvc.setAttribute3(ATTR3);
        createNoteSrvc.setAttribute4(ATTR4);
        createNoteSrvc.setAttribute5(ATTR5);
        createNoteSrvc.setAttribute6(ATTR6);
        createNoteSrvc.setAttribute7(ATTR7);
        createNoteSrvc.setAttribute8(ATTR8);
        createNoteSrvc.setAttribute9(ATTR9);
        createNoteSrvc.setAttribute10(ATTR10);

        createNoteSrvc.createNote();

        String noteId = createNoteSrvc.getNoteId();

        assertNotNull("CreateNoteService should have succeeded and returned a noteId", noteId);

        // get the note
        getNoteByIdSrvc.setNoteId(noteId);
        getNoteByIdSrvc.getNoteById();
        Note note = getNoteByIdSrvc.getNote();

        assertNotNull("The created note [" + noteId + "] should have been found.", note);

        assertEquals("Note id mismatch", noteId, note.getId());
        assertEquals("Note text mismatch", NOTE_TEXT, note.getText());
        assertEquals("Attribute 1 mismatch", ATTR1, note.getAttribute1());
        assertEquals("Attribute 2 mismatch", ATTR2, note.getAttribute2());
        assertEquals("Attribute 3 mismatch", ATTR3, note.getAttribute3());
        assertEquals("Attribute 4 mismatch", ATTR4, note.getAttribute4());
        assertEquals("Attribute 5 mismatch", ATTR5, note.getAttribute5());
        assertEquals("Attribute 6 mismatch", ATTR6, note.getAttribute6());
        assertEquals("Attribute 7 mismatch", ATTR7, note.getAttribute7());
        assertEquals("Attribute 8 mismatch", ATTR8, note.getAttribute8());
        assertEquals("Attribute 9 mismatch", ATTR9, note.getAttribute9());
        assertEquals("Attribute 10 mismatch", ATTR10, note.getAttribute10());
    }

}
