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
package org.opentaps.notes.repository;

import java.util.List;
import org.opentaps.notes.domain.Note;

public interface NoteRepository {

    /**
     * Gets the Note for the given ID.
     * @param noteId a <code>String</code> value
     * @return a <code>Note</code> value
     */
    public Note getNoteById(String noteId);

    /**
     * Gets a paginated list of Notes.
     * @param fromSequence the starting sequence <code>Long</code> value
     * @param numberOfNotes the maximum number of notes to return, an <code>Integer</code> value
     * @return the list of <code>Note</code> starting at the given fromSequence
     */
    public List<Note> getNotesPaginated(Long fromSequence, Integer numberOfNotes);

    /**
     * Gets a paginated list of Notes.
     * @param fromSequence the starting sequence <code>Long</code> value
     * @param numberOfNotes the maximum number of notes to return, an <code>Integer</code> value
     * @param order an <code>integer</code> to indicate the order in which records are returned, null or positive for ascendnig sequence, negative for descending
     * @return the list of <code>Note</code> starting at the given fromSequence
     */
    public List<Note> getNotesPaginated(Long fromSequence, Integer numberOfNotes, Integer order);

    /**
     * Persists a Note.
     * @param note a <code>Note</code> value
     */
    public void persist(Note note);

    /**
     * Persists a list of Note.
     * @param notes a <code>List<Note></code> value
     */
    public void persist(List<Note> notes);

}
