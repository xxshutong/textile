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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.validator.GenericValidator;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.security.NoteUser;
import org.osgi.service.useradmin.User;


/**
 * Represents a Note with openJPA persistence.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NOTE_DATA")
public class NoteJPA implements Note, Serializable {

    /** Set containing the base field names of a Note. */
    public static final Set<String> FIELD_NAMES = new TreeSet<String>();;
    static {
        for (Fields field : Fields.values()) {
            FIELD_NAMES.add(field.getName());
        }
    }

    /** {@inheritDoc} */
    public boolean isBaseField(String fieldName) {
        return FIELD_NAMES.contains(fieldName);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid-type4-hex")
    @Column(name = "NOTE_ID", nullable = false, length = 32)
    private String noteId;

    @Lob
    @Column(name = "NOTE_TEXT", nullable = false)
    private String noteText;

    @Column(name = "CREATED_BY_USER_ID")
    private String createdByUserId;

    @Column(name = "USER_ID_TYPE")
    private String userIdType;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SEQUENCE_NUM", nullable = false)
    private Long sequenceNum;

    @Column(name = "CLIENT_DOMAIN")
    private String clientDomain;

    @Column(name = "DATE_TIME_CREATED")
    private Timestamp dateTimeCreated;

    @Column(name = "ATTRIBUTE_1")
    private String attribute1;
    @Column(name = "ATTRIBUTE_2")
    private String attribute2;
    @Column(name = "ATTRIBUTE_3")
    private String attribute3;
    @Column(name = "ATTRIBUTE_4")
    private String attribute4;
    @Column(name = "ATTRIBUTE_5")
    private String attribute5;
    @Column(name = "ATTRIBUTE_6")
    private String attribute6;
    @Column(name = "ATTRIBUTE_7")
    private String attribute7;
    @Column(name = "ATTRIBUTE_8")
    private String attribute8;
    @Column(name = "ATTRIBUTE_9")
    private String attribute9;
    @Column(name = "ATTRIBUTE_10")
    private String attribute10;

    @Column(name = "VALUE_1")
    private String value1;
    @Column(name = "VALUE_2")
    private String value2;
    @Column(name = "VALUE_3")
    private String value3;
    @Column(name = "VALUE_4")
    private String value4;
    @Column(name = "VALUE_5")
    private String value5;
    @Column(name = "VALUE_6")
    private String value6;
    @Column(name = "VALUE_7")
    private String value7;
    @Column(name = "VALUE_8")
    private String value8;
    @Column(name = "VALUE_9")
    private String value9;
    @Column(name = "VALUE_10")
    private String value10;

    // in a NoSQL DB like the mongo implementation we can store as many custom fields as needed, on JPA implementations
    // we can only store 10 such attribute -> value pairs
    @Transient
    private Map<String, String> customFields = new HashMap<String, String>();

    /**
     * Default constructor.
     */
    public NoteJPA() { }

    /** {@inheritDoc} */
    public String getNoteId() {
        return noteId;
    }

    /** {@inheritDoc} */
    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    /** {@inheritDoc} */
    public String getNoteText() {
        return noteText;
    }

    /** {@inheritDoc} */
    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    /** {@inheritDoc} */
    public String getClientDomain() {
        return clientDomain;
    }

    /** {@inheritDoc} */
    public void setClientDomain(String clientDomain) {
        this.clientDomain = clientDomain;
    }

    /** {@inheritDoc} */
    public Timestamp getDateTimeCreated() {
        return dateTimeCreated;
    }

    /** {@inheritDoc} */
    public void setDateTimeCreated(Timestamp dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    /** {@inheritDoc} */
    public User getCreatedByUser() {
        return !GenericValidator.isBlankOrNull(createdByUserId) ? new NoteUser(createdByUserId, userIdType) : null;
    }

    /** {@inheritDoc} */
    public void setCreatedByUser(User createdByUser) {
        if (createdByUser != null) {
            this.createdByUserId = ((NoteUser) createdByUser).getUserId();
            this.userIdType = ((NoteUser) createdByUser).getUserType();
        }
    }

    /** {@inheritDoc} */
    public Long getSequenceNum() {
        return sequenceNum;
    }

    /** {@inheritDoc} */
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

        if (customFields.containsKey(fieldName)) {
            return customFields.get(fieldName);
        } else {
            // lookup the attributes
            if (fieldName.equals(attribute1)) {
                return value1;
            }
            if (fieldName.equals(attribute2)) {
                return value2;
            }
            if (fieldName.equals(attribute3)) {
                return value3;
            }
            if (fieldName.equals(attribute4)) {
                return value4;
            }
            if (fieldName.equals(attribute5)) {
                return value5;
            }
            if (fieldName.equals(attribute6)) {
                return value6;
            }
            if (fieldName.equals(attribute7)) {
                return value7;
            }
            if (fieldName.equals(attribute8)) {
                return value8;
            }
            if (fieldName.equals(attribute9)) {
                return value9;
            }
            if (fieldName.equals(attribute10)) {
                return value10;
            }
        }

        return null;
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
        // lookup if the field is defined in the attributes
        if (fieldName.equals(attribute1)) {
            value1 = value;
            return;
        }
        if (fieldName.equals(attribute2)) {
            value2 = value;
            return;
        }
        if (fieldName.equals(attribute3)) {
            value3 = value;
            return;
        }
        if (fieldName.equals(attribute4)) {
            value4 = value;
            return;
        }
        if (fieldName.equals(attribute5)) {
            value5 = value;
            return;
        }
        if (fieldName.equals(attribute6)) {
            value6 = value;
            return;
        }
        if (fieldName.equals(attribute7)) {
            value7 = value;
            return;
        }
        if (fieldName.equals(attribute8)) {
            value8 = value;
            return;
        }
        if (fieldName.equals(attribute9)) {
            value9 = value;
            return;
        }
        if (fieldName.equals(attribute10)) {
            value10 = value;
            return;
        }
        // else set on the first null pair
        if (attribute1 == null) {
            attribute1 = fieldName;
            value1 = value;
            return;
        }
        if (attribute2 == null) {
            attribute2 = fieldName;
            value2 = value;
            return;
        }
        if (attribute3 == null) {
            attribute3 = fieldName;
            value3 = value;
            return;
        }
        if (attribute4 == null) {
            attribute4 = fieldName;
            value4 = value;
            return;
        }
        if (attribute5 == null) {
            attribute5 = fieldName;
            value5 = value;
            return;
        }
        if (attribute6 == null) {
            attribute6 = fieldName;
            value6 = value;
            return;
        }
        if (attribute7 == null) {
            attribute7 = fieldName;
            value7 = value;
            return;
        }
        if (attribute8 == null) {
            attribute8 = fieldName;
            value8 = value;
            return;
        }
        if (attribute9 == null) {
            attribute9 = fieldName;
            value9 = value;
            return;
        }
        if (attribute10 == null) {
            attribute10 = fieldName;
            value10 = value;
            return;
        }
    }

    /**
     * Gets the Set of all defined custom field names for this Note.
     * @return a <code>Set<String></code> value
     */
    public Set<String> getAttributeNames() {
        Set<String> fieldNames = new TreeSet<String>(customFields.keySet());
        if (attribute1 != null) {
            fieldNames.add(attribute1);
        }
        if (attribute2 != null) {
            fieldNames.add(attribute2);
        }
        if (attribute3 != null) {
            fieldNames.add(attribute3);
        }
        if (attribute4 != null) {
            fieldNames.add(attribute4);
        }
        if (attribute5 != null) {
            fieldNames.add(attribute5);
        }
        if (attribute6 != null) {
            fieldNames.add(attribute6);
        }
        if (attribute7 != null) {
            fieldNames.add(attribute7);
        }
        if (attribute8 != null) {
            fieldNames.add(attribute8);
        }
        if (attribute9 != null) {
            fieldNames.add(attribute9);
        }
        if (attribute10 != null) {
            fieldNames.add(attribute10);
        }
        return fieldNames;
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
