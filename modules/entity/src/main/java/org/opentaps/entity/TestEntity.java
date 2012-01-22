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

package org.opentaps.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;


@Entity
@Table(name = "TEST_ENTITY")
public class TestEntity implements Serializable {

    private static final long serialVersionUID = -4144678701591091589L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="uuid-type4-hex")
    @Column(name = "TEST_ID", nullable = false, length = 32)
    private String testId;

    @Column(name = "TEST_STRING_FIELD", length = 60)
    private String testStringField;

    @Column(name = "TEST_DATE_TIME_FIELD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date testDateTimeField;

    @Lob
    @Column(name = "TEST_BLOB_FIELD")
    private byte[] testBlobField;

    @Column(name = "TEST_NUMERIC_FIELD", precision = 20)
    private Long testNumericField;

    @Column(name = "TEST_FLOATING_POINT_FIELD", scale = 6, precision = 18)
    private BigDecimal testFloatingPointField;

    @Column(name = "TEST_CURRENCY_PRECISE_FIELD", scale = 3, precision = 18)
    private BigDecimal testCurrencyPreciseField;

    @Column(name = "TEST_CREDIT_CARD_NUMBER_FIELD")
    private String testCreditCardNumberField;

    @Column(name = "TEST_CREDIT_CARD_DATE_FIELD", length = 20)
    private String testCreditCardDateField;

    @Column(name = "TEST_EMAIL_FIELD")
    private String testEmailField;

    @Column(name = "TEST_URL_FIELD")
    private String testUrlField;

    @Column(name = "TEST_TELPHONE_FIELD", length = 60)
    private String testTelphoneField;

    @Column(name = "TEST_ENCRYPT")
    private String testEncrypt;                

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="ENUM_ID")
    private Enumeration testEnum;

    @OneToMany(targetEntity=org.opentaps.entity.TestEntityItem.class, mappedBy="testEntity", cascade=CascadeType.MERGE)
    private Set<TestEntityItem> items = new HashSet<TestEntityItem>();

    public TestEntity() {}

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestStringField() {
        return testStringField;
    }

    public void setTestStringField(String testStringField) {
        this.testStringField = testStringField;
    }

    public Date getTestDateTimeField() {
        return testDateTimeField;
    }

    public void setTestDateTimeField(Date testDateTimeField) {
        this.testDateTimeField = testDateTimeField;
    }

    public byte[] getTestBlobField() {
        return testBlobField;
    }

    public void setTestBlobField(byte[] testBlobField) {
        this.testBlobField = testBlobField;
    }

    public Long getTestNumericField() {
        return testNumericField;
    }

    public void setTestNumericField(Long testNumericField) {
        this.testNumericField = testNumericField;
    }

    public BigDecimal getTestFloatingPointField() {
        return testFloatingPointField;
    }

    public void setTestFloatingPointField(BigDecimal testFloatingPointField) {
        this.testFloatingPointField = testFloatingPointField;
    }

    public BigDecimal getTestCurrencyPreciseField() {
        return testCurrencyPreciseField;
    }

    public void setTestCurrencyPreciseField(BigDecimal testCurrencyPreciseField) {
        this.testCurrencyPreciseField = testCurrencyPreciseField;
    }

    public String getTestCreditCardNumberField() {
        return testCreditCardNumberField;
    }

    public void setTestCreditCardNumberField(String testCreditCardNumberField) {
        this.testCreditCardNumberField = testCreditCardNumberField;
    }

    public String getTestCreditCardDateField() {
        return testCreditCardDateField;
    }

    public void setTestCreditCardDateField(String testCreditCardDateField) {
        this.testCreditCardDateField = testCreditCardDateField;
    }

    public String getTestEmailField() {
        return testEmailField;
    }

    public void setTestEmailField(String testEmailField) {
        this.testEmailField = testEmailField;
    }

    public String getTestUrlField() {
        return testUrlField;
    }

    public void setTestUrlField(String testUrlField) {
        this.testUrlField = testUrlField;
    }

    public String getTestTelphoneField() {
        return testTelphoneField;
    }

    public void setTestTelphoneField(String testTelphoneField) {
        this.testTelphoneField = testTelphoneField;
    }

    public String getTestEncrypt() {
        return testEncrypt;
    }

    public void setTestEncrypt(String testEncrypt) {
        this.testEncrypt = testEncrypt;
    }

    public Set<TestEntityItem> getItems() {
        return items;
    }

    public void setItems(Set<TestEntityItem> items) {
        this.items = items;
    }

    public Enumeration getTestEnum() {
        return testEnum;
    }

}
