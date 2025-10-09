/*
 * Copyright (C) 2019, 2022 Randall Wood.
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

import javax.swing.InputVerifier;
import javax.swing.JComponent;

/**
 * A {@link JInputValidator} that uses a standard
 * {@link InputVerifier} to toggle a good/bad state.
 *
 * @author Randall Wood
 */
public class VerifyingValidator extends JInputValidator {

    /**
     * The verifier to use. (f for "field" to avoid conflict with parameter
     * name)
     */
    private final InputVerifier fVerifier;
    /**
     * The validation to use when the verifier returns true. (f for "field"
     * to avoid conflict with parameter name)
     */
    private final Validation fValid;
    /**
     * The validation to use when the verifier returns false. (f for "field"
     * to avoid conflict with parameter name)
     */
    private final Validation fInvalid;

    /**
     * Create a VerifyingValidator with a valid {@link Validation} of type
     * {@link Validation.Type#NONE}, validation on every input,
     * {@link InputVerifier#verify(JComponent)} returning true or false,
     * and default preferences.
     *
     * @param component the component to verify; must not be null
     * @param verifier  the verifier to use; must not be null
     * @param invalid   the validation to use when the
     *                  {@link InputVerifier#verify(JComponent)}
     *                  method of the verifier returns false
     */
    public VerifyingValidator(
        final JComponent component,
        final InputVerifier verifier,
        final Validation invalid) {
        this(component, verifier, invalid, true, true);
    }

    /**
     * Create a VerifyingValidator with a valid {@link Validation} of type
     * {@link Validation.Type#NONE} and default preferences.
     *
     * @param component   the component to verify; must not be null
     * @param verifier    the verifier to use; must not be null
     * @param invalid     the validation to use when the
     *                    {@link InputVerifier#verify(JComponent)}
     *                    method of the verifier returns false
     * @param onInput     true if validation should occur on every change to
     *                    input; false if validation should only occur on focus
     *                    changes
     * @param isVerifying {@code true} if validator is to return true or false
     *                    per {@link InputVerifier#verify(JComponent)};
     *                    {@code false} to always return {@code true} for that
     *                    method.
     */
    public VerifyingValidator(
        final JComponent component,
        final InputVerifier verifier,
        final Validation invalid,
        final boolean onInput,
        final boolean isVerifying) {
        this(
            component,
            verifier,
            invalid,
            null,
            onInput,
            isVerifying,
            JInputValidatorPreferences.getPreferences());
    }

    /**
     * Create a VerifyingValidator.
     *
     * @param component   the component to verify; must not be null
     * @param verifier    the verifier to use; must not be null
     * @param invalid     the validation to use when the
     *                    {@link InputVerifier#verify(JComponent)}
     *                    method of the verifier returns false; must not be
     *                    null
     * @param valid       the validation to use when the
     *                    {@link InputVerifier#verify(JComponent)}
     *                    method of the verifier returns true; may be null
     * @param onInput     {@code true} if validation should occur on every
     *                    change to input; {@code false} if validation should
     *                    only occur on focus changes
     * @param isVerifying {@code true} if validator is to return true or false
     *                    per {@link InputVerifier#verify(JComponent)};
     *                    {@code false} to always return {@code true} for that
     *                    method.
     * @param preferences the preferences to use to draw the validation icons
     */
    public VerifyingValidator(
        final JComponent component,
        final InputVerifier verifier,
        final Validation invalid,
        final Validation valid,
        final boolean onInput,
        final boolean isVerifying,
        final JInputValidatorPreferences preferences) {
        super(component, onInput, isVerifying, preferences);
        this.fVerifier = verifier;
        this.fInvalid = new Validation(invalid, preferences);
        if (valid != null) {
            this.fValid = new Validation(valid, preferences);
        } else {
            this.fValid = getNoneValidation();
        }
    }

    /**
     * Get the validation for the current result of calling
     * {@link InputVerifier#verify(JComponent)} using
     * the current verifier.
     *
     * @param input       the component to verify
     * @param preferences preferences to apply to Validation
     * @return the Validation for the valid or invalid states as appropriate
     */
    @Override
    protected Validation getValidation(
        final JComponent input,
        final JInputValidatorPreferences preferences) {
        return new Validation(
            fVerifier.verify(input) ? fValid : fInvalid,
            preferences);
    }

}
