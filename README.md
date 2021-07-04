JInputValidator
===============

An [InputVerifier](https://docs.oracle.com/javase/8/docs/api/index.html?javax/swing/InputVerifier.html) for Java Swing that shows validation state to the right of the validating component.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.alexandriasoftware.swing/jinputvalidator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.alexandriasoftware.swing/jinputvalidator)
[![Javadocs](https://www.javadoc.io/badge/com.alexandriasoftware.swing/jinputvalidator.svg)](https://www.javadoc.io/doc/com.alexandriasoftware.swing/jinputvalidator)
[![Build](https://github.com/rhwood/jinputvalidator/actions/workflows/build.yml/badge.svg)](https://github.com/rhwood/jinputvalidator/actions/workflows/build.yml)
[![CodeQL](https://github.com/rhwood/jinputvalidator/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/rhwood/jinputvalidator/actions/workflows/codeql-analysis.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=rhwood_jinputvalidator&metric=alert_status)](https://sonarcloud.io/dashboard?id=rhwood_jinputvalidator)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=rhwood_jinputvalidator&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=rhwood_jinputvalidator)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=rhwood_jinputvalidator&metric=coverage)](https://sonarcloud.io/dashboard?id=rhwood_jinputvalidator)

JInputValidator extends the verify idiom to use six states, each with its own graphical representation and tool tip text:

- __DANGER__, a failure state, which defaults to a red letter _X_ in a circle.
- __INFORMATION__, a non-failure state, which defaults to a light blue letter _i_ in a circle.
- __NONE__, the default state, which does not show any icon.
- __SUCCESS__, a non-failure state, which defaults to a green check mark in a circle.
- __UNKNOWN__, a non-failure state, which defaults to a light blue question mark in a circle.
- __WARNING__, a failure state, which defaults to a yellow-orange exclamtion mark in a triangle.

When a status is set to a "failure state" `InputVerifier.verify(Component c)` returns `false`, while for "non-failure states" that method returns `true`.

## Screenshots

![Nimbus L&F Screenshot](https://raw.github.com/rhwood/jinputvalidator/master/wiki/images/all-nimbus.png)

## Using the Control

Simple example that shows a Danger indicator if an input is less than 8 characters:

```java
// create a control
JTextField myJTextField = new JTextField();
// add a new JInputValidator as the InputVerifier
myJTextField.setInputVerifier(new JInputValidator(myJTextField, true) {
            @Override
            protected Validation getValidation(JComponent input, JInputValidatorPreferences preferences) {
                if (myJTextField.getText().length < 8) {
                    return new Validation(Validation.Type.DANGER, "Too short", preferences);
                }
                return new Validation(Validation.Type.NONE, "", preferences);
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
myJTextField.setInputVerifier(new VerifyingValidator(myJTextField,
        myJTextField.getInputVerifier(),
        new Validation(Validation.Type.DANGER, "Too short", preferences)));
```

## Customizing

By default, `JInputValidator` uses [PatternFly 4 colors](https://www.patternfly.org/v4/design-guidelines/styles/colors) and [Font Awesome icons](https://fontawesome.com/icons?d=gallery&s=solid&m=free) for the various states.

These values can be overridden by providing a [`Preferences`](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/prefs/Preferences.html) object to [`JInputValidatorPreferences.getPreferences(Preferences preferences)`](https://static.javadoc.io/com.alexandriasoftware.swing/jinputvalidator/0.1.0/com/alexandriasoftware/swing/JInputValidatorPreferences.html#getPreferences-java.util.prefs.Preferences-) containing the following keys:

- __font__ The path of the font to use for the visual verification. This must be a path that is loadable as a resource.
- __danger.color__ The color to render the danger indicator in.
- __danger.icon__ The Unicode characters for the danger indicator.
- __information.color__ The color to render the information indicator in.
- __information.icon__ The Unicode characters for the information indicator.
- __success.color__ The color to render the success indicator in.
- __sucess.icon__ The Unicode characters for the success indicator.
- __unknown.color__ The color to render the unknown indicator in.
- __unknown.icon__ The Unicode characters for the unknown indicator.
- __warning.color__ The color to render the warning indicator in.
- __warning.icon__ The Unicode characters for the warning indicator.

Once you have the `JInputValidatorPreferences` object, it can be passed to the constructor of a `JInputValidator`.

If your application already uses `Preferences` for other purposes, you can set the above keys in the package `com.alexandriasoftware.swing` to override the defaults as long as your application reads its preferences before creating the first JInputValidator object.

## License

JInputValidator is released under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0)
