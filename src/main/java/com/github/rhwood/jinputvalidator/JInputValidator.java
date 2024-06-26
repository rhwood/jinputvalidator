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

import com.github.rhwood.jinputvalidator.Validation.Type;
import com.github.rhwood.jinputvalidator.border.ValidatorBorder;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
 * @see PredicateValidator
 * @see VerifyingValidator
 */
public abstract class JInputValidator extends InputVerifier {

    private final Border originalBorder;
    private String originalToolTipText = null;
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private Validation validation;
    private Validation oldValidation;
    private final JInputValidatorPreferences preferences;
    private final JComponent component;
    private boolean inVerifyMethod;
    private boolean isVerifying;

    /**
     * Create a JInputValidator with the default preferences. The validator created
     * with this call listens to all changes to the component if the component is a
     * {@link javax.swing.text.JTextComponent}.
     *
     * @param component the component to attach the validator to; must not be null
     */
    protected JInputValidator(JComponent component) {
        this(component, true, true);
    }

    /**
     * Create a JInputValidator with the default preferences.
     *
     * @param component   the component to attach the validator to; must not be null
     * @param onInput     {@code true} if validator to validate on all input;
     *                    {@code false} to validate only on focus change; note this
     *                    has no effect if component is not a
     *                    {@link javax.swing.text.JTextComponent}
     * @param isVerifying {@code true} if validator is to return true or false per
     *                    {@link #verify(javax.swing.JComponent)}; {@code false} to
     *                    always return {@code true} for that method.
     */
    protected JInputValidator(JComponent component, boolean onInput, boolean isVerifying) {
        this(component, onInput, isVerifying, JInputValidatorPreferences.getPreferences());
    }

    /**
     * Create a JInputValidator with custom preferences.
     *
     * @param component   the component to attach the validator to; must not be null
     * @param onInput     {@code true} if validator to validate on all input;
     *                    {@code false} to validate only on focus change; note this
     *                    has no effect if component is not a
     *                    {@link javax.swing.text.JTextComponent}
     * @param isVerifying {@code true} if validator is to return true or false per
     *                    {@link #verify(javax.swing.JComponent)}; {@code false} to
     *                    always return {@code true} for that method.
     * @param preferences the custom preferences; must not be null
     */
    protected JInputValidator(JComponent component, boolean onInput, boolean isVerifying,
            JInputValidatorPreferences preferences) {
        this.component = component;
        originalBorder = this.component.getBorder();
        originalToolTipText = this.component.getToolTipText();
        if (onInput && this.component instanceof JTextComponent) {
            addChangeListener((JTextComponent) this.component, e -> verify(this.component));
        }
        this.preferences = preferences;
        this.isVerifying = isVerifying;
        validation = getNoneValidation();
        oldValidation = getNoneValidation();
    }

    /**
     * Set if this validator is verifying when
     * {@link #verify(javax.swing.JComponent)} is called. When not verifying
     * {@link #verify(javax.swing.JComponent)} always returns {@code true}. When
     * verifying, that method returns {@code false} if the current {@link Type} is
     * {@link Type#WARNING} or {@link Type#DANGER}.
     *
     * @param isVerifying {@code true} if verifying; {@code false} if not
     */
    public void setVerifying(boolean isVerifying) {
        this.isVerifying = isVerifying;
    }

    /**
     * Check if this validator is verifying when
     * {@link #verify(javax.swing.JComponent)} is called.
     *
     * @return {@code true} if verifying; {@code false} if not
     */
    public boolean isVerifying() {
        return isVerifying;
    }

    /**
     * Set the tool tip text used when the validation state is
     * {@link Validation.Type#NONE}. If the validation state is NONE when calling
     * this method, the component's tool tip text is changed as well.
     *
     * @param toolTipText the default tool tip text for the component being
     *                    validated
     */
    public void setToolTipText(String toolTipText) {
        originalToolTipText = toolTipText;
        if (validation.getType() == Type.NONE) {
            component.setToolTipText(toolTipText);
        }
    }

    /**
     * Get the tool tip text used when the validation state is
     * {@link Validation.Type#NONE}.
     *
     * @return the tool tip text
     */
    public String getToolTipText() {
        return originalToolTipText;
    }

