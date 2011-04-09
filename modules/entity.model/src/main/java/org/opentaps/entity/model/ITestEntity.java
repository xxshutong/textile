/*
 * Copyright (c) 2011 Open Source Strategies, Inc.
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
package org.opentaps.entity.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


public interface ITestEntity {

    public String getTestId();

    public void setTestId(String testId);

    public String getTestStringField();

    public void setTestStringField(String testStringField);

    public Date getTestDateTimeField();

    public void setTestDateTimeField(Date testDateTimeField);

    public byte[] getTestBlobField();

    public void setTestBlobField(byte[] testBlobField);

    public Long getTestNumericField();

    public void setTestNumericField(Long testNumericField);

    public BigDecimal getTestFloatingPointField();

    public void setTestFloatingPointField(BigDecimal testFloatingPointField);

    public BigDecimal getTestCurrencyPreciseField();

    public void setTestCurrencyPreciseField(BigDecimal testCurrencyPreciseField);

    public String getTestCreditCardNumberField();

    public void setTestCreditCardNumberField(String testCreditCardNumberField);

    public String getTestCreditCardDateField();

    public void setTestCreditCardDateField(String testCreditCardDateField);

    public String getTestEmailField();

    public void setTestEmailField(String testEmailField);

    public String getTestUrlField();

    public void setTestUrlField(String testUrlField);

    public String getTestTelphoneField();

    public void setTestTelphoneField(String testTelphoneField);

    public String getTestEncrypt();

    public void setTestEncrypt(String testEncrypt);

    public Set<ITestEntityItem> getItems();

    public void setItems(Set<ITestEntityItem> items);

    public IEnumeration getTestEnum();
}
