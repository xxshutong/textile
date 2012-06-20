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
import javax.validation.constraints.NotNull;

import org.opentaps.notes.domain.Note;
import org.opentaps.validation.contraints.NotEmpty;


/**
 * Represents a Note.
 * This class has all the markups for openJPA persistence, but it can also be used as POJO under another persistence context.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NOTE_DATA")
public class NoteJPA implements Note, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid-type4-hex")
    @Column(name = "NOTE_ID", nullable = false, length = 32)
    private String noteId;

    @Lob
    @NotEmpty
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

    @NotNull
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

    // in a NoSQL DB like the mongo implementation we can store as many custom fields as needed, on JPA implementations
    // we can only store them in the attributes fields eg: attribute1 = key, attribute2 = value, etc ...
    private Map<String, String> customFields = new HashMap<String, String>();

    public NoteJPA() {}

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

    private String getAttribute1() {
        return attribute1;
    }
    private void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    private String getAttribute2() {
        return attribute2;
    }
    private void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    private String getAttribute3() {
        return attribute3;
    }
    private void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    private String getAttribute4() {
        return attribute4;
    }
    private void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    private String getAttribute5() {
        return attribute5;
    }
    private void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    private String getAttribute6() {
        return attribute6;
    }
    private void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    private String getAttribute7() {
        return attribute7;
    }
    private void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    private String getAttribute8() {
        return attribute8;
    }
    private void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }

    private String getAttribute9() {
        return attribute9;
    }
    private void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }

    private String getAttribute10() {
        return attribute10;
    }
    private void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
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
            if (fieldName.equals(getAttribute1())) {
                return getAttribute2();
            }
            if (fieldName.equals(getAttribute3())) {
                return getAttribute4();
            }
            if (fieldName.equals(getAttribute5())) {
                return getAttribute6();
            }
            if (fieldName.equals(getAttribute7())) {
                return getAttribute8();
            }
            if (fieldName.equals(getAttribute9())) {
                return getAttribute10();
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
        if (fieldName.equals(getAttribute1())) {
            setAttribute2(value);
            return;
        }
        if (fieldName.equals(getAttribute3())) {
            setAttribute4(value);
            return;
        }
        if (fieldName.equals(getAttribute5())) {
            setAttribute6(value);
            return;
        }
        if (fieldName.equals(getAttribute7())) {
            setAttribute8(value);
            return;
        }
        if (fieldName.equals(getAttribute9())) {
            setAttribute10(value);
            return;
        }
        // else set on the first null pair
        if (getAttribute1() == null) {
            setAttribute1(fieldName);
            setAttribute2(value);
            return;
        }
        if (getAttribute3() == null) {
            setAttribute3(fieldName);
            setAttribute4(value);
            return;
        }
        if (getAttribute5() == null) {
            setAttribute5(fieldName);
            setAttribute6(value);
            return;
        }
        if (getAttribute7() == null) {
            setAttribute7(fieldName);
            setAttribute8(value);
            return;
        }
        if (getAttribute9() == null) {
            setAttribute9(fieldName);
            setAttribute10(value);
            return;
        }
    }

    /**
     * Gets the Set of all defined custom field names for this Note.
     * @return a <code>Set<String></code> value
     */
    public Set<String> getAttributeNames() {
        Set<String> fieldNames = new TreeSet<String>(customFields.keySet());
        if (getAttribute1() != null) {
            fieldNames.add(getAttribute1());
        }
        if (getAttribute3() != null) {
            fieldNames.add(getAttribute3());
        }
        if (getAttribute5() != null) {
            fieldNames.add(getAttribute5());
        }
        if (getAttribute7() != null) {
            fieldNames.add(getAttribute7());
        }
        if (getAttribute9() != null) {
            fieldNames.add(getAttribute9());
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
