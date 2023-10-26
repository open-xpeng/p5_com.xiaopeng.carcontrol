package org.tukaani.xz;

/* loaded from: classes3.dex */
public class MemoryLimitException extends XZIOException {
    private static final long serialVersionUID = 3;
    private final int memoryLimit;
    private final int memoryNeeded;

    public MemoryLimitException(int i, int i2) {
        super(new StringBuffer().append("").append(i).append(" KiB of memory would be needed; limit was ").append(i2).append(" KiB").toString());
        this.memoryNeeded = i;
        this.memoryLimit = i2;
    }

    public int getMemoryLimit() {
        return this.memoryLimit;
    }

    public int getMemoryNeeded() {
        return this.memoryNeeded;
    }
}
