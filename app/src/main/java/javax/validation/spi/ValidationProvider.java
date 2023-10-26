package javax.validation.spi;

import javax.validation.Configuration;
import javax.validation.ValidatorFactory;

/* loaded from: classes3.dex */
public interface ValidationProvider<T extends Configuration<T>> {
    ValidatorFactory buildValidatorFactory(ConfigurationState configurationState);

    Configuration<?> createGenericConfiguration(BootstrapState bootstrapState);

    T createSpecializedConfiguration(BootstrapState bootstrapState);
}
