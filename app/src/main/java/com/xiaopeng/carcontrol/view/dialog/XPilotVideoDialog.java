package com.xiaopeng.carcontrol.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public class XPilotVideoDialog extends BaseVideoDialog {
    @Override // com.xiaopeng.carcontrol.view.dialog.BaseVideoDialog
    protected boolean enableVui() {
        return true;
    }

    public XPilotVideoDialog(Context context) {
        super(context);
    }

    public static XPilotVideoDialog createLccVideoDialog() {
        return new Builder().videoPath("/system/etc/lcc.mp4").title(App.getInstance().getString(R.string.laa_feature_title)).tips(App.getInstance().getString(R.string.lane_center_video_tips)).forceWatch(true).hasNegative(true).build(App.getInstance());
    }

    public static XPilotVideoDialog createMemParkVideoDialog() {
        return new Builder().videoPath("/system/etc/lcc.mp4").title(App.getInstance().getString(R.string.mem_park_feature_title)).tips(App.getInstance().getString(R.string.mem_park_video_dialog_tips)).forceWatch(false).hasNegative(false).build(App.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.carcontrol.view.dialog.BaseVideoDialog
    public void initViews() {
        super.initViews();
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$XPilotVideoDialog$mEvmAU5cYC42amB443CkXlu4xjo
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                XPilotVideoDialog.this.lambda$initViews$0$XPilotVideoDialog(dialogInterface);
            }
        });
    }

    public /* synthetic */ void lambda$initViews$0$XPilotVideoDialog(DialogInterface dialog) {
        release();
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.BaseVideoDialog
    protected void bindView(View view) {
        this.mTvTips = (XTextView) view.findViewById(R.id.tv_lane_center_video_tips);
        this.mTextureView = (SurfaceView) view.findViewById(R.id.tv_video);
        this.mIvPlayer = (ImageView) view.findViewById(R.id.iv_play);
        this.mIvCover = (ImageView) view.findViewById(R.id.iv_video_cover);
    }

    @Override // com.xiaopeng.carcontrol.view.dialog.BaseVideoDialog
    protected int getLayoutId() {
        return R.layout.layout_xpilot_video_dialog;
    }

    /* loaded from: classes2.dex */
    private static class Builder {
        private boolean forceWatch;
        private boolean hasNegativeButton;
        private String tips;
        private String title;
        private String videoPath;

        private Builder() {
        }

        Builder videoPath(String videoPath) {
            this.videoPath = videoPath;
            return this;
        }

        Builder tips(String tips) {
            this.tips = tips;
            return this;
        }

        Builder title(String title) {
            this.title = title;
            return this;
        }

        Builder hasNegative(boolean hasNegative) {
            this.hasNegativeButton = hasNegative;
            return this;
        }

        Builder forceWatch(boolean forceWatch) {
            this.forceWatch = forceWatch;
            return this;
        }

        XPilotVideoDialog build(Context context) {
            XPilotVideoDialog xPilotVideoDialog = new XPilotVideoDialog(context);
            xPilotVideoDialog.setTitle(this.title);
            xPilotVideoDialog.setVideoTips(this.tips);
            xPilotVideoDialog.setVideoPath(this.videoPath);
            xPilotVideoDialog.setForceWatch(this.forceWatch);
            if (this.hasNegativeButton) {
                xPilotVideoDialog.setNegativeButton(context.getString(R.string.btn_cancel));
            }
            return xPilotVideoDialog;
        }
    }
}
