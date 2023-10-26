package android.support.rastermill.data;

import android.support.rastermill.util.ByteArrayPool;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class StreamEncoder implements Encoder<InputStream> {
    private static final String TAG = "StreamEncoder";

    @Override // android.support.rastermill.data.Encoder
    public String getId() {
        return "";
    }

    @Override // android.support.rastermill.data.Encoder
    public boolean encode(InputStream inputStream, OutputStream outputStream) {
        byte[] bytes = ByteArrayPool.get().getBytes();
        while (true) {
            try {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    outputStream.write(bytes, 0, read);
                } else {
                    return true;
                }
            } catch (IOException e) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Failed to encode data onto the OutputStream", e);
                }
                return false;
            } finally {
                ByteArrayPool.get().releaseBytes(bytes);
            }
        }
    }
}
