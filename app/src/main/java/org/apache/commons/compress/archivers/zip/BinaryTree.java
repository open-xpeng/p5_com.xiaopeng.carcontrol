package org.apache.commons.compress.archivers.zip;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/* loaded from: classes3.dex */
class BinaryTree {
    private static final int NODE = -2;
    private static final int UNDEFINED = -1;
    private final int[] tree;

    public BinaryTree(int i) {
        int[] iArr = new int[(1 << (i + 1)) - 1];
        this.tree = iArr;
        Arrays.fill(iArr, -1);
    }

    public void addLeaf(int i, int i2, int i3, int i4) {
        if (i3 == 0) {
            int[] iArr = this.tree;
            if (iArr[i] == -1) {
                iArr[i] = i4;
                return;
            }
            throw new IllegalArgumentException("Tree value at index " + i + " has already been assigned (" + this.tree[i] + ")");
        }
        this.tree[i] = -2;
        addLeaf((i * 2) + 1 + (i2 & 1), i2 >>> 1, i3 - 1, i4);
    }

    public int read(BitStream bitStream) throws IOException {
        int i = 0;
        while (true) {
            int nextBit = bitStream.nextBit();
            if (nextBit == -1) {
                return -1;
            }
            int i2 = (i * 2) + 1 + nextBit;
            int i3 = this.tree[i2];
            if (i3 != -2) {
                if (i3 != -1) {
                    return i3;
                }
                throw new IOException("The child " + nextBit + " of node at index " + i + " is not defined");
            }
            i = i2;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static BinaryTree decode(InputStream inputStream, int i) throws IOException {
        int read = inputStream.read() + 1;
        if (read == 0) {
            throw new IOException("Cannot read the size of the encoded tree, unexpected end of stream");
        }
        byte[] bArr = new byte[read];
        new DataInputStream(inputStream).readFully(bArr);
        int[] iArr = new int[i];
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < read; i4++) {
            byte b = bArr[i4];
            int i5 = ((b & 240) >> 4) + 1;
            int i6 = (b & 15) + 1;
            int i7 = 0;
            while (i7 < i5) {
                iArr[i3] = i6;
                i7++;
                i3++;
            }
            i2 = Math.max(i2, i6);
        }
        int[] iArr2 = new int[i];
        for (int i8 = 0; i8 < i; i8++) {
            iArr2[i8] = i8;
        }
        int[] iArr3 = new int[i];
        int i9 = 0;
        for (int i10 = 0; i10 < i; i10++) {
            for (int i11 = 0; i11 < i; i11++) {
                if (iArr[i11] == i10) {
                    iArr3[i9] = i10;
                    iArr2[i9] = i11;
                    i9++;
                }
            }
        }
        int[] iArr4 = new int[i];
        int i12 = 0;
        int i13 = 0;
        int i14 = 0;
        for (int i15 = i - 1; i15 >= 0; i15--) {
            i12 += i13;
            if (iArr3[i15] != i14) {
                i14 = iArr3[i15];
                i13 = 1 << (16 - i14);
            }
            iArr4[iArr2[i15]] = i12;
        }
        BinaryTree binaryTree = new BinaryTree(i2);
        for (int i16 = 0; i16 < i; i16++) {
            int i17 = iArr[i16];
            if (i17 > 0) {
                binaryTree.addLeaf(0, Integer.reverse(iArr4[i16] << 16), i17, i16);
            }
        }
        return binaryTree;
    }
}
