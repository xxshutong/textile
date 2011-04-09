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

import java.util.Set;

public interface IEnumeration {

    public String getEnumId();

    public void setEnumId(String enumId);

    public String getEnumTypeId();

    public void setEnumTypeId(String enumTypeId);

    public String getEnumCode();

    public void setEnumCode(String enumCode);

    public String getSequenceId();

    public void setSequenceId(String sequenceId);

    public String getDescription();

    public void setDescription(String description);

    public String getDisabled();

    public void setDisabled(String disabled);

    public String getParentEnumId();

    public void setParentEnumId(String parentEnumId);

    public IEnumeration getParentEnumeration();

    public void setParentEnumeration(IEnumeration enumeration);

    public IEnumerationType getEnumerationType();

    public void setEnumerationType(IEnumerationType enumerationType);

    public Set<IEnumeration> getChildEnumerations();
}
