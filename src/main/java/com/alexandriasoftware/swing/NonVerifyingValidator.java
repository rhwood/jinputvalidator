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

import javax.swing.JComponent;

/**
 * A {@link JInputValidator} that always returns {@code true} for
 * {@link javax.swing.InputVerifier#verify(javax.swing.JComponent)}.
 *
 * @author Randall Wood
 */
public abstract class NonVerifyingValidator extends JInputValidator {

    /**
     * Create a NonVerifyingValidator with the default preferences. The
     * validator created with this call listens to all changes to the component
     * if the component is a {@link javax.swing.text.JTextComponent}.
     *
     * @param component the component to attach the validator to
     */
    public NonVerifyingValidator(JComponent component) {
        super(component);
    }

    /**
     * Create a NonVerifyingValidator with the default preferences.
     *
     * @param component the component to attach the validator to
     * @param onInput   {@code true} if validator to validate on all input;
     *                  {@code false} to validate only on focus change; note
     *                  this has no effect if component is not a
     *                  {@link javax.swing.text.JTextComponent}
     */
    public NonVerifyingValidator(JComponent component, boolean onInput) {
        super(component, onInput);
    }

    /**
     * Create a NonVerifyingValidator with custom preferences.
     *
     * @param component   the component to attach the validator to
     * @param onInput     {@code true} if validator to validate on all input;
     *                    {@code false} to validate only on focus change; note
     *                    this has no effect if component is not a
     *                    {@link javax.swing.text.JTextComponent}
     * @param preferences the custom preferences
     */
    public NonVerifyingValidator(JComponent component, boolean onInput, JInputValidatorPreferences preferences) {
        super(component, onInput, preferences);
    }

    /**
     * {@inheritDoc}
     *
     * This implementation always returns {@code true}.
     */
    @Override
    public boolean verify(JComponent input) {
        super.verify(input);
        return true;
    }

}
