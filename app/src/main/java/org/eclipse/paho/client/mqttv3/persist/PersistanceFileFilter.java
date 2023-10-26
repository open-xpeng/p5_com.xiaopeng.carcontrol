package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FileFilter;

/* loaded from: classes3.dex */
public class PersistanceFileFilter implements FileFilter {
    private final String fileExtension;

    public PersistanceFileFilter(String str) {
        this.fileExtension = str;
    }

    @Override // java.io.FileFilter
    public boolean accept(File file) {
        return file.getName().endsWith(this.fileExtension);
    }
}
