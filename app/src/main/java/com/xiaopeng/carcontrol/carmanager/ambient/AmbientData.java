package com.xiaopeng.carcontrol.carmanager.ambient;

import android.util.Log;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class AmbientData {
    private int bright;
    private List<Integer> colors;
    private int fade;
    private List<Integer> groups;
    private int period;

    public AmbientData(int group, int color, int bright, int fade, int period) {
        this.groups = Collections.singletonList(Integer.valueOf(group));
        this.colors = Collections.singletonList(Integer.valueOf(color));
        this.bright = bright;
        this.fade = fade;
        this.period = period;
    }

    public AmbientData(int[] groups, int[] colors, int bright, int fade, int period) {
        this.groups = (List) Arrays.stream(groups).boxed().collect(Collectors.toList());
        this.colors = (List) Arrays.stream(colors).boxed().collect(Collectors.toList());
        this.fade = fade;
        this.period = period;
    }

    public JSONObject toJsonObject() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("groups", new JSONArray((Collection) this.groups));
            jSONObject.put("colors", new JSONArray((Collection) this.colors));
            jSONObject.put("bright", this.bright);
            jSONObject.put("fade", this.fade);
            jSONObject.put(TypedValues.Cycle.S_WAVE_PERIOD, this.period);
        } catch (Exception e) {
            Log.e("AmbientData", "serialize exception " + e);
        }
        return jSONObject;
    }
}
