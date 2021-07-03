package com.alexandriasoftware.swing;

import org.junit.jupiter.api.BeforeEach;

class JInputValidatorBeanInfoTest extends AbstractValidatorBeanInfoTest {

    @BeforeEach
    public void setUp() {
        info = new JInputValidatorBeanInfo();
        trgt = JInputValidator.class;
    }
}
