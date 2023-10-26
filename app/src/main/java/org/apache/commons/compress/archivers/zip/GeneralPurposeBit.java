package org.apache.commons.compress.archivers.zip;

/* loaded from: classes3.dex */
public final class GeneralPurposeBit implements Cloneable {
    private static final int DATA_DESCRIPTOR_FLAG = 8;
    private static final int ENCRYPTION_FLAG = 1;
    private static final int NUMBER_OF_SHANNON_FANO_TREES_FLAG = 4;
    private static final int SLIDING_DICTIONARY_SIZE_FLAG = 2;
    private static final int STRONG_ENCRYPTION_FLAG = 64;
    public static final int UFT8_NAMES_FLAG = 2048;
    private int numberOfShannonFanoTrees;
    private int slidingDictionarySize;
    private boolean languageEncodingFlag = false;
    private boolean dataDescriptorFlag = false;
    private boolean encryptionFlag = false;
    private boolean strongEncryptionFlag = false;

    public boolean usesUTF8ForNames() {
        return this.languageEncodingFlag;
    }

    public void useUTF8ForNames(boolean z) {
        this.languageEncodingFlag = z;
    }

    public boolean usesDataDescriptor() {
        return this.dataDescriptorFlag;
    }

    public void useDataDescriptor(boolean z) {
        this.dataDescriptorFlag = z;
    }

    public boolean usesEncryption() {
        return this.encryptionFlag;
    }

    public void useEncryption(boolean z) {
        this.encryptionFlag = z;
    }

    public boolean usesStrongEncryption() {
        return this.encryptionFlag && this.strongEncryptionFlag;
    }

    public void useStrongEncryption(boolean z) {
        this.strongEncryptionFlag = z;
        if (z) {
            useEncryption(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getSlidingDictionarySize() {
        return this.slidingDictionarySize;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getNumberOfShannonFanoTrees() {
        return this.numberOfShannonFanoTrees;
    }

    public byte[] encode() {
        byte[] bArr = new byte[2];
        encode(bArr, 0);
        return bArr;
    }

    public void encode(byte[] bArr, int i) {
        ZipShort.putShort((this.dataDescriptorFlag ? 8 : 0) | (this.languageEncodingFlag ? 2048 : 0) | (this.encryptionFlag ? 1 : 0) | (this.strongEncryptionFlag ? 64 : 0), bArr, i);
    }

    public static GeneralPurposeBit parse(byte[] bArr, int i) {
        int value = ZipShort.getValue(bArr, i);
        GeneralPurposeBit generalPurposeBit = new GeneralPurposeBit();
        generalPurposeBit.useDataDescriptor((value & 8) != 0);
        generalPurposeBit.useUTF8ForNames((value & 2048) != 0);
        generalPurposeBit.useStrongEncryption((value & 64) != 0);
        generalPurposeBit.useEncryption((value & 1) != 0);
        generalPurposeBit.slidingDictionarySize = (value & 2) != 0 ? 8192 : 4096;
        generalPurposeBit.numberOfShannonFanoTrees = (value & 4) != 0 ? 3 : 2;
        return generalPurposeBit;
    }

    public int hashCode() {
        return (((((((this.encryptionFlag ? 1 : 0) * 17) + (this.strongEncryptionFlag ? 1 : 0)) * 13) + (this.languageEncodingFlag ? 1 : 0)) * 7) + (this.dataDescriptorFlag ? 1 : 0)) * 3;
    }

    public boolean equals(Object obj) {
        if (obj instanceof GeneralPurposeBit) {
            GeneralPurposeBit generalPurposeBit = (GeneralPurposeBit) obj;
            return generalPurposeBit.encryptionFlag == this.encryptionFlag && generalPurposeBit.strongEncryptionFlag == this.strongEncryptionFlag && generalPurposeBit.languageEncodingFlag == this.languageEncodingFlag && generalPurposeBit.dataDescriptorFlag == this.dataDescriptorFlag;
        }
        return false;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("GeneralPurposeBit is not Cloneable?", e);
        }
    }
}
