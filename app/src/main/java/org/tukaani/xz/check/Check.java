package org.tukaani.xz.check;

import java.security.NoSuchAlgorithmException;
import org.tukaani.xz.UnsupportedOptionsException;

/* loaded from: classes3.dex */
public abstract class Check {
    String name;
    int size;

    public static Check getInstance(int i) throws UnsupportedOptionsException {
        if (i != 0) {
            if (i != 1) {
                if (i != 4) {
                    if (i == 10) {
                        try {
                            return new SHA256();
                        } catch (NoSuchAlgorithmException unused) {
                        }
                    }
                    throw new UnsupportedOptionsException(new StringBuffer().append("Unsupported Check ID ").append(i).toString());
                }
                return new CRC64();
            }
            return new CRC32();
        }
        return new None();
    }

    public abstract byte[] finish();

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public void update(byte[] bArr) {
        update(bArr, 0, bArr.length);
    }

    public abstract void update(byte[] bArr, int i, int i2);
}
