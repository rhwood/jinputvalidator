/*
 * Copyright (C) 2019, 2021 Randall Wood.
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
package com.github.rhwood.jinputvalidator;

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provide a graphical editor for {@link JInputValidator}.
 * <p>
 * Icon is from
 * <a href="https://fontawesome.com/icons/clipboard-check?style=solid">Font
 * Awesome</a>.
 * 
 * @author Randall Wood
 */
@SuppressWarnings("java:S115") // static names include names of methods
public class JInputValidatorBeanInfo extends AbstractValidatorBeanInfo {

    // Bean descriptor
    /* lazy BeanDescriptor */
    private static BeanDescriptor getBdescriptor() {
        return new BeanDescriptor(JInputValidator.class, null);
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
                    JInputValidator.class, "getPropertyChangeListeners", null);
            properties[PROPERTY_toolTipText] = new PropertyDescriptor("toolTipText", JInputValidator.class,
                    "getToolTipText", "setToolTipText");
            properties[PROPERTY_validation] = new PropertyDescriptor("validation", JInputValidator.class,
                    "getValidation", null);
        } catch (IntrospectionException e) {
            Logger.getLogger(JInputValidatorBeanInfo.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        // Here you can add code for customizing the properties array.

        return properties;
    }

    /**
     * Construct a {@code JInputValidatorBeanInfo }
     */
    public JInputValidatorBeanInfo() {
        super();
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

}
