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
package org.opentaps.testsuit.jpa;

import java.net.URL;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;
//import org.testng.annotations.Test;


public class JPATest extends TestCase {

    @Test
    public void testGeneralJPA() throws Exception {
        checkReply("/JPATests");
    }

    private void checkReply(String address) throws Exception {
        URL url = new URL("http://localhost:8080/itest" + address);
        String reply = doGET(url, 6, 10 * 1000);
    }

    private String doGET(URL url, int repeat, long delay) {
        Assert.assertTrue(false);
        return null;
    }

}
