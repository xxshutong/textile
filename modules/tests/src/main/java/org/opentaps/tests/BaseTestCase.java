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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.opentaps.core.log.Log;


/**
 * Opentaps Test Suite defines common testing patterns and API methods
 * for use by subclasses.  The intent is to help speed up the writing
 * of unit tests, and to keep the tests themselves clean and easy to read.
 */
public class BaseTestCase {

    public static final String SUCCESS_RET_CODE = "SUCCESS";

    public static final long STANDARD_PAUSE_DURATION = 1001; // standard pause

    public void log(String msg) {
        //TODO: try to use standard logging class from core bundle
        System.err.println("[NO LOGGER] " + msg);
    }

    /*************************************************************************/
    /***                                                                   ***/
    /***                        Pause Function                             ***/
    /***                                                                   ***/
    /*************************************************************************/

    /**
     * Pause the execution for some time to allow timestamps to be distinct on DB that
     * does not support <code>Timestamp</code> with subseconds (like MySQL).
     * @param reason the message to log
     * @param milliseconds the time to sleep in milliseconds
     * @throws AssertionException 
     */
    public void pause(String reason, long milliseconds) throws AssertionException {
        try {
            Log.logInfo(String.format("Waiting %1$dms : %2$s", milliseconds, reason));
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            fail(e);
        }
    }

    /**
     * Pause the execution for some time to allow timestamps to be distinct on DB that
     * does not support <code>Timestamp</code> with subseconds (like MySQL).
     * @param reason the message to log
     * @throws AssertionException 
     */
    public void pause(String reason) throws AssertionException {
        Log.logInfo(String.format("Waiting %1$dms : %2$s", STANDARD_PAUSE_DURATION, reason));
        pause(reason, STANDARD_PAUSE_DURATION);
    }

    /*************************************************************************/
    /***                                                                   ***/
    /***                        Assert Functions                           ***/
    /***                                                                   ***/
    /*************************************************************************/

    /**
     * Asserts that two Object are not equal. This method is the opposite of assertEquals from JUnit.
     * @param message the assert message
     * @param value actual value
     * @param expected expected value that the actual value should not be equal to
     * @throws AssertionException 
     */
    public void assertNotEquals(String message, Object value, Object expected) throws AssertionException {
        if (value.equals(expected)) {
            fail(String.format(message + " Expected NOT [%1$s] but was [%2$s].", expected.toString(), value.toString()));
        }
    }

    /**
     * Asserts that two Numbers are not equal.  This method is the opposite of assertEquals.
     * @param message the assert message
     * @param value the actual value
     * @param expected the expected value that the actual value should not be equal to
     * @throws AssertionException 
     */
    public void assertNotEquals(String message, Number value, Number expected) throws AssertionException {
        BigDecimal valueBd = asBigDecimal(value);
        BigDecimal expectedBd = asBigDecimal(expected);
        if (valueBd.compareTo(expectedBd) == 0) {
            fail(message + " Expected NOT [" + expectedBd + "] but was [" + valueBd + "].");
        }
    }

    /**
     * Rounds the two BigDecimals according to decimals and rounding, then check if they are not equal.
     * @param message the assert message
     * @param value the actual value
     * @param expected the expected value that the actual value should not be equal to
     * @param decimals passed to <code>setScale</code>
     * @param rounding passed to <code>setScale</code>
     * @throws AssertionException 
     */
    public void assertNotEquals(String message, BigDecimal value, BigDecimal expected, int decimals, RoundingMode rounding) throws AssertionException {
        BigDecimal valueRounded = value.setScale(decimals, rounding);
        BigDecimal expectedRounded = expected.setScale(decimals, rounding);
        assertNotEquals(message, valueRounded, expectedRounded);
    }

    /**
     * Asserts that two Numbers are equal.  This method helps test that <code>2.0 == 2.00000</code>.
     * @param message the assert message
     * @param value the actual value
     * @param expected the expected value that the actual value should be equal to
     * @throws AssertionException 
     */
    public void assertEquals(String message, Number value, Number expected) throws AssertionException {
        BigDecimal valueBd = asBigDecimal(value);
        BigDecimal expectedBd = asBigDecimal(expected);
        if (valueBd.compareTo(expectedBd) != 0) {
            fail(message + " Expected [" + expectedBd + "] but was [" + valueBd + "].");
        }
    }

