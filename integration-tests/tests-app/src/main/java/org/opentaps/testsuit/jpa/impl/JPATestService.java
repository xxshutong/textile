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

import org.opentaps.testsuit.jpa.IJPATestService;

public class JPATestService implements IJPATestService {

    private EntityManager em;

    /* {@inheritDoc} */
    public void insertTestEntityWithJTA() throws Exception {
        System.out.print("Test InsertTestEntityWithJTA is not implemented.");
    }

    /* {@inheritDoc} */
    public void updateTestEntityWithJTA() throws Exception {
        System.out.print("Test UpdateTestEntityWithJTA is not implemented.");
    }

    /* {@inheritDoc} */
    public void insertTestEntity() throws Exception {
        System.out.print("Test InsertTestEntity is not implemented.");
    }

    /* {@inheritDoc} */
    public void updateTestEntity() throws Exception {
        System.out.print("Test UpdateTestEntity is not implemented.");
    }

    /* {@inheritDoc} */
    public void removeTestEntity() throws Exception {
        System.out.print("Test RemoveTestEntity is not implemented.");
    }

    /* {@inheritDoc} */
    public void allMajorFieldTypes() throws Exception {
        System.out.print("Test AllMajorFieldTypes is not implemented.");
    }

    /* {@inheritDoc} */
    public void identifierGenerator() throws Exception {
        System.out.print("Test IdentifierGenerator is not implemented.");
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

}
