package javax.validation;

import java.util.Locale;
import javax.validation.metadata.ConstraintDescriptor;

/* loaded from: classes3.dex */
public interface MessageInterpolator {

    /* loaded from: classes3.dex */
    public interface Context {
        ConstraintDescriptor<?> getConstraintDescriptor();

        Object getValidatedValue();

        <T> T unwrap(Class<T> cls);
    }

    String interpolate(String str, Context context);

    String interpolate(String str, Context context, Locale locale);
}