    /**
     * Asserts two BigDecimal are equal more or less an accepted delta.
     * @param message the assert message
     * @param value the actual value
     * @param expected the expected value that the actual value should be equal to
     * @param delta accepted delta
     * @throws AssertionException 
     */
    public void assertEquals(String message, BigDecimal value, BigDecimal expected, BigDecimal delta) throws AssertionException {
        assertEquals(message, value.doubleValue(), expected.doubleValue(), delta.doubleValue());
    }

    /**
     * Round the two BigDecimals according to decimals and rounding, then check if they are equal.
     * @param message the assert message
     * @param value the actual value
     * @param expected the expected value that the actual value should be equal to
     * @param decimals passed to <code>setScale</code>
     * @param rounding passed to <code>setScale</code>
     * @throws AssertionException 
     */
    public void assertEquals(String message, BigDecimal value, BigDecimal expected, int decimals, RoundingMode rounding) throws AssertionException {
        BigDecimal valueRounded = value.setScale(decimals, rounding);
        BigDecimal expectedRounded = expected.setScale(decimals, rounding);
        assertEquals(message, valueRounded, expectedRounded);
    }

    /**
     * Asserts that two date times are equal.  This method ignore milliseconds as a workaround for DB that do not support milliseconds.
     * @param message the assert message
     * @param expected the expected <code>Calendar</code> value  that the actual value should be equal to
     * @param given the actual <code>Calendar</code> value
     * @throws AssertionException 
     */
    public void assertDatesEqual(String message, Calendar expected, Calendar given) throws AssertionException {
        expected.set(Calendar.MILLISECOND, 0);
        given.set(Calendar.MILLISECOND, 0);
        if (expected.compareTo(given) != 0) {
            fail(message + String.format(" Expected [%1$tc] but was [%2$tc].", expected.getTime(), given.getTime()));
        }
    }


    /**
     * Asserts that the values in a Map are equal to the given Map.
     * This uses <code>assertEquals</code> to compare the Map values as the default implementation uses <code>equals</code> which does not always work on <code>BigDecimal</code>.
     * @param message the assert message
     * @param actual a <code>Map</code> of values
     * @param expected the <code>Map</code> of expected values
     * @param ignoreExtraActualValues if set to <code>true</code>, does not fail if some values in the actual Map are not in the expected Map
     * @throws AssertionException 
     */
    public void assertEquals(String message, Map<?, ?> actual, Map<?, ?> expected, boolean ignoreExtraActualValues) throws AssertionException {
        Log.logInfo("Comparing maps :\nactual = " + actual + "\nexpected = " + expected);

        for (Object key : expected.keySet()) {
            Object expectedObj = expected.get(key);
            Object actualObj = actual.get(key);
            if (expectedObj == null) {
                assertNull(message + " for key value [" + key + "]", actualObj);
            } else {
                if (actualObj instanceof Map || expectedObj instanceof Map) {
                    assertEquals(message + " for key value [" + key + "]", (Map<?, ?>) expectedObj, (Map<?, ?>) actualObj, ignoreExtraActualValues);
                } else if (actualObj instanceof List || expectedObj instanceof List) {
                    assertEquals(message + " for key value [" + key + "]", (List<?>) expectedObj, (List<?>) actualObj, ignoreExtraActualValues);
                } else if (actualObj instanceof Number || expectedObj instanceof Number) {
                    assertEquals(message + " for key value [" + key + "]", (Number) expectedObj, (Number) actualObj);
                } else {
                    assertEquals(message + " for key value [" + key + "]", expectedObj, actualObj);
                }
            }
        }
        if (!ignoreExtraActualValues) {
            assertTrue("Some keys were found in the actual Map [" + actual.keySet() + "] that were not expected [" + expected.keySet() + "].", expected.keySet().containsAll(actual.keySet()));
        }
    }

