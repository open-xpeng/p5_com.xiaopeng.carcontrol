package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

/* loaded from: classes3.dex */
public class WebSocketFrame {
    public static final int frameLengthOverhead = 6;
    private boolean closeFlag;
    private boolean fin;
    private byte opcode;
    private byte[] payload;

    public byte getOpcode() {
        return this.opcode;
    }

    public boolean isFin() {
        return this.fin;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public boolean isCloseFlag() {
        return this.closeFlag;
    }

    public WebSocketFrame(byte b, boolean z, byte[] bArr) {
        this.closeFlag = false;
        this.opcode = b;
        this.fin = z;
        this.payload = bArr;
    }

    public WebSocketFrame(byte[] bArr) {
        int i = 0;
        this.closeFlag = false;
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        setFinAndOpCode(wrap.get());
        byte b = wrap.get();
        boolean z = (b & 128) != 0;
        int i2 = (byte) (b & Byte.MAX_VALUE);
        int i3 = i2 == 127 ? 8 : i2 == 126 ? 2 : 0;
        while (true) {
            i3--;
            if (i3 <= 0) {
                break;
            }
            i2 |= (wrap.get() & 255) << (i3 * 8);
        }
        byte[] bArr2 = null;
        if (z) {
            byte[] bArr3 = new byte[4];
            wrap.get(bArr3, 0, 4);
            bArr2 = bArr3;
        }
        byte[] bArr4 = new byte[i2];
        this.payload = bArr4;
        wrap.get(bArr4, 0, i2);
        if (!z) {
            return;
        }
        while (true) {
            byte[] bArr5 = this.payload;
            if (i >= bArr5.length) {
                return;
            }
            bArr5[i] = (byte) (bArr5[i] ^ bArr2[i % 4]);
            i++;
        }
    }

    private void setFinAndOpCode(byte b) {
        this.fin = (b & 128) != 0;
        this.opcode = (byte) (b & 15);
    }

    public WebSocketFrame(InputStream inputStream) throws IOException {
        byte[] bArr;
        int i = 0;
        this.closeFlag = false;
        setFinAndOpCode((byte) inputStream.read());
        byte b = this.opcode;
        int i2 = 2;
        if (b != 2) {
            if (b == 8) {
                this.closeFlag = true;
                return;
            }
            throw new IOException("Invalid Frame: Opcode: " + ((int) this.opcode));
        }
        byte read = (byte) inputStream.read();
        boolean z = (read & 128) != 0;
        int i3 = (byte) (read & Byte.MAX_VALUE);
        if (i3 == 127) {
            i2 = 8;
        } else if (i3 != 126) {
            i2 = 0;
        }
        i3 = i2 > 0 ? 0 : i3;
        while (true) {
            i2--;
            if (i2 < 0) {
                break;
            }
            i3 |= (((byte) inputStream.read()) & 255) << (i2 * 8);
        }
        if (z) {
            bArr = new byte[4];
            inputStream.read(bArr, 0, 4);
        } else {
            bArr = null;
        }
        this.payload = new byte[i3];
        int i4 = 0;
        int i5 = i3;
        while (i4 != i3) {
            int read2 = inputStream.read(this.payload, i4, i5);
            i4 += read2;
            i5 -= read2;
        }
        if (!z) {
            return;
        }
        while (true) {
            byte[] bArr2 = this.payload;
            if (i >= bArr2.length) {
                return;
            }
            bArr2[i] = (byte) (bArr2[i] ^ bArr[i % 4]);
            i++;
        }
    }

    public byte[] encodeFrame() {
        byte[] bArr = this.payload;
        int length = bArr.length + 6;
        if (bArr.length > 65535) {
            length += 8;
        } else if (bArr.length >= 126) {
            length += 2;
        }
        ByteBuffer allocate = ByteBuffer.allocate(length);
        appendFinAndOpCode(allocate, this.opcode, this.fin);
        byte[] generateMaskingKey = generateMaskingKey();
        appendLengthAndMask(allocate, this.payload.length, generateMaskingKey);
        int i = 0;
        while (true) {
            byte[] bArr2 = this.payload;
            if (i < bArr2.length) {
                byte b = (byte) (bArr2[i] ^ generateMaskingKey[i % 4]);
                bArr2[i] = b;
                allocate.put(b);
                i++;
            } else {
                allocate.flip();
                return allocate.array();
            }
        }
    }

    public static void appendLengthAndMask(ByteBuffer byteBuffer, int i, byte[] bArr) {
        if (bArr != null) {
            appendLength(byteBuffer, i, true);
            byteBuffer.put(bArr);
            return;
        }
        appendLength(byteBuffer, i, false);
    }

    private static void appendLength(ByteBuffer byteBuffer, int i, boolean z) {
        if (i < 0) {
            throw new IllegalArgumentException("Length cannot be negative");
        }
        int i2 = z ? -128 : 0;
        if (i <= 65535) {
            if (i >= 126) {
                byteBuffer.put((byte) (i2 | 126));
                byteBuffer.put((byte) (i >> 8));
                byteBuffer.put((byte) (i & 255));
                return;
            }
            byteBuffer.put((byte) (i | i2));
            return;
        }
        byteBuffer.put((byte) (i2 | 127));
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) 0);
        byteBuffer.put((byte) ((i >> 24) & 255));
        byteBuffer.put((byte) ((i >> 16) & 255));
        byteBuffer.put((byte) ((i >> 8) & 255));
        byteBuffer.put((byte) (i & 255));
    }

    public static void appendFinAndOpCode(ByteBuffer byteBuffer, byte b, boolean z) {
        byteBuffer.put((byte) ((b & 15) | (z ? (byte) 128 : (byte) 0)));
    }

    public static byte[] generateMaskingKey() {
        SecureRandom secureRandom = new SecureRandom();
        return new byte[]{(byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255), (byte) secureRandom.nextInt(255)};
    }
}
