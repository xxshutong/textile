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
package org.opentaps.notes.rest.locale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.validator.GenericValidator;
import org.opentaps.core.log.Log;
import org.restlet.Request;
import org.restlet.data.ClientInfo;
import org.restlet.data.Language;
import org.restlet.data.Preference;


public class Messages {
    private static final String BUNDLE_NAME = "org.opentaps.notes.rest.locale.messages";
    private ResourceBundle resourceBundle;


    private Messages (ResourceBundle rb) {
        resourceBundle = rb;
    }

    public String get(String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    public String getMsg(String key, Object...arguments) {
        try {
            Locale locale = resourceBundle.getLocale();
            if (locale == null || GenericValidator.isBlankOrNull(locale.toString())) {
                locale = Locale.getDefault();
            }
            return String.format(locale, resourceBundle.getString(key), arguments);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    /**
     * Returns class initialized with resource bundle of the requested locale.<br>
     * First we check URL for embed locale identifier that equals to Locale valid string representation.<br>
     * Example of encoding locale with URL is: <code>/notes/note/fr_fr/noteID</code><br>
     * If not found we have to check <code>Accept-Language</code> header and select first available language
     * looking through the list in the natural order.   
     */
    public static Messages getInstance(Request request) {
        List<Locale> localeList = null;
        // get encoded locale
        String lang = (String) request.getAttributes().get("lang");
        if (!GenericValidator.isBlankOrNull(lang)) {
            try {
                Locale locale = new Locale(lang);
                localeList = Arrays.asList(locale);
            } catch (NullPointerException e) {
                //do nothing
            }
        }

        // get languages from Accept-Language
        if (localeList == null || localeList.size() == 0) {
            ClientInfo clientInfo = request.getClientInfo();
            List<Preference<Language>> preferences = clientInfo.getAcceptedLanguages();
            localeList = new ArrayList<Locale>(preferences.size());
            for (Preference<Language> pref : preferences) {
                localeList.add(new Locale(pref.getMetadata().toString()));
            }
        }

        // looking for the first available
        if (localeList != null && localeList.size() > 0) {
            ResourceBundle.clearCache();
            for (Locale locale : localeList) {
                try {
                    String langCode = locale.toString().replaceAll("-", "_");
                    ResourceBundle rb = ResourceBundle.getBundle(BUNDLE_NAME, locale);
                    Locale rbLocale = rb.getLocale();
                    if (rbLocale == null || GenericValidator.isBlankOrNull(rbLocale.toString())) {
                        rbLocale = Locale.getDefault();
                    }
                    if (langCode.equalsIgnoreCase(rbLocale.toString())) {
                        return new Messages(rb);
                    } else {
                        continue;
                    }
                } catch (MissingResourceException e) {
                    Log.logWarning(e.getLocalizedMessage());
                    continue;
                }
            }
        }

        return new Messages(ResourceBundle.getBundle(BUNDLE_NAME));
    }
}