    /**
     * Asserts that the values in a List are equal to the given List.
     * This uses <code>assertEquals</code> to compare the List values as the default implementation uses <code>equals</code> which does not always work on <code>BigDecimal</code>.
     * @param message the assert message
     * @param actual a <code>List</code> of values
     * @param expected the <code>List</code> of expected values
     * @param ignoreExtraActualValues if set to <code>true</code>, does not fail if some values in actual are not in expected
     * @throws AssertionException 
     */
    public void assertEquals(String message, List<?> actual, List<?> expected, boolean ignoreExtraActualValues) throws AssertionException {
        Log.logInfo("Comparing lists :\nactual = " + actual + "\nexpected = " + expected);

        for (int i = 0; i < expected.size(); i++) {
            Object expectedObj = expected.get(i);
            assertTrue(message + " for index [" + i + "] expected [" + expectedObj + "] but there is no more values", i < actual.size());
            Object actualObj = actual.get(i);
            if (expectedObj == null) {
                assertNull(message + " for index [" + i + "]", actualObj);
            } else {
                if (actualObj instanceof Map || expectedObj instanceof Map) {
                    assertEquals(message + " for index [" + i + "]", (Map<?, ?>) expectedObj, (Map<?, ?>) actualObj, ignoreExtraActualValues);
                } else if (actualObj instanceof List || expectedObj instanceof List) {
                    assertEquals(message + " for index [" + i + "]", (List<?>) expectedObj, (List<?>) actualObj, ignoreExtraActualValues);
                } else if (actualObj instanceof Number || expectedObj instanceof Number) {
                    assertEquals(message + " for index [" + i + "]", (Number) expectedObj, (Number) actualObj);
                } else {
                    assertEquals(message + " for index [" + i + "]", expectedObj, actualObj);
                }
            }
        }

    }

    /**
     * Asserts that the values in a Map are equal to the given Map.
     * This uses <code>assertEquals</code> to compare the Map values as the default implementation uses <code>equals</code> which does not always work on <code>BigDecimal</code>.
     * @param message the assert message
     * @param actual a <code>Map</code> of values
     * @param expected the <code>Map</code> of expected values
     * @throws AssertionException 
     */
    public void assertEquals(String message, Map<?, ?> actual, Map<?, ?> expected) throws AssertionException {
        assertEquals(message, actual, expected, true);
    }


    /**
     * Asserts that the given collection is not null, and not empty.
     * @param message the assert message
     * @param list the <code>Collection</code> to test
     * @throws AssertionException 
     */
    public void assertNotEmpty(String message, Collection<?> list) throws AssertionException {
        assertNotNull(message, list);
        assertTrue(message, list.size() > 0);
    }

    /**
     * Asserts that the given collection is null or empty.
     * @param message the assert message
     * @param list the <code>Collection</code> to test
     * @throws AssertionException 
     */
    public void assertEmpty(String message, Collection<?> list) throws AssertionException {
        assertTrue(message, list == null || list.isEmpty());
    }

    /**
     * Print the differences between two <code>Map</code> of <code>String</code> => <code>BigDecimal</code> for Debug.
     * @param initialMap the initial <code>Map</code>
     * @param finalMap the final <code>Map</code>
     * @throws AssertionException 
     */
    public void printMapDifferences(Map<?, ?> initialMap, Map<?, ?> finalMap) throws AssertionException {

        Log.logInfo("---------------------");
        Log.logInfo("printMapDifferences:");

        String fs = "%12s %12.3f => %12.3f *** %12.3f";

        Set<Object> keys = new HashSet<Object>();
        keys.addAll(initialMap.keySet());
        keys.addAll(finalMap.keySet());

        for (Iterator<Object> iter = keys.iterator(); iter.hasNext();) {
            Object key = iter.next();

            BigDecimal initialBd = BigDecimal.ZERO;
            if (initialMap.get(key) != null) {
                initialBd = asBigDecimal(initialMap.get(key));
            }
            BigDecimal finalBd = BigDecimal.ZERO;
            if (finalMap.get(key) != null) {
                finalBd = asBigDecimal(finalMap.get(key));
            }
            BigDecimal differenceBd = finalBd.subtract(initialBd);
            Log.logInfo(String.format(fs, key, initialBd, finalBd, differenceBd));

        }
    }


