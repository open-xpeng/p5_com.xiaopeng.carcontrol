package com.xiaopeng.xui.vui.utils;

import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.VuiFeedbackType;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.vui.commons.VuiPriority;

/* loaded from: classes2.dex */
public class VuiCommonUtils {
    public static VuiPriority getViewLeveByPriority(int i) {
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i == 3) {
                        return VuiPriority.LEVEL4;
                    }
                    return VuiPriority.LEVEL3;
                }
                return VuiPriority.LEVEL3;
            }
            return VuiPriority.LEVEL2;
        }
        return VuiPriority.LEVEL1;
    }

    public static VuiMode getVuiMode(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return VuiMode.UNDERSTOOD;
                }
                return VuiMode.NORMAL;
            }
            return VuiMode.SILENT;
        }
        return VuiMode.DISABLED;
    }

    public static VuiFeedbackType getFeedbackType(int i) {
        if (i != 1) {
            if (i == 2) {
                return VuiFeedbackType.TTS;
            }
            return VuiFeedbackType.SOUND;
        }
        return VuiFeedbackType.SOUND;
    }

    public static VuiElementType getElementType(int i) {
        switch (i) {
            case 1:
                return VuiElementType.BUTTON;
            case 2:
                return VuiElementType.LISTVIEW;
            case 3:
                return VuiElementType.CHECKBOX;
            case 4:
                return VuiElementType.RADIOBUTTON;
            case 5:
                return VuiElementType.RADIOGROUP;
            case 6:
                return VuiElementType.GROUP;
            case 7:
                return VuiElementType.EDITTEXT;
            case 8:
                return VuiElementType.PROGRESSBAR;
            case 9:
                return VuiElementType.SEEKBAR;
            case 10:
                return VuiElementType.TABHOST;
            case 11:
                return VuiElementType.SEARCHVIEW;
            case 12:
                return VuiElementType.RATINGBAR;
            case 13:
                return VuiElementType.IMAGEBUTTON;
            case 14:
                return VuiElementType.IMAGEVIEW;
            case 15:
                return VuiElementType.SCROLLVIEW;
            case 16:
                return VuiElementType.TEXTVIEW;
            case 17:
                return VuiElementType.RECYCLEVIEW;
            case 18:
                return VuiElementType.SWITCH;
            case 19:
                return VuiElementType.CUSTOM;
            case 20:
                return VuiElementType.XSLIDER;
            case 21:
                return VuiElementType.XTABLAYOUT;
            case 22:
                return VuiElementType.XGROUPHEADER;
            default:
                return VuiElementType.UNKNOWN;
        }
    }
}
