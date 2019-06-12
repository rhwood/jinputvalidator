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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.SystemColor;
import java.io.IOException;
import java.util.prefs.Preferences;
import javax.swing.JLabel;
import org.slf4j.LoggerFactory;

/**
 * Preferences for a JInputValidator. By default the preferences are pulled from a
 * {@link java.util.prefs.Preferences} object for the package
 * {@code com.alexandriasoftware.swing} with the following keys:
 * <dl>
 * <dt>font (String)</dt>
 * <dd>Path to a font that can be loaded using
 * {@link java.lang.Class#getResourceAsStream(java.lang.String)}.</dd>
 * <dt>unknown.icon (String)</dt>
 * <dd>The character or Unicode sequence for the character that represents
 * {@link Validation.Type#UNKNOWN}</dd>
 * <dt>unknown.color (int)</dt>
 * <dd>The color that represents {@link Validation.Type#UNKNOWN}</dd>
 * <dt>information.icon (String)</dt>
 * <dd>The character or Unicode sequence for the character that represents
 * {@link Validation.Type#INFORMATION}</dd>
 * <dt>information.color (int)</dt>
 * <dd>The color that represents {@link Validation.Type#INFORMATION}</dd>
 * <dt>success.icon (String)</dt>
 * <dd>The character or Unicode sequence for the character that represents
 * {@link Validation.Type#SUCCESS}</dd>
 * <dt>success.color (int)</dt>
 * <dd>The color that represents {@link Validation.Type#SUCCESS}</dd>
 * <dt>warning.icon (String)</dt>
 * <dd>The character or Unicode sequence for the character that represents
 * {@link Validation.Type#WARNING}</dd>
 * <dt>warning.color (int)</dt>
 * <dd>The color that represents {@link Validation.Type#WARNING}</dd>
 * <dt>danger.icon (String)</dt>
 * <dd>The character or Unicode sequence for the character that represents
 * {@link Validation.Type#DANGER}</dd>
 * <dt>danger.color (int)</dt>
 * <dd>The color that represents {@link Validation.Type#DANGER}</dd>
 * <dd>
 * </dl>
 *
 * @author Randall Wood
 */
public class JInputValidatorPreferences {

    private static JInputValidatorPreferences defaultPreferences = null;

    public synchronized static JInputValidatorPreferences getPreferences() {
        if (defaultPreferences == null) {
            defaultPreferences = new JInputValidatorPreferences();
        }
        return defaultPreferences;
    }

    public static JInputValidatorPreferences getPreferences(Preferences preferences) {
        return new JInputValidatorPreferences(preferences);
    }

    private final Font font;
    private final String unknownIcon;
    private final Color unknownColor;
    private final String informationIcon;
    private final Color informationColor;
    private final String successIcon;
    private final Color successColor;
    private final String warningIcon;
    private final Color warningColor;
    private final String dangerIcon;
    private final Color dangerColor;

    private JInputValidatorPreferences() {
        this(Preferences.userNodeForPackage(JInputValidatorPreferences.class));
    }

    private JInputValidatorPreferences(Preferences preferences) {
        String patternfly = "/org/patternfly/PatternFlyIcons-webfont.ttf";
        Font f;
        try {
            f = Font.createFont(Font.TRUETYPE_FONT, JInputValidatorPreferences.class.getResourceAsStream(preferences.get("font", patternfly)));
        } catch (FontFormatException | IOException ex) {
            LoggerFactory.getLogger(this.getClass()).error("Unable to get Font resource named {}", preferences.get("font", patternfly), ex);
            f = (new JLabel()).getFont();
        }
        font = f;
        unknownIcon = preferences.get("unknown.icon", "\ue605");
        unknownColor = new Color(preferences.getInt("unknown.color", 0x73BCF7));
        informationIcon = preferences.get("information.icon", "\ue92b");
        informationColor = new Color(preferences.getInt("information.color", 0x73BCF7));
        successIcon = preferences.get("success.icon", "\ue602");
        successColor = new Color(preferences.getInt("success.color", 0x92D400));
        warningIcon = preferences.get("warning.icon", "\ue913");
        warningColor = new Color(preferences.getInt("warning.color", 0xF0AB00));
        dangerIcon = preferences.get("danger.icon", "\ue926");
        dangerColor = new Color(preferences.getInt("danger.color", 0xC9190B));
    }

    public Font getFont() {
        return font;
    }

    public String getIcon(Validation.Type type) {
        switch (type) {
            case UNKNOWN:
                return unknownIcon;
            case INFORMATION:
                return informationIcon;
            case SUCCESS:
                return successIcon;
            case WARNING:
                return warningIcon;
            case DANGER:
                return dangerIcon;
            case NONE:
            default:
                // ignored, so return empty string
                return "";
        }
    }

    public Color getColor(Validation.Type type) {
        switch (type) {
            case UNKNOWN:
                return unknownColor;
            case INFORMATION:
                return informationColor;
            case SUCCESS:
                return successColor;
            case WARNING:
                return warningColor;
            case DANGER:
                return dangerColor;
            case NONE:
            default:
                // ignored, so return anything
                return SystemColor.textText;
        }
    }
}
