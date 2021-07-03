package com.alexandriasoftware.swing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidationTest {

    private Validation validation;

    @BeforeEach
    public void setUp() {
        validation = new Validation(null, "foo");
    }

    @Test
    void testEquals() {
        assertFalse(validation.equals(null));
        // test that equals returns false for unrelated class
        assertFalse(validation.equals(""));
        assertTrue(validation.equals(validation));
        assertTrue(validation.equals(new Validation(null, "foo")));
        assertFalse(validation.equals(new Validation(null, "bar")));
    }
}
