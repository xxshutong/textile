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
package org.opentaps.notes.rest.auth;

import org.opentaps.core.log.Log;
import org.opentaps.core.service.ServiceUtil;
import org.opentaps.notes.services.security.NotesSecurityFactory;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.security.Authenticator;


public class WrapperAuthenticator extends Authenticator {
    Restlet next;

    public WrapperAuthenticator(Context context, Restlet next) {
        super(context);
        this.next = next;
    }

    @Override
    protected boolean authenticate(Request request, Response response) {
        // try to find authenticator
        Authenticator authenticator = null;
        NotesSecurityFactory sf = (NotesSecurityFactory) ServiceUtil.getService(NotesSecurityFactory.class.getName());
        if (sf != null) {
            authenticator = sf.getAuthenticator(getContext(), false, null);
            if (authenticator != null) {
                Log.logInfo("Authenticator is found and attached to a router.");
                authenticator.setNext(next);
                setNext(authenticator);
                return true;
            }
        }

        setNext(next);
        return true;
    }

}
