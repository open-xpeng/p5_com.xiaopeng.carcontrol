package javax.validation;

import java.util.List;

/* loaded from: classes3.dex */
public interface Path extends Iterable<Node> {

    /* loaded from: classes3.dex */
    public interface BeanNode extends Node {
    }

    /* loaded from: classes3.dex */
    public interface ConstructorNode extends Node {
        List<Class<?>> getParameterTypes();
    }

    /* loaded from: classes3.dex */
    public interface CrossParameterNode extends Node {
    }

    /* loaded from: classes3.dex */
    public interface MethodNode extends Node {
        List<Class<?>> getParameterTypes();
    }

    /* loaded from: classes3.dex */
    public interface Node {
        <T extends Node> T as(Class<T> cls);

        Integer getIndex();

        Object getKey();

        ElementKind getKind();

        String getName();

        boolean isInIterable();
    }

    /* loaded from: classes3.dex */
    public interface ParameterNode extends Node {
        int getParameterIndex();
    }

    /* loaded from: classes3.dex */
    public interface PropertyNode extends Node {
    }

    /* loaded from: classes3.dex */
    public interface ReturnValueNode extends Node {
    }
}
