package org.opentaps.notes.rest;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import org.opentaps.notes.services.CreateNoteService;

public class NoteResource extends ServerResource {

	@Get
	public String getNote() {
		return "Get Note";
	}

	@Post
	public Representation createNote(Representation entity) throws IOException, NamingException {
	    String repString = "";
		Form form = new Form(entity);
		String noteText = form.getFirstValue("note");

		InitialContext context = new InitialContext();
		CreateNoteService createNoteService = (CreateNoteService) context.lookup("osgi:service/org.opentaps.notes.services.CreateNoteService");

		if (createNoteService != null) {
		    createNoteService.setText(noteText);
		    String noteId = createNoteService.getNoteId();

		    if (noteId != null && noteId.length() > 0) {
		        setStatus(Status.SUCCESS_OK);
		        repString = noteId;
		    } else {
		        setStatus(Status.SERVER_ERROR_INTERNAL);
		    }
		} else {
		    setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
		}

        Representation rep = new StringRepresentation(repString, MediaType.TEXT_PLAIN);

        return rep;
	}
}
