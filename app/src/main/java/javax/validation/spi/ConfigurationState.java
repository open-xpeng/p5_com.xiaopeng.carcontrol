package javax.validation.spi;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;

/* loaded from: classes3.dex */
public interface ConfigurationState {
    ConstraintValidatorFactory getConstraintValidatorFactory();

    Set<InputStream> getMappingStreams();

    MessageInterpolator getMessageInterpolator();

    ParameterNameProvider getParameterNameProvider();

    Map<String, String> getProperties();

    TraversableResolver getTraversableResolver();

    boolean isIgnoreXmlConfiguration();
}
