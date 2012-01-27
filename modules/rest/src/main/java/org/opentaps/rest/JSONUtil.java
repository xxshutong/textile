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
package org.opentaps.rest;

import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONBuilder;
import net.sf.json.util.JSONStringer;
import org.restlet.Response;
import org.restlet.data.Form;
import org.restlet.engine.http.header.HeaderConstants;


/**
 * Utility class for JSON related output.
 */
public final class JSONUtil {

    private JSONUtil() { }

    /**
     * Set http header.
     * @param response a <code>Response</code>
     * @param header <code>String</code>
     * @param value <code>String</code>
     */
    public static void setResponseHttpHeader(Response response, String header, String value) {
        Form responseHeaders = (Form) response.getAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
        if (responseHeaders == null) {
            responseHeaders = new Form();
        }
        responseHeaders.add(header, value);
        response.getAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, responseHeaders);
    }

    /**
     * Get a standard JSON response.
     * With a "result" object containing the following:
     *  "resultValue" an Object that contains the results
     *  "successMessage" a String that should be displayed to the user
     *  "errorMessage" a String that should be displayed to the user
     *  "errorDetail" a list of detail Objects, for example field validation error data
     * @param resultValue <code>String</code>
     * @param successMessage <code>String</code>
     * @param errorMessage <code>String</code>
     * @param errorDetail an <code>Object</code> value
     * @return <code>String</code>
     */
    public static String getJSONResult(String resultValue, String successMessage, String errorMessage, Object errorDetail) {
        JSONBuilder result = new JSONStringer().object();

        if (resultValue != null) {
            result = result.key("resultValue").value(JSONSerializer.toJSON(resultValue));
        }
        if (successMessage != null) {
            result = result.key("successMessage").value(successMessage);
        }
        if (errorMessage != null) {
            result = result.key("errorMessage").value(errorMessage);
        }
        if (errorDetail != null) {
            result = result.key("errorDetail").value(JSONSerializer.toJSON(errorDetail));
        }

        result = result.endObject();

        JSONBuilder json = new JSONStringer()
            .object()
            .key("result")
            .value(JSONSerializer.toJSON(result.toString()))
            .endObject();

        return json.toString();
    }

    /**
     * Get a standard JSON response.
     * With a "result" object containing the following:
     *  "resultValue" an Object that contains the results
     *  "successMessage" a String that should be displayed to the user
     *  "errorMessage" a String that should be displayed to the user
     * @param resultValue <code>String</code>
     * @param successMessage <code>String</code>
     * @param errorMessage <code>String</code>
     * @return <code>String</code>
     */
    public static String getJSONResult(String resultValue, String successMessage, String errorMessage) {
        return getJSONResult(resultValue, successMessage, errorMessage, null);
    }

    /**
     * Get a standard JSON success response.
     * With a "result" object containing the following:
     *  "resultValue" an Object that contains the results
     *  "successMessage" a String that should be displayed to the user
     * @param resultValue <code>String</code>
     * @param successMessage <code>String</code>
     * @return <code>String</code>
     */
    public static String getJSONSuccess(String resultValue, String successMessage) {
        return getJSONResult(resultValue, successMessage, null, null);
    }

    /**
     * Get a standard JSON error response.
     * With a "result" object containing the following:
     *  "errorMessage" a String that should be displayed to the user
     *  "errorDetail" a list of detail Objects, for example field validation error data
     * @param errorMessage <code>String</code>
     * @param errorDetail an <code>Object</code> value
     * @return <code>String</code>
     */
    public static String getJSONError(String errorMessage, Object errorDetail) {
        return getJSONResult(null, null, errorMessage, errorDetail);
    }

    /**
     * Get a standard JSON error response.
     * With a "result" object containing the following:
     *  "errorMessage" a String that should be displayed to the user
     * @param errorMessage <code>String</code>
     * @return <code>String</code>
     */
    public static String getJSONError(String errorMessage) {
        return getJSONError(errorMessage, null);
    }
}
