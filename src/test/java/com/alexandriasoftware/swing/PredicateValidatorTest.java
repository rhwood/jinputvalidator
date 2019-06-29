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
import java.util.function.Predicate;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
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
public class PredicateValidatorTest {

    public PredicateValidatorTest() {
    }

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
    public void testGetValidation() {
        JTextField c = new JTextField();
        c.setText("test1");
        c.setToolTipText("1");
        PredicateValidator v = new PredicateValidator(c,
                (String t) -> t.equals("test1"),
                new Validation(Type.INFORMATION, "info"));
        c.setInputVerifier(v);
        Validation v1 = v.getValidation();
        assertNotNull(v1);
        assertEquals(Type.NONE, v1.getType());
        assertEquals("1", v1.getMessage());
        assertEquals("", v1.getIcon());
        assertEquals(SystemColor.textText, v1.getColor());
        c.setText("test2");
        v.verify(c); // manually call verify to avoid possibly asserting before Swing thread triggers
        // verify method
        v1 = v.getValidation();
        assertNotNull(v1);
        assertEquals(Type.INFORMATION, v1.getType());
        assertEquals("info", v1.getMessage());
        assertEquals("\ue92b", v1.getIcon());
        assertEquals(new Color(0x73BCF7), v1.getColor());
    }

}
