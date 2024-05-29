package com.github.rhwood.jinputvalidator;

import static java.beans.BeanInfo.ICON_COLOR_16x16;
import static java.beans.BeanInfo.ICON_COLOR_32x32;
import static java.beans.BeanInfo.ICON_MONO_16x16;
import static java.beans.BeanInfo.ICON_MONO_32x32;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.beans.BeanInfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

abstract class AbstractValidatorBeanInfoTest {

    protected BeanInfo info;
    protected Class<?> trgt;

    @Disabled("See https://github.com/rhwood/jinputvalidator/issues/389")
    @Test
    void testGetIcon() {
        assertNotNull(info.getIcon(ICON_COLOR_16x16));
        assertNotNull(info.getIcon(ICON_COLOR_32x32));
        assertNotNull(info.getIcon(ICON_MONO_16x16));
        assertNotNull(info.getIcon(ICON_MONO_32x32));
        assertNull(info.getIcon(Integer.MAX_VALUE));
    }

    @Test
    void TestGetBeanDescriptor() {
        assertNotNull(info.getBeanDescriptor());
        assertEquals(trgt, info.getBeanDescriptor().getBeanClass());
    }

    @Test
    void TestGetPropertyDescriptors() {
        assertNotNull(info.getPropertyDescriptors());
    }

    @Test
    void TestGetEventSetDescriptors() {
        assertNotNull(info.getEventSetDescriptors());
    }

    @Test
    void TestGetMethodDescriptors() {
        assertNotNull(info.getMethodDescriptors());
    }

    @Test
    void TestGetDefaultPropertyIndex() {
        assertEquals(-1, info.getDefaultPropertyIndex());
    }

    @Test
    void TestGetDefaultEventIndex() {
        assertEquals(-1, info.getDefaultEventIndex());
    }
}
