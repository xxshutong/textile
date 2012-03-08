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