    /**
     * For each key, asserts that the numeric difference is as expected:  finalValue - initialValue = expectedValue.
     * If a value is null, it is assumed to be zero.  The values must be either Numbers or Strings representing Numbers.
     * Pass in an optional message string to help identify the test case failure.
     * @param message the assert message
     * @param initialMap the initial <code>Map</code>
     * @param finalMap the final <code>Map</code>
     * @param expectedMap the <code>Map</code> of expected differences
     * @throws AssertionException 
     */
    public void assertMapDifferenceCorrect(String message, Map<?, ?> initialMap, Map<?, ?> finalMap, Map<?, ?> expectedMap) throws AssertionException {
        for (Iterator<?> iter = expectedMap.keySet().iterator(); iter.hasNext();) {
            Object key = iter.next();
            Object expectedValue = expectedMap.get(key);

            BigDecimal expectedBd = asBigDecimal(expectedValue);
            BigDecimal initialBd = BigDecimal.ZERO;
            if (initialMap.get(key) != null) {
                initialBd = asBigDecimal(initialMap.get(key));
            }
            BigDecimal finalBd = BigDecimal.ZERO;
            if (finalMap.get(key) != null) {
                finalBd = asBigDecimal(finalMap.get(key));
            }
            BigDecimal differenceBd = finalBd.subtract(initialBd);

            if (differenceBd.compareTo(expectedBd) != 0) {
                String failMessage = "Unexpected change for [" + key + "] with initial value [" + initialBd + "] and final value [" + finalBd + "]:  Expected difference of [" + expectedBd + "].  Difference is actually [" + differenceBd + "]";
                fail((message != null ? message + " " : "") + failMessage);
            }
        }
    }

    /**
     * As above, but without an additional message.
     * @param initialMap the initial <code>Map</code>
     * @param finalMap the final <code>Map</code>
     * @param expectedMap the <code>Map</code> of expected differences
     * @throws AssertionException 
     */
    public void assertMapDifferenceCorrect(Map<?, ?> initialMap, Map<?, ?> finalMap, Map<?, ?> expectedMap) throws AssertionException {
        assertMapDifferenceCorrect(null, initialMap, finalMap, expectedMap);
    }

    /**
     * Check that all values of the actual Map agree with the expected Map
     * note it will only check the key values in expectedMap against actualMap
     * use expectedMap of new HashMap() to check that the actualMap should be empty.
     * @param actualMap
     * @param expectedMap
     * @throws AssertionException 
     */
    public void assertMapCorrect(Map<?, ?> actualMap, Map<?, ?> expectedMap) throws AssertionException {
        for (Iterator<?> iter = expectedMap.keySet().iterator(); iter.hasNext();) {
            Object key = iter.next();
            Object actualValue = actualMap.get(key);
            Object expectedValue = expectedMap.get(key);

            BigDecimal expectedBd = asBigDecimal(expectedValue);
            BigDecimal actualBd = asBigDecimal(actualValue);

            if (actualBd.compareTo(expectedBd) != 0) {
                String failMessage = "Unexpected value of [" + actualBd + "] for [" + key + "]; was expecting [" + expectedBd + "]";
                fail(failMessage);
            }
        }
    }

    /**
     * For each key, asserts that the numeric difference is as expected:  Sum(finalValue) - Sum(initialValue) = expectedValue.
     * If a value is null, it is assumed to be zero.  The values must be either Numbers or Strings representing Numbers.
     * Pass in an optional message string to help identify the test case failure.
     * @param message the assert message
     * @param initialList the initial <code>List</code>
     * @param finalList the final <code>List</code>
     * @param expectedBd the expected difference
     * @throws AssertionException 
     */
    public void assertDifferenceCorrect(String message, List<?> initialList, List<?> finalList, BigDecimal expectedBd) throws AssertionException {
        BigDecimal initialBd = BigDecimal.ZERO;
        BigDecimal finalBd = BigDecimal.ZERO;

        for (Iterator<?> iter = initialList.iterator(); iter.hasNext();) {
            initialBd.add(asBigDecimal(iter.next()));
        }

        for (Iterator<?> iter = finalList.iterator(); iter.hasNext();) {
            finalBd.add(asBigDecimal(iter.next()));
        }

        BigDecimal differenceBd = finalBd.subtract(initialBd);
        if (differenceBd.compareTo(expectedBd) != 0) {
            String failMessage = "Unexpected change with initial value [" + initialBd + ": " + initialList + "] and final value [" + finalBd + ": " + finalList + "]:  Expected difference of [" + expectedBd + "].  Difference is actually [" + differenceBd + "]";
            fail((message != null ? message + " " : "") + failMessage);
        }
    }

