package com.github.rhwood.jinputvalidator;

import org.junit.jupiter.api.BeforeEach;

class PredicateValidatorBeanInfoTest extends AbstractValidatorBeanInfoTest {

    @BeforeEach
    public void setUp() {
        info = new PredicateValidatorBeanInfo();
        trgt = PredicateValidator.class;
    }
}
