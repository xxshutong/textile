package org.opentaps.notes.rest;

import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONStringer;

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

    /**
     * Get response JSON result
     * @param resultValue <code>String</code>
     * @param succesMessage <code>String</code>
     * @param errorMessage <code>String</code>
     * @return <code>String</code>
     */
    public static String getResultJSON(String resultValue, String succesMessage, String errorMessage) {
        if (resultValue == null || succesMessage ==  null || errorMessage == null) {
            throw new IllegalArgumentException();
        }

        JSONBuilder json = new JSONStringer()
            .object()
                .key("result")
                    .value(JSONSerializer.toJSON(new JSONStringer()
                            .object()
                            .key("resultValue").value(JSONSerializer.toJSON(resultValue))
                            .key("successMessage").value(succesMessage)
                            .key("errorMessage").value(errorMessage)
                            .endObject()
                            .toString()))
            .endObject();

        return json.toString();
    }
}
