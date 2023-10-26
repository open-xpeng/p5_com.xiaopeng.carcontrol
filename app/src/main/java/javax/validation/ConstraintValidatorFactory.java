package javax.validation;

/* loaded from: classes3.dex */
public interface ConstraintValidatorFactory {
    <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> cls);

    void releaseInstance(ConstraintValidator<?, ?> constraintValidator);
}
