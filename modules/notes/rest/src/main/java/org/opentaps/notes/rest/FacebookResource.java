package org.opentaps.notes.rest;

import org.apache.commons.validator.GenericValidator;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.representation.Representation;
import org.restlet.representation. StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class FacebookResource extends ServerResource {

    public static final String FB_API_URL = "https://www.facebook.com/";
    public static final String FB_GRAPH_API_URL = "https://graph.facebook.com/";
    public static final String FB_SCOPE = "offline_access,email";
    public static final String FB_OAUTH_CALL = "dialog/oauth";
    public static final String FB_TOKEN_CALL = "oauth/access_token";
    public static final String FB_ME_CALL = "me";
    public static final String FB_CLIENT_ID = "147297945387974";
    public static final String FB_CLIENT_SECRET = "47543515439316957dd2ad605a8e22f1";
    public static final String FB_REDIRECT_URL = "http://localhost:8080/notes/facebook/callback";
    public static final String FB_ACTION_LOGIN = "login";
    public static final String FB_ACTION_CALLBACK = "callback";

    @Get
    public Representation getLogin() {
        Representation rep = null;
        String action = (String) getRequest().getAttributes().get("action");

        if (FB_ACTION_LOGIN.equalsIgnoreCase(action)) {
            login();
        } else if (FB_ACTION_CALLBACK.equalsIgnoreCase(action)) {
            rep =  callback();
        } else {
            rep = new StringRepresentation("Unknown request: " + action);
        }

        return rep;
    }

    /**
     * Redirect to facebook login page
     */
    private void login() {
        Reference ref = new Reference(FB_API_URL+FB_OAUTH_CALL);
        ref.addQueryParameter("client_id", FB_CLIENT_ID);
        ref.addQueryParameter("redirect_uri", FB_REDIRECT_URL);
        ref.addQueryParameter("scope", FB_SCOPE);

        getResponse().redirectTemporary(ref);
    }

    /**
     * Catch callback from facebook
     * @return Representation
     */
    private Representation callback() {
        Representation rep = new StringRepresentation("Can't get token ");
        String code = getRequest().getResourceRef().getQueryAsForm().getFirstValue("code");

        if (!GenericValidator.isBlankOrNull(code)) {
            Form form = getAccessToken(code);

            if (form != null) {
                String accessToken = form.getFirstValue("access_token");

                if (!GenericValidator.isBlankOrNull(accessToken)) {
                    rep = getMe(accessToken);
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
    private Form getAccessToken(String code) {
        Reference ref = new Reference(FB_GRAPH_API_URL+FB_TOKEN_CALL);
        ref.addQueryParameter("client_id", FB_CLIENT_ID);
        ref.addQueryParameter("redirect_uri", FB_REDIRECT_URL);
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
