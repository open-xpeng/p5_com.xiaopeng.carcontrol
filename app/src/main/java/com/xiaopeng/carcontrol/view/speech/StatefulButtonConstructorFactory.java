package com.xiaopeng.carcontrol.view.speech;

import android.content.Context;
import com.xiaopeng.speech.vui.constructor.IStatefulButtonConstructor;

/* loaded from: classes2.dex */
public class StatefulButtonConstructorFactory {

    /* loaded from: classes2.dex */
    public enum ConstructorType {
        SEAT_AIR,
        SEAT_HOT
    }

    /* renamed from: com.xiaopeng.carcontrol.view.speech.StatefulButtonConstructorFactory$1  reason: invalid class name */
    /* loaded from: classes2.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$carcontrol$view$speech$StatefulButtonConstructorFactory$ConstructorType;

        static {
            int[] iArr = new int[ConstructorType.values().length];
            $SwitchMap$com$xiaopeng$carcontrol$view$speech$StatefulButtonConstructorFactory$ConstructorType = iArr;
            try {
                iArr[ConstructorType.SEAT_AIR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$carcontrol$view$speech$StatefulButtonConstructorFactory$ConstructorType[ConstructorType.SEAT_HOT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static IStatefulButtonConstructor getConstructor(ConstructorType type, Context context, int level) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$carcontrol$view$speech$StatefulButtonConstructorFactory$ConstructorType[type.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return null;
            }
            return new SeatHotStateFulButtonConstructor(context, level);
        }
        return new SeatAirStatefulButtonConstructor(context, level);
    }
}
