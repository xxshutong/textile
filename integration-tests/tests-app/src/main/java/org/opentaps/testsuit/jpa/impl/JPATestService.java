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
package org.opentaps.testsuit.jpa.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.CRC32;

import javax.persistence.EntityManager;

import org.opentaps.entity.TestEntity;
import org.opentaps.tests.OpentapsTestCase;
import org.opentaps.testsuit.jpa.IJPATestService;


public class JPATestService extends OpentapsTestCase implements IJPATestService {

    private EntityManager em;

    /* {@inheritDoc} */
    public void insertTestEntity() throws Exception {

        // 1. Create a TestEntity
        TestEntity e = new TestEntity();
        e.setTestStringField("insertTestEntity string field");
        e.setTestDateTimeField(new Date());
        em.persist(e);
        em.flush();

        String testId = e.getTestId();

        // 2. Clear persistence context
        em.clear();

        // 3. Ensure it was really created
        TestEntity testEntity = em.find(TestEntity.class, testId);
        assertNotNull("The newly created test entity is not found.", testEntity);
    }

    /* {@inheritDoc} */
    public void updateTestEntity() throws Exception {
        final String INITIAL_VAL = "INITIAL";
        final String FINAL_VAL = "FINAL";

        // 1. Create a TestsEntity, assign some value to the string property.
        TestEntity e = new TestEntity();
        e.setTestStringField(INITIAL_VAL);
        e.setTestDateTimeField(new Date());
        em.persist(e);
        em.flush();

        String testId = e.getTestId();

        // 2. Change the string property value and update entity.
        e.setTestStringField(FINAL_VAL);
        em.flush();

        // 3. Clear persistence context
        em.clear();

        // 4. Find the entity and verify string property equals to last value.
        TestEntity testEntity = em.find(TestEntity.class, testId);
        assertNotNull("The newly created test entity is not found.", testEntity);
        assertEquals("Correct value should is final string value", FINAL_VAL, testEntity.getTestStringField());

    }

    /* {@inheritDoc} */
    public void removeTestEntity() throws Exception {
        // 1. Create a TestEntity
        TestEntity e = new TestEntity();
        e.setTestStringField("removeTestEntity string field");
        e.setTestDateTimeField(new Date());
        em.persist(e);
        em.flush();

        String testId = e.getTestId();
        assertNotNull("The newly created test entity is not found.", em.find(TestEntity.class, testId));

        // 2. Remove created entity.
        em.remove(e);
        em.flush();

        em.clear();

        // 3. Try to find to be sure it does not exist.
        assertNull("Test entity should be removed but it exists.", em.find(TestEntity.class, testId));
    }

    /* {@inheritDoc} */
    public void allMajorFieldTypes() throws Exception {
        String testStringField = "test string";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date testDateTimeField = sdf.parse("2009-3-11 23:45:13");

        Long testNumericField = new Long(123456789);
        BigDecimal testFloatingPointField = new BigDecimal("98765432.12").setScale(DECIMALS, ROUNDING);
        // store a file into the blob
        //File file = new File("/home/oandreyev/marathon.xls");
        //byte[] data = getBytesFromFile(file);
        //String oldCRCCode = getCRCCode(data);

        String testCreditCardNumberField = "4013 8663 6050 0822";
        String testCreditCardDateField = "11/10";
        String testEmailField = "test@opensourcestrategies.com";
        String testUrlField = "http://www.opentaps.org";
        String testTelphoneField = "1 310 4512-4875";

        // create a TestEntity and set field values
        TestEntity testEntity = new TestEntity();
        //testEntity.setTestBlobField(data);
        testEntity.setTestCreditCardDateField(testCreditCardDateField);
        testEntity.setTestCreditCardNumberField(testCreditCardNumberField);
        testEntity.setTestDateTimeField(testDateTimeField);
        testEntity.setTestEmailField(testEmailField);
        testEntity.setTestFloatingPointField(testFloatingPointField);
        testEntity.setTestNumericField(testNumericField);
        testEntity.setTestStringField(testStringField);
        testEntity.setTestTelphoneField(testTelphoneField);
        testEntity.setTestUrlField(testUrlField);
        em.persist(testEntity);
        em.flush();
        em.clear();

        // reload testEntity from database
        testEntity = (TestEntity) em.find(TestEntity.class, testEntity.getTestId());

        // verify we can retrieve blob field from entity
        //String newCRCCode = getCRCCode(testEntity.getTestBlobField());

        // verify values
        //assertEquals("Blob value is incorrect, crc32 code : " + oldCRCCode, oldCRCCode, newCRCCode);
        assertEquals("Credit card date value is incorrect.", testCreditCardDateField, testEntity.getTestCreditCardDateField());
        assertEquals("Credit card number value from entity.", testCreditCardNumberField, testEntity.getTestCreditCardNumberField());
        assertEquals("Date value is incorrect.", testDateTimeField, testEntity.getTestDateTimeField());
        assertEquals("Email value is incorrect.", testEmailField, testEntity.getTestEmailField());
        assertEquals("Floating point value is incorrect.", testEntity.getTestFloatingPointField().setScale(DECIMALS, ROUNDING), testFloatingPointField);
        assertEquals("Numeric value is incorrect.", testNumericField, testEntity.getTestNumericField());
        assertEquals("String value is incorrect.", testStringField, testEntity.getTestStringField());
        assertEquals("Telphone number value is incorrect.", testTelphoneField, testEntity.getTestTelphoneField());
        assertEquals("URL value is incorrect.", testUrlField, testEntity.getTestUrlField());
    }

    /**
     * Gets a file crc32 code.
     * @param bytes a <code>byte[]</code> value
     * @throws Exception if an error occurs
     * @return a <code>String</code> CRC32 code
     */
    public static String getCRCCode(byte[] bytes) throws Exception {
        CRC32 crc32 = new CRC32();
        crc32.update(bytes);
        return Long.toHexString(crc32.getValue());
    }

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }
}
