package org.apache.commons.compress.archivers.jar;

import java.security.cert.Certificate;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

/* loaded from: classes3.dex */
public class JarArchiveEntry extends ZipArchiveEntry {
    private final Certificate[] certificates;
    private final Attributes manifestAttributes;

    public JarArchiveEntry(ZipEntry zipEntry) throws ZipException {
        super(zipEntry);
        this.manifestAttributes = null;
        this.certificates = null;
    }

    public JarArchiveEntry(String str) {
        super(str);
        this.manifestAttributes = null;
        this.certificates = null;
    }

    public JarArchiveEntry(ZipArchiveEntry zipArchiveEntry) throws ZipException {
        super(zipArchiveEntry);
        this.manifestAttributes = null;
        this.certificates = null;
    }

    public JarArchiveEntry(JarEntry jarEntry) throws ZipException {
        super(jarEntry);
        this.manifestAttributes = null;
        this.certificates = null;
    }

    @Deprecated
    public Attributes getManifestAttributes() {
        return this.manifestAttributes;
    }

    @Deprecated
    public Certificate[] getCertificates() {
        Certificate[] certificateArr = this.certificates;
        if (certificateArr != null) {
            int length = certificateArr.length;
            Certificate[] certificateArr2 = new Certificate[length];
            System.arraycopy(certificateArr, 0, certificateArr2, 0, length);
            return certificateArr2;
        }
        return null;
    }
}
