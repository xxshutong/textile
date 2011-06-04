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
package org.opentaps.entity.model;


public interface ITestEntityItem {

//    public String getTestEntityId();

//    public void setTestEntityId(String id);

    public String getTestEntityItemSeqId();

    public void setTestEntityItemSeqId(String seqId);
    
    public String getItemValue();

    public void setItemValue(String itemValue);

    public ITestEntity getTestEntity();

    public void setTestEntity(ITestEntity testEntity);

}
