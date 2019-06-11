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
 * A {@link JInputValidator} that uses a standard {@link javax.swing.InputVerifier} to toggle a good/bad state.
 * 
 * @author Randall Wood
 */
public class VerifyingValidator extends JInputValidator {

    private final InputVerifier verifier;
    private final Validation valid;
    private final Validation invalid;

    public VerifyingValidator(@Nonnull JComponent component, @Nonnull InputVerifier verifier, Validation invalid) {
        this(component, verifier, invalid, false);
    }
    
    public VerifyingValidator(@Nonnull JComponent component, @Nonnull InputVerifier verifier, Validation invalid, boolean onInput) {
        this(component, verifier, invalid, onInput, JInputValidatorPreferences.getPreferences());
    }
    
    public VerifyingValidator(@Nonnull JComponent component, @Nonnull InputVerifier verifier, Validation invalid, boolean onInput, @Nonnull JInputValidatorPreferences preferences) {
        super(component, onInput, preferences);
        this.verifier = verifier;
        this.invalid = invalid;
        this.valid = new Validation(Validation.Type.NONE, "", preferences);
    }

    @Override
    protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences) {
        return verifier.verify(input) ? valid : invalid;
    }
    
}
