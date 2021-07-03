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

import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

/**
 * Provide a graphical editor for {@link VerifyingValidator}.
 * <p>
 * Icon is from
 * <a href="https://fontawesome.com/icons/clipboard-check?style=solid">Font
 * Awesome</a>.
 * 
 * @author Randall Wood
 */
@SuppressWarnings("java:S115") // static names include names of methods
public class VerifyingValidatorBeanInfo extends AbstractValidatorBeanInfo {

    // Bean descriptor
    /* lazy BeanDescriptor */
    private static BeanDescriptor getBdescriptor() {
        return new BeanDescriptor(VerifyingValidator.class, null);
    }

    // Property identifiers
    private static final int PROPERTY_propertyChangeListeners = 0;
    private static final int PROPERTY_toolTipText = 1;
    private static final int PROPERTY_validation = 2;

    // Property array
    /* lazy PropertyDescriptor */
    private static PropertyDescriptor[] getPdescriptor() {
        PropertyDescriptor[] properties = new PropertyDescriptor[3];

        try {
            properties[PROPERTY_propertyChangeListeners] = new PropertyDescriptor("propertyChangeListeners",
                    VerifyingValidator.class, "getPropertyChangeListeners", null);
            properties[PROPERTY_toolTipText] = new PropertyDescriptor("toolTipText", VerifyingValidator.class,
                    "getToolTipText", "setToolTipText");
            properties[PROPERTY_validation] = new PropertyDescriptor("validation", VerifyingValidator.class,
                    "getValidation", null);
        } catch (IntrospectionException e) {
            Logger.getLogger(JInputValidatorBeanInfo.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return properties;
    }

    // Method identifiers
    private static final int METHOD_addPropertyChangeListener0 = 0;
    private static final int METHOD_getPropertyChangeListeners1 = 1;
    private static final int METHOD_removePropertyChangeListener2 = 2;
    private static final int METHOD_shouldYieldFocus3 = 3;
    private static final int METHOD_shouldYieldFocus4 = 4;
    private static final int METHOD_verify5 = 5;
    private static final int METHOD_verifyTarget6 = 6;

    // Method array
    /* lazy MethodDescriptor */
    private static MethodDescriptor[] getMdescriptor() {
        MethodDescriptor[] methods = new MethodDescriptor[7];

        try {
            methods[METHOD_addPropertyChangeListener0] = new MethodDescriptor(JInputValidator.class
                    .getMethod("addPropertyChangeListener", String.class, PropertyChangeListener.class));
            methods[METHOD_addPropertyChangeListener0].setDisplayName("");
            methods[METHOD_getPropertyChangeListeners1] = new MethodDescriptor(
                    JInputValidator.class.getMethod("getPropertyChangeListeners", String.class));
            methods[METHOD_getPropertyChangeListeners1].setDisplayName("");
            methods[METHOD_removePropertyChangeListener2] = new MethodDescriptor(JInputValidator.class
                    .getMethod("removePropertyChangeListener", String.class, PropertyChangeListener.class));
            methods[METHOD_removePropertyChangeListener2].setDisplayName("");
            methods[METHOD_shouldYieldFocus3] = new MethodDescriptor(
                    InputVerifier.class.getMethod("shouldYieldFocus", JComponent.class));
            methods[METHOD_shouldYieldFocus3].setDisplayName("");
            methods[METHOD_shouldYieldFocus4] = new MethodDescriptor(
                    InputVerifier.class.getMethod("shouldYieldFocus", JComponent.class, JComponent.class));
            methods[METHOD_shouldYieldFocus4].setDisplayName("");
            methods[METHOD_verify5] = new MethodDescriptor(JInputValidator.class.getMethod("verify", JComponent.class));
            methods[METHOD_verify5].setDisplayName("");
            methods[METHOD_verifyTarget6] = new MethodDescriptor(
                    InputVerifier.class.getMethod("verifyTarget", JComponent.class));
            methods[METHOD_verifyTarget6].setDisplayName("");
        } catch (Exception e) {
            Logger.getLogger(JInputValidatorBeanInfo.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return methods;
    }

    /**
     * Gets the bean's <code>BeanDescriptor</code>s.
     *
     * @return BeanDescriptor describing the editable properties of this bean. May
     *         return null if the information should be obtained by automatic
     *         analysis.
     */
    @Override
    public BeanDescriptor getBeanDescriptor() {
        return getBdescriptor();
    }

    /**
     * Gets the bean's <code>PropertyDescriptor</code>s.
     *
     * @return An array of PropertyDescriptors describing the editable properties
     *         supported by this bean. May return null if the information should be
     *         obtained by automatic analysis.
     *         <p>
     *         If a property is indexed, then its entry in the result array will
     *         belong to the IndexedPropertyDescriptor subclass of
     *         PropertyDescriptor. A client of getPropertyDescriptors can use
     *         "instanceof" to check if a given PropertyDescriptor is an
     *         IndexedPropertyDescriptor.
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return getPdescriptor();
    }

    /**
     * Gets the bean's <code>MethodDescriptor</code>s.
     *
     * @return An array of MethodDescriptors describing the methods implemented by
     *         this bean. May return null if the information should be obtained by
     *         automatic analysis.
     */
    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return getMdescriptor();
    }

}
