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

public class TestEntityItemPK implements Serializable {

    private static final long serialVersionUID = 3352769862107536974L;

    public String testEntityItemSeqId;
    public String testEntityId;

    public TestEntityItemPK() {};

    public TestEntityItemPK(String testEntityId, String testEntityItemSeqId) {
        this.testEntityId = testEntityId;
        this.testEntityItemSeqId = testEntityItemSeqId;
    };

    public String getTestEntityItemSeqId() {
        return testEntityItemSeqId;
    }

    public String setTestEntityItemSeqId(String itemSeqId) {
        return testEntityItemSeqId = itemSeqId;
    }

    public String getTestEntityId() {
        return testEntityId;
    }

    public String setTestEntityId(String entityId) {
        return testEntityId = entityId;
    }

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
