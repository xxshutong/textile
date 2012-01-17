package org.opentaps.testsuit.notes;

import org.junit.Test;
import org.opentaps.testsuit.RemoteTestCase;
import org.restlet.resource.ClientResource;


public class NotesTests extends RemoteTestCase {

    @Test
    public void testCreateNote() throws Exception {
        checkReply("/runTests?service=NotesTestService&test=createNote");
    }

    @Test
    public void testRemoteCreateAndRetriveNote() throws Exception {
        ClientResource resource = new ClientResource("http://localhost:8080/notes");
        resource.setNext(client);

        //TODO create and get a note
    }
}
