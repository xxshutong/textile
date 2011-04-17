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

    public void insertTestEntityWithJTA() throws Exception {
        // TODO implement method
        for (int i = 0; i < 20; i++) {
            System.out.print(">>>>>>>>>>>>>>>>>>>" + i + "<<<<<<<<<<<<<<<<<<<<");
        }
    }

    public void updateTestEntityWithJTA() throws Exception {
        // TODO implement method
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
