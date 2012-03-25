/*
 * Copyright (c) Open Source Strategies, Inc.
 *
 * Opentaps is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Opentaps is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Opentaps.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opentaps.notes.rest;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;


public class NotesApplication extends Application {

    @Override
    public synchronized Restlet createInboundRoot() {
        Router router = new Router(getContext());

        // Attach the resources to the router
        router.attach("/note", NoteResource.class);
        router.attach("/notes", NoteResource.class);
        router.attach("/notes/{numberOfNotes}", NoteResource.class);
        router.attach("/notes/{numberOfNotes}/{fromSequence}", NoteResource.class);
        router.attach("/note/{noteId}", NoteResource.class);
        router.attach("/note/{lang}/{noteId}", NoteResource.class);

        // Action should be login or callback
        router.attach("/facebook/{action}", FacebookResource.class);

        router.attach("/user/{userKey}", UserResource.class);
        router.attach("/user/logout/{userKey}", UserResource.class);

        return router;
    }
}
