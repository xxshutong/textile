package org.opentaps.webapp.poc.leads;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;


public class LeadsSession extends WebSession {

    private static final long serialVersionUID = 6790743878501241576L;

    public LeadsSession(Request request) {
        super(request);
    }

    public static LeadsSession get() {
        return (LeadsSession) Session.get();
    }

    public boolean isAuthenticated() {
        return true;
    }
}
