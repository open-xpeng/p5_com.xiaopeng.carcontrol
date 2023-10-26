package javax.validation.metadata;

import java.lang.annotation.ElementType;
import java.util.Set;

/* loaded from: classes3.dex */
public interface ElementDescriptor {

    /* loaded from: classes3.dex */
    public interface ConstraintFinder {
        ConstraintFinder declaredOn(ElementType... elementTypeArr);

        Set<ConstraintDescriptor<?>> getConstraintDescriptors();

        boolean hasConstraints();

        ConstraintFinder lookingAt(Scope scope);

        ConstraintFinder unorderedAndMatchingGroups(Class<?>... clsArr);
    }

    ConstraintFinder findConstraints();

    Set<ConstraintDescriptor<?>> getConstraintDescriptors();

    Class<?> getElementClass();

    boolean hasConstraints();
}
