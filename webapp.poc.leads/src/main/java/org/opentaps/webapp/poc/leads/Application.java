package org.opentaps.webapp.poc.leads;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;


public class Application extends WebApplication {

    @Override
    public Class<MainPage> getHomePage() {
        return MainPage.class;
    }

    @Override
    protected void init() {
        super.init();

        getComponentInstantiationListeners().add(new IComponentInstantiationListener() {
            public void onInstantiation(Component component) {
                if (!getSecuritySettings().getAuthorizationStrategy().isInstantiationAuthorized(component.getClass())) {
                    getSecuritySettings().getUnauthorizedComponentInstantiationListener().onUnauthorizedInstantiation(component);
                }
            }
        });
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new LeadsSession(request);
    }
}
