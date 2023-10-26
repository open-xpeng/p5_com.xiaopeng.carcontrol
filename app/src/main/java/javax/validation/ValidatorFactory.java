package javax.validation;

/* loaded from: classes3.dex */
public interface ValidatorFactory {
    void close();

    ConstraintValidatorFactory getConstraintValidatorFactory();

    MessageInterpolator getMessageInterpolator();

    ParameterNameProvider getParameterNameProvider();

    TraversableResolver getTraversableResolver();

    Validator getValidator();

    <T> T unwrap(Class<T> cls);

    ValidatorContext usingContext();
}
