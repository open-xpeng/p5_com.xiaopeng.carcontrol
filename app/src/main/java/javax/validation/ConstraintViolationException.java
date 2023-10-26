package javax.validation;

import java.util.HashSet;
import java.util.Set;

/* loaded from: classes3.dex */
public class ConstraintViolationException extends ValidationException {
    private final Set<ConstraintViolation<?>> constraintViolations;

    public ConstraintViolationException(String str, Set<? extends ConstraintViolation<?>> set) {
        super(str);
        if (set == null) {
            this.constraintViolations = null;
        } else {
            this.constraintViolations = new HashSet(set);
        }
    }

    public ConstraintViolationException(Set<? extends ConstraintViolation<?>> set) {
        this(null, set);
    }

    public Set<ConstraintViolation<?>> getConstraintViolations() {
        return this.constraintViolations;
    }
}
