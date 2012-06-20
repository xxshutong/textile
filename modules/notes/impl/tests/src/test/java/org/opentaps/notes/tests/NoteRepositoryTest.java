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

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.domain.NoteFactory;
import org.opentaps.notes.repository.NoteRepository;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;

@RunWith(JUnit4TestRunner.class)
public class NoteRepositoryTest extends NotesTestConfig {

    @Inject
    private NoteRepository noteRepository;

    @Inject
    private NoteFactory factory;

    @Test
    public void testRepository() throws Exception {

        assertNotNull("NoteRepository should have been initialized", noteRepository);

        log("NoteRepositoryTest :: testRepository : Got the NoteRepository OSGI service : " + noteRepository.getClass());

        Note note = factory.newInstance();
        note.setNoteText("NoteRepository test note text");
        note.setAttribute("field1", "value1");
        note.setAttribute("field2", "value2");
        note.setAttribute("field3", "value3");
        note.setAttribute("field4", "value4");
        note.setAttribute("field5", "value5");

        noteRepository.persist(note);
        String noteId = note.getNoteId();

        log("NoteRepositoryTest :: testRepository : Persisted note [" + noteId + "]");

        note = noteRepository.getNoteById(noteId);

        assertNotNull("getNoteById should have returned the note [" + noteId + "]", note);

        log("NoteRepositoryTest :: testRepository : Found note [" + noteId + "], checking values ...");

        assertEquals("note id mismatch", noteId, note.getNoteId());
        assertEquals("note text mismatch", "NoteRepository test note text", note.getNoteText());
        assertEquals("custom field 1 mismatch", "value1", note.getAttribute("field1"));
        assertEquals("custom field 2 mismatch", "value2", note.getAttribute("field2"));
        assertEquals("custom field 3 mismatch", "value3", note.getAttribute("field3"));
        assertEquals("custom field 4 mismatch", "value4", note.getAttribute("field4"));
        assertEquals("custom field 5 mismatch", "value5", note.getAttribute("field5"));
    }

    @Test
    public void testNoteSequence() throws Exception {

        assertNotNull("NoteRepository should have been initialized", noteRepository);

        log("NoteRepositoryTest :: testNoteSequence : Got the NoteRepository OSGI service : " + noteRepository.getClass());

        // create a few of notes
        int numOfNotes = 100;
        List<String> noteIds = new ArrayList<String>(numOfNotes);
        for (int i = 1; i <= numOfNotes; i++) {
            Note note = factory.newInstance();
            note.setNoteText("NoteRepository test note sequence " + i);
            noteRepository.persist(note);
            noteIds.add(note.getNoteId());
        }

        log("NoteRepositoryTest :: testRepository : Persisted notes [" + noteIds + "]");

        // retrieve notes int the order they were created and check the sequence
        Long lastSequence = -1L;
        Long firstSequence = -1L;
        for (int i = 1; i <= numOfNotes; i++) {
            String noteId = noteIds.get(i - 1);
            Note note = noteRepository.getNoteById(noteId);
            assertNotNull("getNoteById should have returned the note [" + noteId + "]", note);
            log("NoteRepositoryTest :: testRepository : Found note [" + noteId + "], checking values ...");
            assertEquals("note text mismatch", "NoteRepository test note sequence " + i, note.getNoteText());
            // test the sequencing
            if (lastSequence > 0) {
                assertEquals("note sequence incorrect, should be a continuous sequence of numbers", (Long) (lastSequence + 1L), note.getSequenceNum());
            }
            if (firstSequence < 0) {
                firstSequence = note.getSequenceNum();
            }
            assertTrue("note sequence should be a positive number", note.getSequenceNum() > 0);
            lastSequence = note.getSequenceNum();
        }

        // check the paginated retrieval
        List<Note> notes = noteRepository.getNotesPaginated(firstSequence, numOfNotes);
        assertEquals("getNotesPaginated should have retrieved the " + numOfNotes + " created before", numOfNotes, notes.size());
        for (int i = 0; i < numOfNotes; i++) {
            String noteId = noteIds.get(i);
            Note note = noteRepository.getNoteById(noteId);
            assertNotNull("getNoteById should have returned the note [" + noteId + "]", note);
            log("NoteRepositoryTest :: testRepository : Found note [" + noteId + "], checking values ...");
            assertEquals("note text mismatch", "NoteRepository test note sequence " + (i + 1), note.getNoteText());
            // test the sequencing
            assertEquals("note sequence incorrect, should be a continuous sequence of numbers", (Long) (firstSequence + i), note.getSequenceNum());
        }

        int d = 50;
        int n = numOfNotes - d;
        notes = noteRepository.getNotesPaginated(firstSequence + d, n);
        assertEquals("getNotesPaginated should have retrieved the " + n + " created before", n, notes.size());
        for (int i = d; i < numOfNotes; i++) {
            String noteId = noteIds.get(i);
            Note note = noteRepository.getNoteById(noteId);
            assertNotNull("getNoteById should have returned the note [" + noteId + "]", note);
            log("NoteRepositoryTest :: testRepository : Found note [" + noteId + "], checking values ...");
            assertEquals("note text mismatch", "NoteRepository test note sequence " + (i + 1), note.getNoteText());
            // test the sequencing
            assertEquals("note sequence incorrect, should be a continuous sequence of numbers", (Long) (firstSequence + i), note.getSequenceNum());
        }

    }
}
