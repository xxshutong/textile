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
package org.opentaps.tests;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;

public class OpentapsTestCase extends BaseTestCase {

    // Since financial amounts and most business values are rounded to 2 decimal places this way,
    // we set these as the universal rounding settings for all tests.  Create your own rounding settings
    // if you need something different.
    public static final int DECIMALS = 2;
    public static final int ROUNDING = BigDecimal.ROUND_HALF_EVEN;

    /**
     * Helper method to get byte[] from a file.
     *
     * @param f a <code>File</code> value
     * @return a <code>byte[]</code> value
     * @throws IOException if an error occurs
     */
    public static byte[] getBytesFromFile(File f) throws IOException {
        if (f == null) {
            return null;
        }

        FileInputStream stream = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] b = new byte[1024];
        int n;
        while ((n = stream.read(b)) != -1) {
            out.write(b, 0, n);
        }
        stream.close();
        out.close();
        return out.toByteArray();
    }

}
