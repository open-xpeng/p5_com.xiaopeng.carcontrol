package javax.validation;

import java.util.List;
import javax.validation.spi.ValidationProvider;

/* loaded from: classes3.dex */
public interface ValidationProviderResolver {
    List<ValidationProvider<?>> getValidationProviders();
}
