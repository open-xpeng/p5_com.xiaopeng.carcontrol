package javax.validation;

import java.util.Set;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;

/* loaded from: classes3.dex */
public interface Validator {
    ExecutableValidator forExecutables();

    BeanDescriptor getConstraintsForClass(Class<?> cls);

    <T> T unwrap(Class<T> cls);

    <T> Set<ConstraintViolation<T>> validate(T t, Class<?>... clsArr);

    <T> Set<ConstraintViolation<T>> validateProperty(T t, String str, Class<?>... clsArr);

    <T> Set<ConstraintViolation<T>> validateValue(Class<T> cls, String str, Object obj, Class<?>... clsArr);
}
