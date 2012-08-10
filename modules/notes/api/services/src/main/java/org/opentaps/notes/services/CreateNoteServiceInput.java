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
package org.opentaps.notes.services;

import java.util.HashMap;
import java.util.Map;

import org.opentaps.core.service.ServiceInput;
import org.opentaps.notes.security.NoteUser;
import org.opentaps.validation.contraints.NotEmpty;

public class CreateNoteServiceInput implements ServiceInput {

    @NotEmpty
    private String text;
    private Map<String, String> customFields = new HashMap<String, String>();
    private String clientDomain;
    private NoteUser user;

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public NoteUser getCreatedByUser() { return user; };
    public void setCreatedByUser(NoteUser user) { this.user = user; }
    public String getClientDomain() { return clientDomain; }
    public void setClientDomain(String clientDomain) { this.clientDomain = clientDomain; }
    public String getAttribute(String fieldName) { return customFields.get(fieldName); }
    public void setAttribute(String fieldName, String value) { this.customFields.put(fieldName, value); }
    public Map<String, String> getAttributes() { return new HashMap<String, String>(customFields); }
    public void setAttributes(Map<String, String> fields) { 
        for (String field : fields.keySet()) {
            setAttribute(field, fields.get(field));
        }
    }
}
