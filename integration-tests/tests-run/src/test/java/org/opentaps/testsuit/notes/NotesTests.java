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
package org.opentaps.testsuit.notes;

import java.io.BufferedReader;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONUtils;

import org.apache.commons.validator.GenericValidator;
import org.junit.Test;
import org.opentaps.notes.domain.Note;
import org.opentaps.testsuit.RemoteTestCase;
import org.restlet.data.Form;
import org.restlet.data.Parameter;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;


public class NotesTests extends RemoteTestCase {

    @Test
    public void testRemoteCreateAndRetrieveNote() throws Exception {
        final String TEXT   = "Integration Tests Test Note!";
        final String ATTR1  = "ATTR01";
        final String ATTR2  = "ATTR02";
        final String ATTR3  = "ATTR03";
        final String ATTR4  = "ATTR04";
        final String ATTR5  = "ATTR05";
        final String ATTR6  = "ATTR06";
        final String ATTR7  = "ATTR07";
        final String ATTR8  = "ATTR08";
        final String ATTR9  = "ATTR09";
        final String ATTR10 = "ATTR10";

        // work around timing issue, in some cases the test will be run before the webapp is mounted
        Thread.sleep(5000);

        // 1. Create a note.
        ClientResource postURI = new ClientResource("http://localhost:8080/notes/note");
        postURI.setNext(client);

        Form formData = new Form();
        formData.add(new Parameter(Note.Fields.noteText.getName(), TEXT));
        formData.add(new Parameter("note_field_" + ATTR1, ATTR2));
        formData.add(new Parameter("note_field_" + ATTR3, ATTR4));
        formData.add(new Parameter("note_field_" + ATTR5, ATTR6));
        formData.add(new Parameter("note_field_" + ATTR7, ATTR8));
        formData.add(new Parameter("note_field_" + ATTR9, ATTR10));

        Representation response = postURI.post(formData);
        long contentLength = response.getSize();
        assertTrue(String.format("Post request should return a note ID but content length is %1$d", contentLength), contentLength > 0);

        BufferedReader reader = new BufferedReader(response.getReader());
        String content = reader.readLine();
        assertTrue("There is no content in response", !GenericValidator.isBlankOrNull(content));
        assertTrue("It looks like the response is not a JSON string", JSONUtils.mayBeJSON(content));

        Note note = toNote(content);
        String noteId = note.getNoteId();
        assertTrue("Note ID is not found", !GenericValidator.isBlankOrNull(noteId));

        /*
         * 2. Read the note created during previous step and verify quality of the properties.
         */
        ClientResource getURI = new ClientResource(String.format("http://localhost:8080/notes/note/%1$s", noteId));
        postURI.setNext(client);
        content = getURI.get().getText();

        note = toNote(content);
        assertNotNull(note);

        assertEquals("Checking text property", TEXT, note.getNoteText());
        assertEquals("Checking attribute1 property", ATTR2, note.getAttribute(ATTR1));
        assertEquals("Checking attribute1 property", ATTR4, note.getAttribute(ATTR3));
        assertEquals("Checking attribute1 property", ATTR6, note.getAttribute(ATTR5));
        assertEquals("Checking attribute1 property", ATTR8, note.getAttribute(ATTR7));
        assertEquals("Checking attribute1 property", ATTR10, note.getAttribute(ATTR9));

        /*
         * 3. Test that validation is working, the following should fail.
         * TODO: implement actual error handling in the REST code, right now it just returns a 503 Service Unavailable error
         */
        /*formData = new Form();
        formData.add(new Parameter("attribute1", ATTR1));
        response = postURI.post(formData);*/
    }

    /**
     * Notes application responds with JSON string that meets the following:<br/>
     * <pre>
     * {"result":{"resultValue":{"note":{"noteId":"NOTEID", ...}},"successMessage":"","errorMessage":""}}"
     * </pre>
     * This method extract JSON object with key "note" and convert it into an instance of Note class.
     *
     * @param respJSON A correctly formated JSON string
     * @return The instance of {@link Note}
      */
    private Note toNote(String respJSON) {
        JSONObject contentJSON = (JSONObject) JSONSerializer.toJSON(respJSON);
        JSONObject result = contentJSON.getJSONObject("result");
        assertNotNull("Expected \"result\" object", result);
        JSONObject resultValue = result.getJSONObject("resultValue");
        assertNotNull("Expected \"resultValue\" object", resultValue);
        JSONObject note = resultValue.getJSONObject("note");
        assertNotNull("Expected \"note\" object", note);
        JSONUtils.getMorpherRegistry().registerMorpher(new org.opentaps.rest.ezmorph.TimestampMorpher(new String[] {"yyyy-MM-dd HH:mm:ss.S"}));
        return (Note) JSONObject.toBean(note, Note.class);
    }
}
