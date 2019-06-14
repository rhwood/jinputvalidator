JInputValidator
===============

An [InputVerifier](https://docs.oracle.com/javase/8/docs/api/index.html?javax/swing/InputVerifier.html) for Java Swing that shows validation state to the right of the validating component.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.alexandriasoftware.swing/jinputvalidator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.alexandriasoftware.swing/jinputvalidator)
[![Javadocs](https://www.javadoc.io/badge/com.alexandriasoftware.swing/jinputvalidator.svg)](https://www.javadoc.io/doc/com.alexandriasoftware.swing/jinputvalidator)
[![Build Status](https://travis-ci.com/rhwood/jinputvalidator.svg?token=cqDHpGf1kVSnERY1P7z7&branch=master)](https://travis-ci.com/rhwood/jinputvalidator)
[![Coverage Status](https://coveralls.io/repos/github/rhwood/jinputvalidator/badge.svg?branch=master)](https://coveralls.io/github/rhwood/jinputvalidator?branch=master)
[![Maintainability](https://api.codeclimate.com/v1/badges/3a346edbb0d353e71536/maintainability)](https://codeclimate.com/github/rhwood/jinputvalidator/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/3a346edbb0d353e71536/test_coverage)](https://codeclimate.com/github/rhwood/jinputvalidator/test_coverage)

JInputValidator extends the verify idiom to use six states, each with its own graphical representation and tool tip text:

- __DANGER__, a failure state, which defaults to a red letter _X_ in a circle.
- __INFORMATION__, a non-failure state, which defaults to a light blue letter _i_ in a circle.
- __NONE__, the default state, which does not show any icon.
- __SUCCESS__, a non-failure state, which defaults to a green check mark in a circle.
- __UNKNOWN__, a non-failure state, which defaults to a light blue question mark in a circle.
- __WARNING__, a failure state, which defaults to a yellow-orange exclamtion mark in a triangle.

![Nimbus L&F Screenshot](wiki/images/all-nimbus.png)

## Usage

Simple example that shows a Danger indicator if an input is less than 8 characters:

```java
myJTextField.setInputVerifier(new JInputValidator(myJTextField, true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences) {
                if (myJTextField.getText().length < 8) {
                    return new Validation(Validation.Type.DANGER, "Too short");
                }
                return new Validation(Validation.Type.NONE, "");
            }
        });
```

You can also use existing verifiers with the `JInputValidator` subclass `VerifyingValidator`:

```java
myJTextField.setInputVerifier(new InputVerifer() {
    @Override
    public boolean verify(JComponent input) {
        return (myJTextField.getText().length >= 8);
    }
})
myJTextField.setInputVerifier(new VerifyingValidator(myJTextField, myJTextField.getInputVerifier(), new Validation(Validation.Type.DANGER, "Too short")));
```