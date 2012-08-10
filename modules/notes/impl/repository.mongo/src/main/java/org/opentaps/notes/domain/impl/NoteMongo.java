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

import org.apache.commons.validator.GenericValidator;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.security.NoteUser;
import org.osgi.service.useradmin.User;


/**
 * Represents a Note in MongoDB.
 */
@SuppressWarnings("serial")
public class NoteMongo implements Note, Serializable {

    /** All Mongo objects have an _id field as the PK. */
    public static final String MONGO_ID_FIELD = "_id";

    /** Set containing the base field names of a Note. */
    public static final Set<String> FIELD_NAMES = new TreeSet<String>();;
    static {
        for (Fields field : Fields.values()) {
            FIELD_NAMES.add(field.getName());
        }
        FIELD_NAMES.add(MONGO_ID_FIELD);
    }

    /** {@inheritDoc} */
    public boolean isBaseField(String fieldName) {
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

    /**
     * Default constructor.
     */
    public NoteMongo() { }

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
    public String getUserIdType() {
        return userIdType;
    }

    /** {@inheritDoc} */
    public void setUserIdType(String userIdType) {
        this.userIdType = userIdType;
    }

    /** {@inheritDoc} */
    public Long getSequenceNum() {
        return sequenceNum;
    }

    /** {@inheritDoc} */
    public void setSequenceNum(Long sequenceNum) {
        this.sequenceNum = sequenceNum;
    }

    /** {@inheritDoc} */
    public String getAttribute(String fieldName) {
        if (fieldName == null) {
            throw new IllegalArgumentException("Attribute name cannot be null");
        }

        return customFields.get(fieldName);
    }

    /** {@inheritDoc} */
    public void setAttribute(String fieldName, String value) {
        if (fieldName == null) {
            throw new IllegalArgumentException("Attribute name cannot be null");
        }

        customFields.put(fieldName, value);
    }

    /** {@inheritDoc} */
    public Set<String> getAttributeNames() {
        return new TreeSet<String>(customFields.keySet());
    }

    /** {@inheritDoc} */
    public Map<String, String> getAttributes() {
        Map<String, String> cf = new HashMap<String, String>();
        for (String field : getAttributeNames()) {
            cf.put(field, getAttribute(field));
        }
        return cf;
    }

    /** {@inheritDoc} */
    public void setAttributes(Map<String, String> map) {
        for (String field : map.keySet()) {
            setAttribute(field, map.get(field));
        }
    }

}
