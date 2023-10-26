package com.irdeto.securesdk;

/* loaded from: classes.dex */
public enum FileIndex {
    ISF_SS_FileIndex(1);
    
    private int index;

    FileIndex(int i) {
        this.index = i;
    }

    public int getValue() {
        return this.index;
    }
}
