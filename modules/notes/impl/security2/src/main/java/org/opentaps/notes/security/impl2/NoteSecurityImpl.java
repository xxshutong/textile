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
package org.opentaps.notes.security.impl2;

import org.opentaps.core.log.Log;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.security.NoteSecurity;
import org.opentaps.notes.security.NoteUser;

public class NoteSecurityImpl implements NoteSecurity {

    private String errorMessage = null;
    
    @Override
    public boolean hasPermission(Note note, Operation permissionId) {
        Log.logInfo("This is the alternate security implementation.  I will not let anonymous users do anything.");
        if (note.getCreatedByUser() == null) {
            errorMessage = "Sorry, but anonymous users are not allowed.";
            return false;
        } else {
            NoteUser user = (NoteUser) note.getCreatedByUser();
            Log.logInfo("I hereby give permission to user [" + user.getName() + "] from [" + user.getUserType() + "].");
            return true;    
        }
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
