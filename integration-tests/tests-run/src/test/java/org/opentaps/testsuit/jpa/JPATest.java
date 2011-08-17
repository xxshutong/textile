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

import java.io.IOException;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.geronimo.testsupport.HttpUtils;
import org.junit.Assert;
import org.junit.Test;
import org.opentaps.tests.BaseTestCase;


@SuppressWarnings("unused")
public class JPATest extends TestCase {

    @Test
    public void testInsertTestEntity() throws Exception {
        checkReply("/JPATests?test=InsertTestEntity");
    }

    @Test
    public void testUpdateTestEntity() throws Exception {
        checkReply("/JPATests?test=UpdateTestEntity");
    }

    @Test
    public void testRemoveTestEntity() throws Exception {
        checkReply("/JPATests?test=RemoveTestEntity");
    }

    @Test
    public void testAllMajorFieldTypes() throws Exception {
        checkReply("/JPATests?test=AllMajorFieldTypes");
    }

    private void checkReply(String address) throws Exception {
        URL url = new URL("http://localhost:8080/itest" + address);
        String reply = doGET(url, 3, 10 * 1000);
        assertTrue(reply, reply.contains(BaseTestCase.SUCCESS_RET_CODE));
    }

    private String doGET(URL url, int repeat, long delay) {
        for (int i = 0; i < repeat; i++) {
            try {
                return HttpUtils.doGET(url); 
            } catch (IOException e) {
                // do nothing
                try {
                    Thread.sleep(delay);
                } catch (Exception ee) {
                    break;
                }
            }
        }
        fail("Did not get servlet response in time");
        return "";
    }

}
