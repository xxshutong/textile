package org.opentaps.webapp.poc.leads.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.opentaps.core.bundle.AbstractBundle;


public class LeadsActivator extends AbstractBundle {

    // the shared instance
    private static BundleActivator bundle;

    public void start(BundleContext context) throws Exception {
        bundle = this;
        super.start(context);
    }

    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        bundle = null;
    }

    public static LeadsActivator getInstance() {
        return (LeadsActivator) bundle;
    };

}
