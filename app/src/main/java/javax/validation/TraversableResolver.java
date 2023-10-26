package javax.validation;

import java.lang.annotation.ElementType;
import javax.validation.Path;

/* loaded from: classes3.dex */
public interface TraversableResolver {
    boolean isCascadable(Object obj, Path.Node node, Class<?> cls, Path path, ElementType elementType);

    boolean isReachable(Object obj, Path.Node node, Class<?> cls, Path path, ElementType elementType);
}
