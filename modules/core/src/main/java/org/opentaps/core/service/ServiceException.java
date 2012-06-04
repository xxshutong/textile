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

/**
 * Base exception thrown by services.
 */
public class ServiceException extends Exception {

    private static final long serialVersionUID = 42L;

    private List<String> messages = null;

    /**
     * Creates new <code>ServiceException</code> without detail message.
     */
    public ServiceException() {
        super();
    }

    /**
     * Constructs an <code>ServiceException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public ServiceException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>ServiceException</code> with the specified detail message and nested Exception.
     * @param msg the detail message.
     * @param nested the nested exception.
     */
    public ServiceException(String msg, Throwable nested) {
        super(msg, nested);
    }

    /**
     * Constructs an <code>ServiceException</code> with the specified detail message and nested Exception.
     * @param nested the nested exception.
     */
    public ServiceException(Throwable nested) {
        super(nested);
    }

    /**
     * Constructs an <code>ServiceException</code> with the specified detail message, list and nested Exception.
     * @param msg the detail message.
     * @param messages error message list.
     */
    public ServiceException(String msg, List<String> messages) {
        super(msg);
        this.messages = messages;
    }

    /**
     * Constructs an <code>ServiceException</code> with the specified detail message, list and nested Exception.
     * @param msg the detail message.
     * @param messages error message list.
     * @param nested the nexted exception
     */
    public ServiceException(String msg, List<String> messages, Throwable nested) {
        super(msg, nested);
        this.messages = messages;
    }

    /**
     * Constructs an <code>ServiceException</code> with the specified detail message list and nested Exception.
     * @param messages error message list.
     * @param nested the nested exception.
     */
    public ServiceException(List<String> messages, Throwable nested) {
        super(nested);
        this.messages = messages;
    }

    /**
     * Constructs an <code>ServiceException</code> with the specified detail message list.
     * @param messages error message list.
     */
    public ServiceException(List<String> messages) {
        super();
        this.messages = messages;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder(super.getMessage());
        if (messages != null && messages.size() > 0) {
            sb.append("\n Also messages on the list:");
            for (String msg : messages) {
                sb.append(String.format("\n\t%1$s", msg));
            }
        }
        return sb.toString();
    }

    /**
     * Returns the detail message, including the message from the nested exception if there is one.
     * @return the formatted message
     */
    public String getMessageWithNested() {
        Throwable nested = getCause();
        if (nested != null) {
            if (super.getMessage() == null) {
                return nested.getMessage();
            } else {
                return super.getMessage() + " (" + nested.getMessage() + ")";
            }
        } else {
            return super.getMessage();
        }
    }

    /**
     * Returns the detail messages.
     * @return the List of message
     */
    public List<String> getMessageList() {
        return this.messages;
    }

    /**
     * Sets the detail messages.
     * @param messages the List of message
     */
    protected void setMessageList(List<String> messages) {
        this.messages = messages;
    }

    /**
     * Returns the nested exception if there is one, null if there is not.
     * @return the nested exception
     */
    public Throwable getNested() {
        Throwable nested = getCause();
        if (nested == null) {
            return this;
        }
        return nested;
    }
}
