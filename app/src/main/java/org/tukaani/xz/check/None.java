package org.tukaani.xz.check;

import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi.BizConstants;

/* loaded from: classes3.dex */
public class None extends Check {
    public None() {
        this.size = 0;
        this.name = BizConstants.CLIENT_ENCODING_NONE;
    }

    @Override // org.tukaani.xz.check.Check
    public byte[] finish() {
        return new byte[0];
    }

    @Override // org.tukaani.xz.check.Check
    public void update(byte[] bArr, int i, int i2) {
    }
}
