package com.github.rhwood.jinputvalidator;

import org.junit.jupiter.api.BeforeEach;

class VerifyingValidatorBeanInfoTest extends AbstractValidatorBeanInfoTest {

    @BeforeEach
    public void setUp() {
        info = new VerifyingValidatorBeanInfo();
        trgt = VerifyingValidator.class;
    }
}
