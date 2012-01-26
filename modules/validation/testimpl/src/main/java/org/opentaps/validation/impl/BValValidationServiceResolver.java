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

import java.util.ArrayList;
import java.util.List;
import javax.validation.ValidationProviderResolver;
import javax.validation.spi.ValidationProvider;

/**
 * A provider of the apache bval validation for bootstrapping Bean Validation in the tests.
 * In geronimo the same implementation (apache bval) is provided by default
 */
public class BValValidationServiceResolver implements ValidationProviderResolver {


    /** {@inheritDoc} */
    public List<ValidationProvider<?>> getValidationProviders() {
        List<ValidationProvider<?>> providers = new ArrayList<ValidationProvider<?>>();
        providers.add(new org.apache.bval.jsr303.ApacheValidationProvider());
        return providers;
    }

}
