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


import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
import org.opentaps.testsuit.RemoteTestCase;


@SuppressWarnings("unused")
public class JPATest extends RemoteTestCase {

    @Test
    public void testInsertTestEntity() throws Exception {
        checkReply("/runTests?service=JPATestService&test=insertTestEntity");
    }

    @Test
    public void testUpdateTestEntity() throws Exception {
        checkReply("/runTests?service=JPATestService&test=updateTestEntity");
    }

    @Test
    public void testRemoveTestEntity() throws Exception {
        checkReply("/runTests?service=JPATestService&test=removeTestEntity");
    }

    @Test
    public void testAllMajorFieldTypes() throws Exception {
        checkReply("/runTests?service=JPATestService&test=allMajorFieldTypes");
    }

}
