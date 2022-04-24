/*
 * Copyright (C) 2019 Randall Wood
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.Duration;

import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import static org.awaitility.Awaitility.await;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Randall Wood
 */
class JInputValidatorTest {
    
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
     * Test of setToolTipText method, of class JInputValidator.
     */
    @Test
    void testSetToolTipText() {
        JTextField c = new JTextField();
        c.setText("test1");
        c.setToolTipText("1");
        // do not attempt to automatically verify
        JInputValidator v = new JInputValidator(c, false, true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences settings) {
                if (input instanceof JTextComponent) {
                    String text = ((JTextComponent) input).getText();
                    if (text.isEmpty()) {
                        return new Validation(Type.DANGER, "empty", settings);
                    }
                }
                return new Validation(Type.NONE, "none", settings);
            }
        };
        assertSame(c, v.getComponent());
        c.setInputVerifier(v);
        assertEquals("1", v.getToolTipText());
        // verify validator puts wrong tool tip in with type NONE when component
        // tool tip text is changed and validator's is not
        c.setToolTipText("2");
        assertEquals("2", c.getToolTipText());
        assertEquals("1", v.getToolTipText());
        c.setText(""); // trigger a change and then trigger a change back
        // manually call to assert return value
        assertFalse(v.verify(c));
        await().atMost(Duration.ofSeconds(1));
        c.setText("test2");
        // manually call to assert return value
        assertTrue(v.verify(c));
        await().atMost(Duration.ofSeconds(1));
        assertEquals(Type.NONE, v.getValidation().getType());
        assertEquals("1", c.getToolTipText());
        // verify changing validator tool tip text changes component tool tip text
        v.setToolTipText("3");
        assertEquals("3", c.getToolTipText());
        c.setText("test3");
        // manually call to assert return value
        assertTrue(v.verify(c));
        await().atMost(Duration.ofSeconds(1));
        // verify validator puts correct tool tip in with type NONE
        assertEquals("3", c.getToolTipText());
        c.setText("");
        // manually call to assert return value
        assertFalse(v.verify(c));
        await().atMost(Duration.ofSeconds(1));
        assertEquals(Type.DANGER, v.getValidation().getType());
        assertEquals("empty", c.getToolTipText());
        // verify validator does not change tool tip with type DANGER
        v.setToolTipText("3");
        assertEquals("empty", c.getToolTipText());
        c.setText("test4");
        // manually call to assert return value
        assertTrue(v.verify(c));
        await().atMost(Duration.ofSeconds(1));
        assertEquals("3", c.getToolTipText());
    }

    /**
     * Test of getValidation method, of class JInputValidator.
     */
    @Test
    void testGetValidation_0args() {
        JTextField c = new JTextField();
        c.setText("test1");
        c.setToolTipText("1");
        JInputValidator v = new JInputValidator(c) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences settings) {
                return new Validation(Type.INFORMATION, "info", settings);
            }
        };
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

    /**
     * Test of verify method, of class JInputValidator.
     */
    @Test
    void testVerify() {
        JTextField c = new JTextField();
        c.setText("test1");
        c.setToolTipText("1");
        JInputValidator v = new JInputValidator(c) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences settings) {
                return new Validation(Type.INFORMATION, "info", settings);
            }
        };
        c.setInputVerifier(v);
        Validation v1 = v.getValidation();
        assertNotNull(v1);
        assertEquals(Type.NONE, v1.getType());
        assertEquals("1", v1.getMessage());
        assertEquals("", v1.getIcon());
        assertEquals(SystemColor.textText, v1.getColor());
        // manually call to assert return value
        assertTrue(v.verify(c));
        await().atMost(Duration.ofSeconds(1));
        v1 = v.getValidation();
        assertNotNull(v1);
        assertEquals(Type.INFORMATION, v1.getType());
        assertEquals("info", v1.getMessage());
        assertEquals("\uf05a", v1.getIcon());
        assertEquals(new Color(0x73BCF7), v1.getColor());
    }

    /**
     * Test of addPropertyChangeListener method, of class JInputValidator.
     */
    @Test
    void testAddPropertyChangeListener_PropertyChangeListener() {
        PropertyChangeListener listener = evt -> {
            // do nothing
        };
        JInputValidator instance = new JInputValidator(new JTextField()) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences settings) {
                return new Validation(Type.NONE, "", settings);
            }
        };
        assertEquals(0, instance.getPropertyChangeListeners().length);
        instance.addPropertyChangeListener(listener);
        assertEquals(1, instance.getPropertyChangeListeners().length);
        instance.removePropertyChangeListener(listener);
        assertEquals(0, instance.getPropertyChangeListeners().length);
    }

    /**
     * Test of addPropertyChangeListener method, of class JInputValidator.
     */
    @Test
    void testAddPropertyChangeListener_String_PropertyChangeListener() {
        PropertyChangeListener listener = evt -> {
            // do nothing
        };
        JInputValidator instance = new JInputValidator(new JTextField()) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences settings) {
                return new Validation(Type.NONE, "", settings);
            }
        };
        assertEquals(0, instance.getPropertyChangeListeners("validationType").length);
        instance.addPropertyChangeListener("validationType", listener);
        assertEquals(1, instance.getPropertyChangeListeners("validationType").length);
        instance.removePropertyChangeListener("validationType", listener);
        assertEquals(0, instance.getPropertyChangeListeners("validationType").length);
    }
    
    @Test
    void testTrimHtmlTags() {
        JInputValidator instance = new JInputValidator(new JTextField()) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences settings) {
                return new Validation(Type.NONE, "", settings);
            }
        };
        assertEquals("", instance.trimHtmlTags(null));
        assertEquals("abcd", instance.trimHtmlTags("abcd"));
        assertEquals("abcd", instance.trimHtmlTags(" abcd"));
        assertEquals("abcd", instance.trimHtmlTags("abcd "));
        assertEquals("abcd", instance.trimHtmlTags(" abcd "));
        assertEquals("abcd", instance.trimHtmlTags("<html>abcd</html>"));
        assertEquals("abcd", instance.trimHtmlTags(" <html> abcd </html> "));
        assertEquals("abcd", instance.trimHtmlTags("<html>abcd"));
        assertEquals("abcd", instance.trimHtmlTags("abcd</html>"));
    }

    @Test
    void testVerifying() {
        JInputValidator instance = new JInputValidator(new JTextField()) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences settings) {
                return new Validation(Type.NONE, "", settings);
            }
        };
        assertTrue(instance.isVerifying());
        instance.setVerifying(false);
        assertFalse(instance.isVerifying());
        instance.setVerifying(true);
        assertTrue(instance.isVerifying());
    }

    /**
     * Test that verify() in a non-verifying JInputValidator is always true.
     */
    @Test
    void testNotVerifying() {
        JTextField c = new JTextField();
        JInputValidator v = new JInputValidator(c, true, false) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences settings) {
                if (input instanceof JTextComponent) {
                    String text = ((JTextComponent) input).getText();
                    if (text.isEmpty()) {
                        return new Validation(Type.DANGER, "empty", settings);
                    }
                }
                return new Validation(Type.NONE, "none", settings);
            }
        };
        assertFalse(v.isVerifying());
        assertTrue(c.getText().isEmpty());
        assertTrue(v.verify(c));
        v.setVerifying(true);
        assertFalse(v.verify(c));
        v.setVerifying(false);
        assertTrue(v.verify(c));
        c.setText("test");
        assertTrue(v.verify(c));
    }

    @Test
    void testPropertyChangeSupport() {
        JInputValidator v = new JInputValidator(new JTextField()) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences settings) {
                return new Validation(Type.NONE, "", settings);
            }
        };
        PropertyChangeSupport pcs = v.getPropertyChangeSupport();
        assertNotNull(pcs);
        PropertyChangeListener l = (PropertyChangeEvent evt) -> {
            // nothing to do
        };
        pcs.addPropertyChangeListener(l);
        assertArrayEquals(new PropertyChangeListener[]{l}, v.getPropertyChangeListeners());
    }
}
