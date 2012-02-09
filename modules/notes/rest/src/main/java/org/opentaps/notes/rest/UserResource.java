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
import org.opentaps.core.service.ServiceException;
import org.opentaps.rest.FacebookUser;
import org.opentaps.rest.JSONUtil;
import org.opentaps.rest.ServerResource;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;

public class UserResource extends ServerResource {

    @Get
    public Representation getUser() throws NamingException, ServiceException {
        String repString = getEmptyJSON();
        String successMessage = "";
        String errorMessage = "";

        JSONUtil.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");

        String userKey = (String) getRequest().getAttributes().get("userKey");
        if (!GenericValidator.isBlankOrNull(userKey)) {
            FacebookUser user = userCache.getUser(userKey);

            if (user != null) {
                repString = user.getUserJSON();
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
