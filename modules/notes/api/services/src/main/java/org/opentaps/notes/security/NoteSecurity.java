package org.opentaps.notes.security;

import org.opentaps.notes.domain.Note;

public interface NoteSecurity {

    public static enum Operation {CREATE, UPDATE, DELETE};
    
    public boolean hasPermission(Note note, Operation permissionId);
    
    public String getErrorMessage();
    
}
