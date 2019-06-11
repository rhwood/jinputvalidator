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

import com.alexandriasoftware.swing.Validation.Type;
import com.alexandriasoftware.swing.border.ValidatorBorder;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.annotation.Nonnull;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 * JInputValidator is an InputVerifier that adds a colored icon and tool tip
 * text indicating validation status to the component being verified.
 *
 * @author Randall Wood
 */
public abstract class JInputValidator extends InputVerifier {

    private final Border originalBorder;
    private String originalToolTipText = null;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Validation validation;
    private Type validationType = Type.NONE;
    private final JInputValidatorPreferences preferences;
    private final JComponent component;
    private boolean verifying;

    /**
     * Create a JInputValidator with the default preferences. The validator
     * created with this call listens to all changes to the component if the
     * component is a {@link javax.swing.text.JTextComponent}.
     *
     * @param component the component to attach the validator to
     */
    public JInputValidator(@Nonnull JComponent component) {
        this(component, true);
    }

    /**
     * Create a JInputValidator with the default preferences.
     *
     * @param component the component to attach the validator to
     * @param onInput   true if validator to validate on all input; false to
     *                  validate only on focus change; note this has no effect
     *                  if component is not a
     *                  {@link javax.swing.text.JTextComponent}
     */
    public JInputValidator(@Nonnull JComponent component, boolean onInput) {
        this(component, onInput, JInputValidatorPreferences.getPreferences());
    }

    /**
     * Create a JInputValidator with custom preferences.
     *
     * @param component   the component to attach the validator to
     * @param onInput     true if validator to validate on all input; false to
     *                    validate only on focus change; note this has no effect
     *                    if component is not a
     *                    {@link javax.swing.text.JTextComponent}
     * @param preferences the custom preferences
     */
    public JInputValidator(@Nonnull JComponent component, boolean onInput, @Nonnull JInputValidatorPreferences preferences) {
        this.component = component;
        originalBorder = this.component.getBorder();
        originalToolTipText = this.component.getToolTipText();
        if (onInput && this.component instanceof JTextComponent) {
            addChangeListener((JTextComponent) this.component, (e) -> {
                verify(this.component);
            });
        }
        this.preferences = preferences;
        validation = new Validation(Type.NONE, "", this.preferences);
    }

    /**
     * Set the tool tip text used when the validation state is
     * {@link Validation.Type#NONE}. If the validation state is NONE when
     * calling this method, the component's tool tip text is changed as well.
     *
     * @param toolTipText the default tool tip text for the component being
     *                    validated
     */
    public void setToolTipText(String toolTipText) {
        originalToolTipText = toolTipText;
        if (validationType == Type.NONE) {
            component.setToolTipText(toolTipText);
        }
    }

    /**
     * Set the tool tip text used when the validation state is
     * {@link Validation.Type#NONE}.
     *
     * @return the tool tip text
     */
    public String getToolTipText() {
        return originalToolTipText;
    }

    public final Validation getValidation() {
        return validation;
    }

    protected abstract Validation getValidation(JComponent input, JInputValidatorPreferences preferences);

    @Override
    public final boolean verify(JComponent input) {
        validation = getValidation(input, preferences);
        if (validation.getType() != validationType && !verifying) {
            verifying = true;
            if (validation.getType() == Type.NONE) {
                input.setBorder(originalBorder);
                input.setToolTipText(originalToolTipText);
            } else {
                input.setBorder(new ValidatorBorder(validation, originalBorder));
                input.setToolTipText(validation.getMessage());
            }
            input.validate();
            pcs.firePropertyChange("validationType", validationType, validation.getType());
            validationType = validation.getType();
            verifying = false;
        }
        return validation.getType() != Type.WARNING && validation.getType() != Type.DANGER;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    public PropertyChangeListener[] getPropertyChangeListeners() {
        return pcs.getPropertyChangeListeners();
    }

    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return pcs.getPropertyChangeListeners(propertyName);
    }

    private void addChangeListener(@Nonnull JTextComponent component, @Nonnull ChangeListener changeListener) {
        DocumentListener listener = new DocumentListener() {
            private int lastChange = 0;
            private int lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (lastNotifiedChange != lastChange) {
                        lastNotifiedChange = lastChange;
                        changeListener.stateChanged(new ChangeEvent(component));
                    }
                });
            }
        };
        component.addPropertyChangeListener("document", (e) -> {
            Document oldDocument = (Document) e.getOldValue();
            Document newDocument = (Document) e.getNewValue();
            if (oldDocument != null) {
                oldDocument.removeDocumentListener(listener);
            }
            if (newDocument != null) {
                newDocument.addDocumentListener(listener);
            }
            listener.changedUpdate(null);
        });
        Document document = component.getDocument();
        if (document != null) {
            document.addDocumentListener(listener);
        }
    }
}
