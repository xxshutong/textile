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
package org.opentaps.notes.domain;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;


/**
 * Represents a Note.
 */
public interface Note {

    /** Enum defining the base fields of a Note. */
    public static enum Fields {
            noteId("noteId"),
            noteText("noteText"),
            createdByUserId("createdByUserId"),
            userIdType("userIdType"),
            sequenceNum("sequenceNum"),
            clientDomain("clientDomain"),
            dateTimeCreated("dateTimeCreated");
        private final String fieldName;
        private Fields(String name) { fieldName = name; }
        public String getName() { return fieldName; }
    }

    public String getNoteId();

    public void setNoteId(String noteId);

    public String getNoteText();

    public void setNoteText(String noteText);

    public String getClientDomain();

    public void setClientDomain(String clientDomain);

    public Timestamp getDateTimeCreated();

    public void setDateTimeCreated(Timestamp dateTimeCreated);

    public String getCreatedByUserId();

    public void setCreatedByUserId(String createdByUserId);

    public String getUserIdType();

    public void setUserIdType(String userIdType);

    public Long getSequenceNum();

    public void setSequenceNum(Long sequenceNum);

    /**
     * Gets a custom field value for this note.
     * @param fieldName a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String getAttribute(String fieldName);

    /**
     * Sets a custom field / value pair on this Note.
     * @param fieldName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void setAttribute(String fieldName, String value);

    /**
     * Gets the Set of all defined custom field names for this Note.
     * @return a <code>Set<String></code> value
     */
    public Set<String> getAttributeNames();

    /**
     * Gets the custom fields for this Note.
     * @return a <code>Map<String, String></code> value
     */
    public Map<String, String> getAttributes();

    /**
     * Sets the custom fields for this Note.
     * @param map a <code>Map<String, String></code> value
     */
    public void setAttributes(Map<String, String> map);

}
