package org.opentaps.webapp.poc.leads.security;

import org.apache.wicket.Component;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.request.component.IRequestableComponent;


public class LeadsAuthorizationStrategy implements IAuthorizationStrategy, IUnauthorizedComponentInstantiationListener {

    public boolean isActionAuthorized(Component arg0, Action arg1) {
        return false;
    }

    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> arg0) {
        return false;
    }

    public void onUnauthorizedInstantiation(Component arg0) {
    }

}
