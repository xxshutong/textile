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

import java.io.IOException;
import java.util.UUID;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.validator.GenericValidator;
import org.opentaps.core.log.Log;
import org.opentaps.rest.FacebookUser;
import org.opentaps.rest.JSONUtil;
import org.opentaps.rest.ServerResource;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.restlet.representation. StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;

public class FacebookResource extends ServerResource {

    public static final String FB_API_URL = "https://www.facebook.com/";
    public static final String FB_M_API_URL = "https://m.facebook.com/";
    public static final String FB_GRAPH_API_URL = "https://graph.facebook.com/";
    public static final String FB_SCOPE = "offline_access,email,user_photos,publish_stream";
    public static final String FB_OAUTH_CALL = "dialog/oauth";
    public static final String FB_TOKEN_CALL = "oauth/access_token";
    public static final String FB_ME_CALL = "me";
    public static final String FB_FEED_CALL = "feed";
    public static final String FB_CLIENT_ID = "147297945387974";
    public static final String FB_CLIENT_SECRET = "47543515439316957dd2ad605a8e22f1";
    public static final String FB_REDIRECT_URL = "http://localhost:8080/notes/facebook/callback";
    public static final String FB_M_REDIRECT_URL = "http://localhost:8080/notes/facebook/mcallback";
    public static final String FB_ACTION_LOGIN = "login";
    public static final String FB_M_ACTION_LOGIN = "mlogin";
    public static final String FB_ACTION_CALLBACK = "callback";
    public static final String FB_M_ACTION_CALLBACK = "mcallback";
    public static final String FB_M_ACTION_SUCCESS = "success";
    public static final String FB_HTML_CLIENT_CALLBACK = "http://localhost/note_app.html";
    public static final String FB_MOBILE_CLIENT_CALLBACK = "http://localhost:8080/notes/facebook/success";
    public static final String USER_KEY_NAME = "userKey";

    @Get
    public Representation getLogin() {
        Representation rep = null;
        String action = (String) getRequest().getAttributes().get("action");
        JSONUtil.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");

        if (FB_ACTION_LOGIN.equalsIgnoreCase(action)) {
            login(FB_API_URL, FB_REDIRECT_URL);
        } else if (FB_M_ACTION_LOGIN.equalsIgnoreCase(action)) {
            login(FB_M_API_URL, FB_M_REDIRECT_URL);
        } else if (FB_ACTION_CALLBACK.equalsIgnoreCase(action)) {
            rep = callback(FB_HTML_CLIENT_CALLBACK, FB_REDIRECT_URL);
        } else if (FB_M_ACTION_CALLBACK.equalsIgnoreCase(action)) {    
            rep = callback(FB_MOBILE_CLIENT_CALLBACK, FB_M_REDIRECT_URL);
        } else if (FB_M_ACTION_SUCCESS.equalsIgnoreCase(action)) {    
                rep = success();    
        } else {
            rep = new StringRepresentation("Unknown request: " + action);
        }

        return rep;
    }
    
    private Representation success() {
        Representation rep = new StringRepresentation("Success");
        
        return rep;
    }

    /**
     * Redirect to facebook login page
     */
    private void login(String apiURL, String callbackURL) {
        Reference ref = new Reference(apiURL+FB_OAUTH_CALL);
        ref.addQueryParameter("client_id", FB_CLIENT_ID);
        ref.addQueryParameter("redirect_uri", callbackURL);
        ref.addQueryParameter("scope", FB_SCOPE);

        getResponse().redirectTemporary(ref);
    }

    /**
     * Catch callback from facebook
     * @return Representation
     */
    private Representation callback(String clientCallback, String callbackURL) {
        Representation rep = new StringRepresentation("Can't get token ");
        String code = getRequest().getResourceRef().getQueryAsForm().getFirstValue("code");

        if (!GenericValidator.isBlankOrNull(code)) {
            Form form = getAccessToken(code, callbackURL);

            if (form != null) {
                String accessToken = form.getFirstValue("access_token");

                if (!GenericValidator.isBlankOrNull(accessToken)) {
                    rep = getMe(accessToken);

                    if (rep != null) {
                        String userKey = UUID.randomUUID().toString();
                        try {
                            JSONObject userJSON = (JSONObject) JSONSerializer.toJSON(rep.getText());
                            FacebookUser fbUser = new FacebookUser(userJSON);
                            if (fbUser != null) {
                                fbUser.setAccessToken(accessToken);
                                userCache.putUser(userKey, fbUser);
                                Reference ref = new Reference(clientCallback + "?" + USER_KEY_NAME + "=" + userKey);
                                getResponse().redirectTemporary(ref);
                            }
                        } catch (IOException e) {
                            rep = new StringRepresentation("Can't get facebook user from json ");
                            Log.logError(e.toString());
                        }

                    } else {
                        rep = new StringRepresentation("Can't get facebook user ");
                    }
                }
            }
        }

        return rep;
    }

    /**
     * Get facebook access token
     * @param code a <code>String</code>
     * @return access token a <code>Representation</code>
     */
    private Form getAccessToken(String code, String callbackURL) {
        Reference ref = new Reference(FB_GRAPH_API_URL+FB_TOKEN_CALL);
        ref.addQueryParameter("client_id", FB_CLIENT_ID);
        ref.addQueryParameter("redirect_uri", callbackURL);
        ref.addQueryParameter("client_secret", FB_CLIENT_SECRET);
        ref.addQueryParameter("code", code);

        ClientResource cr = new ClientResource(ref);
        Client client = new Client(new Context(), Protocol.HTTPS);
        client.getContext().getParameters().add("useForwardedForHeader", "false");
        cr.setNext(client);
        Form form = cr.get(Form.class);

        return form;
    }

    /**
     * Get information about user from facebook
     * @param accessToken a <code>String</code>
     * @return a <code>Representation</code>
     */
    private Representation getMe(String accessToken) {
        Reference ref = new Reference(FB_GRAPH_API_URL+FB_ME_CALL);
        ref.addQueryParameter("access_token", accessToken);

        ClientResource cr = new ClientResource(ref);
        Client client = new Client(new Context(), Protocol.HTTPS);
        cr.setNext(client);

        Representation rep = cr.get();
        return rep;
    }
}
