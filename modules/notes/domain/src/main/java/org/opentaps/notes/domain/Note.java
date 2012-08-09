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

import javax.validation.constraints.NotNull;

import org.opentaps.validation.contraints.NotEmpty;
import org.osgi.service.useradmin.User;

/**
 * Represents a Note.
 */
public interface Note {

    /** Enum defining the base fields of a Note. */
    public static enum Fields {
            noteId("noteId"),
            noteText("noteText"),
            createdByUser("createdByUser"),
            sequenceNum("sequenceNum"),
            clientDomain("clientDomain"),
            dateTimeCreated("dateTimeCreated");
        private final String fieldName;
        private Fields(String name) { fieldName = name; }
        public String getName() { return fieldName; }
    }

    /**
     * Checks if a given field name is one the Note base fields.
     * @param fieldName a <code>String</code> value
     * @return a <code>boolean</code>
     */
    public boolean isBaseField(String fieldName);

    /**
     * Gets this Note ID value.
     * @return a <code>String</code> value
     */
    public String getNoteId();

    /**
     * Sets this Note ID value.
     * @param noteId a <code>String</code> value
     */
    public void setNoteId(String noteId);

    /**
     * Gets this Note text value.
     * @return a <code>String</code> value
     */
    @NotEmpty
    public String getNoteText();

    /**
     * Sets this Note text value.
     * @param noteText a <code>String</code> value
     */
    public void setNoteText(String noteText);

    /**
     * Gets this Note client domain value.
     * @return a <code>String</code> value
     */
    public String getClientDomain();

    /**
     * Sets this Note client domain value.
     * @param clientDomain a <code>String</code> value
     */
    public void setClientDomain(String clientDomain);

    /**
     * Gets this Note DateTimeCreated value.
     * @return a <code>Timestamp</code> value
     */
    @NotNull
    public Timestamp getDateTimeCreated();

    /**
     * Sets this Note DateTimeCreated value.
     * @param dateTimeCreated a <code>Timestamp</code> value
     */
    public void setDateTimeCreated(Timestamp dateTimeCreated);

    /**
     * Gets this Note created by user value.
     * @return a <code>String</code> value
     */
    public User getCreatedByUser();

    /**
     * Sets this Note created by user value.
     * @param createdByUserId a <code>String</code> value
     */
    public void setCreatedByUser(User user);

    /**
     * Gets this Note sequence number value.
     * @return a <code>String</code> value
     */
    public Long getSequenceNum();

    /**
     * Sets this Note sequence number value.
     * @param sequenceNum a <code>Long</code> value
     */
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
