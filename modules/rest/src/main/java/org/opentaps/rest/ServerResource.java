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
package org.opentaps.rest;

import java.util.Locale;
import javax.naming.NamingException;

import org.opentaps.core.log.Log;
import org.opentaps.core.service.ServiceValidationException;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.StringRepresentation;

/**
 * Provides error handling to format the common exceptions in a standard JSON response.
 */
public class ServerResource extends org.restlet.resource.ServerResource {

    private Locale locale = Locale.US;

    protected static volatile UserCache userCache = new UserCache();
    
    protected Locale getLocale() {
        return locale;
    }

    @Override
    protected void doCatch(Throwable throwable) {

        // the actual exception is wrapped before coming in the doCatch
        if (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        String errorMessage = throwable.getMessage();
        Log.logError(errorMessage, throwable);

        if (throwable instanceof NamingException) {
            errorMessage = "Service Unavailable";
            setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE, errorMessage);
            getResponse().setEntity(new StringRepresentation(JSONUtil.getJSONError(errorMessage), MediaType.APPLICATION_JSON));
        } else if (throwable instanceof ServiceValidationException) {
            errorMessage = "Validation Error " + throwable.getLocalizedMessage();
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            getResponse().setEntity(new StringRepresentation(JSONUtil.getJSONError(errorMessage, ((ServiceValidationException) throwable).getFieldErrors()), MediaType.APPLICATION_JSON));
        } else {
            StringBuilder sb = new StringBuilder(throwable.getLocalizedMessage());
            // In some cases like exception from the persistence layer, errors can be duplicated in the stack
            // so we filter that out
            Throwable parent = throwable;
            Throwable nested = throwable.getCause();
            while (nested != null) {
                if (parent.getMessage() == null || !parent.getMessage().equals(nested.getMessage())) {
                    sb.append("\n Caused by: ").append(nested.getMessage());
                }
                parent = nested;
                nested = nested.getCause();
            }
            errorMessage = "Unexpected Error " + sb.toString();
            setStatus(Status.SERVER_ERROR_INTERNAL, errorMessage);
            getResponse().setEntity(new StringRepresentation(JSONUtil.getJSONError(errorMessage), MediaType.APPLICATION_JSON));
        }
    }
}
