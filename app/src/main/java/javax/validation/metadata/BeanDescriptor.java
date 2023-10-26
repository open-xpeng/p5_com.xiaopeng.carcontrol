package javax.validation.metadata;

import java.util.Set;

/* loaded from: classes3.dex */
public interface BeanDescriptor extends ElementDescriptor {
    Set<ConstructorDescriptor> getConstrainedConstructors();

    Set<MethodDescriptor> getConstrainedMethods(MethodType methodType, MethodType... methodTypeArr);

    Set<PropertyDescriptor> getConstrainedProperties();

    ConstructorDescriptor getConstraintsForConstructor(Class<?>... clsArr);

    MethodDescriptor getConstraintsForMethod(String str, Class<?>... clsArr);

    PropertyDescriptor getConstraintsForProperty(String str);

    boolean isBeanConstrained();
}
