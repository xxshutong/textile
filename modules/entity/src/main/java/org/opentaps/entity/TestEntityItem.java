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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@IdClass(TestEntityItemPK.class)
@Table(name="TEST_ENTITY_ITEM")
public class TestEntityItem implements Serializable {

    private static final long serialVersionUID = 3596944152636118951L;

    @Id
    @Column(name="TEST_ENTITY_ID", nullable=false, length = 32)
    private String testEntityId;

    @Id
    @Column(name="TEST_ENTITY_ITEM_SEQ_ID", nullable=false, length = 20)
    private String testEntityItemSeqId;

    @Column(name="ITEM_VALUE", length =  60)
    private String itemValue;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TEST_ENTITY_ID")
    private TestEntity testEntity;

    public TestEntityItemPK getId() {
        return new TestEntityItemPK(testEntityId, testEntityItemSeqId);
    }

    public void setId(TestEntityItemPK pk) {
        this.testEntityId = pk.getTestEntityId();
        this.testEntityItemSeqId = pk.getTestEntityItemSeqId();
    }

    public TestEntityItem() {}


    public String getTestEntityItemSeqId() {
        return this.testEntityItemSeqId;
    }

    public void setTestEntityItemSeqId(String seqId) {
        this.testEntityItemSeqId = seqId;
    }

    public String getItemValue() {
        return this.itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public TestEntity getTestEntity() {
        return this.testEntity;
    }

    public void setTestEntity(TestEntity testEntity) {
        this.testEntity = testEntity;
    }

}
