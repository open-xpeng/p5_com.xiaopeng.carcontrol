package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class AddressBookAUResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String massagedText = getMassagedText(result);
        if (massagedText.contains("MEMORY") && massagedText.contains("\r\n")) {
            String matchSinglePrefixedField = matchSinglePrefixedField("NAME1:", massagedText, '\r', true);
            String matchSinglePrefixedField2 = matchSinglePrefixedField("NAME2:", massagedText, '\r', true);
            String[] matchMultipleValuePrefix = matchMultipleValuePrefix("TEL", 3, massagedText, true);
            String[] matchMultipleValuePrefix2 = matchMultipleValuePrefix("MAIL", 3, massagedText, true);
            String matchSinglePrefixedField3 = matchSinglePrefixedField("MEMORY:", massagedText, '\r', false);
            String matchSinglePrefixedField4 = matchSinglePrefixedField("ADD:", massagedText, '\r', true);
            return new AddressBookParsedResult(maybeWrap(matchSinglePrefixedField), null, matchSinglePrefixedField2, matchMultipleValuePrefix, null, matchMultipleValuePrefix2, null, null, matchSinglePrefixedField3, matchSinglePrefixedField4 != null ? new String[]{matchSinglePrefixedField4} : null, null, null, null, null, null, null);
        }
        return null;
    }

    private static String[] matchMultipleValuePrefix(String str, int i, String str2, boolean z) {
        ArrayList arrayList = null;
        for (int i2 = 1; i2 <= i; i2++) {
            String matchSinglePrefixedField = matchSinglePrefixedField(str + i2 + ':', str2, '\r', z);
            if (matchSinglePrefixedField == null) {
                break;
            }
            if (arrayList == null) {
                arrayList = new ArrayList(i);
            }
            arrayList.add(matchSinglePrefixedField);
        }
        if (arrayList == null) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
