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
package org.opentaps.core;

import org.opentaps.core.bundle.AbstractBundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class Activator extends AbstractBundle {

    // the shared instance
    private static BundleActivator bundle;

    /** {@inheritDoc} */
    public void start(BundleContext context) throws Exception {

        bundle = this;
        super.start(context);
    }

    /** {@inheritDoc} */
    public void stop(BundleContext context) throws Exception {

        super.stop(context);
        bundle = null;
    }

    public static Activator getInstance() {
        return (Activator) bundle;
    };

}
