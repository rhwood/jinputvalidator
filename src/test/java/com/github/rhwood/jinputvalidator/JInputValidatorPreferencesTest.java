/*
 * Copyright (C) 2022 Randall Wood
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

import static com.github.rhwood.jinputvalidator.JInputValidatorPreferences.DEFAULT_DANGER_COLOR;
import static com.github.rhwood.jinputvalidator.JInputValidatorPreferences.DEFAULT_INFORMATION_COLOR;
import static com.github.rhwood.jinputvalidator.JInputValidatorPreferences.DEFAULT_SUCCESS_COLOR;
import static com.github.rhwood.jinputvalidator.JInputValidatorPreferences.DEFAULT_UNKNOWN_COLOR;
import static com.github.rhwood.jinputvalidator.JInputValidatorPreferences.DEFAULT_WARNING_COLOR;
import java.awt.Color;
import java.awt.SystemColor;
import java.util.logging.Logger;
import java.util.UUID;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.JLabel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author rhwood
 */
class JInputValidatorPreferencesTest {

    @BeforeEach
    void setup() throws BackingStoreException {
        Preferences p = Preferences.systemNodeForPackage(JInputValidatorPreferences.class);
        p.put("font", "/com/github/rhwood/jinputvalidator/Font Awesome 5 Free-Solid-900.otf");
    }

    /**
     * Test of getPreferences method, of class JInputValidatorPreferences.
     */
    @Test
    void testGetPreferences_0args() {
        JInputValidatorPreferences preferences = JInputValidatorPreferences.getPreferences();
        assertNotNull(preferences);
        assertEquals("Font Awesome 5 Free Solid", preferences.getFont().getFontName(), "default font");
        assertEquals(new Color(DEFAULT_DANGER_COLOR), preferences.getColor(Validation.Type.DANGER), "default danger color");
        assertEquals(new Color(DEFAULT_INFORMATION_COLOR), preferences.getColor(Validation.Type.INFORMATION), "default information color");
        assertEquals(SystemColor.textText, preferences.getColor(Validation.Type.NONE), "default none color");
        assertEquals(new Color(DEFAULT_SUCCESS_COLOR), preferences.getColor(Validation.Type.SUCCESS), "default success color");
        assertEquals(new Color(DEFAULT_UNKNOWN_COLOR), preferences.getColor(Validation.Type.UNKNOWN), "default unknown color");
        assertEquals(new Color(DEFAULT_WARNING_COLOR), preferences.getColor(Validation.Type.WARNING), "default warning color");
        assertEquals("\uf06a", preferences.getIcon(Validation.Type.DANGER), "default danger icon");
        assertEquals("\uf05a", preferences.getIcon(Validation.Type.INFORMATION), "default information icon");
        assertEquals("", preferences.getIcon(Validation.Type.NONE), "default none icon");
        assertEquals("\uf058", preferences.getIcon(Validation.Type.SUCCESS), "default success icon");
        assertEquals("\uf059", preferences.getIcon(Validation.Type.UNKNOWN), "default unknown icon");
        assertEquals("\uf071", preferences.getIcon(Validation.Type.WARNING), "default warning icon");
    }

    /**
     * Test of getPreferences method, of class JInputValidatorPreferences.
     */
    @Test
    void testGetPreferences_Preferences() {
        Preferences parameter = Preferences.systemNodeForPackage(JInputValidatorPreferences.class);
        parameter.put("danger.icon", "foo");
        parameter.put("danger.color", "0");
        JInputValidatorPreferences preferences = JInputValidatorPreferences.getPreferences(parameter);
        // same assertions as in testGetPrefences_0args, except DANGER is different
        assertNotNull(preferences);
        assertEquals("Font Awesome 5 Free Solid", preferences.getFont().getFontName(), "default font");
        assertEquals(Color.BLACK, preferences.getColor(Validation.Type.DANGER), "overridden danger color");
        assertEquals(new Color(DEFAULT_INFORMATION_COLOR), preferences.getColor(Validation.Type.INFORMATION), "default information color");
        assertEquals(SystemColor.textText, preferences.getColor(Validation.Type.NONE), "default none color");
        assertEquals(new Color(DEFAULT_SUCCESS_COLOR), preferences.getColor(Validation.Type.SUCCESS), "default success color");
        assertEquals(new Color(DEFAULT_UNKNOWN_COLOR), preferences.getColor(Validation.Type.UNKNOWN), "default unknown color");
        assertEquals(new Color(DEFAULT_WARNING_COLOR), preferences.getColor(Validation.Type.WARNING), "default warning color");
        assertEquals("foo", preferences.getIcon(Validation.Type.DANGER), "overridden danger icon");
        assertEquals("\uf05a", preferences.getIcon(Validation.Type.INFORMATION), "default information icon");
        assertEquals("", preferences.getIcon(Validation.Type.NONE), "default none icon");
        assertEquals("\uf058", preferences.getIcon(Validation.Type.SUCCESS), "default success icon");
        assertEquals("\uf059", preferences.getIcon(Validation.Type.UNKNOWN), "default unknown icon");
        assertEquals("\uf071", preferences.getIcon(Validation.Type.WARNING), "default warning icon");
    }
    
    /**
     * Test of getPreferences method, of class JInputValidatorPreferences with invalid font name.
     */
    @Test
    void testGetPreferences_badFont() {
        Logger.getAnonymousLogger().info("SEVERE Stack trace is expected.");
        Preferences parameter = Preferences.systemNodeForPackage(JInputValidatorPreferences.class);
        parameter.put("font", UUID.randomUUID().toString());
        String defaultFont = new JLabel().getFont().getFontName();
        JInputValidatorPreferences preferences = JInputValidatorPreferences.getPreferences(parameter);
        assertNotNull(preferences);
        assertEquals(defaultFont, preferences.getFont().getFontName(), "invalid font");
    }
    
}
