package org.apache.commons.compress.archivers.ar;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.utils.ArchiveUtils;

/* loaded from: classes3.dex */
public class ArArchiveOutputStream extends ArchiveOutputStream {
    public static final int LONGFILE_BSD = 1;
    public static final int LONGFILE_ERROR = 0;
    private final OutputStream out;
    private ArArchiveEntry prevEntry;
    private long entryOffset = 0;
    private boolean haveUnclosedEntry = false;
    private int longFileMode = 0;
    private boolean finished = false;

    public ArArchiveOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public void setLongFileMode(int i) {
        this.longFileMode = i;
    }

    private long writeArchiveHeader() throws IOException {
        byte[] asciiBytes = ArchiveUtils.toAsciiBytes(ArArchiveEntry.HEADER);
        this.out.write(asciiBytes);
        return asciiBytes.length;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void closeArchiveEntry() throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        if (this.prevEntry == null || !this.haveUnclosedEntry) {
            throw new IOException("No current entry to close");
        }
        if (this.entryOffset % 2 != 0) {
            this.out.write(10);
        }
        this.haveUnclosedEntry = false;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void putArchiveEntry(ArchiveEntry archiveEntry) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        ArArchiveEntry arArchiveEntry = (ArArchiveEntry) archiveEntry;
        ArArchiveEntry arArchiveEntry2 = this.prevEntry;
        if (arArchiveEntry2 == null) {
            writeArchiveHeader();
        } else if (arArchiveEntry2.getLength() != this.entryOffset) {
            throw new IOException("length does not match entry (" + this.prevEntry.getLength() + " != " + this.entryOffset);
        } else {
            if (this.haveUnclosedEntry) {
                closeArchiveEntry();
            }
        }
        this.prevEntry = arArchiveEntry;
        writeEntryHeader(arArchiveEntry);
        this.entryOffset = 0L;
        this.haveUnclosedEntry = true;
    }

    private long fill(long j, long j2, char c) throws IOException {
        long j3 = j2 - j;
        if (j3 > 0) {
            for (int i = 0; i < j3; i++) {
                write(c);
            }
        }
        return j2;
    }

    private long write(String str) throws IOException {
        byte[] bytes = str.getBytes("ascii");
        write(bytes);
        return bytes.length;
    }

    private long writeEntryHeader(ArArchiveEntry arArchiveEntry) throws IOException {
        long write;
        boolean z;
        String name = arArchiveEntry.getName();
        if (this.longFileMode == 0 && name.length() > 16) {
            throw new IOException("filename too long, > 16 chars: " + name);
        }
        if (1 == this.longFileMode && (name.length() > 16 || name.contains(" "))) {
            z = true;
            write = 0 + write("#1/" + String.valueOf(name.length()));
        } else {
            write = 0 + write(name);
            z = false;
        }
        long fill = fill(write, 16L, ' ');
        String str = "" + arArchiveEntry.getLastModified();
        if (str.length() > 12) {
            throw new IOException("modified too long");
        }
        long fill2 = fill(fill + write(str), 28L, ' ');
        String str2 = "" + arArchiveEntry.getUserId();
        if (str2.length() > 6) {
            throw new IOException("userid too long");
        }
        long fill3 = fill(fill2 + write(str2), 34L, ' ');
        String str3 = "" + arArchiveEntry.getGroupId();
        if (str3.length() > 6) {
            throw new IOException("groupid too long");
        }
        long fill4 = fill(fill3 + write(str3), 40L, ' ');
        String str4 = "" + Integer.toString(arArchiveEntry.getMode(), 8);
        if (str4.length() > 8) {
            throw new IOException("filemode too long");
        }
        long fill5 = fill(fill4 + write(str4), 48L, ' ');
        String valueOf = String.valueOf(arArchiveEntry.getLength() + (z ? name.length() : 0));
        if (valueOf.length() > 10) {
            throw new IOException("size too long");
        }
        long fill6 = fill(fill5 + write(valueOf), 58L, ' ') + write(ArArchiveEntry.TRAILER);
        return z ? fill6 + write(name) : fill6;
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i, int i2) throws IOException {
        this.out.write(bArr, i, i2);
        count(i2);
        this.entryOffset += i2;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (!this.finished) {
            finish();
        }
        this.out.close();
        this.prevEntry = null;
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public ArchiveEntry createArchiveEntry(File file, String str) throws IOException {
        if (this.finished) {
            throw new IOException("Stream has already been finished");
        }
        return new ArArchiveEntry(file, str);
    }

    @Override // org.apache.commons.compress.archivers.ArchiveOutputStream
    public void finish() throws IOException {
        if (this.haveUnclosedEntry) {
            throw new IOException("This archive contains unclosed entries.");
        }
        if (this.finished) {
            throw new IOException("This archive has already been finished");
        }
        this.finished = true;
    }
}
