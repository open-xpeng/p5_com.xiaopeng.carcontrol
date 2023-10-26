package org.apache.commons.compress.archivers.zip;

import java.util.zip.ZipException;

/* loaded from: classes3.dex */
public class Zip64ExtendedInformationExtraField implements ZipExtraField {
    private static final String LFH_MUST_HAVE_BOTH_SIZES_MSG = "Zip64 extended information must contain both size values in the local file header.";
    private ZipEightByteInteger compressedSize;
    private ZipLong diskStart;
    private byte[] rawCentralDirectoryData;
    private ZipEightByteInteger relativeHeaderOffset;
    private ZipEightByteInteger size;
    static final ZipShort HEADER_ID = new ZipShort(1);
    private static final byte[] EMPTY = new byte[0];

    public Zip64ExtendedInformationExtraField() {
    }

    public Zip64ExtendedInformationExtraField(ZipEightByteInteger zipEightByteInteger, ZipEightByteInteger zipEightByteInteger2) {
        this(zipEightByteInteger, zipEightByteInteger2, null, null);
    }

    public Zip64ExtendedInformationExtraField(ZipEightByteInteger zipEightByteInteger, ZipEightByteInteger zipEightByteInteger2, ZipEightByteInteger zipEightByteInteger3, ZipLong zipLong) {
        this.size = zipEightByteInteger;
        this.compressedSize = zipEightByteInteger2;
        this.relativeHeaderOffset = zipEightByteInteger3;
        this.diskStart = zipLong;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getHeaderId() {
        return HEADER_ID;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getLocalFileDataLength() {
        return new ZipShort(this.size != null ? 16 : 0);
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public ZipShort getCentralDirectoryLength() {
        return new ZipShort((this.size != null ? 8 : 0) + (this.compressedSize != null ? 8 : 0) + (this.relativeHeaderOffset == null ? 0 : 8) + (this.diskStart != null ? 4 : 0));
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getLocalFileDataData() {
        ZipEightByteInteger zipEightByteInteger = this.size;
        if (zipEightByteInteger != null || this.compressedSize != null) {
            if (zipEightByteInteger == null || this.compressedSize == null) {
                throw new IllegalArgumentException(LFH_MUST_HAVE_BOTH_SIZES_MSG);
            }
            byte[] bArr = new byte[16];
            addSizes(bArr);
            return bArr;
        }
        return EMPTY;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public byte[] getCentralDirectoryData() {
        byte[] bArr = new byte[getCentralDirectoryLength().getValue()];
        int addSizes = addSizes(bArr);
        ZipEightByteInteger zipEightByteInteger = this.relativeHeaderOffset;
        if (zipEightByteInteger != null) {
            System.arraycopy(zipEightByteInteger.getBytes(), 0, bArr, addSizes, 8);
            addSizes += 8;
        }
        ZipLong zipLong = this.diskStart;
        if (zipLong != null) {
            System.arraycopy(zipLong.getBytes(), 0, bArr, addSizes, 4);
        }
        return bArr;
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromLocalFileData(byte[] bArr, int i, int i2) throws ZipException {
        if (i2 == 0) {
            return;
        }
        if (i2 < 16) {
            throw new ZipException(LFH_MUST_HAVE_BOTH_SIZES_MSG);
        }
        this.size = new ZipEightByteInteger(bArr, i);
        int i3 = i + 8;
        this.compressedSize = new ZipEightByteInteger(bArr, i3);
        int i4 = i3 + 8;
        int i5 = i2 - 16;
        if (i5 >= 8) {
            this.relativeHeaderOffset = new ZipEightByteInteger(bArr, i4);
            i4 += 8;
            i5 -= 8;
        }
        if (i5 >= 4) {
            this.diskStart = new ZipLong(bArr, i4);
        }
    }

    @Override // org.apache.commons.compress.archivers.zip.ZipExtraField
    public void parseFromCentralDirectoryData(byte[] bArr, int i, int i2) throws ZipException {
        byte[] bArr2 = new byte[i2];
        this.rawCentralDirectoryData = bArr2;
        System.arraycopy(bArr, i, bArr2, 0, i2);
        if (i2 >= 28) {
            parseFromLocalFileData(bArr, i, i2);
        } else if (i2 == 24) {
            this.size = new ZipEightByteInteger(bArr, i);
            int i3 = i + 8;
            this.compressedSize = new ZipEightByteInteger(bArr, i3);
            this.relativeHeaderOffset = new ZipEightByteInteger(bArr, i3 + 8);
        } else if (i2 % 8 == 4) {
            this.diskStart = new ZipLong(bArr, (i + i2) - 4);
        }
    }

    public void reparseCentralDirectoryData(boolean z, boolean z2, boolean z3, boolean z4) throws ZipException {
        byte[] bArr = this.rawCentralDirectoryData;
        if (bArr != null) {
            int i = 0;
            int i2 = (z ? 8 : 0) + (z2 ? 8 : 0) + (z3 ? 8 : 0) + (z4 ? 4 : 0);
            if (bArr.length < i2) {
                throw new ZipException("central directory zip64 extended information extra field's length doesn't match central directory data.  Expected length " + i2 + " but is " + this.rawCentralDirectoryData.length);
            }
            if (z) {
                this.size = new ZipEightByteInteger(this.rawCentralDirectoryData, 0);
                i = 8;
            }
            if (z2) {
                this.compressedSize = new ZipEightByteInteger(this.rawCentralDirectoryData, i);
                i += 8;
            }
            if (z3) {
                this.relativeHeaderOffset = new ZipEightByteInteger(this.rawCentralDirectoryData, i);
                i += 8;
            }
            if (z4) {
                this.diskStart = new ZipLong(this.rawCentralDirectoryData, i);
            }
        }
    }

    public ZipEightByteInteger getSize() {
        return this.size;
    }

    public void setSize(ZipEightByteInteger zipEightByteInteger) {
        this.size = zipEightByteInteger;
    }

    public ZipEightByteInteger getCompressedSize() {
        return this.compressedSize;
    }

    public void setCompressedSize(ZipEightByteInteger zipEightByteInteger) {
        this.compressedSize = zipEightByteInteger;
    }

    public ZipEightByteInteger getRelativeHeaderOffset() {
        return this.relativeHeaderOffset;
    }

    public void setRelativeHeaderOffset(ZipEightByteInteger zipEightByteInteger) {
        this.relativeHeaderOffset = zipEightByteInteger;
    }

    public ZipLong getDiskStartNumber() {
        return this.diskStart;
    }

    public void setDiskStartNumber(ZipLong zipLong) {
        this.diskStart = zipLong;
    }

    private int addSizes(byte[] bArr) {
        int i;
        ZipEightByteInteger zipEightByteInteger = this.size;
        if (zipEightByteInteger != null) {
            System.arraycopy(zipEightByteInteger.getBytes(), 0, bArr, 0, 8);
            i = 8;
        } else {
            i = 0;
        }
        ZipEightByteInteger zipEightByteInteger2 = this.compressedSize;
        if (zipEightByteInteger2 != null) {
            System.arraycopy(zipEightByteInteger2.getBytes(), 0, bArr, i, 8);
            return i + 8;
        }
        return i;
    }
}
