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
package org.opentaps.testsuit.jpa.impl;

import javax.persistence.EntityManager;

import org.opentaps.entity.TestEntity;
import org.opentaps.entity.model.ITestEntity;
import org.opentaps.tests.OpentapsTestCase;
import org.opentaps.testsuit.jpa.IJPATestService;


public class JPATestService extends OpentapsTestCase implements IJPATestService {

    private EntityManager em;

    /* {@inheritDoc} */
    public void insertTestEntityWithJTA() throws Exception {
        assertTrue("Test InsertTestEntityWithJTA is not implemented.", false);
    }

    /* {@inheritDoc} */
    public void updateTestEntityWithJTA() throws Exception {
        assertTrue("Test UpdateTestEntityWithJTA is not implemented.", false);
    }

    /* {@inheritDoc} */
    public void insertTestEntity() throws Exception {

        // 1. Create a TestEntity
        ITestEntity e = new TestEntity();
        e.setTestStringField("insertTestEntity string field");
        em.persist(e);
        em.flush();

        // 2. Clear persistence context
        em.clear();

        // 3. Ensure it was really created
        ITestEntity testEntity = em.find(TestEntity.class, e.getTestId());
        if (testEntity == null) {
            fail(String.format("Test entity with PK %1$s is not exist.", e.getTestId()));
        }
    }

    /* {@inheritDoc} */
    public void updateTestEntity() throws Exception {
        // 1. Create a TestsEntity, assign some value to the string property.
        // 2. Change the string property value and update entity.
        // 3. Clear persistence context
        // 4. Find the entity and verify string property equals to last value.
        assertTrue("Test UpdateTestEntity is not implemented.", false);
    }

    /* {@inheritDoc} */
    public void removeTestEntity() throws Exception {
        // 1. Create a TestEntity
        // 2. Remove created entity.
        // 3. Try to find to be sure it does not exist.
        assertTrue("Test RemoveTestEntity is not implemented.", false);
    }

    /* {@inheritDoc} */
    public void allMajorFieldTypes() throws Exception {
        assertTrue("Test AllMajorFieldTypes is not implemented.", false);
    }

    /* {@inheritDoc} */
    public void identifierGenerator() throws Exception {
        assertTrue("Test IdentifierGenerator is not implemented.", false);
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
