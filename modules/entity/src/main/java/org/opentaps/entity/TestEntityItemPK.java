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
import javax.persistence.*;


/**
 * The primary key class for the <b>TEST_ENTITY_ITEM</b> database table.
 */
@Embeddable
public class TestEntityItemPK implements Serializable {

    private static final long serialVersionUID = -7546415545483689079L;

    @Column(name="test_entity_id", nullable = false)
    private String testEntityId;

    @Column(name="test_entity_item_seq_id", nullable = false)
    private String testEntityItemSeqId;

    public TestEntityItemPK() {}

    public String getTestEntityId() {
        return this.testEntityId;
    }

    public void setTestEntityId(String testEntityId) {
        this.testEntityId = testEntityId;
    }

    public String getTestEntityItemSeqId() {
        return this.testEntityItemSeqId;
    }

    public void setTestEntityItemSeqId(String testEntityItemSeqId) {
        this.testEntityItemSeqId = testEntityItemSeqId;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TestEntityItemPK)) {
            return false;
        }
        TestEntityItemPK castOther = (TestEntityItemPK)other;
        return this.testEntityId.equals(castOther.testEntityId) && this.testEntityItemSeqId.equals(castOther.testEntityItemSeqId);

    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.testEntityId.hashCode();
        hash = hash * prime + this.testEntityItemSeqId.hashCode();

        return hash;
    }
}