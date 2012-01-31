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
package org.opentaps.rest.ezmorph;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.object.AbstractObjectMorpher;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A morpher based on net.sf.ezmorph.object.DateMorpher but that can convert java.sql.Timestamp values.
 */
public class TimestampMorpher extends AbstractObjectMorpher {

    private Timestamp defaultValue;
    private String[] formats;
    private boolean lenient;
    private Locale locale;

    /**
     * Creates a new <code>TimestampMorpher</code> instance.
     * @param formats an array of format strings (eg: "yyyy-MM-dd HH:mm:ss")
     */
    public TimestampMorpher(String[] formats) {
        this(formats, Locale.getDefault(), false);
    }

    /**
     * Creates a new <code>TimestampMorpher</code> instance.
     * @param formats an array of format strings (eg: "yyyy-MM-dd HH:mm:ss")
     * @param lenient if the date parser should be lenient
     */
    public TimestampMorpher(String[] formats, boolean lenient) {
        this(formats, Locale.getDefault(), lenient);
    }

    /**
     * Creates a new <code>TimestampMorpher</code> instance.
     * @param formats an array of format strings (eg: "yyyy-MM-dd HH:mm:ss")
     * @param defaultValue a <code>Timestamp</code> value
     */
    public TimestampMorpher(String[] formats, Timestamp defaultValue) {
        this(formats, defaultValue, Locale.getDefault(), false);
    }

    /**
     * Creates a new <code>TimestampMorpher</code> instance.
     * @param formats an array of format strings (eg: "yyyy-MM-dd HH:mm:ss")
     * @param defaultValue a <code>Timestamp</code> value
     * @param locale a <code>Locale</code> value
     * @param lenient if the date parser should be lenient
     */
    public TimestampMorpher(String[] formats, Timestamp defaultValue, Locale locale, boolean lenient) {
        super(true);
        if (formats == null || formats.length == 0) {
            throw new MorphException("invalid array of formats");
        }
        // should use defensive copying ?
        this.formats = formats;

        if (locale == null) {
            this.locale = Locale.getDefault();
        } else {
            this.locale = locale;
        }

        this.lenient = lenient;
        setDefaultValue(defaultValue);
    }

    /**
     * Creates a new <code>TimestampMorpher</code> instance.
     * @param formats an array of format strings (eg: "yyyy-MM-dd HH:mm:ss")
     * @param locale a <code>Locale</code> value
     */
    public TimestampMorpher(String[] formats, Locale locale) {
        this(formats, locale, false);
    }

    /**
     * Creates a new <code>TimestampMorpher</code> instance.
     * @param formats an array of format strings (eg: "yyyy-MM-dd HH:mm:ss")
     * @param locale a <code>Locale</code> value
     * @param lenient a <code>boolean</code> value
     */
    public TimestampMorpher(String[] formats, Locale locale, boolean lenient) {
        if (formats == null || formats.length == 0) {
            throw new MorphException("invalid array of formats");
        }
        // should use defensive copying ?
        this.formats = formats;

        if (locale == null) {
            this.locale = Locale.getDefault();
        } else {
            this.locale = locale;
        }

        this.lenient = lenient;
    }

    /**
     * Returns the <code>defaultValue</code>, which is the value set if the input could not be parsed.
     * @return a <code>Timestamp</code> value
     */
    public Timestamp getDefaultValue() {
        return (Timestamp) defaultValue.clone();
    }

    /**
     * Sets the <code>defaultValue</code>, which is the value set if the input could not be parsed.
     * @param defaultValue a <code>Timestamp</code> value
     */
    public void setDefaultValue(Timestamp defaultValue) {
        this.defaultValue = (Timestamp) defaultValue.clone();
    }

    @Override
    public Class morphsTo() {
        return Timestamp.class;
    }

    @Override
    public Object morph(Object value) {
        if (value == null) {
            return null;
        }

        if (Timestamp.class.isAssignableFrom(value.getClass())) {
            return value;
        }

        if (!supports(value.getClass())) {
            throw new MorphException(value.getClass() + " is not supported");
        }

        String strValue = (String) value;
        SimpleDateFormat dateParser = null;

        for (int i = 0; i < formats.length; i++) {
            if (dateParser == null) {
                dateParser = new SimpleDateFormat(formats[i], locale);
            } else {
                dateParser.applyPattern(formats[i]);
            }
            dateParser.setLenient(lenient);
            try {
                return new Timestamp(dateParser.parse(strValue.toLowerCase()).getTime());
            } catch (ParseException pe) {
                // ignore exception, try the next format
            }
        }

        // unable to parse the date
        if (isUseDefault()) {
            return defaultValue;
        } else {
            throw new MorphException("Unable to parse the date " + value);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof TimestampMorpher)) {
            return false;
        }

        TimestampMorpher other = (TimestampMorpher) obj;
        EqualsBuilder builder = new EqualsBuilder();
        builder.append(formats, other.formats);
        builder.append(locale, other.locale);
        builder.append(lenient, other.lenient);
        if (isUseDefault() && other.isUseDefault()) {
            builder.append(getDefaultValue(), other.getDefaultValue());
            return builder.isEquals();
        } else if (!isUseDefault() && !other.isUseDefault()) {
            return builder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(formats);
        builder.append(locale);
        builder.append(lenient);
        if (isUseDefault()) {
            builder.append(getDefaultValue());
        }
        return builder.toHashCode();
    }
}
