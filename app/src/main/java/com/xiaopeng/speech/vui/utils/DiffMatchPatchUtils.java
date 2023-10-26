package com.xiaopeng.speech.vui.utils;

import androidx.core.internal.view.SupportMenu;
import com.xiaopeng.carcontrol.quicksetting.QuickSettingConstants;
import com.xiaopeng.xvs.xid.base.AbsException;
import com.xiaopeng.xvs.xid.sync.api.ISync;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.tukaani.xz.common.Util;

/* loaded from: classes2.dex */
public class DiffMatchPatchUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public float Diff_Timeout = 1.0f;
    public short Diff_EditCost = 4;
    public float Match_Threshold = 0.5f;
    public int Match_Distance = 1000;
    public float Patch_DeleteThreshold = 0.5f;
    public short Patch_Margin = 4;
    private short Match_MaxBits = 32;
    private Pattern BLANKLINEEND = Pattern.compile("\\n\\r?\\n\\Z", 32);
    private Pattern BLANKLINESTART = Pattern.compile("\\A\\r?\\n\\r?\\n", 32);

    /* loaded from: classes2.dex */
    public enum Operation {
        DELETE,
        INSERT,
        EQUAL
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes2.dex */
    public static class LinesToCharsResult {
        protected String chars1;
        protected String chars2;
        protected List<String> lineArray;

        protected LinesToCharsResult(String str, String str2, List<String> list) {
            this.chars1 = str;
            this.chars2 = str2;
            this.lineArray = list;
        }
    }

    public LinkedList<Diff> diff_main(String str, String str2) {
        return diff_main(str, str2, true);
    }

    public LinkedList<Diff> diff_main(String str, String str2, boolean z) {
        return diff_main(str, str2, z, this.Diff_Timeout <= 0.0f ? Util.VLI_MAX : System.currentTimeMillis() + (this.Diff_Timeout * 1000.0f));
    }

    private LinkedList<Diff> diff_main(String str, String str2, boolean z, long j) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("Null inputs. (diff_main)");
        }
        if (str.equals(str2)) {
            LinkedList<Diff> linkedList = new LinkedList<>();
            if (str.length() != 0) {
                linkedList.add(new Diff(Operation.EQUAL, str));
            }
            return linkedList;
        }
        int diff_commonPrefix = diff_commonPrefix(str, str2);
        String substring = str.substring(0, diff_commonPrefix);
        String substring2 = str.substring(diff_commonPrefix);
        String substring3 = str2.substring(diff_commonPrefix);
        int diff_commonSuffix = diff_commonSuffix(substring2, substring3);
        String substring4 = substring2.substring(substring2.length() - diff_commonSuffix);
        LinkedList<Diff> diff_compute = diff_compute(substring2.substring(0, substring2.length() - diff_commonSuffix), substring3.substring(0, substring3.length() - diff_commonSuffix), z, j);
        if (substring.length() != 0) {
            diff_compute.addFirst(new Diff(Operation.EQUAL, substring));
        }
        if (substring4.length() != 0) {
            diff_compute.addLast(new Diff(Operation.EQUAL, substring4));
        }
        diff_cleanupMerge(diff_compute);
        return diff_compute;
    }

    private LinkedList<Diff> diff_compute(String str, String str2, boolean z, long j) {
        LinkedList<Diff> linkedList = new LinkedList<>();
        if (str.length() == 0) {
            linkedList.add(new Diff(Operation.INSERT, str2));
            return linkedList;
        } else if (str2.length() == 0) {
            linkedList.add(new Diff(Operation.DELETE, str));
            return linkedList;
        } else {
            String str3 = str.length() > str2.length() ? str : str2;
            String str4 = str.length() > str2.length() ? str2 : str;
            int indexOf = str3.indexOf(str4);
            if (indexOf != -1) {
                Operation operation = str.length() > str2.length() ? Operation.DELETE : Operation.INSERT;
                linkedList.add(new Diff(operation, str3.substring(0, indexOf)));
                linkedList.add(new Diff(Operation.EQUAL, str4));
                linkedList.add(new Diff(operation, str3.substring(indexOf + str4.length())));
                return linkedList;
            } else if (str4.length() == 1) {
                linkedList.add(new Diff(Operation.DELETE, str));
                linkedList.add(new Diff(Operation.INSERT, str2));
                return linkedList;
            } else {
                String[] diff_halfMatch = diff_halfMatch(str, str2);
                if (diff_halfMatch != null) {
                    String str5 = diff_halfMatch[0];
                    String str6 = diff_halfMatch[1];
                    String str7 = diff_halfMatch[2];
                    String str8 = diff_halfMatch[3];
                    String str9 = diff_halfMatch[4];
                    LinkedList<Diff> diff_main = diff_main(str5, str7, z, j);
                    LinkedList<Diff> diff_main2 = diff_main(str6, str8, z, j);
                    diff_main.add(new Diff(Operation.EQUAL, str9));
                    diff_main.addAll(diff_main2);
                    return diff_main;
                } else if (z && str.length() > 100 && str2.length() > 100) {
                    return diff_lineMode(str, str2, j);
                } else {
                    return diff_bisect(str, str2, j);
                }
            }
        }
    }

    private LinkedList<Diff> diff_lineMode(String str, String str2, long j) {
        LinesToCharsResult diff_linesToChars = diff_linesToChars(str, str2);
        String str3 = diff_linesToChars.chars1;
        String str4 = diff_linesToChars.chars2;
        List<String> list = diff_linesToChars.lineArray;
        LinkedList<Diff> diff_main = diff_main(str3, str4, false, j);
        diff_charsToLines(diff_main, list);
        diff_cleanupSemantic(diff_main);
        diff_main.add(new Diff(Operation.EQUAL, ""));
        ListIterator<Diff> listIterator = diff_main.listIterator();
        Diff next = listIterator.next();
        String str5 = "";
        String str6 = str5;
        int i = 0;
        int i2 = 0;
        while (next != null) {
            int i3 = AnonymousClass1.$SwitchMap$com$xiaopeng$speech$vui$utils$DiffMatchPatchUtils$Operation[next.operation.ordinal()];
            if (i3 == 1) {
                i++;
                str6 = str6 + next.text;
            } else if (i3 == 2) {
                i2++;
                str5 = str5 + next.text;
            } else if (i3 == 3) {
                if (i2 >= 1 && i >= 1) {
                    listIterator.previous();
                    for (int i4 = 0; i4 < i2 + i; i4++) {
                        listIterator.previous();
                        listIterator.remove();
                    }
                    Iterator<Diff> it = diff_main(str5, str6, false, j).iterator();
                    while (it.hasNext()) {
                        listIterator.add(it.next());
                    }
                }
                str5 = "";
                str6 = str5;
                i = 0;
                i2 = 0;
            }
            next = listIterator.hasNext() ? listIterator.next() : null;
        }
        diff_main.removeLast();
        return diff_main;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$speech$vui$utils$DiffMatchPatchUtils$Operation;

        static {
            int[] iArr = new int[Operation.values().length];
            $SwitchMap$com$xiaopeng$speech$vui$utils$DiffMatchPatchUtils$Operation = iArr;
            try {
                iArr[Operation.INSERT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$speech$vui$utils$DiffMatchPatchUtils$Operation[Operation.DELETE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$speech$vui$utils$DiffMatchPatchUtils$Operation[Operation.EQUAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0061, code lost:
        if (r7[r14 - 1] < r7[r14 + 1]) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00f1, code lost:
        if (r20[r1 - 1] < r20[r1 + 1]) goto L102;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0094 A[EDGE_INSN: B:105:0x0094->B:35:0x0094 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x0134 A[EDGE_INSN: B:112:0x0134->B:72:0x0134 ?: BREAK  , SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0089 A[LOOP:3: B:29:0x0079->B:33:0x0089, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0125 A[LOOP:5: B:66:0x0107->B:70:0x0125, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0138  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x013c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected java.util.LinkedList<com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.Diff> diff_bisect(java.lang.String r25, java.lang.String r26, long r27) {
        /*
            Method dump skipped, instructions count: 419
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.diff_bisect(java.lang.String, java.lang.String, long):java.util.LinkedList");
    }

    private LinkedList<Diff> diff_bisectSplit(String str, String str2, int i, int i2, long j) {
        String substring = str.substring(0, i);
        String substring2 = str2.substring(0, i2);
        String substring3 = str.substring(i);
        String substring4 = str2.substring(i2);
        LinkedList<Diff> diff_main = diff_main(substring, substring2, false, j);
        diff_main.addAll(diff_main(substring3, substring4, false, j));
        return diff_main;
    }

    protected LinesToCharsResult diff_linesToChars(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        arrayList.add("");
        return new LinesToCharsResult(diff_linesToCharsMunge(str, arrayList, hashMap, AbsException.ERROR_CODE_BASE_SYNC), diff_linesToCharsMunge(str2, arrayList, hashMap, SupportMenu.USER_MASK), arrayList);
    }

    private String diff_linesToCharsMunge(String str, List<String> list, Map<String, Integer> map, int i) {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        int i3 = -1;
        while (i3 < str.length() - 1) {
            i3 = str.indexOf(10, i2);
            if (i3 == -1) {
                i3 = str.length() - 1;
            }
            String substring = str.substring(i2, i3 + 1);
            if (map.containsKey(substring)) {
                sb.append(String.valueOf((char) map.get(substring).intValue()));
            } else {
                if (list.size() == i) {
                    substring = str.substring(i2);
                    i3 = str.length();
                }
                list.add(substring);
                map.put(substring, Integer.valueOf(list.size() - 1));
                sb.append(String.valueOf((char) (list.size() - 1)));
            }
            i2 = i3 + 1;
        }
        return sb.toString();
    }

    protected void diff_charsToLines(List<Diff> list, List<String> list2) {
        for (Diff diff : list) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < diff.text.length(); i++) {
                sb.append(list2.get(diff.text.charAt(i)));
            }
            diff.text = sb.toString();
        }
    }

    public int diff_commonPrefix(String str, String str2) {
        int min = Math.min(str.length(), str2.length());
        for (int i = 0; i < min; i++) {
            if (str.charAt(i) != str2.charAt(i)) {
                return i;
            }
        }
        return min;
    }

    public int diff_commonSuffix(String str, String str2) {
        int length = str.length();
        int length2 = str2.length();
        int min = Math.min(length, length2);
        for (int i = 1; i <= min; i++) {
            if (str.charAt(length - i) != str2.charAt(length2 - i)) {
                return i - 1;
            }
        }
        return min;
    }

    protected int diff_commonOverlap(String str, String str2) {
        int length = str.length();
        int length2 = str2.length();
        if (length == 0 || length2 == 0) {
            return 0;
        }
        if (length > length2) {
            str = str.substring(length - length2);
        } else if (length < length2) {
            str2 = str2.substring(0, length);
        }
        int min = Math.min(length, length2);
        if (str.equals(str2)) {
            return min;
        }
        int i = 1;
        int i2 = 0;
        while (true) {
            int indexOf = str2.indexOf(str.substring(min - i));
            if (indexOf == -1) {
                return i2;
            }
            i += indexOf;
            if (indexOf == 0 || str.substring(min - i).equals(str2.substring(0, i))) {
                i2 = i;
                i++;
            }
        }
    }

    protected String[] diff_halfMatch(String str, String str2) {
        if (this.Diff_Timeout <= 0.0f) {
            return null;
        }
        String str3 = str.length() > str2.length() ? str : str2;
        String str4 = str.length() > str2.length() ? str2 : str;
        if (str3.length() < 4 || str4.length() * 2 < str3.length()) {
            return null;
        }
        String[] diff_halfMatchI = diff_halfMatchI(str3, str4, (str3.length() + 3) / 4);
        String[] diff_halfMatchI2 = diff_halfMatchI(str3, str4, (str3.length() + 1) / 2);
        if (diff_halfMatchI == null && diff_halfMatchI2 == null) {
            return null;
        }
        if (diff_halfMatchI2 != null && (diff_halfMatchI == null || diff_halfMatchI[4].length() <= diff_halfMatchI2[4].length())) {
            diff_halfMatchI = diff_halfMatchI2;
        }
        return str.length() > str2.length() ? diff_halfMatchI : new String[]{diff_halfMatchI[2], diff_halfMatchI[3], diff_halfMatchI[0], diff_halfMatchI[1], diff_halfMatchI[4]};
    }

    private String[] diff_halfMatchI(String str, String str2, int i) {
        int i2;
        String substring = str.substring(i, (str.length() / 4) + i);
        int i3 = -1;
        String str3 = "";
        int i4 = -1;
        String str4 = "";
        String str5 = str4;
        String str6 = str5;
        String str7 = str6;
        while (true) {
            i4 = str2.indexOf(substring, i4 + 1);
            if (i4 == i3) {
                break;
            }
            int diff_commonPrefix = diff_commonPrefix(str.substring(i), str2.substring(i4));
            int diff_commonSuffix = diff_commonSuffix(str.substring(0, i), str2.substring(0, i4));
            if (str3.length() < diff_commonSuffix + diff_commonPrefix) {
                int i5 = i4 - diff_commonSuffix;
                String substring2 = str.substring(0, i - diff_commonSuffix);
                str5 = str.substring(i + diff_commonPrefix);
                str6 = str2.substring(0, i5);
                str7 = str2.substring(i4 + diff_commonPrefix);
                str3 = str2.substring(i5, i4) + str2.substring(i4, i2);
                str4 = substring2;
            }
            i3 = -1;
        }
        if (str3.length() * 2 >= str.length()) {
            return new String[]{str4, str5, str6, str7, str3};
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00eb  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:46:0x00e5 -> B:48:0x00e9). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void diff_cleanupSemantic(java.util.LinkedList<com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.Diff> r15) {
        /*
            Method dump skipped, instructions count: 413
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.diff_cleanupSemantic(java.util.LinkedList):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0129 A[LOOP:0: B:14:0x002f->B:45:0x0129, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x002e A[SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x002e -> B:14:0x002f). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void diff_cleanupSemanticLossless(java.util.LinkedList<com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.Diff> r15) {
        /*
            Method dump skipped, instructions count: 306
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.diff_cleanupSemanticLossless(java.util.LinkedList):void");
    }

    private int diff_cleanupSemanticScore(String str, String str2) {
        if (str.length() == 0 || str2.length() == 0) {
            return 6;
        }
        char charAt = str.charAt(str.length() - 1);
        char charAt2 = str2.charAt(0);
        boolean z = !Character.isLetterOrDigit(charAt);
        boolean z2 = !Character.isLetterOrDigit(charAt2);
        boolean z3 = z && Character.isWhitespace(charAt);
        boolean z4 = z2 && Character.isWhitespace(charAt2);
        boolean z5 = z3 && Character.getType(charAt) == 15;
        boolean z6 = z4 && Character.getType(charAt2) == 15;
        boolean z7 = z5 && this.BLANKLINEEND.matcher(str).find();
        boolean z8 = z6 && this.BLANKLINESTART.matcher(str2).find();
        if (z7 || z8) {
            return 5;
        }
        if (z5 || z6) {
            return 4;
        }
        if (z && !z3 && z4) {
            return 3;
        }
        if (z3 || z4) {
            return 2;
        }
        return (z || z2) ? 1 : 0;
    }

    public void diff_cleanupEfficiency(LinkedList<Diff> linkedList) {
        int i;
        if (linkedList.isEmpty()) {
            return;
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        ListIterator<Diff> listIterator = linkedList.listIterator();
        Diff next = listIterator.next();
        Diff diff = next;
        String str = null;
        boolean z = false;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (next != null) {
            if (next.operation == Operation.EQUAL) {
                if (next.text.length() < this.Diff_EditCost && (i2 != 0 || i3 != 0)) {
                    arrayDeque.push(next);
                    str = next.text;
                    next = diff;
                } else {
                    arrayDeque.clear();
                    str = null;
                    i2 = i4;
                    i3 = i5;
                }
                diff = next;
                i4 = i2;
                i5 = i3;
                i2 = 0;
                i3 = 0;
            } else {
                if (next.operation == Operation.DELETE) {
                    i3 = 1;
                } else {
                    i2 = 1;
                }
                if (str != null && ((i4 != 0 && i5 != 0 && i2 != 0 && i3 != 0) || (str.length() < this.Diff_EditCost / 2 && i4 + i5 + i2 + i3 == 3))) {
                    while (next != arrayDeque.peek()) {
                        next = listIterator.previous();
                    }
                    listIterator.next();
                    listIterator.set(new Diff(Operation.DELETE, str));
                    Diff diff2 = new Diff(Operation.INSERT, str);
                    listIterator.add(diff2);
                    arrayDeque.pop();
                    if (i4 != 0 && i5 != 0) {
                        arrayDeque.clear();
                        diff = diff2;
                        i = 1;
                    } else {
                        if (!arrayDeque.isEmpty()) {
                            arrayDeque.pop();
                        }
                        do {
                        } while ((arrayDeque.isEmpty() ? diff : (Diff) arrayDeque.peek()) != listIterator.previous());
                        i = 0;
                    }
                    i2 = i;
                    i3 = i2;
                    str = null;
                    z = true;
                }
            }
            next = listIterator.hasNext() ? listIterator.next() : null;
        }
        if (z) {
            diff_cleanupMerge(linkedList);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0175 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:101:0x016d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x029c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0294 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void diff_cleanupMerge(java.util.LinkedList<com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.Diff> r15) {
        /*
            Method dump skipped, instructions count: 677
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.diff_cleanupMerge(java.util.LinkedList):void");
    }

    public int diff_xIndex(List<Diff> list, int i) {
        Diff diff;
        Iterator<Diff> it = list.iterator();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (true) {
            if (!it.hasNext()) {
                diff = null;
                break;
            }
            diff = it.next();
            if (diff.operation != Operation.INSERT) {
                i2 += diff.text.length();
            }
            if (diff.operation != Operation.DELETE) {
                i3 += diff.text.length();
            }
            if (i2 > i) {
                break;
            }
            i4 = i2;
            i5 = i3;
        }
        return (diff == null || diff.operation != Operation.DELETE) ? i5 + (i - i4) : i5;
    }

    public String diff_prettyHtml(List<Diff> list) {
        StringBuilder sb = new StringBuilder();
        for (Diff diff : list) {
            String replace = diff.text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\n", "&para;<br>");
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$speech$vui$utils$DiffMatchPatchUtils$Operation[diff.operation.ordinal()];
            if (i == 1) {
                sb.append("<ins style=\"background:#e6ffe6;\">").append(replace).append("</ins>");
            } else if (i == 2) {
                sb.append("<del style=\"background:#ffe6e6;\">").append(replace).append("</del>");
            } else if (i == 3) {
                sb.append("<span>").append(replace).append("</span>");
            }
        }
        return sb.toString();
    }

    public String diff_text1(List<Diff> list) {
        StringBuilder sb = new StringBuilder();
        for (Diff diff : list) {
            if (diff.operation != Operation.INSERT) {
                sb.append(diff.text);
            }
        }
        return sb.toString();
    }

    public String diff_text2(List<Diff> list) {
        StringBuilder sb = new StringBuilder();
        for (Diff diff : list) {
            if (diff.operation != Operation.DELETE) {
                sb.append(diff.text);
            }
        }
        return sb.toString();
    }

    public int diff_levenshtein(List<Diff> list) {
        int i = 0;
        int i2 = 0;
        while (true) {
            int i3 = i2;
            for (Diff diff : list) {
                int i4 = AnonymousClass1.$SwitchMap$com$xiaopeng$speech$vui$utils$DiffMatchPatchUtils$Operation[diff.operation.ordinal()];
                if (i4 == 1) {
                    i2 += diff.text.length();
                } else if (i4 == 2) {
                    i3 += diff.text.length();
                } else if (i4 != 3) {
                }
            }
            return i + Math.max(i2, i3);
            i += Math.max(i2, i3);
            i2 = 0;
        }
    }

    public String diff_toDelta(List<Diff> list) {
        StringBuilder sb = new StringBuilder();
        for (Diff diff : list) {
            int i = AnonymousClass1.$SwitchMap$com$xiaopeng$speech$vui$utils$DiffMatchPatchUtils$Operation[diff.operation.ordinal()];
            if (i == 1) {
                try {
                    sb.append(MqttTopic.SINGLE_LEVEL_WILDCARD).append(URLEncoder.encode(diff.text, "UTF-8").replace('+', ' ')).append("\t");
                } catch (UnsupportedEncodingException e) {
                    throw new Error("This system does not support UTF-8.", e);
                }
            } else if (i == 2) {
                sb.append("-").append(diff.text.length()).append("\t");
            } else if (i == 3) {
                sb.append("=").append(diff.text.length()).append("\t");
            }
        }
        String sb2 = sb.toString();
        return sb2.length() != 0 ? unescapeForEncodeUriCompatability(sb2.substring(0, sb2.length() - 1)) : sb2;
    }

    public LinkedList<Diff> diff_fromDelta(String str, String str2) throws IllegalArgumentException {
        String[] split;
        LinkedList<Diff> linkedList = new LinkedList<>();
        int i = 0;
        for (String str3 : str2.split("\t")) {
            if (str3.length() != 0) {
                String substring = str3.substring(1);
                char charAt = str3.charAt(0);
                if (charAt == '+') {
                    String replace = substring.replace(MqttTopic.SINGLE_LEVEL_WILDCARD, "%2B");
                    try {
                        linkedList.add(new Diff(Operation.INSERT, URLDecoder.decode(replace, "UTF-8")));
                    } catch (UnsupportedEncodingException e) {
                        throw new Error("This system does not support UTF-8.", e);
                    } catch (IllegalArgumentException e2) {
                        throw new IllegalArgumentException("Illegal escape in diff_fromDelta: " + replace, e2);
                    }
                } else if (charAt == '-' || charAt == '=') {
                    try {
                        int parseInt = Integer.parseInt(substring);
                        if (parseInt < 0) {
                            throw new IllegalArgumentException("Negative number in diff_fromDelta: " + substring);
                        }
                        int i2 = parseInt + i;
                        try {
                            String substring2 = str.substring(i, i2);
                            if (str3.charAt(0) == '=') {
                                linkedList.add(new Diff(Operation.EQUAL, substring2));
                            } else {
                                linkedList.add(new Diff(Operation.DELETE, substring2));
                            }
                            i = i2;
                        } catch (StringIndexOutOfBoundsException e3) {
                            throw new IllegalArgumentException("Delta length (" + i2 + ") larger than source text length (" + str.length() + ").", e3);
                        }
                    } catch (NumberFormatException e4) {
                        throw new IllegalArgumentException("Invalid number in diff_fromDelta: " + substring, e4);
                    }
                } else {
                    throw new IllegalArgumentException("Invalid diff operation in diff_fromDelta: " + str3.charAt(0));
                }
            }
        }
        if (i == str.length()) {
            return linkedList;
        }
        throw new IllegalArgumentException("Delta length (" + i + ") smaller than source text length (" + str.length() + ").");
    }

    public int match_main(String str, String str2, int i) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("Null inputs. (match_main)");
        }
        int max = Math.max(0, Math.min(i, str.length()));
        if (str.equals(str2)) {
            return 0;
        }
        if (str.length() == 0) {
            return -1;
        }
        return (str2.length() + max > str.length() || !str.substring(max, str2.length() + max).equals(str2)) ? match_bitap(str, str2, max) : max;
    }

    protected int match_bitap(String str, String str2, int i) {
        Map<Character, Integer> match_alphabet = match_alphabet(str2);
        double d = this.Match_Threshold;
        int indexOf = str.indexOf(str2, i);
        int i2 = -1;
        int i3 = 0;
        if (indexOf != -1) {
            d = Math.min(match_bitapScore(0, indexOf, i, str2), d);
            int lastIndexOf = str.lastIndexOf(str2, str2.length() + i);
            if (lastIndexOf != -1) {
                d = Math.min(match_bitapScore(0, lastIndexOf, i, str2), d);
            }
        }
        int i4 = 1;
        int length = 1 << (str2.length() - 1);
        int length2 = str2.length() + str.length();
        int[] iArr = new int[0];
        int i5 = 0;
        while (i5 < str2.length()) {
            int i6 = i3;
            int i7 = length2;
            while (i6 < length2) {
                if (match_bitapScore(i5, i + length2, i, str2) <= d) {
                    i6 = length2;
                } else {
                    i7 = length2;
                }
                length2 = ((i7 - i6) / 2) + i6;
            }
            int max = Math.max(i4, (i - length2) + i4);
            int min = Math.min(i + length2, str.length()) + str2.length();
            int[] iArr2 = new int[min + 2];
            iArr2[min + 1] = (i4 << i5) - 1;
            while (true) {
                if (min < max) {
                    break;
                }
                int i8 = i2;
                i2 = min - 1;
                int intValue = (str.length() <= i2 || !match_alphabet.containsKey(Character.valueOf(str.charAt(i2)))) ? 0 : match_alphabet.get(Character.valueOf(str.charAt(i2))).intValue();
                if (i5 == 0) {
                    iArr2[min] = ((iArr2[min + 1] << 1) | 1) & intValue;
                } else {
                    int i9 = min + 1;
                    iArr2[min] = (((iArr2[i9] << 1) | 1) & intValue) | ((iArr[i9] | iArr[min]) << 1) | 1 | iArr[i9];
                }
                if ((iArr2[min] & length) != 0) {
                    double match_bitapScore = match_bitapScore(i5, i2, i, str2);
                    if (match_bitapScore <= d) {
                        if (i2 <= i) {
                            i4 = 1;
                            d = match_bitapScore;
                            break;
                        }
                        i4 = 1;
                        max = Math.max(1, (i * 2) - i2);
                        d = match_bitapScore;
                        min--;
                    }
                }
                i4 = 1;
                i2 = i8;
                min--;
            }
            i5++;
            if (match_bitapScore(i5, i, i, str2) > d) {
                break;
            }
            iArr = iArr2;
            i3 = 0;
        }
        return i2;
    }

    private double match_bitapScore(int i, int i2, int i3, String str) {
        float length = i / str.length();
        int abs = Math.abs(i3 - i2);
        int i4 = this.Match_Distance;
        if (i4 == 0) {
            if (abs == 0) {
                return length;
            }
            return 1.0d;
        }
        return length + (abs / i4);
    }

    protected Map<Character, Integer> match_alphabet(String str) {
        HashMap hashMap = new HashMap();
        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            hashMap.put(Character.valueOf(c), 0);
        }
        int i = 0;
        for (char c2 : charArray) {
            hashMap.put(Character.valueOf(c2), Integer.valueOf(((Integer) hashMap.get(Character.valueOf(c2))).intValue() | (1 << ((str.length() - i) - 1))));
            i++;
        }
        return hashMap;
    }

    protected void patch_addContext(Patch patch, String str) {
        if (str.length() == 0) {
            return;
        }
        String substring = str.substring(patch.start2, patch.start2 + patch.length1);
        int i = 0;
        while (str.indexOf(substring) != str.lastIndexOf(substring)) {
            int length = substring.length();
            short s = this.Match_MaxBits;
            short s2 = this.Patch_Margin;
            if (length >= (s - s2) - s2) {
                break;
            }
            i += s2;
            substring = str.substring(Math.max(0, patch.start2 - i), Math.min(str.length(), patch.start2 + patch.length1 + i));
        }
        int i2 = i + this.Patch_Margin;
        String substring2 = str.substring(Math.max(0, patch.start2 - i2), patch.start2);
        if (substring2.length() != 0) {
            patch.diffs.addFirst(new Diff(Operation.EQUAL, substring2));
        }
        String substring3 = str.substring(patch.start2 + patch.length1, Math.min(str.length(), patch.start2 + patch.length1 + i2));
        if (substring3.length() != 0) {
            patch.diffs.addLast(new Diff(Operation.EQUAL, substring3));
        }
        patch.start1 -= substring2.length();
        patch.start2 -= substring2.length();
        patch.length1 += substring2.length() + substring3.length();
        patch.length2 += substring2.length() + substring3.length();
    }

    public LinkedList<Patch> patch_make(String str, String str2) {
        if (str == null || str2 == null) {
            throw new IllegalArgumentException("Null inputs. (patch_make)");
        }
        LinkedList<Diff> diff_main = diff_main(str, str2, true);
        if (diff_main.size() > 2) {
            diff_cleanupSemantic(diff_main);
            diff_cleanupEfficiency(diff_main);
        }
        return patch_make(str, diff_main);
    }

    public LinkedList<Patch> patch_make(LinkedList<Diff> linkedList) {
        if (linkedList == null) {
            throw new IllegalArgumentException("Null inputs. (patch_make)");
        }
        return patch_make(diff_text1(linkedList), linkedList);
    }

    @Deprecated
    public LinkedList<Patch> patch_make(String str, String str2, LinkedList<Diff> linkedList) {
        return patch_make(str, linkedList);
    }

    public LinkedList<Patch> patch_make(String str, LinkedList<Diff> linkedList) {
        if (str == null || linkedList == null) {
            throw new IllegalArgumentException("Null inputs. (patch_make)");
        }
        LinkedList<Patch> linkedList2 = new LinkedList<>();
        if (linkedList.isEmpty()) {
            return linkedList2;
        }
        Patch patch = new Patch();
        Iterator<Diff> it = linkedList.iterator();
        Patch patch2 = patch;
        int i = 0;
        int i2 = 0;
        String str2 = str;
        while (it.hasNext()) {
            Diff next = it.next();
            if (patch2.diffs.isEmpty() && next.operation != Operation.EQUAL) {
                patch2.start1 = i;
                patch2.start2 = i2;
            }
            int i3 = AnonymousClass1.$SwitchMap$com$xiaopeng$speech$vui$utils$DiffMatchPatchUtils$Operation[next.operation.ordinal()];
            if (i3 == 1) {
                patch2.diffs.add(next);
                patch2.length2 += next.text.length();
                str2 = str2.substring(0, i2) + next.text + str2.substring(i2);
            } else if (i3 == 2) {
                patch2.length1 += next.text.length();
                patch2.diffs.add(next);
                str2 = str2.substring(0, i2) + str2.substring(next.text.length() + i2);
            } else if (i3 == 3) {
                if (next.text.length() <= this.Patch_Margin * 2 && !patch2.diffs.isEmpty() && next != linkedList.getLast()) {
                    patch2.diffs.add(next);
                    patch2.length1 += next.text.length();
                    patch2.length2 += next.text.length();
                }
                if (next.text.length() >= this.Patch_Margin * 2 && !patch2.diffs.isEmpty() && !patch2.diffs.isEmpty()) {
                    patch_addContext(patch2, str);
                    linkedList2.add(patch2);
                    patch2 = new Patch();
                    str = str2;
                    i = i2;
                }
            }
            if (next.operation != Operation.INSERT) {
                i += next.text.length();
            }
            if (next.operation != Operation.DELETE) {
                i2 += next.text.length();
            }
        }
        if (!patch2.diffs.isEmpty()) {
            patch_addContext(patch2, str);
            linkedList2.add(patch2);
        }
        return linkedList2;
    }

    public LinkedList<Patch> patch_deepCopy(LinkedList<Patch> linkedList) {
        LinkedList<Patch> linkedList2 = new LinkedList<>();
        Iterator<Patch> it = linkedList.iterator();
        while (it.hasNext()) {
            Patch next = it.next();
            Patch patch = new Patch();
            Iterator<Diff> it2 = next.diffs.iterator();
            while (it2.hasNext()) {
                Diff next2 = it2.next();
                patch.diffs.add(new Diff(next2.operation, next2.text));
            }
            patch.start1 = next.start1;
            patch.start2 = next.start2;
            patch.length1 = next.length1;
            patch.length2 = next.length2;
            linkedList2.add(patch);
        }
        return linkedList2;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0098  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object[] patch_apply(java.util.LinkedList<com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.Patch> r17, java.lang.String r18) {
        /*
            Method dump skipped, instructions count: 434
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.patch_apply(java.util.LinkedList, java.lang.String):java.lang.Object[]");
    }

    public String patch_addPadding(LinkedList<Patch> linkedList) {
        Diff last;
        short s = this.Patch_Margin;
        String str = "";
        for (short s2 = 1; s2 <= s; s2 = (short) (s2 + 1)) {
            str = str + String.valueOf((char) s2);
        }
        Iterator<Patch> it = linkedList.iterator();
        while (it.hasNext()) {
            Patch next = it.next();
            next.start1 += s;
            next.start2 += s;
        }
        Patch first = linkedList.getFirst();
        LinkedList<Diff> linkedList2 = first.diffs;
        if (linkedList2.isEmpty() || linkedList2.getFirst().operation != Operation.EQUAL) {
            linkedList2.addFirst(new Diff(Operation.EQUAL, str));
            first.start1 -= s;
            first.start2 -= s;
            first.length1 += s;
            first.length2 += s;
        } else if (s > linkedList2.getFirst().text.length()) {
            Diff first2 = linkedList2.getFirst();
            int length = s - first2.text.length();
            first2.text = str.substring(first2.text.length()) + first2.text;
            first.start1 -= length;
            first.start2 -= length;
            first.length1 += length;
            first.length2 += length;
        }
        Patch last2 = linkedList.getLast();
        LinkedList<Diff> linkedList3 = last2.diffs;
        if (linkedList3.isEmpty() || linkedList3.getLast().operation != Operation.EQUAL) {
            linkedList3.addLast(new Diff(Operation.EQUAL, str));
            last2.length1 += s;
            last2.length2 += s;
        } else if (s > linkedList3.getLast().text.length()) {
            int length2 = s - linkedList3.getLast().text.length();
            last.text += str.substring(0, length2);
            last2.length1 += length2;
            last2.length2 += length2;
        }
        return str;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:5:0x0014 -> B:6:0x0015). Please submit an issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void patch_splitMax(java.util.LinkedList<com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.Patch> r15) {
        /*
            Method dump skipped, instructions count: 520
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.speech.vui.utils.DiffMatchPatchUtils.patch_splitMax(java.util.LinkedList):void");
    }

    public String patch_toText(List<Patch> list) {
        StringBuilder sb = new StringBuilder();
        for (Patch patch : list) {
            sb.append(patch);
        }
        return sb.toString();
    }

    public List<Patch> patch_fromText(String str) throws IllegalArgumentException {
        LinkedList linkedList = new LinkedList();
        if (str.length() == 0) {
            return linkedList;
        }
        LinkedList linkedList2 = new LinkedList(Arrays.asList(str.split("\n")));
        Pattern compile = Pattern.compile("^@@ -(\\d+),?(\\d*) \\+(\\d+),?(\\d*) @@$");
        while (!linkedList2.isEmpty()) {
            Matcher matcher = compile.matcher((CharSequence) linkedList2.getFirst());
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid patch string: " + ((String) linkedList2.getFirst()));
            }
            Patch patch = new Patch();
            linkedList.add(patch);
            patch.start1 = Integer.parseInt(matcher.group(1));
            if (matcher.group(2).length() == 0) {
                patch.start1--;
                patch.length1 = 1;
            } else if (matcher.group(2).equals("0")) {
                patch.length1 = 0;
            } else {
                patch.start1--;
                patch.length1 = Integer.parseInt(matcher.group(2));
            }
            patch.start2 = Integer.parseInt(matcher.group(3));
            if (matcher.group(4).length() == 0) {
                patch.start2--;
                patch.length2 = 1;
            } else if (matcher.group(4).equals("0")) {
                patch.length2 = 0;
            } else {
                patch.start2--;
                patch.length2 = Integer.parseInt(matcher.group(4));
            }
            linkedList2.removeFirst();
            while (true) {
                if (!linkedList2.isEmpty()) {
                    try {
                        char charAt = ((String) linkedList2.getFirst()).charAt(0);
                        String replace = ((String) linkedList2.getFirst()).substring(1).replace(MqttTopic.SINGLE_LEVEL_WILDCARD, "%2B");
                        try {
                            String decode = URLDecoder.decode(replace, "UTF-8");
                            if (charAt == '-') {
                                patch.diffs.add(new Diff(Operation.DELETE, decode));
                            } else if (charAt == '+') {
                                patch.diffs.add(new Diff(Operation.INSERT, decode));
                            } else if (charAt == ' ') {
                                patch.diffs.add(new Diff(Operation.EQUAL, decode));
                            } else if (charAt != '@') {
                                throw new IllegalArgumentException("Invalid patch mode '" + charAt + "' in: " + decode);
                            }
                            linkedList2.removeFirst();
                        } catch (UnsupportedEncodingException e) {
                            throw new Error("This system does not support UTF-8.", e);
                        } catch (IllegalArgumentException e2) {
                            throw new IllegalArgumentException("Illegal escape in patch_fromText: " + replace, e2);
                        }
                    } catch (IndexOutOfBoundsException unused) {
                        linkedList2.removeFirst();
                    }
                }
            }
        }
        return linkedList;
    }

    /* loaded from: classes2.dex */
    public static class Diff {
        public Operation operation;
        public String text;

        public Diff(Operation operation, String str) {
            this.operation = operation;
            this.text = str;
        }

        public String toString() {
            return "Diff(" + this.operation + ",\"" + this.text.replace('\n', (char) 182) + "\")";
        }

        public int hashCode() {
            Operation operation = this.operation;
            int hashCode = operation == null ? 0 : operation.hashCode();
            String str = this.text;
            return hashCode + ((str != null ? str.hashCode() : 0) * 31);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Diff diff = (Diff) obj;
                if (this.operation != diff.operation) {
                    return false;
                }
                String str = this.text;
                if (str == null) {
                    if (diff.text != null) {
                        return false;
                    }
                } else if (!str.equals(diff.text)) {
                    return false;
                }
                return true;
            }
            return false;
        }
    }

    /* loaded from: classes2.dex */
    public static class Patch {
        public LinkedList<Diff> diffs = new LinkedList<>();
        public int length1;
        public int length2;
        public int start1;
        public int start2;

        public String toString() {
            String str;
            String str2;
            int i = this.length1;
            if (i == 0) {
                str = this.start1 + ",0";
            } else if (i == 1) {
                str = Integer.toString(this.start1 + 1);
            } else {
                str = (this.start1 + 1) + "," + this.length1;
            }
            int i2 = this.length2;
            if (i2 == 0) {
                str2 = this.start2 + ",0";
            } else if (i2 == 1) {
                str2 = Integer.toString(this.start2 + 1);
            } else {
                str2 = (this.start2 + 1) + "," + this.length2;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("@@ -").append(str).append(" +").append(str2).append(" @@\n");
            Iterator<Diff> it = this.diffs.iterator();
            while (it.hasNext()) {
                Diff next = it.next();
                int i3 = AnonymousClass1.$SwitchMap$com$xiaopeng$speech$vui$utils$DiffMatchPatchUtils$Operation[next.operation.ordinal()];
                if (i3 == 1) {
                    sb.append('+');
                } else if (i3 == 2) {
                    sb.append('-');
                } else if (i3 == 3) {
                    sb.append(' ');
                }
                try {
                    sb.append(URLEncoder.encode(next.text, "UTF-8").replace('+', ' ')).append("\n");
                } catch (UnsupportedEncodingException e) {
                    throw new Error("This system does not support UTF-8.", e);
                }
            }
            return DiffMatchPatchUtils.unescapeForEncodeUriCompatability(sb.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String unescapeForEncodeUriCompatability(String str) {
        return str.replace("%21", "!").replace("%7E", "~").replace("%27", "'").replace("%28", "(").replace("%29", ")").replace("%3B", ";").replace("%2F", MqttTopic.TOPIC_LEVEL_SEPARATOR).replace("%3F", "?").replace("%3A", QuickSettingConstants.JOINER).replace("%40", ISync.EXTRA_SYNC_GROUP_KEY_SEPARATOR).replace("%26", "&").replace("%3D", "=").replace("%2B", MqttTopic.SINGLE_LEVEL_WILDCARD).replace("%24", "$").replace("%2C", ",").replace("%23", MqttTopic.MULTI_LEVEL_WILDCARD);
    }

    public static String diffAndMerge(String str, String str2) {
        DiffMatchPatchUtils diffMatchPatchUtils = new DiffMatchPatchUtils();
        LinkedList<Diff> diff_compute = diffMatchPatchUtils.diff_compute(str, str2, true, 2147483647L);
        Iterator<Diff> it = diff_compute.iterator();
        while (it.hasNext()) {
            Diff next = it.next();
            LogUtils.logDebug("VuiSceneCache", next.operation + "========" + next.text);
            System.out.println(next.operation + "========" + next.text);
        }
        return (String) diffMatchPatchUtils.patch_apply((LinkedList) diffMatchPatchUtils.patch_fromText(diffMatchPatchUtils.patch_toText(diffMatchPatchUtils.patch_make(str, diff_compute))), str)[0];
    }
}
