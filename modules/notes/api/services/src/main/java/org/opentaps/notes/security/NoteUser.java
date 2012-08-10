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
package org.opentaps.notes.security;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.useradmin.User;


public class NoteUser implements User {
    public static final String PROP_USER_ID = "id";
    public static final String PROP_USER_TYPE = "userType";

    Dictionary<String, Object> props;

    public NoteUser() {
        props = new Hashtable<String, Object>();
    }

    public NoteUser(String userId, String type) {
        if (userId == null) {
            throw new IllegalArgumentException("User identifier should have a value.");
        }

        props = new Hashtable<String, Object>();
        props.put(PROP_USER_ID, userId);
        props.put(PROP_USER_TYPE, type);
    }

    @Override
    public String getName() {
        return getUserId();
    }

    @Override
    public int getType() {
        return 1;
    }

    @Override
    public Dictionary<String, Object> getProperties() {
        return props;
    }

    @Override
    public Dictionary<String, Object> getCredentials() {
        throw new IllegalStateException("The method is not implemented");
    }

    @Override
    public boolean hasCredential(String key, Object value) {
        throw new IllegalStateException("The method is not implemented");
    }

    public String getUserType() {
        return (String) props.get(PROP_USER_TYPE);
    }

    public String getUserId() {
        return (String) props.get(PROP_USER_ID);
    }
}
