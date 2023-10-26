package javax.validation.metadata;

import java.util.Set;

/* loaded from: classes3.dex */
public interface CascadableDescriptor {
    Set<GroupConversionDescriptor> getGroupConversions();

    boolean isCascaded();
}