    /**
     * Get the current validation. This property can be listened to using the
     * property name {@code validation}.
     *
     * @return the current validation; will not be null
     */
    public final Validation getValidation() {
        return validation;
    }

    /**
     * Get the validation object for the current state of the input component.
     *
     * @param input       the component to get the state of
     * @param preferences preferences to use for creating the validation
     * @return the validation for the current state; must never be null
     */
    protected abstract Validation getValidation(JComponent input, JInputValidatorPreferences preferences);

    /**
     * {@inheritDoc}
     *
     * This implementation, besides verifying if focus can change, redraws the
     * component with a re-evaluated validation state. The validation state can be
     * retrieved afterwards using {@link #getValidation()}.
     *
     * @return {@code false} if {@link Validation#getType()} equals
     *         {@link Validation.Type#DANGER} or {@link Validation.Type#WARNING} and
     *         {@link #isVerifying} is {@code true}; otherwise returns {@code true}
     */
    @Override
    public boolean verify(JComponent input) {
        oldValidation = validation;
        validation = getValidation(input, preferences);
        if (!inVerifyMethod && !validation.equals(oldValidation)) {
            inVerifyMethod = true;
            if (validation.getType() == Type.NONE) {
                input.setBorder(originalBorder);
                input.setToolTipText(originalToolTipText);
            } else {
                input.setBorder(new ValidatorBorder(validation, originalBorder));
                input.setToolTipText(validation.getMessage());
            }
            input.validate();
            pcs.firePropertyChange("validation", oldValidation, validation);
            inVerifyMethod = false;
        }
        if (isVerifying) {
            // WARNING or DANGER are false, all others are true
            return validation.getType() != Type.WARNING && validation.getType() != Type.DANGER;
        }
        return true;
    }

    /**
     * Get the {@link javax.swing.JComponent} this validator modifies.
     * 
     * @return the validated component
     */
    protected JComponent getComponent() {
        return component;
    }

    /**
     * Get the {@link java.beans.PropertyChangeSupport} supporting this validator.
     * 
     * @return the support
     */
    protected PropertyChangeSupport getPropertyChangeSupport() {
        return pcs;
    }

    /**
     * Trims input and removes the leading {@code <html>} and trailing
     * {@code </html>} markers if present.
     *
     * @param input the input to modify
     * @return the modified input or an empty string if input was null
     */
    protected final String trimHtmlTags(String input) {
        String output = input != null ? input.trim() : "";
        if (output.startsWith("<html>")) {
            output = output.substring(6);
        }
        if (output.endsWith("</html>")) {
            output = output.substring(0, output.length() - 7);
        }
        return output.trim();
    }

    /**
     * Create a Validation with type {@link Type#NONE} and with the contents of
     * {@link #getToolTipText()} as the message. Note that the message of a
     * Validation with Type.NONE is not used internally, but this allows a listener
     * to get a Validation with the default tool tip text.
     *
     * @return a new Validation with Type.NONE
     */
    public final Validation getNoneValidation() {
        return new Validation(Type.NONE, getToolTipText(), preferences);
    }

    /**
     * Add a {@link java.beans.PropertyChangeListener} for all properties.
     * 
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Add a {@link java.beans.PropertyChangeListener} for the named property.
     * 
     * @param propertyName the property to listen to
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a {@link java.beans.PropertyChangeListener} for all properties.
     * 
     * @param listener the listener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Remove a {@link java.beans.PropertyChangeListener} for the named property.
     * 
     * @param propertyName the property listened to
     * @param listener the listener to remove
     */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * Get the {@link java.beans.PropertyChangeListener}s for all properties.
     * 
     * @return the listeners
     */
    public PropertyChangeListener[] getPropertyChangeListeners() {
        return pcs.getPropertyChangeListeners();
    }

    /**
     * Get the {@link java.beans.PropertyChangeListener}s for the named property.
     * 
     * @param propertyName the property listened to
     * @return the listeners
     */
    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return pcs.getPropertyChangeListeners(propertyName);
    }

    private void addChangeListener(JTextComponent component, ChangeListener changeListener) {
        DocumentListener listener = new DocumentListener() {
            private int lastChange = 0;
            private int lastNotifiedChange = 0;

            /**
             * {@inheritDoc}
             */
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            /**
             * {@inheritDoc}
             */
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
        component.addPropertyChangeListener("document", e -> {
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
