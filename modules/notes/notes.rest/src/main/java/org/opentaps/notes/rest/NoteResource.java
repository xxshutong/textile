package org.opentaps.notes.rest;

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

import org.opentaps.notes.domain.Note;
import org.opentaps.notes.services.NoteServices;
import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.services.GetNoteByIdService;

public class NoteResource extends ServerResource {

    /**
     * Handle GET requests
     * @return JSON representation of the note and http.status SUCCESS_OK
     */
	@Get
	public Representation getNote() {
	    String repString = "";
	    String noteId = (String) getRequest().getAttributes().get("noteId");

	    if (noteId != null && noteId.length() > 0) {
	        try {
	            InitialContext context = new InitialContext();
	            NoteServices noteServices = (NoteServices) context.lookup("osgi:service/org.opentaps.notes.services.NoteServices");
	            GetNoteByIdService getNoteByIdService = noteServices.getGetNoteByIdService();

	            if (getNoteByIdService != null) {
	                getNoteByIdService.setNoteId(noteId);
	                getNoteByIdService.getNoteById();

	                Note note = getNoteByIdService.getNote();

	                if (note != null) {
	                    setStatus(Status.SUCCESS_OK);
	                    repString = getNoteJSON(note);
	                } else {
	                    setStatus(Status.CLIENT_ERROR_NOT_FOUND);
	                }

	            } else {
	                setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
	            }

	        } catch (NamingException e) {
	            setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
	        }
	    }

	    Representation rep = new StringRepresentation(repString, MediaType.APPLICATION_JSON);
	    NoteUtils.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");

		return rep;
	}

	/**
	 * Handle POST requests: create a new note.
	 * @param entity a <code>Representation</code>
	 * @return a noteId as plain text and http.status SUCCESS_CREATED
	 */
	@Post
	public Representation createNote(Representation entity) {
	    String repString = "";
		Form form = new Form(entity);
		String noteText = form.getFirstValue("noteText");
		String attribute1 = form.getFirstValue("attribute1");
		String attribute2 = form.getFirstValue("attribute2");
		String attribute3 = form.getFirstValue("attribute3");
		String attribute4 = form.getFirstValue("attribute4");
		String attribute5 = form.getFirstValue("attribute5");
		String attribute6 = form.getFirstValue("attribute6");
		String attribute7 = form.getFirstValue("attribute7");
		String attribute8 = form.getFirstValue("attribute8");
		String attribute9 = form.getFirstValue("attribute9");
		String attribute10 = form.getFirstValue("attribute10");

		try {
		    InitialContext context = new InitialContext();
	            NoteServices noteServices = (NoteServices) context.lookup("osgi:service/org.opentaps.notes.services.NoteServices");
	            CreateNoteService createNoteService = noteServices.getCreateNoteService();

		    if (createNoteService != null) {
		        createNoteService.setText(noteText);
		        createNoteService.setAttribute1(attribute1);
		        createNoteService.setAttribute2(attribute2);
		        createNoteService.setAttribute3(attribute3);
		        createNoteService.setAttribute4(attribute4);
		        createNoteService.setAttribute5(attribute5);
		        createNoteService.setAttribute6(attribute6);
		        createNoteService.setAttribute7(attribute7);
		        createNoteService.setAttribute8(attribute8);
		        createNoteService.setAttribute9(attribute9);
		        createNoteService.setAttribute10(attribute10);
		        createNoteService.createNote();

		        String noteId = createNoteService.getNoteId();

		        if (noteId != null && noteId.length() > 0) {
		            setStatus(Status.SUCCESS_CREATED);
		            repString = noteId;
		        } else {
		            setStatus(Status.SERVER_ERROR_INTERNAL);
		        }
		    } else {
		        setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
		    }

		} catch (NamingException e) {
		    setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
        }

        Representation rep = new StringRepresentation(repString, MediaType.TEXT_PLAIN);
        NoteUtils.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");

        return rep;
	}

	/**
	 * Get JSON representation of the note
	 * @param note a <code>Note</code>
	 * @return a <code>String</code>
	 */
	private String getNoteJSON(Note note) {
	    StringBuilder json = new StringBuilder();
	    if (note != null) {
	        json.append("{\"note\": {\"noteId\":\"").append(note.getId())
	            .append("\", \"noteText\":\"").append(note.getText() == null ? "" : note.getText())
	            .append("\", \"attribute1\":\"").append(note.getAttribute1() == null ? "" : note.getAttribute1())
	            .append("\", \"attribute2\":\"").append(note.getAttribute2() == null ? "" : note.getAttribute2())
	            .append("\", \"attribute3\":\"").append(note.getAttribute3() == null ? "" : note.getAttribute3())
	            .append("\", \"attribute4\":\"").append(note.getAttribute4() == null ? "" : note.getAttribute4())
	            .append("\", \"attribute5\":\"").append(note.getAttribute5() == null ? "" : note.getAttribute5())
	            .append("\", \"attribute6\":\"").append(note.getAttribute6() == null ? "" : note.getAttribute6())
	            .append("\", \"attribute7\":\"").append(note.getAttribute7() == null ? "" : note.getAttribute7())
	            .append("\", \"attribute8\":\"").append(note.getAttribute8() == null ? "" : note.getAttribute8())
	            .append("\", \"attribute9\":\"").append(note.getAttribute9() == null ? "" : note.getAttribute9())
	            .append("\", \"attribute10\":\"").append(note.getAttribute10() == null ? "" : note.getAttribute10())
	            .append("\"}}");
	    }
	    return json.toString();
	}
}
