package org.opentaps.webapp.poc.leads.security;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.request.component.IRequestableComponent;
import org.opentaps.ui.widget.login.Login;
import org.opentaps.webapp.poc.leads.LeadsSession;
import org.opentaps.webapp.poc.leads.SecurePage;


public class LeadsAuthorizationStrategy implements IAuthorizationStrategy, IUnauthorizedComponentInstantiationListener {

    public boolean isActionAuthorized(Component component, Action action) {
        return true;
    }

    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> clazz) {
        if (SecurePage.class.isAssignableFrom(clazz)){
            //TODO: do authorization here
            if (LeadsSession.get().isSignedIn()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public void onUnauthorizedInstantiation(Component component) {
        throw new RestartResponseAtInterceptPageException(Login.class);
    }

}
