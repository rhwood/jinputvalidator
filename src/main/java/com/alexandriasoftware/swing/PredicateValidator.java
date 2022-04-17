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

import java.util.function.Predicate;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 * A {@link JInputValidator} that uses a standard
 * {@link java.util.function.Predicate} for a String to toggle a good/bad state.
 * This only works against subclasses of {@link JTextComponent}.
 *
 * @author Randall Wood
 */
public class PredicateValidator extends JInputValidator {

    private final Predicate<String> predicate;
    private final Validation valid;
    private final Validation invalid;

    /**
     * Create a PredicateValidator. This is the same as calling
     * {@link #PredicateValidator(JComponent, Predicate, Validation, boolean, boolean)}
     * with the onInput and isVerifying parameters true.
     *
     * @param component the component to verify; must not be null
     * @param predicate the predicate to use; must not be null
     * @param invalid   the validation to use when the
     *                  {@link java.util.function.Predicate#test(java.lang.Object)}
     *                  method of the predicate returns false
     */
    public PredicateValidator(JComponent component, Predicate<String> predicate, Validation invalid) {
        this(component, predicate, invalid, true, true);
    }

    /**
     * Create a PredicateValidator. This is the same as calling
     * {@link #PredicateValidator(JComponent, Predicate, Validation, Validation, boolean, boolean, JInputValidatorPreferences)}
     * with a {@link Validation} that has type {@link Validation.Type#NONE} for
     * the valid parameter and
     * {@link JInputValidatorPreferences#getPreferences()} for the preferences
     * parameter.
     *
     * @param component   the component to verify; must not be null
     * @param predicate   the predicate to use; must not be null
     * @param invalid     the validation to use when the
     *                    {@link java.util.function.Predicate#test(java.lang.Object)}
     *                    method of the predicate returns false
     * @param onInput     true if validation should occur on every change to
     *                    input; false if validation should only occur on focus
     *                    changes
     * @param isVerifying {@code true} if validator is to return true or false
     *                    per {@link javax.swing.InputVerifier#verify(javax.swing.JComponent)};
     *                    {@code false} to always return {@code true} for that method.
     */
    public PredicateValidator(JComponent component, Predicate<String> predicate, Validation invalid, boolean onInput, boolean isVerifying) {
        this(component, predicate, invalid, null, onInput, isVerifying, JInputValidatorPreferences.getPreferences());
    }

    /**
     * Create a PredicateValidator.
     *
     * @param component   the component to verify; must not be null
     * @param predicate   the predicate to use; must not be null
     * @param invalid     the validation to use when the
     *                    {@link java.util.function.Predicate#test(java.lang.Object)}
     *                    method of the predicate returns false; must not be null
     * @param valid       the validation to use when the
     *                    {@link java.util.function.Predicate#test(java.lang.Object)}
     *                    method of the predicate returns true; may be null
     * @param onInput     true if validation should occur on every change to
     *                    input; false if validation should only occur on focus
     *                    changes
     * @param isVerifying {@code true} if validator is to return true or false
     *                    per {@link javax.swing.InputVerifier#verify(javax.swing.JComponent)};
     *                    {@code false} to always return {@code true} for that method.
     * @param preferences the preferences to use to draw the validation icons; must not be null
     */
    public PredicateValidator(JComponent component, Predicate<String> predicate, Validation invalid, Validation valid, boolean onInput, boolean isVerifying, JInputValidatorPreferences preferences) {
        super(component, onInput, isVerifying, preferences);
        this.predicate = predicate;
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
     * the predicate. If input is not a subclass of {@link JTextComponent}; no
     * validation is performed.
     *
     * @param input       the component to verify
     * @param preferences ignored, but required by implemented API
     * @return the Validation for the valid or invalid states as appropriate
     */
    @Override
    protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences) {
        if (input instanceof JTextComponent) {
            return predicate.test(((JTextComponent) input).getText()) ? valid : invalid;
        } else {
            return getNoneValidation();
        }
    }

}
