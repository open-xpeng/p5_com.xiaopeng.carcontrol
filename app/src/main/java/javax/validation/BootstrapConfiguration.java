package javax.validation;

import java.util.Map;
import java.util.Set;
import javax.validation.executable.ExecutableType;

/* loaded from: classes3.dex */
public interface BootstrapConfiguration {
    Set<String> getConstraintMappingResourcePaths();

    String getConstraintValidatorFactoryClassName();

    String getDefaultProviderClassName();

    Set<ExecutableType> getDefaultValidatedExecutableTypes();

    String getMessageInterpolatorClassName();

    String getParameterNameProviderClassName();

    Map<String, String> getProperties();

    String getTraversableResolverClassName();

    boolean isExecutableValidationEnabled();
}
