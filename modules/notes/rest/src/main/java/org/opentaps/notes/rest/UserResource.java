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
package org.opentaps.notes.rest;

import javax.naming.NamingException;

import net.sf.json.util.JSONStringer;

import org.apache.commons.validator.GenericValidator;
import org.opentaps.core.log.Log;
import org.opentaps.core.service.ServiceException;
import org.opentaps.notes.rest.locale.Messages;
import org.opentaps.rest.FacebookUser;
import org.opentaps.rest.JSONUtil;
import org.opentaps.rest.ServerResource;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

public class UserResource extends ServerResource {

    /**
     * Handle GET requests.
     * @return JSON representation of the user and http.status SUCCESS_OK
     * @throws NamingException if the service cannot be found
     * @throws ServiceException if an error occur in the service
     */
    @Get
    public Representation getUser() throws NamingException, ServiceException {
        Messages messages = Messages.getInstance(getRequest());
        String repString = getEmptyJSON();
        String successMessage = "";
        String errorMessage = "";

        JSONUtil.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");

        String userKey = (String) getRequest().getAttributes().get("userKey");
        if (!GenericValidator.isBlankOrNull(userKey)) {
            FacebookUser user = userCache.getUser(userKey);

            if (user != null) {
                successMessage = messages.get("LoggedInAs");
                setStatus(Status.SUCCESS_OK);
                repString = user.getUserJSON();
            } else {
                errorMessage = messages.getMsg("UserNotFound", userKey);
                setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                Log.logError(errorMessage);
            }
        }

        return new StringRepresentation(JSONUtil.getJSONResult(repString, successMessage, errorMessage), MediaType.APPLICATION_JSON);
    }

    /**
     * Handle POST request to logout user i.e. remove it from UserCache
     * @return JSON representation of the user and http.status SUCCESS_OK
     * @throws NamingException if the service cannot be found
     * @throws ServiceException if an error occur in the service
     */
    @Post
    public Representation logout() throws NamingException, ServiceException {
        Messages messages = Messages.getInstance(getRequest());
        String repString = getEmptyJSON();
        String successMessage = "";
        String errorMessage = "";

        JSONUtil.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");
        String userKey = (String) getRequest().getAttributes().get("userKey");

        if (!GenericValidator.isBlankOrNull(userKey)) {
            FacebookUser user = userCache.getUser(userKey);

            if (user != null) {
                userCache.removeUser(userKey);
                setStatus(Status.SUCCESS_OK);
            } else {
                errorMessage = messages.getMsg("UserNotFound", userKey);
                setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                Log.logError(errorMessage);
            }

        }

        return new StringRepresentation(JSONUtil.getJSONResult(repString, successMessage, errorMessage), MediaType.APPLICATION_JSON);
    }

    /**
     * Get empty JSON representation.
     * @return a <code>String</code>
     */
    private String getEmptyJSON() {
        return new JSONStringer()
            .object()
                .key("user")
                .value("")
            .endObject()
            .toString();
    }
}
