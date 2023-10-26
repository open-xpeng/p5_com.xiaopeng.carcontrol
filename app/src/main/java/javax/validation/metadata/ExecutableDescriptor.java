package javax.validation.metadata;

import java.util.List;
import java.util.Set;
import javax.validation.metadata.ElementDescriptor;

/* loaded from: classes3.dex */
public interface ExecutableDescriptor extends ElementDescriptor {
    @Override // javax.validation.metadata.ElementDescriptor
    ElementDescriptor.ConstraintFinder findConstraints();

    @Override // javax.validation.metadata.ElementDescriptor
    Set<ConstraintDescriptor<?>> getConstraintDescriptors();

    CrossParameterDescriptor getCrossParameterDescriptor();

    String getName();

    List<ParameterDescriptor> getParameterDescriptors();

    ReturnValueDescriptor getReturnValueDescriptor();

    boolean hasConstrainedParameters();

    boolean hasConstrainedReturnValue();

    @Override // javax.validation.metadata.ElementDescriptor
    boolean hasConstraints();
}
