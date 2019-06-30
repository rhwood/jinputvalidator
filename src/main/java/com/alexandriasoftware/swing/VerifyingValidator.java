/*
 * Copyright (C) 2019 Randall Wood.
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

import javax.annotation.Nonnull;
import javax.swing.InputVerifier;
import javax.swing.JComponent;

/**
 * A {@link JInputValidator} that uses a standard
 * {@link javax.swing.InputVerifier} to toggle a good/bad state.
 *
 * @author Randall Wood
 */
public class VerifyingValidator extends JInputValidator {

    private final InputVerifier verifier;
    private final Validation valid;
    private final Validation invalid;

    /**
     * Create a VerifyingValidator. This is the same as calling
     * {@link #VerifyingValidator(javax.swing.JComponent, javax.swing.InputVerifier, com.alexandriasoftware.swing.Validation, boolean, boolean)}
     * with the onInput and isVerifying parameters true.
     *
     * @param component the component to verify
     * @param verifier  the verifier to use
     * @param invalid   the validation to use when the
     *                  {@link javax.swing.InputVerifier#verify(javax.swing.JComponent)}
     *                  method of the verifier returns false
     */
    public VerifyingValidator(@Nonnull JComponent component, @Nonnull InputVerifier verifier, Validation invalid) {
        this(component, verifier, invalid, true, true);
    }

    /**
     * Create a VerifyingValidator. This is the same as calling
     * {@link #VerifyingValidator(javax.swing.JComponent, javax.swing.InputVerifier, com.alexandriasoftware.swing.Validation, com.alexandriasoftware.swing.Validation, boolean, boolean, com.alexandriasoftware.swing.JInputValidatorPreferences)}
     * with a {@link Validation} that has type {@link Validation.Type#NONE} for
     * the valid parameter and
     * {@link JInputValidatorPreferences#getPreferences()} for the preferences
     * parameter.
     *
     * @param component   the component to verify
     * @param verifier    the verifier to use
     * @param invalid     the validation to use when the
     *                    {@link javax.swing.InputVerifier#verify(javax.swing.JComponent)}
     *                    method of the verifier returns false
     * @param onInput     true if validation should occur on every change to
     *                    input; false if validation should only occur on focus
     *                    changes
     * @param isVerifying {@code true} if validator is to return true or false
     *                    per {@link javax.swing.InputVerifier#verify(javax.swing.JComponent)};
     *                    {@code false} to always return {@code true} for that method.
     */
    public VerifyingValidator(@Nonnull JComponent component, @Nonnull InputVerifier verifier, Validation invalid, boolean onInput, boolean isVerifying) {
        this(component, verifier, invalid, null, onInput, isVerifying, JInputValidatorPreferences.getPreferences());
    }

    /**
     * Create a VerifyingValidator.
     *
     * @param component   the component to verify
     * @param verifier    the verifier to use
     * @param invalid     the validation to use when the
     *                    {@link javax.swing.InputVerifier#verify(javax.swing.JComponent)}
     *                    method of the verifier returns false
     * @param valid       the validation to use when the
     *                    {@link javax.swing.InputVerifier#verify(javax.swing.JComponent)}
     *                    method of the verifier returns true
     * @param onInput     {@code true{} if validation should occur on every change to
     *                    input; {@code false} if validation should only occur on focus
     *                    changes
     * @param isVerifying {@code true} if validator is to return true or false
     *                    per {@link javax.swing.InputVerifier#verify(javax.swing.JComponent)};
     *                    {@code false} to always return {@code true} for that method.
     * @param preferences the preferences to use to draw the validation icons
     */
    public VerifyingValidator(@Nonnull JComponent component, @Nonnull InputVerifier verifier, @Nonnull Validation invalid, Validation valid, boolean onInput, boolean isVerifying, @Nonnull JInputValidatorPreferences preferences) {
        super(component, onInput, isVerifying, preferences);
        this.verifier = verifier;
        this.invalid = invalid;
        setValidationPreferences(this.invalid);
        if (valid != null) {
            this.valid = valid;
            setValidationPreferences(this.valid);
        } else {
            this.valid = getNoneValidation();
        }
    }

    /**
     * Get the validation for the current result of calling
     * {@link javax.swing.InputVerifier#verify(javax.swing.JComponent)} using
     * the current verifier.
     *
     * @param input       the component to verify
     * @param preferences ignored, but required by implemented API
     * @return the Validation for the valid or invalid states as appropriate
     */
    @Override
    protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences) {
        return verifier.verify(input) ? valid : invalid;
    }

}
