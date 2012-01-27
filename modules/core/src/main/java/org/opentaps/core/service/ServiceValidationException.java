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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * Exception thrown by services if the validation of input or output failed.
 */
public class ServiceValidationException extends ServiceException {

    private static final long serialVersionUID = 43L;

    /**
     * Describe a validation error on a particular field.
     */
    public static class FieldError implements Serializable {

        private static final long serialVersionUID = 4301L;

        private String field;
        private Object value;
        private String message;

        /**
         * Creates a new <code>FieldError</code> instance.
         * @param field a <code>String</code> value
         * @param value an <code>Object</code> value
         * @param message a <code>String</code> value
         */
        public FieldError(String field, Object value, String message) {
            this.field = field;
            this.value = value;
            this.message = message;
        }

        /**
         * Creates a new <code>FieldError</code> instance.
         * @param violation a <code>ConstraintViolation</code> value
         */
        public FieldError(ConstraintViolation<?> violation) {
            this.field = violation.getPropertyPath().toString();
            this.value = violation.getInvalidValue();
            this.message = violation.getMessage();
        }

        /**
         * Gets the name of the field that failed validation.
         * @return a <code>String</code> value
         */
        public String getField() { return this.field; }

        /**
         * Gets the value that failed validation.
         * @return an <code>Object</code> value
         */
        public Object getValue() { return this.value; }

        /**
         * Gets the message describing the validation error.
         * @return a <code>String</code> value
         */
        public String getMessage() { return this.message; }
    }

    private Set<FieldError> fieldErrors;;

    /**
     * Creates new <code>ServiceValidationException</code> without detail message.
     */
    public ServiceValidationException() {
        super();
    }

    /**
     * Creates new <code>ServiceValidationException</code> without detail message.
     * @param violations a collection of JSR303 <code>ConstraintViolation</code>
     */
    public ServiceValidationException(Collection<ConstraintViolation<?>> violations) {
        super();
        translate(violations);
    }

    /**
     * Creates new <code>ServiceValidationException</code> with detail message and collection of JSR303 <code>ConstraintViolation</code>.
     * @param msg the detail message.
     * @param violations a collection of JSR303 <code>ConstraintViolation</code>
     */
    public ServiceValidationException(String msg, Collection<ConstraintViolation<?>> violations) {
        super(msg);
        translate(violations);
    }

    /**
     * Creates new <code>ServiceValidationException</code> with detail message and collection of JSR303 <code>ConstraintViolation</code>.
     * @param msg the detail message.
     * @param exception a JSR303 <code>ConstraintViolationException</code>
     */
    public ServiceValidationException(String msg, ConstraintViolationException exception) {
        super(msg, exception);
        translate(exception.getConstraintViolations());
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
     * @param violations a collection of JSR303 <code>ConstraintViolation</code>
     */
    protected void translate(Collection<ConstraintViolation<?>> violations) {
        // build the message list from the list of validation error
        List<String> messages = new ArrayList<String>();
        fieldErrors = new HashSet<FieldError>();
        for (ConstraintViolation<?> violation : violations) {
            StringBuilder sb = new StringBuilder();
            sb.append(violation.getPropertyPath()).append(" : ").append(" was given \"").append(violation.getInvalidValue()).append("\", ").append(violation.getMessage());
            messages.add(sb.toString());
            fieldErrors.add(new FieldError(violation));
        }
        setMessageList(messages);
    }

    /**
     * Gets the Field Errors that caused the exception.
     * @return a <code>Set<FieldError></code> value
     */
    public Set<FieldError> getFieldErrors() {
        return fieldErrors;
    }

}
