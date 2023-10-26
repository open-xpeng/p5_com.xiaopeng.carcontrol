package javax.validation.spi;

import javax.validation.ValidationProviderResolver;

/* loaded from: classes3.dex */
public interface BootstrapState {
    ValidationProviderResolver getDefaultValidationProviderResolver();

    ValidationProviderResolver getValidationProviderResolver();
}
