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
package org.opentaps.validation.impl;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.opentaps.validation.services.ValidationService;


public class ValidationServiceTestImpl implements ValidationService {

    private ValidatorFactory factory;
    private Configuration<?> config;

    public ValidationServiceTestImpl() {
    }

    // Note: although it would be cleaner to get the factory in a static way, this has to be initialized this way
    // as there seem to be some timing issues in OSGI .. eg: during testing static init, i got the correct Validator instance
    // but it would not actually validate anything for some reasons
    public Validator getValidator() {
        if (factory == null) {
            synchronized(this) {
                if (factory == null) {
                    config = Validation.byDefaultProvider()
                        .providerResolver(new BValValidationServiceResolver())
                        .configure();
                    factory = config.buildValidatorFactory();
                }
            }
        }

        return factory.getValidator();
    }

}
