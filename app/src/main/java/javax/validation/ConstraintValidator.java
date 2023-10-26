package javax.validation;

import java.lang.annotation.Annotation;

/* loaded from: classes3.dex */
public interface ConstraintValidator<A extends Annotation, T> {
    void initialize(A a);

    boolean isValid(T t, ConstraintValidatorContext constraintValidatorContext);
}
