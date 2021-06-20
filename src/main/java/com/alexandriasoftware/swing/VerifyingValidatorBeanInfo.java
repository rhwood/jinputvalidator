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
import java.beans.PropertyDescriptor;

/**
 * Provide a graphical editor for {@link VerifyingValidator}.
 * <p>
 * Icon is from <a href="https://fontawesome.com/icons/clipboard-check?style=solid">Font Awesome</a>.
 * 
 * @author Randall Wood
 */
public class VerifyingValidatorBeanInfo extends AbstractValidatorBeanInfo {

    // Bean descriptor//GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor  ( com.alexandriasoftware.swing.VerifyingValidator.class , null ); // NOI18N//GEN-HEADEREND:BeanDescriptor
        // Here you can add code for customizing the BeanDescriptor.

        return beanDescriptor;     }//GEN-LAST:BeanDescriptor


    // Property identifiers//GEN-FIRST:Properties
    private static final int PROPERTY_propertyChangeListeners = 0;
    private static final int PROPERTY_toolTipText = 1;
    private static final int PROPERTY_validation = 2;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[3];
    
        try {
            properties[PROPERTY_propertyChangeListeners] = new PropertyDescriptor ( "propertyChangeListeners", com.alexandriasoftware.swing.VerifyingValidator.class, "getPropertyChangeListeners", null ); // NOI18N
            properties[PROPERTY_toolTipText] = new PropertyDescriptor ( "toolTipText", com.alexandriasoftware.swing.VerifyingValidator.class, "getToolTipText", "setToolTipText" ); // NOI18N
            properties[PROPERTY_validation] = new PropertyDescriptor ( "validation", com.alexandriasoftware.swing.VerifyingValidator.class, "getValidation", null ); // NOI18N
        }
        catch(IntrospectionException e) {}//GEN-HEADEREND:Properties
        // Here you can add code for customizing the properties array.

        return properties;     }//GEN-LAST:Properties

    // EventSet identifiers//GEN-FIRST:Events
    private static final int EVENT_propertyChangeListener = 0;

    // EventSet array
    /*lazy EventSetDescriptor*/
    private static EventSetDescriptor[] getEdescriptor(){
        EventSetDescriptor[] eventSets = new EventSetDescriptor[1];
    
        try {
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor ( com.alexandriasoftware.swing.VerifyingValidator.class, "propertyChangeListener", java.beans.PropertyChangeListener.class, new String[] {"propertyChange"}, "addPropertyChangeListener", "removePropertyChangeListener" ); // NOI18N
        }
        catch(IntrospectionException e) {}//GEN-HEADEREND:Events
        // Here you can add code for customizing the event sets array.

        return eventSets;     }//GEN-LAST:Events

    // Method identifiers//GEN-FIRST:Methods
    private static final int METHOD_addPropertyChangeListener0 = 0;
    private static final int METHOD_getPropertyChangeListeners1 = 1;
    private static final int METHOD_removePropertyChangeListener2 = 2;
    private static final int METHOD_shouldYieldFocus3 = 3;
    private static final int METHOD_shouldYieldFocus4 = 4;
    private static final int METHOD_verify5 = 5;
    private static final int METHOD_verifyTarget6 = 6;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[7];
    
        try {
            methods[METHOD_addPropertyChangeListener0] = new MethodDescriptor(com.alexandriasoftware.swing.JInputValidator.class.getMethod("addPropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_addPropertyChangeListener0].setDisplayName ( "" );
            methods[METHOD_getPropertyChangeListeners1] = new MethodDescriptor(com.alexandriasoftware.swing.JInputValidator.class.getMethod("getPropertyChangeListeners", new Class[] {java.lang.String.class})); // NOI18N
            methods[METHOD_getPropertyChangeListeners1].setDisplayName ( "" );
            methods[METHOD_removePropertyChangeListener2] = new MethodDescriptor(com.alexandriasoftware.swing.JInputValidator.class.getMethod("removePropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_removePropertyChangeListener2].setDisplayName ( "" );
            methods[METHOD_shouldYieldFocus3] = new MethodDescriptor(javax.swing.InputVerifier.class.getMethod("shouldYieldFocus", new Class[] {javax.swing.JComponent.class})); // NOI18N
            methods[METHOD_shouldYieldFocus3].setDisplayName ( "" );
            methods[METHOD_shouldYieldFocus4] = new MethodDescriptor(javax.swing.InputVerifier.class.getMethod("shouldYieldFocus", new Class[] {javax.swing.JComponent.class, javax.swing.JComponent.class})); // NOI18N
            methods[METHOD_shouldYieldFocus4].setDisplayName ( "" );
            methods[METHOD_verify5] = new MethodDescriptor(com.alexandriasoftware.swing.JInputValidator.class.getMethod("verify", new Class[] {javax.swing.JComponent.class})); // NOI18N
            methods[METHOD_verify5].setDisplayName ( "" );
            methods[METHOD_verifyTarget6] = new MethodDescriptor(javax.swing.InputVerifier.class.getMethod("verifyTarget", new Class[] {javax.swing.JComponent.class})); // NOI18N
            methods[METHOD_verifyTarget6].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods
        // Here you can add code for customizing the methods array.

        return methods;     }//GEN-LAST:Methods

    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx


//GEN-FIRST:Superclass
    // Here you can add code for customizing the Superclass BeanInfo.

//GEN-LAST:Superclass
    /**
     * Gets the bean's <code>BeanDescriptor</code>s.
     *
     * @return BeanDescriptor describing the editable properties of this bean.
     *         May return null if the information should be obtained by
     *         automatic analysis.
     */
    @Override
    public BeanDescriptor getBeanDescriptor() {
        return getBdescriptor();
    }

    /**
     * Gets the bean's <code>PropertyDescriptor</code>s.
     *
     * @return An array of PropertyDescriptors describing the editable
     *         properties supported by this bean. May return null if the
     *         information should be obtained by automatic analysis.
     * <p>
     * If a property is indexed, then its entry in the result array will belong
     * to the IndexedPropertyDescriptor subclass of PropertyDescriptor. A client
     * of getPropertyDescriptors can use "instanceof" to check if a given
     * PropertyDescriptor is an IndexedPropertyDescriptor.
     */
    @Override
    public PropertyDescriptor[] getPropertyDescriptors() {
        return getPdescriptor();
    }

    /**
     * Gets the bean's <code>EventSetDescriptor</code>s.
     *
     * @return An array of EventSetDescriptors describing the kinds of events
     *         fired by this bean. May return null if the information should be
     *         obtained by automatic analysis.
     */
    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return getEdescriptor();
    }

    /**
     * Gets the bean's <code>MethodDescriptor</code>s.
     *
     * @return An array of MethodDescriptors describing the methods implemented
     *         by this bean. May return null if the information should be
     *         obtained by automatic analysis.
     */
    @Override
    public MethodDescriptor[] getMethodDescriptors() {
        return getMdescriptor();
    }

    /**
     * A bean may have a "default" property that is the property that will
     * mostly commonly be initially chosen for update by human's who are
     * customizing the bean.
     *
     * @return Index of default property in the PropertyDescriptor array
     *         returned by getPropertyDescriptors.
     * <P>
     * Returns -1 if there is no default property.
     */
    @Override
    public int getDefaultPropertyIndex() {
        return defaultPropertyIndex;
    }

    /**
     * A bean may have a "default" event that is the event that will mostly
     * commonly be used by human's when using the bean.
     *
     * @return Index of default event in the EventSetDescriptor array returned
     *         by getEventSetDescriptors.
     * <P>
     * Returns -1 if there is no default event.
     */
    @Override
    public int getDefaultEventIndex() {
        return defaultEventIndex;
    }
    
}