    /**
     * As above, but without an additional message.
     * @param initialList the initial <code>List</code>
     * @param finalList the final <code>List</code>
     * @param expectedBd the expected difference
     * @throws AssertionException 
     */
    public void assertDifferenceCorrect(List<?> initialList, List<?> finalList, BigDecimal expectedBd) throws AssertionException {
        assertDifferenceCorrect(null, initialList, finalList, expectedBd);
    }

    /**
     * Asserts that a condition is true. If it isn't it throws an
     * {@link AssertionException} with the given message.
     *
     * @param message the identifying message for the {@link AssertionException}
     * @param condition condition to be checked
     * @throws AssertionException 
     */
    public void assertTrue(String message, boolean condition) throws AssertionException {
        if (!condition) {
            fail(message);
        }
    }

    /**
     * Asserts that a condition is true. If it isn't it throws an
     * {@link AssertionException} without a message.
     *
     * @param condition condition to be checked
     * @throws AssertionException 
     */
    public void assertTrue(boolean condition) throws AssertionException {
        assertTrue(null, condition);
    }

    /**
     * Asserts that a condition is false. If it isn't it throws an
     * {@link AssertionException} with the given message.
     *
     * @param message the identifying message for the {@link AssertionException}
     * @param condition condition to be checked
     * @throws AssertionException 
     */
    public void assertFalse(String message, boolean condition) throws AssertionException {
        assertTrue(message, !condition);
    }

