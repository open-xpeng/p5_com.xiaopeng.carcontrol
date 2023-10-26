package com.xiaopeng.carcontrol.speech;

/* loaded from: classes2.dex */
public class SpeechModelFactory {
    public static final String CARCONTROL = "carcontrol";
    public static final String HVAC = "hvac";

    private SpeechModelFactory() {
    }

    public static ISpeechModel getSpeechModel(String type) {
        type.hashCode();
        if (type.equals("hvac")) {
            return HvacControlSpeechModel.getInstance();
        }
        if (type.equals("carcontrol")) {
            return CarControlSpeechModel.getInstance();
        }
        throw new IllegalArgumentException("unknown type={" + type + "} of SpeechModel");
    }
}
