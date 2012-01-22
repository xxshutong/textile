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
package org.opentaps.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "ENUMERATION")
public class Enumeration implements Serializable {

    private static final long serialVersionUID = -4314958909722739986L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="uuid-type4-hex")
    @Column(name = "ENUM_ID", nullable = false, length = 32)
    private String enumId;

    @Column(name = "ENUM_TYPE_ID", nullable = false, length = 20)
    private String enumTypeId;

    @Column(name = "ENUM_CODE")
    private String enumCode;

    @Column(name = "SEQUENCE_ID")
    private String sequenceId;

    private String description;

    @Column(length = 1)
    private String disabled;

    @Column(name = "PARENT_ENUM_ID")
    private String parentEnumId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent_enum_id")
    private Enumeration enumeration;

    @OneToMany(fetch=FetchType.LAZY)
    private Set<Enumeration> enumerations;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="enum_type_id")
    private EnumerationType enumerationType;

    public Enumeration() {}

    public String getEnumId() {
        return enumId;
    }

    public void setEnumId(String enumId) {
        this.enumId = enumId;
    }

    public String getEnumTypeId() {
        return enumTypeId;
    }

    public void setEnumTypeId(String enumTypeId) {
        this.enumTypeId = enumTypeId;
    }

    public String getEnumCode() {
        return enumCode;
    }

    public void setEnumCode(String enumCode) {
        this.enumCode = enumCode;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getParentEnumId() {
        return parentEnumId;
    }

    public void setParentEnumId(String parentEnumId) {
        this.parentEnumId = parentEnumId;
    }

    public Enumeration getParentEnumeration() {
        return enumeration;
    }

    public void setParentEnumeration(Enumeration enumeration) {
        this.enumeration = enumeration;
    }

    public EnumerationType getEnumerationType() {
        return enumerationType;
    }

    public void setEnumerationType(EnumerationType enumerationType) {
        this.enumerationType = enumerationType;
    }

    public Set<Enumeration> getChildEnumerations() {
        return enumerations;
    }
}