    /**
     * Asserts that a condition is false. If it isn't it throws an
     * {@link AssertionException} without a message.
     *
     * @param condition condition to be checked
     * @throws AssertionException 
     */
    public void assertFalse(boolean condition) throws AssertionException {
        assertFalse(null, condition);
    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link AssertionException} is thrown with the given message. If
     * <code>expected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionException}
     * @param expected expected value
     * @param actual actual value
     * @throws AssertionException 
     */
    public void assertEquals(String message, Object expected, Object actual) throws AssertionException {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && isEquals(expected, actual)) {
            return;
        } else {
            failNotEquals(message, expected, actual);
        }
    }

    /**
     * Asserts that two objects are equal. If they are not, an
     * {@link AssertionError} without a message is thrown. If
     * <code>expected</code> and <code>actual</code> are <code>null</code>,
     * they are considered equal.
     *
     * @param expected
     * expected value
     * @param actual
     * the value to check against <code>expected</code>
     * @throws AssertionException 
     */
    public void assertEquals(Object expected, Object actual) throws AssertionException {
        assertEquals(null, expected, actual);
    }

    public void assertEquals(String message, double expected, double actual, double delta) throws AssertionException {
        if (Double.compare(expected, actual) == 0) {
            return;
        }
        if (!(Math.abs(expected - actual) <= delta)) {
            failNotEquals(message, Double.valueOf(expected), Double.valueOf(actual));
        }
    }

    /**
     * Asserts that two longs are equal. If they are not, an
     * {@link AssertionError} is thrown.
     *
     * @param expected
     * expected long value.
     * @param actual
     * actual long value
     * @throws AssertionException 
     */
    public void assertEquals(long expected, long actual) throws AssertionException {
        assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two longs are equal. If they are not, an
     * {@link AssertionError} is thrown with the given message.
     *
     * @param message
     * the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param expected
     * long expected value.
     * @param actual
     * long actual value
     * @throws AssertionException 
     */
    public void assertEquals(String message, long expected, long actual) throws AssertionException {
        assertEquals(message, (Long) expected, (Long) actual);
    }

    /**
     * Asserts that two doubles or floats are equal to within a positive delta.
     * If they are not, an {@link AssertionError} is thrown. If the expected
     * value is infinity then the delta value is ignored.NaNs are considered
     * equal: <code>assertEquals(Double.NaN, Double.NaN, *)</code> passes
     *
     * @param expected
     * expected value
     * @param actual
     * the value to check against <code>expected</code>
     * @param delta
     * the maximum delta between <code>expected</code> and
     * <code>actual</code> for which both numbers are still
     * considered equal.
     * @throws AssertionException 
     */
    public void assertEquals(double expected, double actual, double delta) throws AssertionException {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * Asserts that an object isn't null. If it is an {@link AssertionError} is
     * thrown with the given message.
     *
     * @param message
     * the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param object
     * Object to check or <code>null</code>
     * @throws AssertionException 
     */
    public void assertNotNull(String message, Object object) throws AssertionException {
        assertTrue(message, object != null);
    }

    /**
     * Asserts that an object isn't null. If it is an {@link AssertionError} is
     * thrown.
     *
     * @param object
     * Object to check or <code>null</code>
     * @throws AssertionException 
     */
    public void assertNotNull(Object object) throws AssertionException {
        assertNotNull(null, object);
    }

    /**
     * Asserts that an object is null. If it is not, an {@link AssertionError}
     * is thrown with the given message.
     *
     * @param message
     * the identifying message for the {@link AssertionError} (<code>null</code>
     * okay)
     * @param object
     * Object to check or <code>null</code>
     * @throws AssertionException 
     */
    public void assertNull(String message, Object object) throws AssertionException {
        assertTrue(message, object == null);
    }

    /**
     * Asserts that an object is null. If it isn't an {@link AssertionError} is
     * thrown.
     *
     * @param object
     * Object to check or <code>null</code>
     * @throws AssertionException 
     */
    public void assertNull(Object object) throws AssertionException {
        assertNull(null, object);
    }

    /*************************************************************************/
    /***                                                                   ***/
    /***                        Helper Functions                           ***/
    /***                                                                   ***/
    /*************************************************************************/

    /**
     * Transforms a Number or String into a BigDecimal.  If passed a null, returns BigDecimal.ZERO.
     * This function will assert that the input is a Number or String and that the string can
     * be parsed into a BigDecimal.
     * @param obj an <code>Object</code>, which should be either a <code>Number</code> or a <code>String</code>, to convert into a <code>BigDecimal</code>
     * @return a <code>BigDecimal</code> value
     * @throws AssertionException 
     */
    public BigDecimal asBigDecimal(Object obj) throws AssertionException {
        if (obj == null) {
            return BigDecimal.ZERO;
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        if (!(obj instanceof String) && !(obj instanceof Number)) {
            fail("Object [" + obj + "] is not a Number.");
        }
        try {
            return new BigDecimal(obj.toString());
        } catch (NumberFormatException e) {
            fail("Cannot parse [" + obj + "] into a BigDecimal.");
        }
        fail("Reached unexpected point.");
        return null;
    }


    private static boolean isEquals(Object expected, Object actual) {
        return expected.equals(actual);
    }

    /**
     * Fails a test with the given message.
     *
     * @param message the identifying message for the {@link AssertionException}
     * @throws AssertionException
     */
    public void fail(String message) throws AssertionException  {
        if (message == null) {
            throw new AssertionException();
        } 

        throw new AssertionException(message);
    }

    /**
     * Fails a test with the given message and cause.
     * 
     * @param message the identifying message for the {@link AssertionException}
     * @param cause the identifying cause for the {@link AssertionException}
     * @throws AssertionException
     */
    public void fail(String message, Throwable cause) throws AssertionException {
        throw new AssertionException(message, cause);
    }

    /**
     * Fails a test with the given cause.
     * 
     * @param cause the identifying cause for the {@link AssertionException}
     * @throws AssertionException
     */
    public void fail(Throwable cause) throws AssertionException {
        if (cause == null) {
            throw new AssertionException();
        }
        fail(null, cause);
    };

    public void failNotEquals(String message, Object expected, Object actual) throws AssertionException {
        String expectedString= String.valueOf(expected);
        String actualString= String.valueOf(actual);
        fail(String.format("%1$s expected:<%2$s> but was:<%3$s>", message, expectedString, actualString));
    } 
}
