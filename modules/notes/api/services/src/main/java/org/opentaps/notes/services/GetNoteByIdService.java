package org.opentaps.notes.services;

import org.opentaps.notes.domain.Note;

public interface GetNoteByIdService {

    public GetNoteByIdServiceOutput getNoteById(GetNoteByIdServiceInput input);

}
