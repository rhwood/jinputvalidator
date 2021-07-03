package com.alexandriasoftware.swing;

import java.awt.Image;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyChangeListener;
import java.beans.SimpleBeanInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

// package private
class AbstractValidatorBeanInfo extends SimpleBeanInfo {

    private static java.awt.Image iconColor16 = null;
    private static java.awt.Image iconColor32 = null;
    private static java.awt.Image iconMono16 = null;
    private static java.awt.Image iconMono32 = null;
    private static String iconNameC16 = "/com/fontawesome/clipboard-check.png";
    private static String iconNameC32 = "/com/fontawesome/clipboard-check@2x.png";
    private static String iconNameM16 = "/com/fontawesome/clipboard-check.png";
    private static String iconNameM32 = "/com/fontawesome/clipboard-check@2x.png";

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
