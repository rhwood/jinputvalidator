package com.github.rhwood.jinputvalidator;

import java.awt.Image;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyChangeListener;
import java.beans.SimpleBeanInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.InputVerifier;
import javax.swing.JComponent;

// package private
@SuppressWarnings("java:S115") // static names include names of methods
class AbstractValidatorBeanInfo extends SimpleBeanInfo {

    private static java.awt.Image iconColor16 = null;
    private static java.awt.Image iconColor32 = null;
    private static java.awt.Image iconMono16 = null;
    private static java.awt.Image iconMono32 = null;
    private static String iconNameC16 = "/com/github/rhwood/jinputvalidator/clipboard-check.png";
    private static String iconNameC32 = "/com/github/rhwood/jinputvalidator/clipboard-check@2x.png";
    private static String iconNameM16 = "/com/github/rhwood/jinputvalidator/clipboard-check.png";
    private static String iconNameM32 = "/com/github/rhwood/jinputvalidator/clipboard-check@2x.png";

    // EventSet identifiers
    private static final int EVENT_propertyChangeListener = 0;

    // EventSet array
    /* lazy EventSetDescriptor */

    private static EventSetDescriptor[] getEventSetDescriptor() {
        EventSetDescriptor[] eventSets = new EventSetDescriptor[1];

        try {
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor(PredicateValidator.class,
                    "propertyChangeListener", PropertyChangeListener.class, new String[] { "propertyChange" },
                    "addPropertyChangeListener", "removePropertyChangeListener");
        } catch (IntrospectionException e) {
            Logger.getLogger(JInputValidatorBeanInfo.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

        return eventSets;
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
    private static MethodDescriptor[] getMethodDescriptor() {
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
     * Gets the bean's <code>EventSetDescriptor</code>s.
     *
     * @return An array of EventSetDescriptors describing the kinds of events fired
     *         by this bean. May return null if the information should be obtained
     *         by automatic analysis.
     */
    @Override
    public EventSetDescriptor[] getEventSetDescriptors() {
        return getEventSetDescriptor();
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
        return getMethodDescriptor();
    }

    /**
     * This method returns an image object that can be used to represent the
     * bean in toolboxes, toolbars, etc. Icon images will typically be GIFs, but
     * may in future include other formats.
     * <p>
     * Beans aren't required to provide icons and may return null from this
     * method.
     * <p>
     * There are four possible flavors of icons (16x16 color, 32x32 color, 16x16
     * mono, 32x32 mono). If a bean choses to only support a single icon we
     * recommend supporting 16x16 color.
     * <p>
     * We recommend that icons have a "transparent" background so they can be
     * rendered onto an existing background.
     *
     * @param iconKind The kind of icon requested. This should be one of the
     *                 constant values ICON_COLOR_16x16, ICON_COLOR_32x32,
     *                 ICON_MONO_16x16, or ICON_MONO_32x32.
     * @return An image object representing the requested icon. May return null
     *         if no suitable icon is available.
     */
    @Override
    public Image getIcon(int iconKind) {
        switch (iconKind) {
            case ICON_COLOR_16x16:
                return loadImage(iconColor16, iconNameC16);
            case ICON_COLOR_32x32:
                return loadImage(iconColor32, iconNameC32);
            case ICON_MONO_16x16:
                return loadImage(iconMono16, iconNameM16);
            case ICON_MONO_32x32:
                return loadImage(iconMono32, iconNameM32);
            default:
                return null;
        }
    }

    private Image loadImage(Image image, String name) {
        if (name == null) {
            return null;
        }
        if (image == null) {
            image = loadImage(name);
        }
        return image;
    }
}
