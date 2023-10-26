package org.tukaani.xz;

/* loaded from: classes3.dex */
public class XZFormatException extends XZIOException {
    private static final long serialVersionUID = 3;

    public XZFormatException() {
        super("Input is not in the XZ format");
    }
}
