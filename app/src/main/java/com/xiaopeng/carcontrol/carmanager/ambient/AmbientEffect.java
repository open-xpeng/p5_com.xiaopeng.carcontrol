package com.xiaopeng.carcontrol.carmanager.ambient;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class AmbientEffect {
    private final int count;
    private final int displayId;
    private final boolean hasHue;
    private final boolean hasRhythm;
    private final String name;
    private final List<AmbientData> packets;

    private AmbientEffect(String name, int count, int displayId, boolean hasRhythm, boolean hasHue, List<AmbientData> packets) {
        this.name = name;
        this.count = count;
        this.displayId = displayId;
        this.hasRhythm = hasRhythm;
        this.hasHue = hasHue;
        this.packets = packets;
    }

    public String toJson() {
        JSONObject jSONObject = new JSONObject();
        try {
            JSONArray jSONArray = new JSONArray();
            for (AmbientData ambientData : this.packets) {
                jSONArray.put(ambientData.toJsonObject());
            }
            jSONObject.put("name", this.name);
            jSONObject.put("count", this.count);
            jSONObject.put("hasRhythm", this.hasRhythm);
            jSONObject.put("hasHue", this.hasHue);
            jSONObject.put("packets", jSONArray);
        } catch (Exception e) {
            Log.e("AmbientEffect", "AmbientEffect serialize exception " + e);
        }
        return jSONObject.toString();
    }

    /* loaded from: classes.dex */
    public static class Builder {
        private String mName;
        private int mCount = 1;
        private int mDisplayId = 0;
        private boolean mRhythm = false;
        private boolean mHue = false;
        private List<AmbientData> mPackets = new ArrayList();

        public Builder(String uuid) {
            this.mName = uuid;
        }

        public Builder setCount(int count) {
            this.mCount = count;
            return this;
        }

        public Builder setRhythm() {
            this.mRhythm = true;
            return this;
        }

        public Builder setHue(int displayId) {
            this.mHue = true;
            this.mDisplayId = displayId;
            return this;
        }

        public Builder setSequence(int group, int color, int bright, int fade, int period) {
            this.mPackets.add(new AmbientData(group, color, bright, fade, period));
            return this;
        }

        public Builder setSequence(int[] groups, int[] colors, int bright, int fade, int period) {
            this.mPackets.add(new AmbientData(groups, colors, bright, fade, period));
            return this;
        }

        public Builder setSequence(int[] groups, int color, int bright, int fade, int period) {
            this.mPackets.add(new AmbientData(groups, new int[]{color}, bright, fade, period));
            return this;
        }

        public Builder setSequence(int group, int[] colors, int bright, int fade, int period) {
            this.mPackets.add(new AmbientData(new int[]{group}, colors, bright, fade, period));
            return this;
        }

        public AmbientEffect build() {
            return new AmbientEffect(this.mName, this.mCount, this.mDisplayId, this.mRhythm, this.mHue, this.mPackets);
        }
    }
}
