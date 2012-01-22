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
package org.opentaps.testsuit.rpc.impl;

import java.util.HashMap;
import java.util.Map;

import org.opentaps.core.service.ServiceUtil;
import org.opentaps.core.service.XMLRPCService;
import org.opentaps.tests.OpentapsTestCase;
import org.opentaps.testsuit.rpc.IXMLRPCTests;


public class XMLRPCTests extends OpentapsTestCase implements IXMLRPCTests {

    public void ofbizPing() throws Exception {
        final String MSG = "PING";

        XMLRPCService service = (XMLRPCService) ServiceUtil.getService(XMLRPCService.class.getName());
        assertNotNull(String.format("Service %1$s is unavailable.", XMLRPCService.class.getName()), service);

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("message", MSG);

        Map<String, ?> results = service.execute("ping", context);
        assertEquals("Wrong response from the remote service.", MSG, results.get("message"));
    }

}
