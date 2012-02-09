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

import net.sf.json.JSONObject;

import org.opentaps.core.log.Log;
import org.restlet.security.User;

public class FacebookUser extends User {

    public String id;
    public String name;
    public String firstName;
    public String lastName;
    public String link;
    public String email;

    public FacebookUser(JSONObject user){
        try{
            setUser(user);
        }
        catch(Exception e){
            Log.logError(e.toString());
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUser(JSONObject user) throws Exception{
        setId(user.getString("id"));
        setName(user.getString("name"));
        setFirstName(user.getString("first_name"));
        setLastName(user.getString("last_name"));
        setLink(user.getString("link"));
        setEmail(user.getString("email"));
   }
}
