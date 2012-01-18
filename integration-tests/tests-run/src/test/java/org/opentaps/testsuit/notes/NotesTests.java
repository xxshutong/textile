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
    public void testCreateNote() throws Exception {
        checkReply("/runTests?service=NotesTestService&test=createNote");
    }

    @Test
    public void testRemoteCreateAndRetriveNote() throws Exception {
        ClientResource resource = new ClientResource("http://localhost:8080/notes/note");
        resource.setNext(client);

        Form formData = new Form();
        formData.add(new Parameter("noteText", "Test note text!"));
        Representation returns = resource.post(formData);
        long contentLength = returns.getSize();
        assertTrue(String.format("Post request should return a note ID but content length is %1$d", contentLength), contentLength > 0);
        BufferedReader reader = new BufferedReader(returns.getReader());
        String content = reader.readLine();
        assertTrue("There is no content in response", !GenericValidator.isBlankOrNull(content));

        ClientResource resource1 = new ClientResource(String.format("http://localhost:8080/notes/note/%1$s", content));
        resource.setNext(client);
        String noteJSON = resource1.get().getText();
        System.out.println(noteJSON);

        JSONObject noteArr = (JSONObject) JSONSerializer.toJSON(noteJSON);
        System.out.println(noteArr.get("note"));

        JSONObject note = noteArr.getJSONObject("note");
        System.out.println(note.toString());

        Note noteObj = (Note) JSONObject.toBean(note, Note.class);
        System.out.println(noteObj.getText());
    }
}
