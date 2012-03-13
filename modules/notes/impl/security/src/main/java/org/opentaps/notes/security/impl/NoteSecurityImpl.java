package org.opentaps.notes.security.impl;

import org.opentaps.core.log.Log;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.security.NoteSecurity;

public class NoteSecurityImpl implements NoteSecurity {

    private String errorMessage = null;
    
    @Override
    public boolean hasPermission(Note note, Operation permissionId) {
        Log.logInfo("This test implementation will always return true, so I will let you create your note.");
        return true;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
