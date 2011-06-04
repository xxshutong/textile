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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.opentaps.entity.model.ITestEntity;
import org.opentaps.entity.model.ITestEntityItem;


@Entity
@IdClass(TestEntityItem.TestEntityItemPK.class)
@Table(name="TEST_ENTITY_ITEM")
public class TestEntityItem implements ITestEntityItem, Serializable {

    private static final long serialVersionUID = 3596944152636118951L;

    @Id
    @Column(name="TEST_ENTITY_ID", nullable=false)
    private String testEntityId;

    @Id
    @Column(name="TEST_ENTITY_ITEM_SEQ_ID", nullable=false)
    private String testEntityItemSeqId;

    @Column(name="ITEM_VALUE")
    private String itemValue;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "TEST_ENTITY_ID", referencedColumnName = "TEST_ENTITY_ID")
    private ITestEntity testEntity;

    public static class TestEntityItemPK {

        private static final long serialVersionUID = 1L;

        public String testEntityItemSeqId;
        public String testEntityId;

        public TestEntityItemPK() {};

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((testEntityId == null) ? 0 : testEntityId.hashCode());
            result = prime
                    * result
                    + ((testEntityItemSeqId == null) ? 0 : testEntityItemSeqId
                            .hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            TestEntityItemPK other = (TestEntityItemPK) obj;
            if (testEntityId == null) {
                if (other.testEntityId != null)
                    return false;
            } else if (!testEntityId.equals(other.testEntityId))
                return false;
            if (testEntityItemSeqId == null) {
                if (other.testEntityItemSeqId != null)
                    return false;
            } else if (!testEntityItemSeqId.equals(other.testEntityItemSeqId))
                return false;
            return true;
        }

    }

    public TestEntityItem() {}

//    public String getTestEntityId() {
//        return this.testEntityId;
//    }
//
//    public void setTestEntityId(String id) {
//        this.testEntityId = id;
//    }

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

    public ITestEntity getTestEntity() {
        return this.testEntity;
    }

    public void setTestEntity(ITestEntity testEntity) {
        this.testEntity = testEntity;
    }

}
