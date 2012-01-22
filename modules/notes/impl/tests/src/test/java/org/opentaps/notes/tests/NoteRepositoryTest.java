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
import org.opentaps.notes.repository.NoteRepository;
import org.opentaps.notes.domain.Note;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

@RunWith(JUnit4TestRunner.class)
public class NoteRepositoryTest extends NotesTestConfig {

    @Inject
    private NoteRepository noteRepository;

    @Test
    public void testRepository() throws Exception {

        assertNotNull("NoteRepository should have been initialized", noteRepository);

        log("NoteRepositoryTest :: testRepository : Got the NoteRepository OSGI service : " + noteRepository.getClass());

        Note note = new Note();
        note.setText("NoteRepository test note text");
        note.setAttribute1("attribute 1");
        note.setAttribute2("attribute 2");
        note.setAttribute3("attribute 3");
        note.setAttribute4("attribute 4");
        note.setAttribute5("attribute 5");
        note.setAttribute6("attribute 6");
        note.setAttribute7("attribute 7");
        note.setAttribute8("attribute 8");
        note.setAttribute9("attribute 9");
        note.setAttribute10("attribute 10");

        noteRepository.persist(note);
        String noteId = note.getId();

        log("NoteRepositoryTest :: testRepository : Persisted note [" + noteId + "]");

        note = noteRepository.getNoteById(noteId);

        assertNotNull("getNoteById should have returned the note [" + noteId + "]", note);

        log("NoteRepositoryTest :: testRepository : Found note [" + noteId + "], checking values ...");

        assertEquals("note id mismatch", noteId, note.getId());
        assertEquals("note text mismatch", "NoteRepository test note text", note.getText());
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
