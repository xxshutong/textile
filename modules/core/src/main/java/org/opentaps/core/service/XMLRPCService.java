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
package org.opentaps.core.service; 

import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.AsyncCallback;
import org.apache.xmlrpc.client.XmlRpcClientConfig;


/**
 * Generic service to call remote servers using XML-RPC protocol.
 * Methods mostly duplicates those in <code>XmlRpcClient</code>. 
 */
public interface XMLRPCService {

    /**
     * Sets default configuration.
     * 
     * @param config The default request configuration
     */
    public void setConfig(XmlRpcClientConfig config);

    /**
     * Returns default configuration.
     * 
     * @return The default request configuration
     */
    public XmlRpcClientConfig getConfig();

    /**
     * Performs a request with the clients default configuration.
     * 
     * @param methodName The method being performed.
     * @param params The parameters.
     * @return The result object. 
     * @throws XmlRpcException
     */
    public Object execute(String methodName, List<?> params) throws XmlRpcException;

    /**
     * Performs a request with the given configuration.
     * 
     * @param config The request configuration.
     * @param methodName The method being performed.
     * @param params The parameters.
     * @return The result object. 
     * @throws XmlRpcException
     */
    public Object execute(XmlRpcClientConfig config, String methodName, List<?> params) throws XmlRpcException;

    /**
     * Performs a request with the clients default configuration and Apache OfBiz syntax.
     * 
     * @param serviceName Remote service name.
     * @param context The arguments.
     * @return The result map.
     * @throws XmlRpcException
     */
    public Map<String, ?> execute(String serviceName, Map<String, Object> context) throws XmlRpcException;

    /**
     * Performs a request with the given configuration and Apache OfBiz syntax.
     * 
     * @param config The request configuration.
     * @param serviceName Remote service name.
     * @param context The arguments.
     * @return The result map.
     * @throws XmlRpcException
     */
    public Map<String, ?> execute(XmlRpcClientConfig config, String serviceName, Map<String, Object> context) throws XmlRpcException;

    /**
     * Performs an asynchronous request with the clients default configuration.
     * 
     * @param methodName The method being performed.
     * @param params The parameters.
     * @param callback The callback being notified when the request is finished.
     * @throws XmlRpcException
     */
    public void executeAsync(String methodName, List<?> params, AsyncCallback callback) throws XmlRpcException;

    /**
     * Performs an asynchronous request with the given configuration.
     * 
     * @param config The request configuration.
     * @param methodName
     * @param params The parameters.
     * @param callback The callback being notified when the request is finished.
     * @throws XmlRpcException
     */
    public void executeAsync(XmlRpcClientConfig config, String methodName, List<?> params, AsyncCallback callback) throws XmlRpcException;

}
