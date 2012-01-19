package org.opentaps.testsuit.notes;

import java.io.BufferedReader;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

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
    
        // 1. Create a note.
        ClientResource postURI = new ClientResource("http://localhost:8080/notes/note");
        postURI.setNext(client);

        Form formData = new Form();
        formData.add(new Parameter("noteText", TEXT));
        formData.add(new Parameter("attribute1", ATTR1));
        formData.add(new Parameter("attribute2", ATTR2));
        formData.add(new Parameter("attribute3", ATTR3));
        formData.add(new Parameter("attribute4", ATTR4));
        formData.add(new Parameter("attribute5", ATTR5));
        formData.add(new Parameter("attribute6", ATTR6));
        formData.add(new Parameter("attribute7", ATTR7));
        formData.add(new Parameter("attribute8", ATTR8));
        formData.add(new Parameter("attribute9", ATTR9));
        formData.add(new Parameter("attribute10", ATTR10));

        Representation response = postURI.post(formData);
        long contentLength = response.getSize();
        assertTrue(String.format("Post request should return a note ID but content length is %1$d", contentLength), contentLength > 0);

        BufferedReader reader = new BufferedReader(response.getReader());
        String content = reader.readLine();
        assertTrue("There is no content in response", !GenericValidator.isBlankOrNull(content));

        JSONObject noteIdJSON = (JSONObject) JSONSerializer.toJSON(content);

        /*
         * 2. Read the note created during previous step and verify quality of the properties.
         */
        ClientResource getURI = new ClientResource(String.format("http://localhost:8080/notes/note/%1$s", noteIdJSON.getString("id")));
        postURI.setNext(client);
        String noteJSON = getURI.get().getText();

        JSONObject noteArr = (JSONObject) JSONSerializer.toJSON(noteJSON);
        assertNotNull(noteArr);
        JSONObject note = noteArr.getJSONObject("note");
        assertNotNull(note);

        Note noteObj = (Note) JSONObject.toBean(note, Note.class);
        assertNotNull(noteObj);

        assertEquals("Checking text property", TEXT, noteObj.getText());
        assertEquals("Checking attribute1 property", ATTR1, noteObj.getAttribute1());
        assertEquals("Checking attribute2 property", ATTR2, noteObj.getAttribute2());
        assertEquals("Checking attribute3 property", ATTR3, noteObj.getAttribute3());
        assertEquals("Checking attribute4 property", ATTR4, noteObj.getAttribute4());
        assertEquals("Checking attribute5 property", ATTR5, noteObj.getAttribute5());
        assertEquals("Checking attribute6 property", ATTR6, noteObj.getAttribute6());
        assertEquals("Checking attribute7 property", ATTR7, noteObj.getAttribute7());
        assertEquals("Checking attribute8 property", ATTR8, noteObj.getAttribute8());
        assertEquals("Checking attribute9 property", ATTR9, noteObj.getAttribute9());
        assertEquals("Checking attribute10 property", ATTR10, noteObj.getAttribute10());
    }
}
