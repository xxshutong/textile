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
package org.opentaps.core.log;

import org.opentaps.core.Activator;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;


public class Log {

    /**
     * Logs a debug message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     * @param e The exception that reflects the condition or <code>null</code>.
     * @param sref The <code>ServiceReference</code> of the service that this message is associated with or <code>null</code>.
     */
    public static void logDebug(String message, Throwable e, ServiceReference sref) {
        Activator.getInstance().log(LogService.LOG_DEBUG, message, e, sref);
    };

    /**
     * Logs a debug message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     * @param e The exception that reflects the condition or <code>null</code>.
     */
    public static void logDebug(String message, Throwable e) {
        logDebug(message, e, null);
    };

    /**
     * Logs a debug message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     */
    public static void logDebug(String message) {
        logDebug(message, null, null);
    };

    /**
     * Logs an error message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     * @param e The exception that reflects the condition or <code>null</code>.
     * @param sref The <code>ServiceReference</code> of the service that this message is associated with or <code>null</code>.
     */
    public static void logError(String message, Throwable e, ServiceReference sref) {
        Activator.getInstance().log(LogService.LOG_ERROR, message, e, sref);
    };

    /**
     * Logs an error message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     * @param e The exception that reflects the condition or <code>null</code>.
     */
    public static void logError(String message, Throwable e) {
        logError(message, e, null);
    };

    /**
     * Logs an error message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     */
    public static void logError(String message) {
        logError(message, null, null);
    };

    /**
     * Logs an information message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     * @param e The exception that reflects the condition or <code>null</code>.
     * @param sref The <code>ServiceReference</code> of the service that this message is associated with or <code>null</code>.
     */
    public static void logInfo(String message, Throwable e, ServiceReference sref) {
        Activator.getInstance().log(LogService.LOG_INFO, message, e, sref);
    };

    /**
     * Logs an information message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     * @param e The exception that reflects the condition or <code>null</code>.
     */
    public static void logInfo(String message, Throwable e) {
        logInfo(message, e, null);
    };

    /**
     * Logs an information message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     */
    public static void logInfo(String message) {
        logInfo(message, null, null);
    };

    /**
     * Logs a warning message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     * @param e The exception that reflects the condition or <code>null</code>.
     * @param sref The <code>ServiceReference</code> of the service that this message is associated with or <code>null</code>.
     */
    public static void logWarning(String message, Throwable e, ServiceReference sref) {
        Activator.getInstance().log(LogService.LOG_WARNING, message, e, sref);
    };

    /**
     * Logs a warning message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     * @param e The exception that reflects the condition or <code>null</code>.
     */
    public static void logWarning(String message, Throwable e) {
        logWarning(message, e, null);
    };

    /**
     * Logs a warning message.
     *
     * @see org.opentaps.core.bundle.AbstractBundle#log(int, String, Throwable, ServiceReference)
     * @param message An arbitrary text message or <code>null</code>.
     */
    public static void logWarning(String message) {
        logWarning(message, null, null);
    };
}
