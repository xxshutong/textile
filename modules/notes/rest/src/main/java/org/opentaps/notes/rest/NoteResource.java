package org.opentaps.notes.rest;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONStringer;

import org.apache.commons.validator.GenericValidator;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.services.GetNoteByIdService;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;


public class NoteResource extends ServerResource {

    /**
     * Handle GET requests
     * @return JSON representation of the note and http.status SUCCESS_OK
     */
    @Get
    public Representation getNote() {
        String repString = "";
        String noteId = (String) getRequest().getAttributes().get("noteId");

        if (!GenericValidator.isBlankOrNull(noteId)) {
            try {
                InitialContext context = new InitialContext();
                GetNoteByIdService getNoteByIdService = (GetNoteByIdService) context.lookup("osgi:service/org.opentaps.notes.services.GetNoteByIdService");

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
            CreateNoteService createNoteService = (CreateNoteService) context.lookup("osgi:service/org.opentaps.notes.services.CreateNoteService");

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

                if (!GenericValidator.isBlankOrNull(noteId)) {
                    setStatus(Status.SUCCESS_CREATED);
                    repString = new JSONStringer().object().key("id").value(noteId).endObject().toString();
                } else {
                    setStatus(Status.SERVER_ERROR_INTERNAL);
                }
            } else {
                setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
            }

        } catch (NamingException e) {
            setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
        }

        Representation rep = new StringRepresentation(repString, MediaType.APPLICATION_JSON);
        NoteUtils.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");

        return rep;
    }

    /**
     * Get JSON representation of the note
     * @param note a <code>Note</code>
     * @return a <code>String</code>
     */
    private String getNoteJSON(Note note) {
        if (note == null) {
            throw new IllegalArgumentException();
        }

        return new JSONStringer()
            .object()
                .key("note")
                .value(JSONSerializer.toJSON(new JSONStringer()
                                             .object()
                                             .key("id").value(note.getId())
                                             .key("text").value(note.getText())
                                             .key("attribute1").value(note.getAttribute1())
                                             .key("attribute2").value(note.getAttribute2())
                                             .key("attribute3").value(note.getAttribute3())
                                             .key("attribute4").value(note.getAttribute4())
                                             .key("attribute5").value(note.getAttribute5())
                                             .key("attribute6").value(note.getAttribute6())
                                             .key("attribute7").value(note.getAttribute7())
                                             .key("attribute8").value(note.getAttribute8())
                                             .key("attribute9").value(note.getAttribute9())
                                             .key("attribute10").value(note.getAttribute10())
                                             .endObject()
                                             .toString()))
            .endObject()
            .toString();
    }
}
