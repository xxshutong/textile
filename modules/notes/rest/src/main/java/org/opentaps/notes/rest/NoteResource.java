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

import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONStringer;

import org.apache.commons.validator.GenericValidator;
import org.opentaps.core.log.Log;
import org.opentaps.core.service.ServiceException;
import org.opentaps.core.service.ServiceUtil;
import org.opentaps.notes.domain.Note;
import org.opentaps.notes.rest.locale.Messages;
import org.opentaps.notes.services.CreateNoteService;
import org.opentaps.notes.services.CreateNoteServiceInput;
import org.opentaps.notes.services.GetNoteByIdService;
import org.opentaps.notes.services.GetNoteByIdServiceInput;
import org.opentaps.notes.services.security.AuthorizationHelper;
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
        String organizationId = (String) getRequest().getAttributes().get("organizationId");

        if (!GenericValidator.isBlankOrNull(organizationId)) {
            AuthorizationHelper helper = (AuthorizationHelper) ServiceUtil.getService(AuthorizationHelper.class.getName());
            if (helper == null || !helper.verifyId(organizationId, noteId)) {
                setStatus(Status.CLIENT_ERROR_NOT_FOUND);
                return null;
            }
        }

        JSONUtil.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");

        InitialContext context = new InitialContext();
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
        String createdByUserId = null;
        String organizationId = (String) getRequest().getAttributes().get("organizationId");
        String userIdType = null;
        FacebookUser user = null;

        JSONUtil.setResponseHttpHeader(getResponse(), "Access-Control-Allow-Origin", "*");

        Form form = new Form(entity);
        String userKey = form.getFirstValue("userKey");

        if (!GenericValidator.isBlankOrNull(userKey)) {
            user = userCache.getUser(userKey);

            if (user != null) {
                createdByUserId = user.getId();
                userIdType = user.getUserIdType();
            }
        }

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

        InitialContext context = new InitialContext();
        CreateNoteService createNoteService = (CreateNoteService) context.lookup("osgi:service/org.opentaps.notes.services.CreateNoteService");

        if (createNoteService != null) {
            CreateNoteServiceInput createNoteServiceInput = new CreateNoteServiceInput();
            createNoteServiceInput.setText(noteText);
            createNoteServiceInput.setAttribute1(attribute1);
            createNoteServiceInput.setAttribute2(attribute2);
            createNoteServiceInput.setAttribute3(attribute3);
            createNoteServiceInput.setAttribute4(attribute4);
            createNoteServiceInput.setAttribute5(attribute5);
            createNoteServiceInput.setAttribute6(attribute6);
            createNoteServiceInput.setAttribute7(attribute7);
            createNoteServiceInput.setAttribute8(attribute8);
            createNoteServiceInput.setAttribute9(attribute9);
            createNoteServiceInput.setAttribute10(attribute10);
            createNoteServiceInput.setCreatedByUserId(createdByUserId);
            createNoteServiceInput.setUserIdType(userIdType);
            Reference ref = getRequest().getReferrerRef();
            if (ref != null) {
                createNoteServiceInput.setClientDomain(ref.getHostDomain());
            }

            String noteId = createNoteService.createNote(createNoteServiceInput).getNoteId();

            if (!GenericValidator.isBlankOrNull(noteId)) {
                if (!GenericValidator.isBlankOrNull(organizationId)) {
                    AuthorizationHelper helper = (AuthorizationHelper) ServiceUtil.getService(AuthorizationHelper.class.getName());
                    if (helper == null) {
                        setStatus(Status.SERVER_ERROR_SERVICE_UNAVAILABLE);
                        errorMessage = messages.get("CreateNoteServiceUnavailable");
                        Log.logError("CanNotLinkNoteToOrganization");
                    }
                    helper.registerId(organizationId, noteId);
                }
                setStatus(Status.SUCCESS_CREATED);
                successMessage = messages.get("NoteCreatedSuccess");
                Log.logDebug(successMessage);
                repString = getNoteIdJSON(noteId);

                if (user != null) {
                    String postOnMyWall = form.getFirstValue("postOnMyWall");
                    if ("Y".equalsIgnoreCase(postOnMyWall)) {
                        postNoteOnMyWall(user, noteText);
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
     * Get JSON representation of the note.
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
                                             .key("sequenceNum").value(note.getSequenceNum())
                                             .key("dateTimeCreated").value(note.getDateTimeCreated())
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
                                             .key("id").value(noteId)
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
     * Post note on the user wall
     * @param user a <code>FacebookUser</code>
     * @param noteText a <code>String</code>
     * @return a <code>Representation</code>
     */
    private Representation postNoteOnMyWall(FacebookUser user, String noteText) {
        Reference ref = new Reference(FacebookResource.FB_GRAPH_API_URL + user.getId() + "/"+ FacebookResource.FB_FEED_CALL);
        Form form = new Form();
        form.add("access_token", user.getAccessToken());
        form.add("message", noteText);
        Representation input = form.getWebRepresentation();

        ClientResource cr = new ClientResource(ref);
        Client client = new Client(new Context(), Protocol.HTTPS);
        cr.setNext(client);

        Representation rep = cr.post(input);
        return rep;
    }
}
