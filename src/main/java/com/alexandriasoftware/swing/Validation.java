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

/**
 *
 * @author Randall Wood
 */
public class Validation {

    private final Type type;
    private final String message;
    private final JInputValidatorPreferences preferences;
    
    public enum Type {
        NONE,
        UNKNOWN,
        INFORMATION,
        SUCCESS,
        WARNING,
        DANGER,
    }
    
    public Validation(Type type, String message, JInputValidatorPreferences preferences) {
        this.type = type;
        this.message = message;
        this.preferences = preferences;
    }
    
    protected Type getType() {
        return type;
    }
    
    protected String getMessage() {
        return message;
    }

    public String getIcon() {
        return preferences.getIcon(type);
    }

    public Color getColor() {
        return preferences.getColor(type);
    }
    
    public Font getFont() {
        return preferences.getFont();
    }
}
