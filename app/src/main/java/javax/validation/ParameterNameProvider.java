package javax.validation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

/* loaded from: classes3.dex */
public interface ParameterNameProvider {
    List<String> getParameterNames(Constructor<?> constructor);

    List<String> getParameterNames(Method method);
}
