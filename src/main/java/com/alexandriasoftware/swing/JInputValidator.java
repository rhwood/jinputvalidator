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
import javax.annotation.Nullable;
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

    /**
     * The original border of the validated component.
     */
    private final Border originalBorder;
    /**
     * The original tool tip text of the validated component.
     */
    private String originalToolTipText = null;
    /**
     * Property change support for monitoring this validator as a bean.
     */
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    /**
     * The current validation state.
     */
    private Validation state;
    /**
     * The prior validation state.
     */
    private Validation oldState;
    /**
     * The preferences applied to this validator.
     */
    private final JInputValidatorPreferences preferences;
    /**
     * The validated component.
     */
    private final JComponent component;
    /**
     * True when in {@link #verify(JComponent)} to prevent endless recursion
     * when validating a component triggers a redraw.
     */
    private boolean inVerifyMethod;
    /**
     * True if validation is also verifying the component.
     *
     * @see #setVerifying(boolean)
     */
    private boolean shouldVerify;

    /**
     * Create a JInputValidator with the default preferences. The validator
     * created with this call listens to all changes to the component if the
     * component is a {@link javax.swing.text.JTextComponent}.
     *
     * @param component the component to attach the validator to
     */
    protected JInputValidator(final @Nonnull JComponent component) {
        this(component, true, true);
    }

    /**
     * Create a JInputValidator with the default preferences.
     *
     * @param component   the component to attach the validator to
     * @param onInput     {@code true} if validator to validate on all input;
     *                    {@code false} to validate only on focus change; note
     *                    this has no effect if component is not a
     *                    {@link javax.swing.text.JTextComponent}
     * @param isVerifying {@code true} if validator is to return true or false
     *                    per
     *                    {@link #verify(javax.swing.JComponent)}; {@code false}
     *                    to always return {@code true} for that method.
     */
    protected JInputValidator(@Nonnull final JComponent component,
            final boolean onInput,
            final boolean isVerifying) {
        this(component, onInput, isVerifying,
                JInputValidatorPreferences.getPreferences());
    }

    /**
     * Create a JInputValidator with custom preferences.
     *
     * @param component   the component to attach the validator to
     * @param onInput     {@code true} if validator to validate on all input;
     *                    {@code false} to validate only on focus change; note
     *                    this has no effect if component is not a
     *                    {@link javax.swing.text.JTextComponent}
     * @param isVerifying {@code true} if validator is to return true or false
     *                    per
     *                    {@link #verify(javax.swing.JComponent)}; {@code false}
     *                    to always return {@code true} for that method.
     * @param preferences the custom preferences
     */
    protected JInputValidator(@Nonnull final JComponent component,
            final boolean onInput,
            final boolean isVerifying,
            @Nonnull final JInputValidatorPreferences preferences) {
        this.component = component;
        originalBorder = this.component.getBorder();
        originalToolTipText = this.component.getToolTipText();
        if (onInput && this.component instanceof JTextComponent) {
            addChangeListener((JTextComponent) this.component,
                    e -> verify(this.component));
        }
        this.preferences = preferences;
        this.shouldVerify = isVerifying;
        state = getNoneValidation();
        oldState = getNoneValidation();
    }

    /**
     * Set if this validator is verifying when
     * {@link #verify(javax.swing.JComponent)} is called. When not verifying
     * {@link #verify(javax.swing.JComponent)} always returns {@code true}. When
     * verifying, that method returns {@code false} if the current {@link Type}
     * is {@link Type#WARNING} or {@link Type#DANGER}.
     *
     * @param isVerifying {@code true} if verifying; {@code false} if not
     */
    public void setVerifying(final boolean isVerifying) {
        this.shouldVerify = isVerifying;
    }

    /**
     * Check if this validator is verifying when
     * {@link #verify(javax.swing.JComponent)} is called.
     *
     * @return {@code true} if verifying; {@code false} if not
     */
    public boolean isVerifying() {
        return shouldVerify;
    }

    /**
     * Set the tool tip text used when the validation state is
     * {@link Validation.Type#NONE}. If the validation state is NONE when
     * calling this method, the component's tool tip text is changed as well.
     *
     * @param toolTipText the default tool tip text for the component being
     *                    validated
     */
    public void setToolTipText(final String toolTipText) {
        originalToolTipText = toolTipText;
        if (state == null || state.getType() == Type.NONE) {
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
     * @return the current validation
     */
    @Nonnull
    public final Validation getValidation() {
        return state;
    }

    /**
     * Get the validation object for the current state of the input component.
     *
     * @param input       the component to get the state of
     * @param preferences preferences to use for creating the validation
     * @return the validation for the current state
     */
    protected abstract Validation getValidation(JComponent input,
            JInputValidatorPreferences preferences);

    /**
     * {@inheritDoc}
     *
     * This implementation, besides verifying if focus can change, redraws the
     * component with a re-evaluated validation state. The validation state can
     * be retrieved afterwards using {@link #getValidation()}.
     *
     * @return {@code false} if {@link Validation#getType()} equals
     *         {@link Validation.Type#DANGER} or {@link Validation.Type#WARNING}
     *         and {@link #shouldVerify} is {@code true}; otherwise returns
     *         {@code true}
     */
    @Override
    public boolean verify(final JComponent input) {
        oldState = state;
        state = getValidation(input, preferences);
        if (!state.equals(oldState) && !inVerifyMethod) {
            inVerifyMethod = true;
            if (state.getType() == Type.NONE) {
                input.setBorder(originalBorder);
                input.setToolTipText(originalToolTipText);
            } else {
                input.setBorder(new ValidatorBorder(state, originalBorder));
                input.setToolTipText(state.getMessage());
            }
            input.validate();
            pcs.firePropertyChange("validation", oldState, state);
            inVerifyMethod = false;
        }
        if (shouldVerify) {
            // WARNING or DANGER are false, all others are true
            return state.getType() != Type.WARNING
                    && state.getType() != Type.DANGER;
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
     * Get the {@link java.beans.PropertyChangeSupport} supporting this
     * validator.
     *
     * @return the support
     */
    protected PropertyChangeSupport getPropertyChangeSupport() {
        return pcs;
    }

    /**
     * Set the preferences for a Validation to this JInputValidator's
     * preferences.
     *
     * @param validation the validation to set preferences for
     */
    protected final void setValidationPreferences(final Validation validation) {
        validation.setPreferences(preferences);
    }

    /**
     * Trims input and removes the leading {@code &lt;html&gt;} and trailing
     * {@code &lt;/html&gt;} markers if present.
     *
     * @param input the input to modify
     * @return the modified input or an empty string if input was null
     */
    @Nonnull
    protected final String trimHtmlTags(@Nullable final String input) {
        String output = input != null ? input.trim() : "";
        String start = "<html>";
        String end = "</html>";
        if (output.startsWith(start)) {
            output = output.substring(start.length());
        }
        if (output.endsWith(end)) {
            output = output.substring(0, output.length() - end.length());
        }
        return output.trim();
    }

    /**
     * Create a Validation with type {@link Type#NONE} and with the contents of
     * {@link #getToolTipText()} as the message. Note that the message of a
     * Validation with Type.NONE is not used internally, but this allows a
     * listener to get a Validation with the default tool tip text.
     *
     * @return a new Validation with Type.NONE
     */
    public final Validation getNoneValidation() {
        return new Validation(Type.NONE, getToolTipText(), preferences);
    }

    /**
     * Add a {@link PropertyChangeListener} for all properties.
     *
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(
            final PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Add a {@link PropertyChangeListener} for the named property.
     *
     * @param propertyName the property to listen to
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(final String propertyName,
            final PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * Remove a {@link PropertyChangeListener} for all properties.
     *
     * @param listener the listener to remove
     */
    public void removePropertyChangeListener(
            final PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    /**
     * Remove a {@link PropertyChangeListener} for the named property.
     *
     * @param propertyName the property listened to
     * @param listener the listener to remove
     */
    public void removePropertyChangeListener(final String propertyName,
            final PropertyChangeListener listener) {
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
     * Get the {@link PropertyChangeListener}s for the named property.
     *
     * @param propertyName the property listened to
     * @return the listeners
     */
    public PropertyChangeListener[] getPropertyChangeListeners(
            final String propertyName) {
        return pcs.getPropertyChangeListeners(propertyName);
    }

    private static void addChangeListener(
            @Nonnull final JTextComponent component,
            @Nonnull final ChangeListener changeListener) {
        DocumentListener listener = new DocumentListener() {
            private int lastChange = 0;
            private int lastNotifiedChange = 0;

            /**
             * {@inheritDoc}
             */
            @Override
            public void insertUpdate(final DocumentEvent e) {
                changedUpdate(e);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void removeUpdate(final DocumentEvent e) {
                changedUpdate(e);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void changedUpdate(final DocumentEvent e) {
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
