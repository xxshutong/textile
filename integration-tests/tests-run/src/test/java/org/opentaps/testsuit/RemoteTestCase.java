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
package org.opentaps.testsuit;

import java.io.IOException;
import java.net.URL;

import org.apache.geronimo.testsupport.HttpUtils;
import org.opentaps.tests.BaseTestCase;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Protocol;
import org.restlet.ext.httpclient.HttpClientHelper;

import junit.framework.TestCase;


public class RemoteTestCase extends TestCase {

    protected Client client;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // use Apache HTTP client for REST requests
        client = new Client(new Context(), Protocol.HTTP);
        new HttpClientHelper(client);
    }

    @Override
    protected void tearDown() throws Exception {
        client = null;
        super.tearDown();
    }

    protected void checkReply(String address) throws Exception {
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
