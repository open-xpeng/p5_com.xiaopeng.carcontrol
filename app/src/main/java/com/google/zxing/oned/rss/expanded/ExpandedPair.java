package com.google.zxing.oned.rss.expanded;

import com.google.zxing.oned.rss.DataCharacter;
import com.google.zxing.oned.rss.FinderPattern;

/* loaded from: classes.dex */
final class ExpandedPair {
    private final FinderPattern finderPattern;
    private final DataCharacter leftChar;
    private final boolean mayBeLast;
    private final DataCharacter rightChar;

    /* JADX INFO: Access modifiers changed from: package-private */
    public ExpandedPair(DataCharacter dataCharacter, DataCharacter dataCharacter2, FinderPattern finderPattern, boolean z) {
        this.leftChar = dataCharacter;
        this.rightChar = dataCharacter2;
        this.finderPattern = finderPattern;
        this.mayBeLast = z;
    }

    boolean mayBeLast() {
        return this.mayBeLast;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DataCharacter getLeftChar() {
        return this.leftChar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public DataCharacter getRightChar() {
        return this.rightChar;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FinderPattern getFinderPattern() {
        return this.finderPattern;
    }

    public boolean mustBeLast() {
        return this.rightChar == null;
    }

    public String toString() {
        StringBuilder append = new StringBuilder().append("[ ").append(this.leftChar).append(" , ").append(this.rightChar).append(" : ");
        FinderPattern finderPattern = this.finderPattern;
        return append.append(finderPattern == null ? "null" : Integer.valueOf(finderPattern.getValue())).append(" ]").toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof ExpandedPair) {
            ExpandedPair expandedPair = (ExpandedPair) obj;
            return equalsOrNull(this.leftChar, expandedPair.leftChar) && equalsOrNull(this.rightChar, expandedPair.rightChar) && equalsOrNull(this.finderPattern, expandedPair.finderPattern);
        }
        return false;
    }

    private static boolean equalsOrNull(Object obj, Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public int hashCode() {
        return (hashNotNull(this.leftChar) ^ hashNotNull(this.rightChar)) ^ hashNotNull(this.finderPattern);
    }

    private static int hashNotNull(Object obj) {
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }
}
