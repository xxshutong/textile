package org.opentaps.notes.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class NotesApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		// Attach the resources to the router
		router.attach("/note", NoteResource.class);
        router.attach("/note/{noteId}", NoteResource.class);
		return router;
	}
}
