package org.opentaps.webapp.poc.leads;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.application.IComponentInstantiationListener;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authorization.IUnauthorizedComponentInstantiationListener;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.settings.ISecuritySettings;
import org.opentaps.ui.widget.login.Login;
import org.opentaps.webapp.poc.leads.security.LeadsAuthorizationStrategy;


public class Application extends AuthenticatedWebApplication {

    @Override
    public Class<MainPage> getHomePage() {
        return MainPage.class;
    }

    @Override
    protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass()
    {
        return LeadsSession.class;
    }

    @Override
    protected Class<? extends WebPage> getSignInPageClass()
    {
        return Login.class;
    }

    @Override
    protected void init() {
        super.init();

        getDebugSettings().setDevelopmentUtilitiesEnabled(true);

        final ISecuritySettings securitySettings = getSecuritySettings();
        IAuthorizationStrategy authznStrategy = new LeadsAuthorizationStrategy();
        securitySettings.setAuthorizationStrategy(authznStrategy);
        securitySettings.setUnauthorizedComponentInstantiationListener((IUnauthorizedComponentInstantiationListener) authznStrategy);

        getComponentInstantiationListeners().add(new IComponentInstantiationListener() {
            public void onInstantiation(Component component) {
                if (!securitySettings.getAuthorizationStrategy().isInstantiationAuthorized(component.getClass())) {
                    securitySettings.getUnauthorizedComponentInstantiationListener().onUnauthorizedInstantiation(component);
                }
            }
        });
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new LeadsSession(request);
    }
}
