/*
 * Copyright (C) 2019, 2021 Randall Wood
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
package com.github.rhwood.jinputvalidator;

import com.github.rhwood.jinputvalidator.Validation.Type;
import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
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
class VerifyingValidatorTest {

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

    @Test
    void testGetValidationWARNING() {
        JTextField c = new JTextField();
        c.setText("test1");
        c.setToolTipText("1");
        VerifyingValidator v = new VerifyingValidator(c, new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                return "test1".equals(((JTextField) input).getText());
            }
        }, new Validation(Type.WARNING, "warning"));
        c.setInputVerifier(v);
        // manually call to assert return value
        assertTrue(v.verify(c));
        Validation v1 = v.getValidation();
        assertNotNull(v1);
        assertEquals(Type.NONE, v1.getType());
        assertEquals("1", v1.getMessage());
        assertEquals("", v1.getIcon());
        assertEquals(SystemColor.textText, v1.getColor());
        c.setText("test2");
        // manually call to assert return value
        assertFalse(v.verify(c));
        v1 = v.getValidation();
        assertNotNull(v1);
        assertEquals(Type.WARNING, v1.getType());
        assertEquals("warning", v1.getMessage());
        assertEquals("\uf071", v1.getIcon());
        assertEquals(new Color(0xF0AB00), v1.getColor());
    }


    @Test
    void testGetValidationINFORMATION() {
        JTextField c = new JTextField();
        c.setText("test1");
        c.setToolTipText("1");
        VerifyingValidator v = new VerifyingValidator(c, new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                return "test1".equals(((JTextField) input).getText());
            }
        }, new Validation(Type.INFORMATION, "info"));
        c.setInputVerifier(v);
        // manually call to assert return value
        assertTrue(v.verify(c));
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
}
