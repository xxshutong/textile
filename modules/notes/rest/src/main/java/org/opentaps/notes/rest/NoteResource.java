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

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONStringer;

import org.apache.commons.validator.GenericValidator;
import org.opentaps.core.log.Log;
import org.opentaps.core.service.ServiceException;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.rest.locale.Messages;
import org.opentaps.notes.security.NoteUser;
import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.services.CreateNoteServiceInput;
import org.opentaps.notes.services.GetNoteByIdService;
import org.opentaps.notes.services.GetNoteByIdServiceInput;
import org.opentaps.notes.services.GetNotesService;
import org.opentaps.notes.services.GetNotesServiceInput;
import org.opentaps.rest.FacebookUser;
import org.opentaps.rest.JSONUtil;
import org.opentaps.rest.ServerResource;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.data.Reference;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

/**
 * The Notes REST implementation.
 */
public class NoteResource extends ServerResource {

    private static final String CUSTOM_FIELD_PREFIX = "note_field_";
    private static final String OPENTAPS_FACEBOOK_PAGE_ID = "285070770315";

    /**
     * Handle GET requests.
     * @return JSON representation of the note and http.status SUCCESS_OK
     * @throws NamingException if the service cannot be found
     * @throws ServiceException if an error occur in the service
     */
    @Get
    public Representation getNote() throws NamingException, ServiceException {
        Messages messages = Messages.getInstance(getRequest());
        String repString = getEmptyJSON();
        String successMessage = "";
        String errorMessage = "";
        String noteId = (String) getRequest().getAttributes().get("noteId");

        JSONUtil.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");

        InitialContext context = new InitialContext();
        if (GenericValidator.isBlankOrNull(noteId)) {
            GetNotesService getNotesService = (GetNotesService) context.lookup("osgi:service/org.opentaps.notes.services.GetNotesService");

            if (getNotesService != null) {
                GetNotesServiceInput getNotesServiceInput = new GetNotesServiceInput();
                String fromSeq = (String) getRequest().getAttributes().get("fromSequence");
                String num = (String) getRequest().getAttributes().get("numberOfNotes");
                if (!GenericValidator.isBlankOrNull(fromSeq)) {
                    getNotesServiceInput.setFromSequence(Long.valueOf(fromSeq));
                }
                if (!GenericValidator.isBlankOrNull(num)) {
                    getNotesServiceInput.setNumberOfNotes(Integer.valueOf(num));
                }
                String order = getQuery().getValues("order");
                if (!GenericValidator.isBlankOrNull(order)) {
                    order = order.trim();
                    if (order.equalsIgnoreCase("DESC")) {
                        getNotesServiceInput.setOrderDirection(-1);
                    }
                }

                List<Note> notes = getNotesService.getNotes(getNotesServiceInput).getNotes();

                if (notes != null) {
                    setStatus(Status.SUCCESS_OK);
                    repString = getNotesJSON(notes);
                } else {
                    errorMessage = messages.getMsg("NoteNotFound");
                    setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                    Log.logError(errorMessage);
                }

            } else {
                errorMessage = messages.get("GetNoteServiceUnavailable");
                setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
                Log.logError(errorMessage);
            }
        } else {
            GetNoteByIdService getNoteByIdService = (GetNoteByIdService) context.lookup("osgi:service/org.opentaps.notes.services.GetNoteByIdService");

            if (getNoteByIdService != null) {
                GetNoteByIdServiceInput getNoteByIdServiceInput = new GetNoteByIdServiceInput();
                getNoteByIdServiceInput.setNoteId(noteId);
                Note note = getNoteByIdService.getNoteById(getNoteByIdServiceInput).getNote();

                if (note != null) {
                    setStatus(Status.SUCCESS_OK);
                    repString = getNoteJSON(note);
                } else {
                    errorMessage = messages.getMsg("NoteNotFound", noteId);
                    setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                    Log.logError(errorMessage);
                }

            } else {
                errorMessage = messages.get("GetNoteServiceUnavailable");
                setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
                Log.logError(errorMessage);
            }
        }

        return new StringRepresentation(JSONUtil.getJSONResult(repString, successMessage, errorMessage), MediaType.APPLICATION_JSON);
    }

