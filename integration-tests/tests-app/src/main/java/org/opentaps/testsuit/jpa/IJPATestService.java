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
package org.opentaps.testsuit.jpa;

public interface IJPATestService {

    /**
     * Insert and then searches a <code>TestEntity</code>.
     * @throws Exception
     */
    public void insertTestEntity() throws Exception;

    /**
     * Update an existing <code>TestEntity</code>.
     * @throws Exception
     */
    public void updateTestEntity() throws Exception;

    /**
     * Remove a <code>TestEntity</code>.
     * @throws Exception
     */
    public void removeTestEntity() throws Exception;

    /**
     * Verify we can store and retrieve values in all the field types successfully.
     * @throws Exception
     */
    public void allMajorFieldTypes() throws Exception;

}
