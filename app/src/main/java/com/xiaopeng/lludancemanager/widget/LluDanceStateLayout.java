package com.xiaopeng.lludancemanager.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lludancemanager.LluDanceHelper;
import com.xiaopeng.lludancemanager.R;
import com.xiaopeng.lludancemanager.bean.LluDanceViewData;
import com.xiaopeng.lludancemanager.vui.LluStatefulButtonConstructor;
import com.xiaopeng.speech.vui.VuiEngine;
import com.xiaopeng.speech.vui.model.VuiFeedback;
import com.xiaopeng.vui.commons.VuiElementType;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xui.vui.floatinglayer.VuiFloatingLayerManager;
import com.xiaopeng.xui.widget.XButton;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class LluDanceStateLayout extends XFrameLayout implements View.OnClickListener {
    private static final String TAG = "LluDanceStateLayout";
    private static final int VUI_LLU_DANCE_ACTION_DOWNLOAD_LEVEL = 0;
    private static final int VUI_LLU_DANCE_ACTION_PAUSE_DOWNLOAD_LEVEL = 2;
    private static final int VUI_LLU_DANCE_ACTION_PLAY_LEVEL = 4;
    private static final int VUI_LLU_DANCE_ACTION_RESUME_DOWNLOAD_LEVEL = 1;
    private static final int VUI_LLU_DANCE_ACTION_RE_DOWNLOAD_LEVEL = 3;
    private OnClickListener listener;
    private XButton mBtnLlu;
    private LluDanceViewData mDanceData;
    private int mEnableLevel;
    private ProgressBar mPbDownload;
    private XTextView mTvProgress;

    /* loaded from: classes2.dex */
    public interface OnClickListener {
        void downloadDance(LluDanceViewData data);

        void pauseDownloadDance(LluDanceViewData data);

        void playDance(LluDanceViewData data);
    }

    public LluDanceStateLayout(Context context) {
        super(context);
        this.mEnableLevel = -1;
    }

    public LluDanceStateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mEnableLevel = -1;
        initView(context);
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_llu_status, this);
        this.mBtnLlu = (XButton) findViewById(R.id.llu_activity_dance_video_control_action);
        this.mPbDownload = (ProgressBar) findViewById(R.id.llu_activity_dance_download_progress);
        this.mTvProgress = (XTextView) findViewById(R.id.llu_activity_dance_item_download_progress_value);
        setVuiElementType(VuiElementType.STATEFULBUTTON);
        this.mBtnLlu.setOnClickListener(this);
        this.mTvProgress.setOnClickListener(this);
    }

    public void updateData(LluDanceViewData data) {
        if (data == null) {
            Log.e(TAG, "data is null when update.");
            return;
        }
        this.mDanceData = data;
        int state = data.getState();
        int downloadedPercentage = (int) data.getDownloadedPercentage();
        switch (state) {
            case 1:
                this.mBtnLlu.setVisibility(0);
                this.mPbDownload.setVisibility(8);
                this.mTvProgress.setVisibility(8);
                this.mBtnLlu.setText(R.string.llu_dance_fragment_item_control_download);
                updateStateScene(0);
                return;
            case 2:
                this.mBtnLlu.setVisibility(0);
                this.mPbDownload.setVisibility(8);
                this.mTvProgress.setVisibility(8);
                this.mBtnLlu.setText(R.string.llu_dance_fragment_item_control_waiting);
                return;
            case 3:
            case 6:
                this.mBtnLlu.setVisibility(8);
                this.mPbDownload.setVisibility(0);
                this.mTvProgress.setVisibility(0);
                this.mPbDownload.setProgress(downloadedPercentage);
                this.mTvProgress.setText(downloadedPercentage + "%");
                updateStateScene(2);
                return;
            case 4:
            default:
                Log.d(TAG, "update data default. state : " + state);
                return;
            case 5:
                this.mBtnLlu.setVisibility(0);
                this.mPbDownload.setVisibility(8);
                this.mTvProgress.setVisibility(8);
                this.mBtnLlu.setText(R.string.llu_dance_fragment_item_control_continue_download);
                updateStateScene(1);
                return;
            case 7:
                this.mBtnLlu.setVisibility(0);
                this.mPbDownload.setVisibility(8);
                this.mTvProgress.setVisibility(8);
                this.mBtnLlu.setText(R.string.llu_dance_fragment_item_control_retry_download);
                updateStateScene(3);
                return;
            case 8:
                this.mBtnLlu.setVisibility(0);
                this.mPbDownload.setVisibility(8);
                this.mTvProgress.setVisibility(8);
                this.mBtnLlu.setText(R.string.llu_dance_fragment_item_control_play);
                updateStateScene(4);
                return;
        }
    }

    private void updateStateScene(int level) {
        this.mEnableLevel = level;
        LogUtils.d(TAG, "update llu state level:" + level);
        new LluStatefulButtonConstructor(getContext(), level).construct(this);
        VuiManager.instance().updateVuiScene(VuiManager.SCENE_LLU_MAIN, getContext(), this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        LogUtils.d(TAG, "view:" + v.getClass().getSimpleName() + " state: " + this.mDanceData.getState());
        LluDanceViewData lluDanceViewData = this.mDanceData;
        if (lluDanceViewData == null) {
            Log.e(TAG, "data is null when click.");
            return;
        }
        int state = lluDanceViewData.getState();
        switch (state) {
            case 1:
            case 2:
            case 5:
            case 7:
                OnClickListener onClickListener = this.listener;
                if (onClickListener != null) {
                    onClickListener.downloadDance(this.mDanceData);
                    return;
                }
                return;
            case 3:
            case 6:
                OnClickListener onClickListener2 = this.listener;
                if (onClickListener2 != null) {
                    onClickListener2.pauseDownloadDance(this.mDanceData);
                    return;
                }
                return;
            case 4:
            default:
                Log.d(TAG, "update data default. state : " + state);
                return;
            case 8:
                OnClickListener onClickListener3 = this.listener;
                if (onClickListener3 != null) {
                    onClickListener3.playDance(this.mDanceData);
                    return;
                }
                return;
        }
    }

    public void onVuiEvent(View view, VuiEvent vuiEvent) {
        if (view.getId() == getId()) {
            int i = this.mEnableLevel;
            if (i == 0) {
                if (vuiEvent.getHitVuiElement().toString().contains(this.mContext.getString(R.string.llu_dance_action_download))) {
                    OnClickListener onClickListener = this.listener;
                    if (onClickListener != null) {
                        onClickListener.downloadDance(this.mDanceData);
                    }
                    showVuiFloatLayer(view);
                    return;
                }
                vuiFeedback(R.string.llu_action_disable, this);
            } else if (i == 1) {
                if (vuiEvent.getHitVuiElement().toString().contains(this.mContext.getString(R.string.llu_dance_action_resume_download))) {
                    OnClickListener onClickListener2 = this.listener;
                    if (onClickListener2 != null) {
                        onClickListener2.downloadDance(this.mDanceData);
                    }
                    showVuiFloatLayer(view);
                    return;
                }
                vuiFeedback(R.string.llu_action_disable, this);
            } else if (i == 2) {
                if (vuiEvent.getHitVuiElement().toString().contains(this.mContext.getString(R.string.llu_dance_action_pause_download))) {
                    OnClickListener onClickListener3 = this.listener;
                    if (onClickListener3 != null) {
                        onClickListener3.downloadDance(this.mDanceData);
                    }
                    showVuiFloatLayer(view);
                    return;
                }
                vuiFeedback(R.string.llu_action_disable, this);
            } else if (i == 3) {
                if (vuiEvent.getHitVuiElement().toString().contains(this.mContext.getString(R.string.llu_dance_action_re_download))) {
                    OnClickListener onClickListener4 = this.listener;
                    if (onClickListener4 != null) {
                        onClickListener4.downloadDance(this.mDanceData);
                    }
                    showVuiFloatLayer(view);
                    return;
                }
                vuiFeedback(R.string.llu_action_disable, this);
            } else if (i == 4) {
                if (vuiEvent.getHitVuiElement().toString().contains(this.mContext.getString(R.string.llu_dance_action_play))) {
                    int lluStateTips = LluDanceHelper.getLluStateTips();
                    if (lluStateTips != -1) {
                        LogUtils.d(TAG, "current state not support play llu,vui feedback directly .the reason please check the former log.");
                        vuiFeedback(lluStateTips, this);
                    } else {
                        showVuiFloatLayer(view);
                    }
                    OnClickListener onClickListener5 = this.listener;
                    if (onClickListener5 != null) {
                        onClickListener5.downloadDance(this.mDanceData);
                        return;
                    }
                    return;
                }
                vuiFeedback(R.string.llu_action_disable, this);
            } else {
                vuiFeedback(R.string.llu_action_disable, this);
            }
        }
    }

    private void showVuiFloatLayer(View view) {
        if (view != null) {
            VuiFloatingLayerManager.show(view);
        }
    }

    protected void vuiFeedback(int content, View view) {
        if (view != null) {
            VuiEngine.getInstance(this.mContext.getApplicationContext()).vuiFeedback(view, new VuiFeedback.Builder().state(1).content(this.mContext.getString(content)).build());
        }
    }
}
