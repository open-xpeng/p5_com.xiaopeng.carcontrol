package javax.validation;

/* loaded from: classes3.dex */
public class UnexpectedTypeException extends ConstraintDeclarationException {
    public UnexpectedTypeException(String str) {
        super(str);
    }

    public UnexpectedTypeException() {
    }

    public UnexpectedTypeException(String str, Throwable th) {
        super(str, th);
    }

    public UnexpectedTypeException(Throwable th) {
        super(th);
    }
}
