package org.opentaps.webapp.poc.leads;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;


public class LeadsSession extends AuthenticatedWebSession {

    private static final long serialVersionUID = 6790743878501241576L;

    public LeadsSession(Request request) {
        super(request);
    }

    @Override
    public boolean authenticate(final String username, final String password)
    {
        final String USERNAME = "admin";
        final String PASSWORD = "opentaps";

        return USERNAME.equals(username) && PASSWORD.equals(password);
    }

    @Override
    public Roles getRoles()
    {
        if (isSignedIn())
        {
            // If the user is signed in, they have these roles
            return new Roles(Roles.ADMIN);
        }
        return null;
    }

    public static LeadsSession get() {
        return (LeadsSession) Session.get();
    }

    public boolean isAuthenticated() {
        return true;
    }
}