    /**
     * Handle POST requests: create a new note.
     * @param entity a <code>Representation</code>
     * @return a noteId as plain text and http.status SUCCESS_CREATED
     * @throws NamingException if the service cannot be found
     * @throws ServiceException if an error occur in the service
     */
    @Post
    public Representation createNote(Representation entity) throws NamingException, ServiceException  {
        Messages messages = Messages.getInstance(getRequest());
        String repString = getEmptyJSON();
        String successMessage = "";
        String errorMessage = "";
        FacebookUser fbUser = null;
        NoteUser user = null;

        JSONUtil.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");

        Form form = new Form(entity);
        String userKey = form.getFirstValue("userKey");

        if (!GenericValidator.isBlankOrNull(userKey)) {
            fbUser = userCache.getUser(userKey);
            if (fbUser != null) {
                user = new NoteUser(fbUser.getId(), fbUser.getUserIdType());
            }
        }

        String noteText = form.getFirstValue(Note.Fields.noteText.getName());

        InitialContext context = new InitialContext();
        CreateNoteService createNoteService = (CreateNoteService) context.lookup("osgi:service/org.opentaps.notes.services.CreateNoteService");

        if (createNoteService != null) {
            CreateNoteServiceInput createNoteServiceInput = new CreateNoteServiceInput();
            createNoteServiceInput.setText(noteText);
            for (String param : form.getNames()) {
                if (param.startsWith(CUSTOM_FIELD_PREFIX)) {
                    String fieldName = param.substring(CUSTOM_FIELD_PREFIX.length());
                    String value = form.getFirstValue(param);
                    createNoteServiceInput.setAttribute(fieldName, value);
                }
            }
            createNoteServiceInput.setCreatedByUser(user);
            Reference ref = getRequest().getReferrerRef();
            if (ref != null) {
                createNoteServiceInput.setClientDomain(ref.getHostDomain());
            }

            String noteId = createNoteService.createNote(createNoteServiceInput).getNoteId();

            if (!GenericValidator.isBlankOrNull(noteId)) {
                setStatus(Status.SUCCESS_CREATED);
                successMessage = messages.get("NoteCreatedSuccess");
                Log.logDebug(successMessage);
                repString = getNoteIdJSON(noteId);

                if (fbUser != null) {
                    String postOnMyWall = form.getFirstValue("postOnMyWall");
                    if ("Y".equalsIgnoreCase(postOnMyWall)) {
                        postNoteOnUserWall(fbUser.getId(), fbUser.getAccessToken(), noteText);
                        // post note on to opentaps wall
                        postNoteOnUserWall(OPENTAPS_FACEBOOK_PAGE_ID, fbUser.getAccessToken(), noteText);
                    }
                }
            } else {
                errorMessage = messages.get("CanNotCreateNote");
                setStatus(Status.SERVER_ERROR_INTERNAL);
                Log.logError(errorMessage);
            }
        } else {
            errorMessage = messages.get("CreateNoteServiceUnavailable");
            setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
            Log.logError(errorMessage);
        }

        return new StringRepresentation(JSONUtil.getJSONResult(repString, successMessage, errorMessage), MediaType.APPLICATION_JSON);
    }

    /**
     * Get JSON representation of the notes.
     * @param notes a <code>List</code> of <code>Note</code>
     * @return a <code>String</code>
     */
    private String getNotesJSON(List<Note> notes) {
        if (notes == null) {
            throw new IllegalArgumentException();
        }

        JSONBuilder jb = new JSONStringer().array();
        for (Note note : notes) {
            jb.value(JSONSerializer.toJSON(getNoteJSON(note)));
        }
        jb.endArray();
        return jb.toString();
    }

    /**
     * Get JSON representation of the note.
     * @param note a <code>Note</code>
     * @return a <code>String</code>
     */
    private String getNoteJSON(Note note) {
        if (note == null) {
            throw new IllegalArgumentException();
        }

        JSONBuilder noteBuilder = new JSONStringer()
            .object()
            .key(Note.Fields.noteId.getName()).value(note.getNoteId())
            .key(Note.Fields.noteText.getName()).value(note.getNoteText())
            .key(Note.Fields.sequenceNum.getName()).value(note.getSequenceNum())
            .key(Note.Fields.dateTimeCreated.getName()).value(note.getDateTimeCreated());

        // add user data if exist
        NoteUser user = (NoteUser) note.getCreatedByUser();
        if (user != null) {
            noteBuilder.key(Note.Fields.createdByUser.getName()).value(
                    JSONSerializer.toJSON(user.getProperties())
                    );
        }

        for (String field : note.getAttributeNames()) {
            noteBuilder.key(CUSTOM_FIELD_PREFIX + field).value(note.getAttribute(field));
        }

        return new JSONStringer()
            .object()
                .key("note")
                .value(JSONSerializer.toJSON(noteBuilder.endObject().toString()))
            .endObject()
            .toString();
    }

    /**
     * Get JSON representation of the noteId.
     * @param noteId a <code>String</code>
     * @return a <code>String</code>
     */
    private String getNoteIdJSON(String noteId) {
        if (noteId == null) {
            throw new IllegalArgumentException();
        }

        return new JSONStringer()
            .object()
                .key("note")
                .value(JSONSerializer.toJSON(new JSONStringer()
                                             .object()
                                             .key(Note.Fields.noteId.getName()).value(noteId)
                                             .endObject()
                                             .toString()))
            .endObject()
            .toString();
    }

    /**
     * Get empty JSON representation.
     * @return a <code>String</code>
     */
    private String getEmptyJSON() {
        return new JSONStringer()
            .object()
                .key("note")
                .value("")
            .endObject()
            .toString();
    }

    /**
     * Post note on the user wall.
     * @param user a <code>FacebookUser</code>
     * @param noteText a <code>String</code>
     * @return a <code>Representation</code>
     */
    private Representation postNoteOnUserWall(String userId, String accessToken ,String noteText) {
        Reference ref = new Reference(FacebookResource.FB_GRAPH_API_URL + userId + "/" + FacebookResource.FB_FEED_CALL);
        Form form = new Form();
        form.add("access_token", accessToken);
        form.add("message", noteText);
        Representation input = form.getWebRepresentation();

        ClientResource cr = new ClientResource(ref);
        Client client = new Client(new Context(), Protocol.HTTPS);
        cr.setNext(client);

        Representation rep = cr.post(input);
        return rep;
    }
}
