package com.alibaba.mtl.appmonitor.model;

import com.alibaba.mtl.appmonitor.f.a;
import com.alibaba.mtl.log.model.LogField;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class UTDimensionValueSet extends DimensionValueSet {
    private static final Set<LogField> a = new HashSet<LogField>() { // from class: com.alibaba.mtl.appmonitor.model.UTDimensionValueSet.1
        {
            add(LogField.PAGE);
            add(LogField.ARG1);
            add(LogField.ARG2);
            add(LogField.ARG3);
            add(LogField.ARGS);
        }
    };

    public Integer getEventId() {
        int i;
        String str;
        if (this.map != null && (str = this.map.get(LogField.EVENTID.toString())) != null) {
            try {
                i = a.a(str);
            } catch (NumberFormatException unused) {
            }
            return Integer.valueOf(i);
        }
        i = 0;
        return Integer.valueOf(i);
    }

    public static UTDimensionValueSet create(Map<String, String> map) {
        return (UTDimensionValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(UTDimensionValueSet.class, map);
    }

    @Override // com.alibaba.mtl.appmonitor.model.DimensionValueSet, com.alibaba.mtl.appmonitor.c.b
    public void clean() {
        super.clean();
    }

    @Override // com.alibaba.mtl.appmonitor.model.DimensionValueSet, com.alibaba.mtl.appmonitor.c.b
    public void fill(Object... objArr) {
        super.fill(objArr);
    }
}
