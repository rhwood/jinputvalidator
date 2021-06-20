package com.alexandriasoftware.swing;

import static java.beans.BeanInfo.ICON_COLOR_16x16;
import static java.beans.BeanInfo.ICON_COLOR_32x32;
import static java.beans.BeanInfo.ICON_MONO_16x16;
import static java.beans.BeanInfo.ICON_MONO_32x32;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.beans.BeanInfo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AbstractValidatorBeanInfoTest {

    private BeanInfo info;

    @BeforeEach
    public void setUp() {
        info = new AbstractValidatorBeanInfo();
    }

    @Test
    public void testFoo() {
        assertNotNull(info.getIcon(ICON_COLOR_16x16));
        assertNotNull(info.getIcon(ICON_COLOR_32x32));
        assertNotNull(info.getIcon(ICON_MONO_16x16));
        assertNotNull(info.getIcon(ICON_MONO_32x32));
    }
}
