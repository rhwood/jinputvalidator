/*
 * Copyright (C) 2019, 2022 Randall Wood
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
package com.github.rhwood.jinputvalidator;

import java.awt.Color;
import java.awt.Font;
import java.util.Objects;

/**
 * A Validation is the combination of a validation state and the tool tip to
 * display on the validated component.
 *
 * @author Randall Wood
 */
public class Validation {

    /**
     * The type of validation. (f for "field" to avoid conflict with parameter
     * name)
     */
    private final Type fType;
    /**
     * The tool tip text to display on the validated component. (f for "field"
     * to avoid conflict with parameter name)
     */
    private final String fMessage;
    /**
     * The preferences used to determine the display properties of the
     * validation. (f for "field" to avoid conflict with parameter name)
     */
    private final JInputValidatorPreferences fPreferences;
    /**
     * A prime number used in hashCode calculations.
     */
    private static final int HASH_PRIME = 79;

    /**
     * The Validation state. The states {@link Type#DANGER} and
     * {@link Type#WARNING} are considered invalid in
     * {@link JInputValidator#verify(javax.swing.JComponent)} and all other
     * states are considered valid.
     */
    public enum Type {
        /**
         * Default state with no validation icon or tool tip displayed. If the
         * component being validated has a default tool tip, that tool tip is
         * displayed.
         */
        NONE,
        /**
         * Validation state with an unknown icon and tool tip. By default the
         * icon is a question mark in a circle in the default border color.
         * Considered informative and valid.
         */
        UNKNOWN,
        /**
         * Validation state with an information icon and tool tip. By default
         * the icon is a letter I in a circle in the default border color.
         * Considered informative and valid.
         */
        INFORMATION,
        /**
         * Validation state with a success icon and tool tip. By default the
         * icon is a check mark in a circle in green. Considered informative and
         * valid.
         */
        SUCCESS,
        /**
         * Validation state with an warning icon and tool tip. By default the
         * icon is an exclamation mark in a triangle in yellow. Considered
         * invalid.
         */
        WARNING,
        /**
         * Validation state with an danger icon and tool tip. By default the
         * icon is a letter X in a circle in red. Considered invalid.
         */
        DANGER,
    }

    /**
     * Create a validation with default preferences.
     * <p>
     * This constructor should not be used within
     * {@link JInputValidator#getValidation(javax.swing.JComponent,
     * com.github.rhwood.jinputvalidator.JInputValidatorPreferences)}.
     *
     * @param type the type of validation
     * @param message the tool tip text
     */
    public Validation(final Type type, final String message) {
        this(type, message, JInputValidatorPreferences.getPreferences());
    }

    /**
     * Create a validation with custom preferences.
     *
     * @param type the type of validation
     * @param message the tool tip text
     * @param preferences the preferences to use; must not be null
     */
    public Validation(
        final Type type,
        final String message,
        final JInputValidatorPreferences preferences) {
        this.fType = type;
        this.fMessage = message;
        this.fPreferences = preferences;
    }

    /**
     * Create a validation from an existing validation with custom preferences.
     *
     * @param validation the existing validation to base the new validation on;
     *                   must not be null
     * @param preferences the preferences to use; must not be null
     */
    public Validation(
        final Validation validation,
        final JInputValidatorPreferences preferences) {
        this(validation.getType(), validation.getMessage(), preferences);
    }

    /**
     * Get the validation type.
     *
     * @return the validation type
     */
    public Type getType() {
        return fType;
    }

    /**
     * Get the validation message used in the tool tip for the validated
     * component.
     *
     * @return the tool tip text
     */
    public String getMessage() {
        return fMessage;
    }

    /**
     * Get the Unicode characters for the displayed icon.
     *
     * @return the icon text or an empty string if there is no icon to display
     */
    public String getIcon() {
        return fPreferences.getIcon(fType);
    }

    /**
     * Get the color the icon is displayed in.
     *
     * @return the color to use or the System text color
     */
    public Color getColor() {
        return fPreferences.getColor(fType);
    }

    /**
     * Get the font used to render the icon.
     *
     * @return the font to use
     */
    public Font getFont() {
        return fPreferences.getFont();
    }

    @Override
    public final int hashCode() {
        int hash = HASH_PRIME;
        hash = HASH_PRIME * hash + Objects.hashCode(this.fType);
        hash = HASH_PRIME * hash + Objects.hashCode(this.fMessage);
        return hash;
    }

    /**
     * {@inheritDoc}
     *
     * <strong>Note</strong> two Validations are considered equal if
     * {@code getType()} and {@code getMessage()} are equal (the display
     * properties {@code getColor()}, {@code getFont()}, and {@code getIcon()}
     * are not considered for equality).
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }
}
