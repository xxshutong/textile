package org.opentaps.notes.rest;

import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.engine.http.header.HeaderConstants;

public final class NoteUtils {

    /**
     * Set http header
     * @param response a <code>Response</code>
     * @param header <code>String</code>
     * @param value <code>String</code>
     */
    public static void setResponseHttpHeader(Response response, String header, String value ) {
        Form responseHeaders = (Form) response.getAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (responseHeaders == null)
        {
            responseHeaders = new Form();
        }
        responseHeaders.add(header, value);
        response.getAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, responseHeaders);
    }
}
