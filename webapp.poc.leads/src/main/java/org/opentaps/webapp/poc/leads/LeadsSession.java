package org.opentaps.webapp.poc.leads;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.request.Request;

import com.sun.security.auth.UserPrincipal;


public class LeadsSession extends AuthenticatedWebSession {

    private static final long serialVersionUID = 6790743878501241576L;

    public static final String REALM = "karaf";

    String currentUser;
    Set<String> roles;
    Subject subject;

    public LeadsSession(Request request) {
        super(request);
    }

    @Override
    public boolean authenticate(final String username, final String password)
    {
        subject = new Subject();
        LoginContext loginContext;
        try {
            loginContext = new LoginContext(REALM, subject, new CallbackHandler() {

                public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
                    for (Callback callback : callbacks) {
                        if (callback instanceof NameCallback) {
                            ((NameCallback) callback).setName(username);
                        } else if (callback instanceof PasswordCallback) {
                            ((PasswordCallback) callback).setPassword(password != null ? password.toCharArray() : null);
                        } else {
                            throw new UnsupportedCallbackException(callback);
                        }
                    }
                }
                
            });

            loginContext.login();

            for (Principal p : subject.getPrincipals()) {
                if (p instanceof UserPrincipal) {
                    currentUser = p.getName();
                } else {
                    if (roles == null) {
                        roles = new HashSet<String>();
                    }
                    roles.add(p.getName());
                }
            }

            return true;

        } catch (LoginException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Roles getRoles()
    {
        return (isSignedIn() && roles != null && !roles.isEmpty()) ? new Roles(roles.toArray(new String[0])) : null;
    }

    public static LeadsSession get() {
        return (LeadsSession) Session.get();
    }

}
