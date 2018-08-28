package com.daledev.mockhousingapp.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author dale.ellis
 * @since 23/08/2018
 */
public class BeanPropertyUtilTest {
    private TestObject testObject;

    @Before
    public void setUp() {
        testObject = new TestObject();
    }

    @Test
    public void givenCorrectPropertyNameAndObjectValueThenValueWillBeSet() {
        // Given
        Date value = new Date();

        // When
        BeanPropertyUtil.setPropertyValue(testObject, "objectValue", value);

        // Then
        assertEquals(value, testObject.objectValue);
    }

    @Test
    public void givenCorrectPropertyNameAndObjectValueThenTrueReturned() {
        // Given
        Date value = new Date();

        // When
        boolean isValueSet = BeanPropertyUtil.setPropertyValue(testObject, "objectValue", value);

        // Then
        assertTrue(isValueSet);
    }

    @Test
    public void givenCorrectPropertyNameAndPrimitiveValueThenValueWillBeSet() {
        // Given
        int value = 12345;

        // When
        BeanPropertyUtil.setPropertyValue(testObject, "primitiveValue", value);

        // Then
        assertEquals(value, testObject.primitiveValue);
    }

    @Test
    public void givenCorrectPropertyNameAndPrimitiveValueThenTrueReturned() {
        // Given
        int value = 12345;

        // When
        boolean isValueSet = BeanPropertyUtil.setPropertyValue(testObject, "primitiveValue", value);

        // Then
        assertTrue(isValueSet);
    }

    @Test
    public void givenCorrectPropertyNameInWrongCaseAndObjectValueThenValueWillBeSet() {
        // Given
        Date value = new Date();

        // When
        BeanPropertyUtil.setPropertyValue(testObject, "OBJECTvalue", value);

        // Then
        assertEquals(value, testObject.objectValue);
    }

    @Test
    public void givenCorrectPropertyNameInWrongCaseAndObjectValueThenTrueReturned() {
        // Given
        Date value = new Date();

        // When
        boolean isValueSet = BeanPropertyUtil.setPropertyValue(testObject, "OBJECTvalue", value);

        // Then
        assertTrue(isValueSet);
    }

    @Test
    public void givenInCorrectPropertyNameThenFalseReturned() {
        // Given
        Date value = new Date();

        // When
        boolean isValueSet = BeanPropertyUtil.setPropertyValue(testObject, "incorrectProperty", value);

        // Then
        assertFalse(isValueSet);
    }

    @Test
    public void givenCorrectPropertyNameAndIncorrectDataTypeValueThenFalseReturned() {
        // Given
        long value = 12345;

        // When
        boolean isValueSet = BeanPropertyUtil.setPropertyValue(testObject, "objectValue", value);

        // Then
        assertFalse(isValueSet);
    }

    private class TestObject {
        private Date objectValue;
        private int primitiveValue;


        public void setObjectValue(Date objectValue) {
            this.objectValue = objectValue;
        }

        public void setPrimitiveValue(int primitiveValue) {
            this.primitiveValue = primitiveValue;
        }
    }
}