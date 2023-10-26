package com.xiaopeng.carcontrol.speech;

import android.content.Context;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.listener.IVuiEventListener;
import com.xiaopeng.vui.commons.IVuiElement;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import com.xiaopeng.vui.commons.VuiUpdateType;
import com.xiaopeng.xui.app.XActivity;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.widget.XRecyclerView;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class VuiManager {
    public static final String SCENE_ANTI_SICK_SETTING = "AntiSickSettingDialog";
    public static final String SCENE_AS_HEIGHT_SETTING_CONTROL = "AsHeightSettingsDialog";
    public static final String SCENE_ATL_MODE_SETTING = "AtlModeSettingDialog";
    public static final String SCENE_ATMOSPHERE = "AtlControl";
    public static final String SCENE_AVAS_CLOSE_SETTING = "AvasCloseSettingDialog";
    public static final String SCENE_CAR_SETTING = "CarSettings";
    public static final String SCENE_CAR_SITUATION = "CarSituation";
    public static final String SCENE_CHARGE_PORT_RESET = "ChargePortResetDialog";
    public static final String SCENE_CIU_SETTING = "CiuSetting";
    public static final String SCENE_CNGP_MAP_CONTROL = "CngpMapControlDialog";
    public static final String SCENE_DIALOG_SDC_BRAKE_DOOR = "SdcBrakeDoorConfirmDialog";
    public static final String SCENE_DOORKEY_SETTING = "DoorKeyControlDialog";
    public static final String SCENE_DRIVE = "DriveExperience";
    public static final String SCENE_DRIVE_CONTROL = "DriveDialog";
    public static final String SCENE_DRIVE_INFO_CONTROL = "DriveModeInfoDialog";
    public static final String SCENE_DRIVE_SETTING_CONTROL = "DriveSettingsDialog";
    public static final String SCENE_DSM_SETTING = "DsmSettingDialog";
    public static final String SCENE_ECALL_CONFIRM = "ECallConfirmDialog";
    public static final String SCENE_ISLA_SETTING = "IslaSettingDialog";
    public static final String SCENE_LAMP_EFFECT = "LampControl";
    public static final String SCENE_LAMP_SETTING_CONTROL = "LampSettingControlDialog";
    public static final String SCENE_LLU_MAIN = "LluMain";
    public static final String SCENE_LLU_PLAY_SETTING_DIALOG = "LluPlaySettingDialog";
    public static final String SCENE_LLU_SAYHI_CONFIRM = "LluSayhiConfirmDialog";
    public static final String SCENE_LLU_SAY_CONTROL = "LluSayHiControlDialog";
    public static final String SCENE_LLU_SETTING_CONTROL = "LluSettingControlDialog";
    public static final String SCENE_MAIN_CONTROL = "MainControl";
    public static final String SCENE_METER_MENU_SETTING = "MeterMenuSettingDialog";
    public static final String SCENE_MICHO_PHONE_MUTE_SETTING = "MichophoneMuteSettingDialog";
    public static final String SCENE_MICHO_PHONE_UNMUTE_SETTING = "MichophoneUnmuteSettingDialog";
    public static final String SCENE_MIRROR_CONTROL = "MirrorControlDialog";
    public static final String SCENE_NGP_SETTING = "NgpSettingDialog";
    public static final String SCENE_PSN_POP_PANEL_MENU = "SeatPsnPopPanelMenu";
    public static final String SCENE_SAFE_BELT_SETTING = "SafeBeltSettingDialog";
    public static final String SCENE_SEAT_CONTROL = "SeatControlDialog";
    public static final String SCENE_SEAT_DROP_DOWN_MENU = "SeatDropDownMenu";
    public static final String SCENE_SEAT_GUEST_POP_PANEL_MENU = "SeatGuestPopPanelMenu";
    public static final String SCENE_SEAT_POP_PANEL_MENU = "SeatPopPanelMenu";
    public static final String SCENE_SEAT_SETTING_DIALOG = "SeatSettingDialog";
    public static final String SCENE_SEAT_SMART_CONTROL = "SeatSmartControlDialog";
    public static final String SCENE_STEER_CONTROL = "SteerControlDialog";
    public static final String SCENE_SUN_SHADE_CONTROL = "SunShadeControlDialog";
    public static final String SCENE_TAB = "TabLocal";
    public static final String SCENE_TRAILER_HOOK_CLOSE_CONFIRM_DIALOG = "TrailerHookCloseConfirmDialog";
    public static final String SCENE_TRAILER_HOOK_DIALOG = "TrailerHookDialog";
    public static final String SCENE_TRAILER_HOOK_FAULT_DIALOG = "TrailerHookFaultDialog";
    public static final String SCENE_TRAILER_HOOK_OPEN_CONFIRM_DIALOG = "TrailerHookOpenConfirmDialog";
    public static final String SCENE_TRAILER_MODE_OPEN_CONFIRM_DIALOG = "TrailerModeOpenConfirmDialog";
    public static final String SCENE_WINDOW_CONTROL = "WindowControlDialog";
    public static final String SCENE_XKEY_SETTING = "XKeySettingDialog";
    public static final String SCENE_XPEDAL_SETTING = "XPedalSettingDialog";
    public static final String SCENE_XPILOT_DIALOG = "XPilotDialog";
    public static final String SCENE_XPILOT_DIALOG_ALC = "XPilotAlcDialog";
    public static final String SCENE_XPILOT_DIALOG_AUTO_PARK = "XPilotAutoParkDialog";
    public static final String SCENE_XPILOT_DIALOG_ELK = "XPilotElkDialog";
    public static final String SCENE_XPILOT_DIALOG_FCW = "XPilotFcwDialog";
    public static final String SCENE_XPILOT_DIALOG_ISLA = "XPilotIslaDialog";
    public static final String SCENE_XPILOT_DIALOG_ISLA_CONFIRM_MODE = "XPilotIslaConfirmModeDialog";
    public static final String SCENE_XPILOT_DIALOG_ISLA_SPD_RANGE = "XPilotIslaSpdRangeDialog";
    public static final String SCENE_XPILOT_DIALOG_LCC = "XPilotLccDialog";
    public static final String SCENE_XPILOT_DIALOG_LCC_CROSS_BARRIERS = "XPilotLccCrossBarriersDialog";
    public static final String SCENE_XPILOT_DIALOG_LKA = "XPilotLkaDialog";
    public static final String SCENE_XPILOT_DIALOG_MEM_PARK = "XPilotMemParkDialog";
    public static final String SCENE_XPILOT_DIALOG_NGP = "XPilotNgpDialog";
    public static final String SCENE_XPILOT_DIALOG_NGP_QUICK_LANE = "XPilotNgpQuickLaneDialog";
    public static final String SCENE_XPILOT_DIALOG_NGP_TIP_WINDOW = "XPilotNgpTipWindowDialog";
    public static final String SCENE_XPILOT_DIALOG_NGP_TRUCK_OFFSET = "XPilotNgpTruckOffsetDialog";
    public static final String SCENE_XPILOT_DIALOG_NGP_VOICE_CHANGE_LANE = "XPilotNgpVoiceChangeLaneDialog";
    public static final String SCENE_XPILOT_DIALOG_RAEB = "XPilotRaebDialog";
    public static final String SCENE_XPILOT_DIALOG_SIMPLE_SAS = "XPilotSimpleSasDialog";
    public static final String SCENE_XPILOT_DIALOG_VIDEO = "XPilotLccVideoDialog";
    public static final String SCENE_XPILOT_DIALOG_XPU_LOW_POWER = "XPilotXpuLowPowerDialog";
    public static final String SCENE_XPILOT_EXAM_DIALOG = "XpilotExamDialogDialog";
    public static final String SCENE_XPILOT_INFO_DIALOG = "XPilotInfo";
    public static final String SCENE_XPILOT_NGP_INFO_DIALOG = "NGPInfo";
    public static final String SCENE_XPILOT_STUDY_DIALOG = "XpilotStudyDialogDialog";
    public static final String SCENE_X_PILOT = "XpilotControl";
    public static final String SPECIAL_STATUS_EXCEPTION = "exception";
    public static final String SPECIAL_STATUS_LOADING = "loading";
    public static final String SPECIAL_STATUS_UNKNOWN = "unKnow";
    public static final String SUFFIX_OF_DIALOG_SCENE = "Dialog";
    private static final String TAG = "VuiManager";
    public static final String VUI_PROP_CUSTOM_DISABLE_CONTROL = "customDisableControl";
    public static final String VUI_PROP_HAS_FEEDBACK = "hasFeedback";
    public static final String VUI_PROP_MULTIPLE_ALREADY = "skipMultipleAlready";
    public static final String VUI_PROP_SPECIAL_STATUS = "specialStatus";

    public static VuiManager instance() {
        return Holder.sInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class Holder {
        private static final VuiManager sInstance = new VuiManager();

        private Holder() {
        }
    }

    private VuiManager() {
    }

    public void initScene(final Context context, Lifecycle lifecycle, String sceneId, View rootView, IVuiSceneListener sceneListener, IVuiElementChangedListener elementChangedListener, String fatherId) {
        if (sceneId == null || rootView == null || sceneListener == null) {
            return;
        }
        if (fatherId != null && (rootView instanceof IVuiElement)) {
            ((IVuiElement) rootView).setVuiFatherElementId(fatherId);
        }
        VuiEngine.getInstance(context).initScene(lifecycle, sceneId, rootView, sceneListener, elementChangedListener);
        if (context instanceof XActivity) {
            VuiEngine.getInstance(context).addVuiEventListener(sceneId, new IVuiEventListener() { // from class: com.xiaopeng.carcontrol.speech.-$$Lambda$VuiManager$4JKkpYYEXsdBqfEHIzpgi9hB7Yk
                @Override // com.xiaopeng.speech.vui.listener.IVuiEventListener
                public final void onVuiEventExecutioned() {
                    VuiManager.lambda$initScene$0(context);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initScene$0(final Context context) {
        LogUtils.d(TAG, "dispatchUserEvent when vui event done", false);
        ((XActivity) context).dispatchUserEvent();
    }

    public void initRecyclerView(XRecyclerView recyclerView, String sceneId) {
        recyclerView.initVuiAttr(sceneId, VuiEngine.getInstance(recyclerView.getContext()), true);
    }

    public boolean isVuiAction(View view) {
        return isVuiAction(view, true);
    }

    public boolean isVuiAction(View view, boolean reset) {
        if (view instanceof IVuiElement) {
            IVuiElement iVuiElement = (IVuiElement) view;
            boolean isPerformVuiAction = iVuiElement.isPerformVuiAction();
            if (isPerformVuiAction && reset) {
                iVuiElement.setPerformVuiAction(false);
            }
            return isPerformVuiAction;
        }
        return false;
    }

    public void updateScene(Context context, String sceneId, View view, VuiUpdateType type) {
        if (VuiUpdateType.UPDATE_VIEW.equals(type)) {
            VuiEngine.getInstance(context).updateScene(sceneId, view);
        } else {
            VuiEngine.getInstance(context).updateElementAttribute(sceneId, view);
        }
    }

    public void onBuildScene(String sceneId, View rootView, List<View> viewList, List<String> subSceneList, Context context, boolean isWholeScene) {
        LogUtils.i(TAG, "onBuildScene sceneId:" + sceneId + ", isWholeScene:" + isWholeScene + ", rootView:" + rootView + ", viewList: " + viewList);
        if (sceneId != null) {
            if (viewList != null) {
                VuiEngine.getInstance(context).buildScene(sceneId, viewList, subSceneList, isWholeScene);
            } else if (rootView != null) {
                VuiEngine.getInstance(context).buildScene(sceneId, rootView, subSceneList, isWholeScene);
            }
        }
    }

    public void enterVuiScene(String sceneId, boolean isWholeScene, Context context) {
        if (!isWholeScene || sceneId == null) {
            return;
        }
        VuiEngine.getInstance(context).enterScene(sceneId);
    }

    public void exitVuiScene(String sceneId, boolean isWholeScene, Context context) {
        if (!isWholeScene || sceneId == null) {
            return;
        }
        VuiEngine.getInstance(context).exitScene(sceneId);
    }

    public void updateVuiScene(String sceneId, Context context, View... views) {
        if (sceneId == null || views == null) {
            return;
        }
        if (views.length == 1) {
            VuiEngine.getInstance(context).updateScene(sceneId, views[0]);
        } else {
            VuiEngine.getInstance(context).updateScene(sceneId, Arrays.asList(views));
        }
    }

    public void updateVuiScene(String sceneId, Context context, List<View> viewList) {
        if (sceneId == null || viewList == null) {
            return;
        }
        VuiEngine.getInstance(context).updateScene(sceneId, viewList);
    }

    public void destroyVuiScene(String sceneId, Context context) {
        if (sceneId != null) {
            VuiEngine.getInstance(context).removeVuiSceneListener(sceneId);
        }
    }

    public void setVuiElementUnSupport(Context context, View view, boolean isUnSupport) {
        if (view != null) {
            VuiEngine.getInstance(context).setVuiElementUnSupportTag(view, isUnSupport);
        }
    }

    public void setVuiLabelUnSupportText(Context context, View... views) {
        if (views != null) {
            VuiEngine.getInstance(context).setVuiLabelUnSupportText(views);
        }
    }

    public void setVuiElementVisible(Context context, boolean isVisible, View... views) {
        for (View view : views) {
            if (view != null) {
                VuiEngine.getInstance(context).setVuiElementVisibleTag(view, isVisible);
            }
        }
    }

    public Boolean getVuiElementVisible(Context context, View view) {
        if (view != null) {
            return VuiEngine.getInstance(context).getVuiElementVisibleTag(view);
        }
        return null;
    }

    public void initVuiDialog(XDialog dialog, Context context, String sceneId) {
        initVuiDialog(dialog, context, sceneId, null);
    }

    public void initVuiDialog(XDialog dialog, Context context, String sceneId, IVuiElementListener listener) {
        if (sceneId != null) {
            dialog.initVuiScene(sceneId, VuiEngine.getInstance(context));
            if (listener != null) {
                dialog.setVuiElementListener(listener);
            }
        }
    }
}
