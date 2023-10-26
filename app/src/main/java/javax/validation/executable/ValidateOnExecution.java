package javax.validation.executable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: classes3.dex */
public @interface ValidateOnExecution {
    ExecutableType[] type() default {ExecutableType.IMPLICIT};
}
