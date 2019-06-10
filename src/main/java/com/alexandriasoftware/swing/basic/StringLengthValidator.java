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
package com.alexandriasoftware.swing.basic;

import com.alexandriasoftware.swing.JInputValidator;
import com.alexandriasoftware.swing.JInputValidatorPreferences;
import com.alexandriasoftware.swing.Validation;
import com.alexandriasoftware.swing.Validation.Type;
import javax.annotation.Nonnull;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Randall Wood
 */
public class StringLengthValidator extends JInputValidator {

    private final int minimum;
    private final int maximum;
    
    public StringLengthValidator(@Nonnull JComponent component, int minimum) {
        this(component, minimum, -1);
    }

    public StringLengthValidator(@Nonnull JComponent component, int minimum, int maximum) {
        this(component, minimum, maximum, true, JInputValidatorPreferences.getPreferences());
    }
    
    public StringLengthValidator(@Nonnull JComponent component, int minimum, int maximum, boolean onInput, @Nonnull JInputValidatorPreferences preferences) {
        super(component, onInput, preferences);
        this.minimum = minimum;
        this.maximum = maximum;
    }

    @Override
    protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences) {
        String text = ((JTextComponent) input).getText();
        if (minimum >= 0 && text.length() < minimum) {
            return new Validation(Type.WARNING, "String too short", preferences);
        } else if (maximum >= 0 && text.length() > maximum) {
            return new Validation(Type.DANGER, "String too long", preferences);
        }
        return new Validation(Type.NONE, "", preferences);
    }
    
}
