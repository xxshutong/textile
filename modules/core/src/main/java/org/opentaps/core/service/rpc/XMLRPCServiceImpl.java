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
package org.opentaps.core.service.rpc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.AsyncCallback;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfig;
import org.opentaps.core.service.XMLRPCService;


public class XMLRPCServiceImpl implements XMLRPCService {
    XmlRpcClientConfig config;
    XmlRpcClient client;

    /** {@inheritDoc} */
    public Object execute(String methodName, List<?> params) throws XmlRpcException {
        return execute(null, methodName, params);
    }

    /** {@inheritDoc} */
    public Object execute(XmlRpcClientConfig config, String methodName, List<?> params) throws XmlRpcException {
        return execute(config, methodName, params, null);
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Map<String, ?> execute(String serviceName, Map<String, Object> context) throws XmlRpcException {
        Map<String, Object> callCtx = context;
        if (callCtx == null) {
            callCtx = new HashMap<String, Object>();
        }

        return (Map<String, ?>) execute(serviceName, Arrays.asList(context));
    }

    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public Map<String, ?> execute(XmlRpcClientConfig config, String serviceName, Map<String, Object> context) throws XmlRpcException {
        return (Map<String, ?>) execute(config, serviceName, Arrays.asList(context));
    }

    /** {@inheritDoc} */
    public void executeAsync(String methodName, List<?> params, AsyncCallback callback) throws XmlRpcException {
        executeAsync(null, methodName, params, callback);
    }

    /** {@inheritDoc} */
    public void executeAsync(XmlRpcClientConfig config, String methodName, List<?> params, AsyncCallback callback) throws XmlRpcException {
        execute(config, methodName, params, callback);
    }

    /** {@inheritDoc} */
    public void setConfig(XmlRpcClientConfig config) {
        this.config = config;
        if (client != null) {
            client.setConfig(this.config);
        }
    }

    /** {@inheritDoc} */
    public XmlRpcClientConfig getConfig() {
        return config;
    }

    /**
     * Performs actual request selecting configuration and sync/async patter according to
     * arguments.
     * 
     * @param config The request configuration. Use default if it is <code>null</code>.
     * @param methodName The method being performed.
     * @param params The parameters.
     * @param callback The callback being notified when the request is finished. Perform sync request if it is <code>null</code>.
     * @return The result object or <code>null</code> in case async request.
     * @throws XmlRpcException
     */
    private Object execute(XmlRpcClientConfig config, String methodName, List<?> params, AsyncCallback callback) throws XmlRpcException {
        if (GenericValidator.isBlankOrNull(methodName)) {
            throw new IllegalArgumentException("Missing required argument \"methodName\".");
        }
        if (GenericValidator.isBlankOrNull(methodName)) {
            throw new IllegalArgumentException("Missing parameters to call remote method \"methodName\".");
        }
        if (config == null && this.config == null) {
            throw new IllegalArgumentException("Not present any configuration in argument or default config.");
        }

        if (client == null) {
            client = new XmlRpcClient();
            if (this.config != null) {
                client.setConfig(this.config);
            }
        }

        XmlRpcClientConfig oldConfig = null;
        if (config != null) {
            oldConfig = getConfig();
            client.setConfig(config);
        };

        Object result = null;
        if (callback == null) {
            result = client.execute(methodName, params);
        } else {
            client.executeAsync(methodName, params, callback);
        }

        if (oldConfig != null) {
            client.setConfig(oldConfig);
        }

        return result;
    }


}
