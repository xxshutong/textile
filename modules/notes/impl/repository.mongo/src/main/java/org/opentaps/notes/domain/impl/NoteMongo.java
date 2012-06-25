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
package org.opentaps.notes.domain.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.opentaps.notes.domain.Note;


@SuppressWarnings("serial")
public class NoteMongo implements Note, Serializable {

    /** Set containing the base field names of a Note. */
    public static final Set<String> FIELD_NAMES;
    static {
        FIELD_NAMES = new TreeSet<String>();
        FIELD_NAMES.add(Fields.noteId.getName());
        FIELD_NAMES.add(Fields.noteText.getName());
        FIELD_NAMES.add(Fields.createdByUserId.getName());
        FIELD_NAMES.add(Fields.userIdType.getName());
        FIELD_NAMES.add(Fields.sequenceNum.getName());
        FIELD_NAMES.add(Fields.clientDomain.getName());
        FIELD_NAMES.add(Fields.dateTimeCreated.getName());
    }

    /**
     * Checks if a given field name is one the Note base fields.
     * @param fieldName a <code>String</code> value
     * @return a <code>boolean</code>
     */
    public static boolean isBaseField(String fieldName) {
        return FIELD_NAMES.contains(fieldName);
    }

    private String noteId;

    private String noteText;

    private String createdByUserId;

    private String userIdType;

    private Long sequenceNum;

    private String clientDomain;

    private Timestamp dateTimeCreated;

    // in a NoSQL DB like the mongo implementation we can store as many custom fields as needed, on JPA implementations
    // we can only store them in the attributes fields eg: attribute1 = key, attribute2 = value, etc ...
    private Map<String, String> customFields = new HashMap<String, String>();

    public NoteMongo() {}

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getClientDomain() {
        return clientDomain;
    }

    public void setClientDomain(String clientDomain) {
        this.clientDomain = clientDomain;
    }

    public Timestamp getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Timestamp dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getUserIdType() {
        return userIdType;
    }

    public void setUserIdType(String userIdType) {
        this.userIdType = userIdType;
    }

    public Long getSequenceNum() {
        return sequenceNum;
    }

    public void setSequenceNum(Long sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    /**
     * Gets a custom field value for this note.
     * @param fieldName a <code>String</code> value
     * @return a <code>String</code> value
     */
    public String getAttribute(String fieldName) {
        if (fieldName == null) {
            throw new IllegalArgumentException("Attribute name cannot be null");
        }

        return customFields.get(fieldName);
    }

    /**
     * Sets a custom field / value pair on this Note.
     * @param fieldName a <code>String</code> value
     * @param value a <code>String</code> value
     */
    public void setAttribute(String fieldName, String value) {
        if (fieldName == null) {
            throw new IllegalArgumentException("Attribute name cannot be null");
        }

        customFields.put(fieldName, value);
    }

    /**
     * Gets the Set of all defined custom field names for this Note.
     * @return a <code>Set<String></code> value
     */
    public Set<String> getAttributeNames() {
        return new TreeSet<String>(customFields.keySet());
    }

    /**
     * Gets the custom fields for this Note.
     * @return a <code>Map<String, String></code> value
     */
    public Map<String, String> getAttributes() {
        Map<String, String> cf = new HashMap<String, String>();
        for (String field : getAttributeNames()) {
            cf.put(field, getAttribute(field));
        }
        return cf;
    }

    /**
     * Sets the custom fields for this Note.
     * @param map a <code>Map<String, String></code> value
     */
    public void setAttributes(Map<String, String> map) {
        for (String field : map.keySet()) {
            setAttribute(field, map.get(field));
        }
    }

}
