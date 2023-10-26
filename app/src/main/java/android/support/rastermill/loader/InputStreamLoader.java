package android.support.rastermill.loader;

import android.content.Context;
import android.support.rastermill.FrameSequence;
import android.support.rastermill.FrameSequenceUtil;
import android.support.rastermill.LogUtil;
import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class InputStreamLoader extends AbsLoader {
    private InputStream mInputStream;

    @Override // android.support.rastermill.loader.ILoader
    public int getType() {
        return 4;
    }

    public InputStreamLoader(Context context, String str, InputStream inputStream) {
        super(context);
        this.mInputStream = inputStream;
        this.mKey = TextUtils.isEmpty(str) ? "inputStream@" + inputStream.hashCode() : str;
    }

    @Override // android.support.rastermill.loader.ILoader
    public boolean exists() {
        try {
            InputStream inputStream = this.mInputStream;
            if (inputStream != null) {
                return inputStream.available() > 0;
            }
            return false;
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // android.support.rastermill.loader.AbsLoader
    public FrameSequence decode() {
        InputStream inputStream = this.mInputStream;
        try {
            FrameSequence decodeStream = FrameSequence.decodeStream(inputStream);
            if (inputStream != null) {
                try {
                    inputStream.close();
                    return decodeStream;
                } catch (IOException unused) {
                    return decodeStream;
                }
            }
            return decodeStream;
        } catch (Throwable th) {
            try {
                Log.e(FrameSequenceUtil.class.toString(), "decodeFile", th);
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                return null;
            } catch (Throwable th2) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException unused3) {
                    }
                }
                throw th2;
            }
        }
    }

    @Override // android.support.rastermill.loader.AbsLoader, android.support.rastermill.loader.ILoader
    public void release() {
        if (LogUtil.isLogEnable()) {
            LogUtil.e("InputStreamLoader release : " + this.mKey);
        }
        InputStream inputStream = this.mInputStream;
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException unused) {
            }
        }
        this.mInputStream = null;
    }
}
