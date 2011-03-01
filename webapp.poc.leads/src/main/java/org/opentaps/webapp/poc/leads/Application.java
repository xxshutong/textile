package org.opentaps.webapp.poc.leads;

import org.apache.wicket.protocol.http.WebApplication;


public class Application extends WebApplication {

    @Override
    public Class<MainPage> getHomePage() {
        return MainPage.class;
    }

}
