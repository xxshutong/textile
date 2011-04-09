/*
 * Copyright (c) 2011 Open Source Strategies, Inc.
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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.opentaps.entity.model.ITestEntity;
import org.opentaps.entity.model.ITestEntityItem;
import org.opentaps.entity.model.ITestEntityItemPK;


@Entity
@Table(name="TEST_ENTITY_ITEM")
public class TestEntityItem implements ITestEntityItem, Serializable {

    private static final long serialVersionUID = 3596944152636118951L;

    @EmbeddedId
    private ITestEntityItemPK id;

    @Column(name="ITEM_VALUE")
    private String itemValue;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TEST_ENTITY_ID", referencedColumnName = "TEST_ENTITY_ID")
    private ITestEntity testEntity;

    public TestEntityItem() {}

    public ITestEntityItemPK getId() {
        return this.id;
    }

    public void setId(ITestEntityItemPK id) {
        this.id = id;
    }
    
    public String getItemValue() {
        return this.itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public ITestEntity getTestEntity() {
        return this.testEntity;
    }

    public void setTestEntity(ITestEntity testEntity) {
        this.testEntity = testEntity;
    }

}
