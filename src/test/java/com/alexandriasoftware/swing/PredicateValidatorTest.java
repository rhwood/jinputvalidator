/*
 * Copyright 2019 rhwood.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alexandriasoftware.swing;

import com.alexandriasoftware.swing.Validation.Type;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Randall Wood
 */
class PredicateValidatorTest {

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getValidation method, of class PredicateValidator.
     */
    @Test
    void testGetValidation() {
        JTextField c = new JTextField();
        c.setText("test1");
        c.setToolTipText("1");
        PredicateValidator v = new PredicateValidator(c, (String t) -> t.equals("test1"),
                new Validation(Type.INFORMATION, "info"));
        c.setInputVerifier(v);
        Validation v1 = v.getValidation();
        assertNotNull(v1);
        assertEquals(Type.NONE, v1.getType());
        assertEquals("1", v1.getMessage());
        assertEquals("", v1.getIcon());
        assertEquals(SystemColor.textText, v1.getColor());
        c.setText("test2");
        // manually call to assert return value
        assertTrue(v.verify(c));
        v1 = v.getValidation();
        assertNotNull(v1);
        assertEquals(Type.INFORMATION, v1.getType());
        assertEquals("info", v1.getMessage());
        assertEquals("\uf05a", v1.getIcon());
        assertEquals(new Color(0x73BCF7), v1.getColor());
    }

    @Test
    void testValidValidationsInConstructor() {
        JTextField c = new JTextField();
        Validation valid = new Validation(Type.SUCCESS, "");
        Validation invalid = new Validation(Type.DANGER, "");
        PredicateValidator v = new PredicateValidator(c, (String t) -> t.equals("test1"), invalid);
        assertFalse(v.verify(c));
        assertEquals(invalid, v.getValidation());
        c.setText("test1");
        assertTrue(v.verify(c));
        assertEquals(v.getNoneValidation(), v.getValidation());
        v = new PredicateValidator(c, (String t) -> t.equals("test1"), invalid, valid, true, true, JInputValidatorPreferences.getPreferences());
        assertTrue(v.verify(c));
        assertEquals(valid, v.getValidation());
    }

    @Test
    void testNonTextComponentValidation() {
        JLabel c = new JLabel();
        Validation invalid = new Validation(Type.DANGER, "");
        PredicateValidator v = new PredicateValidator(c, (String t) -> t.equals("test1"), invalid);
        assertTrue(v.verify(c));
        assertEquals(new Validation(Type.NONE, ""), v.getValidation());
    }
}
