package org.opentaps.notes.security.impl2;

import org.opentaps.notes.domain.Note;
import org.opentaps.notes.security.NoteSecurity;
import org.opentaps.notes.security.NoteSecurity.Operation;
import org.opentaps.core.log.Log;

public class NoteSecurityImpl implements NoteSecurity {

    private String errorMessage = null;
    
    @Override
    public boolean hasPermission(Note note, Operation permissionId) {
        Log.logInfo("This is the alternate security implementation.  I will not let anonymous users do anything.");
        if (note.getCreatedByUserId() == null) {
            errorMessage = "Sorry, but anonymous users are not allowed.";
            return false;
        } else {
            Log.logInfo("I hereby give permission to user [" + note.getCreatedByUserId() + "] from [" + note.getUserIdType() + "].");
            return true;    
        }
        
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
