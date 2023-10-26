package com.xiaopeng.carcontrol.view.control;

import android.content.res.Configuration;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class BaseViewControl {
    public static final String ACTION_SHOW_SEAT_HEAT_VENT_DIRECT = "seat_heat_vent";
    public static final String SCENE_ID = "hvac";
    public AppCompatActivity mActivity;
    public View mRootView;

    public void initView() {
    }

    protected void onBuildScene(List<Integer> list) {
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onDestroy() {
    }

    public void onPause() {
    }

    public void onResume() {
    }

    public void onStart() {
    }

    public void onStop() {
    }

    public void showSeatHeatVentDialog() {
    }

    public void onCreate(AppCompatActivity activity, View view) {
        this.mActivity = activity;
        this.mRootView = view;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showVuiFloatingLayer(View view) {
        if (view != null) {
            VuiFloatingLayerManager.show(view);
        }
    }
}
