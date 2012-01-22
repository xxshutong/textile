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
import java.util.Collection;
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
@Table(name = "ENUMERATION_TYPE")
public class EnumerationType implements Serializable {

    private static final long serialVersionUID = 6806002176794105978L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="uuid-type4-hex")
    @Column(name = "ENUM_TYPE_ID", nullable = false, length = 32)
    private String enumTypeId;

    @Column(name = "HAS_TABLE", length = 1)
    private String hasTable;

    private String description;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PARENT_TYPE_ID")
    private EnumerationType enumerationType;

    @OneToMany(mappedBy="enumerationType")
    private Set<EnumerationType> enumerationTypes;

    @OneToMany(mappedBy="enumerationType")
    private Set<Enumeration> enumerations;


    public EnumerationType() {}

    public String getEnumTypeId() {
        return enumTypeId;
    }

    public void setEnumTypeId(String enumTypeId) {
        this.enumTypeId = enumTypeId;
    }

    public String getHasTable() {
        return hasTable;
    }

    public void setHasTable(String hasTable) {
        this.hasTable = hasTable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EnumerationType getParentEnumerationType() {
        return this.enumerationType;
    }

    public void setParentEnumerationType(EnumerationType enumerationType) {
        this.enumerationType = enumerationType;
    }

    public Set<EnumerationType> getChildEnumerationTypes() {
        return enumerationTypes;
    }

    public Collection<Enumeration> getEnumerations() {
        return enumerations;
    }

}
