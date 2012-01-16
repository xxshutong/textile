package org.opentaps.notes.rest;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.restlet.Component;
import org.restlet.data.Protocol;

public class Activator implements BundleActivator {

    private Component component;

    /** {@inheritDoc} */
	public void start(BundleContext context) throws Exception {
		component = new Component();
		component.getServers().add(Protocol.HTTP, 8182);
		component.getDefaultHost().attach("/notesapp",new NotesApplication());

		component.start();
	}

    /** {@inheritDoc} */
	public void stop(BundleContext context) throws Exception {
	    if (component != null) {
	        component.stop();
	    }
	}
}

