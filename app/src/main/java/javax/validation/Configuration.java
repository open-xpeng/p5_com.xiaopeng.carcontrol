package javax.validation;

import java.io.InputStream;
import javax.validation.Configuration;

/* loaded from: classes3.dex */
public interface Configuration<T extends Configuration<T>> {
    T addMapping(InputStream inputStream);

    T addProperty(String str, String str2);

    ValidatorFactory buildValidatorFactory();

    T constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory);

    BootstrapConfiguration getBootstrapConfiguration();

    ConstraintValidatorFactory getDefaultConstraintValidatorFactory();

    MessageInterpolator getDefaultMessageInterpolator();

    ParameterNameProvider getDefaultParameterNameProvider();

    TraversableResolver getDefaultTraversableResolver();

    T ignoreXmlConfiguration();

    T messageInterpolator(MessageInterpolator messageInterpolator);

    T parameterNameProvider(ParameterNameProvider parameterNameProvider);

    T traversableResolver(TraversableResolver traversableResolver);
}
