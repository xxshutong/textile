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
package org.opentaps.core.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.opentaps.core.log.Log;

public class ServiceUtil {

    /**
     * JNDI URL scheme for OSGi services.
     */
    public static final String OSGI_SERVICE_PREFIX = "osgi:service";

    /**
     * JNDI URL scheme that used to lookup list of OSGi services by a filter. 
     */
    public static final String OSGI_SERVICE_LST_PREFIX = "osgi:servicelist";

    /**
     * Lookup and return OSGi service.
     * 
     * @param serviceName A service name, usually interface.
     * @return A proxied instance of the service or null.
     */
    public static final Object getService(String serviceName) {
        return getService(serviceName, null);
    }

    /**
     * Lookup and return OSGi service.
     * 
     * @param serviceName A service name, usually interface.
     * @param filter A filter string like one used in <code>BundleContext.getServiceReferences</code>. If many
     * services match given filter the method return last from found. 
     * @return A proxied instance of the service or null.
     * @see org.osgi.framework.BundleContext#getServiceReferences(String, String) BundleContext.getServiceReferences
     */
    public static final Object getService(String serviceName, String filter) {
        StringBuilder name = new StringBuilder(OSGI_SERVICE_PREFIX).append("/").append(serviceName);
        if (filter != null) {
            name.append("/").append(filter);
        }

        try {
            InitialContext ctx = new InitialContext();
            return ctx.lookup(name.toString());
        } catch (NamingException e) {
            Log.logWarning(e.toString(true)); // not an error in most cases
            return null;
        }
    }
}
