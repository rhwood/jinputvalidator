package com.alexandriasoftware.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidationTest {

    private Validation validation;

    @BeforeEach
    public void setUp() {
        validation = new Validation(null, "foo");
    }

    @SuppressWarnings("java:S3415")
    @Test
    void testEquals() {
        assertNotEquals(validation, null);
        assertNotEquals(validation, "");
        assertEquals(validation, validation);
        assertEquals(validation, new Validation(null, "foo"));
        assertNotEquals(validation, new Validation(null, "bar"));
    }
}
