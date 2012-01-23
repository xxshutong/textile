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
package org.opentaps.core.bundle;

import org.opentaps.core.locale.Messages;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;


/**
 * Common superclass for any bundle activator that implements common
 * functionality, e. g. offers methods for logging.
 */
public abstract class AbstractBundle implements BundleActivator {

    // LogService tracker
    private volatile ServiceTracker logTracker;

    /** {@inheritDoc} */
    public void start(BundleContext context) throws Exception {
        // start tracking services
        logTracker = new ServiceTracker(context, LogService.class.getName(), null);
        logTracker.open();
    }

    /** {@inheritDoc} */
    public void stop(BundleContext context) throws Exception {

        // stop tracking services
        logTracker.close();
    }

    /**
     * Main logging method that uses OSGi <code>LogService</code> to log a message
     * using underlying framework capabilities.
     *
     * @param severity The severity of the message, one of the <code>LogService.LOG_*</code> constants.
     * @param message An arbitrary text message or <code>null</code>.
     * @param e The exception that reflects the condition or <code>null</code>.
     * @param sref The <code>ServiceReference</code> of the service that this message is associated with or <code>null</code>. 
     */
    public void log(int severity, String message, Throwable e, ServiceReference sref) {
        Object[] services = logTracker.getServices();
        if (services != null) {
            for (Object service : services) {
                ((LogService) service).log(sref, severity, message, e);
            }
        } else {
            StringBuilder sb = new StringBuilder();
            switch (severity) {
            case LogService.LOG_ERROR : 
                sb.append(Messages.get("SeverityErrorIndicator")); //$NON-NLS-1$
                break;
            case LogService.LOG_DEBUG : 
                sb.append(Messages.get("SeverityDebugIndicator")); //$NON-NLS-1$
                break;
            case LogService.LOG_INFO : 
                sb.append(Messages.get("SeverityInfoIndicator")); //$NON-NLS-1$
                break;
            case LogService.LOG_WARNING : 
                sb.append(Messages.get("SeverityWarnIndicator")); //$NON-NLS-1$
                break;
            default:
                break;
            }

            sb.append(message);

            if (e != null) {
                sb.append(e);
            }

            System.out.println(sb.toString());
        }
    }

}
