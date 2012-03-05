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
package org.opentaps.notes.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.opentaps.validation.contraints.NotEmpty;

/**
 * The persisted NoteData entity.
 */
@Entity
@Table(name="NOTE_DATA")
public class NoteData implements Serializable {

    private static final long serialVersionUID = -4314958909722739985L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="uuid-type4-hex")
    @Column(name = "NOTE_ID", nullable = false, length = 32)
    private String noteId;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    @NotEmpty
    @Column(name = "NOTE_TEXT", nullable = false)
    private String noteText;

    @Column(name = "CREATED_BY_USER_ID")
    private String createdByUserId;

    @Column(name = "USER_ID_TYPE")
    private String userIdType;

    @GeneratedValue(strategy=GenerationType.AUTO)
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

    public NoteData() {}

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

    public String getAttribute1() {
        return attribute1;
    }
    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getAttribute2() {
        return attribute2;
    }
    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }
    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }
    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }
    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getAttribute6() {
        return attribute6;
    }
    public void setAttribute6(String attribute6) {
        this.attribute6 = attribute6;
    }

    public String getAttribute7() {
        return attribute7;
    }
    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    public String getAttribute8() {
        return attribute8;
    }
    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }

    public String getAttribute9() {
        return attribute9;
    }
    public void setAttribute9(String attribute9) {
        this.attribute9 = attribute9;
    }

    public String getAttribute10() {
        return attribute10;
    }
    public void setAttribute10(String attribute10) {
        this.attribute10 = attribute10;
    }
}
