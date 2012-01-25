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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.validation.ConstraintViolation;

/**
 * Exception thrown by services if the validation of input or output failed.
 */
public class ServiceValidationException extends ServiceException {

    private static final long serialVersionUID = 43L;

    /**
     * Creates new <code>ServiceValidationException</code> without detail message.
     */
    public ServiceValidationException() {
        super();
    }

    /**
     * Creates new <code>ServiceValidationException</code> without detail message.
     * @param <T> a service parameter class
     * @param violations a collection of JSR303 <code>ConstraintViolation</code>
     */
    public <T> ServiceValidationException(Collection<ConstraintViolation<T>> violations) {
        super();
        translate(violations);
    }

    /**
     * Creates new <code>ServiceValidationException</code> with detail message and collection of JSR303 <code>ConstraintViolation</code>.
     * @param <T> a service parameter class
     * @param msg the detail message.
     * @param violations a collection of JSR303 <code>ConstraintViolation</code>
     */
    public <T> ServiceValidationException(String msg, Collection<ConstraintViolation<T>> violations) {
        super(msg);
        translate(violations);
    }

    /**
     * Constructs an <code>ServiceValidationException</code> without detail message and list.
     * @param messages error message list.
     */
    public ServiceValidationException(List<String> messages) {
        super();
        setMessageList(messages);
    }

    /**
     * Constructs an <code>ServiceValidationException</code> with the specified detail message and list.
     * @param msg the detail message.
     * @param messages error message list.
     */
    public ServiceValidationException(String msg, List<String> messages) {
        super(msg);
        setMessageList(messages);
    }

    /**
     * Translates a collection of JSR303 <code>ConstraintViolation</code> into the generic list of strings.
     * @param <T> a service parameter class
     * @param violations a collection of JSR303 <code>ConstraintViolation</code>
     */
    protected <T> void translate(Collection<ConstraintViolation<T>> violations) {
        // build the message list from the list of validation error
        List<String> messages = new ArrayList<String>();
        for (ConstraintViolation<T> violation : violations) {
            messages.add(violation.getMessage());
        }
        setMessageList(messages);
    }

}
