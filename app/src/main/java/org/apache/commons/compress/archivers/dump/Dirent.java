package org.apache.commons.compress.archivers.dump;

/* loaded from: classes3.dex */
class Dirent {
    private final int ino;
    private final String name;
    private final int parentIno;
    private final int type;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Dirent(int i, int i2, int i3, String str) {
        this.ino = i;
        this.parentIno = i2;
        this.type = i3;
        this.name = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getIno() {
        return this.ino;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getParentIno() {
        return this.parentIno;
    }

    int getType() {
        return this.type;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getName() {
        return this.name;
    }

    public String toString() {
        return String.format("[%d]: %s", Integer.valueOf(this.ino), this.name);
    }
}
