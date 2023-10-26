package com.xiaopeng.xui.app.delegate;

import android.app.Activity;
import android.os.Bundle;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.app.delegate.XActivityTemplate;
import com.xiaopeng.xui.app.delegate.XActivityTemplateExtend;
import com.xiaopeng.xui.utils.XLogUtils;
import com.xiaopeng.xui.utils.XuiUtils;
import com.xiaopeng.xui.widget.dialogview.XDialogView;
import com.xiaopeng.xui.widget.dialogview.XDialogViewInterface;

/* loaded from: classes2.dex */
abstract class XActivityTemplateExtend implements XActivityTemplate, XActivityLifecycle {
    private static final String TAG = "XActivityTemplate";

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void initDismiss(XActivityDismissExtend xActivityDismissExtend);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void initDismissCauseGroup(XActivityDismissCauseGroup xActivityDismissCauseGroup);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void initWindowAttributes(XActivityWindowAttributes xActivityWindowAttributes);

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void initWindowVisible(XActivityWindowVisible xActivityWindowVisible);

    XActivityTemplateExtend() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static XActivityTemplateExtend create(Activity activity, int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        return new NormalImpl(activity);
                    }
                    return new SuperPanelImpl(activity);
                }
                return new DialogImpl(activity);
            }
            return new FullscreenImpl(activity);
        }
        return new PanelImpl(activity);
    }

    /* loaded from: classes2.dex */
    private static class BaseTemplate extends XActivityTemplateExtend {
        Activity mActivity;
        XActivityDismissExtend mDismiss;

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowAttributes(XActivityWindowAttributes xActivityWindowAttributes) {
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowVisible(XActivityWindowVisible xActivityWindowVisible) {
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle bundle) {
        }

        BaseTemplate(Activity activity) {
            this.mActivity = activity;
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initDismissCauseGroup(XActivityDismissCauseGroup xActivityDismissCauseGroup) {
            xActivityDismissCauseGroup.enableBackScene();
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initDismiss(XActivityDismissExtend xActivityDismissExtend) {
            this.mDismiss = xActivityDismissExtend;
        }
    }

    /* loaded from: classes2.dex */
    private static class NormalImpl extends BaseTemplate implements XActivityTemplate.Normal {
        NormalImpl(Activity activity) {
            super(activity);
        }
    }

    /* loaded from: classes2.dex */
    private static class SuperPanelImpl extends BaseTemplate implements XActivityTemplate.SuperPanel {
        SuperPanelImpl(Activity activity) {
            super(activity);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
        }
    }

    /* loaded from: classes2.dex */
    private static class FullscreenImpl extends BaseTemplate implements XActivityTemplate.FullScreen {
        FullscreenImpl(Activity activity) {
            super(activity);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class DialogImpl extends BaseTemplate implements XActivityTemplate.Dialog {
        DialogImpl(Activity activity) {
            super(activity);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initDismissCauseGroup(XActivityDismissCauseGroup xActivityDismissCauseGroup) {
            super.initDismissCauseGroup(xActivityDismissCauseGroup);
            xActivityDismissCauseGroup.enableOnPauseScene();
            xActivityDismissCauseGroup.enableOutSideScene();
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowAttributes(XActivityWindowAttributes xActivityWindowAttributes) {
            xActivityWindowAttributes.setWidth(-2).setHeight(-2).setGravity(17);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowVisible(XActivityWindowVisible xActivityWindowVisible) {
            super.initWindowVisible(xActivityWindowVisible);
            xActivityWindowVisible.setAutoVisibleEnableOnPause(true);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplate.Dialog
        public void useXDialogView(XDialogView xDialogView) {
            xDialogView.setCloseVisibility(true);
            xDialogView.setOnDismissListener(new XDialogViewInterface.OnDismissListener() { // from class: com.xiaopeng.xui.app.delegate.-$$Lambda$XActivityTemplateExtend$DialogImpl$D6M74gFfgIWQhagroU09yD5oW1Y
                @Override // com.xiaopeng.xui.widget.dialogview.XDialogViewInterface.OnDismissListener
                public final void onDismiss(XDialogView xDialogView2) {
                    XActivityTemplateExtend.DialogImpl.this.lambda$useXDialogView$0$XActivityTemplateExtend$DialogImpl(xDialogView2);
                }
            });
        }

        public /* synthetic */ void lambda$useXDialogView$0$XActivityTemplateExtend$DialogImpl(XDialogView xDialogView) {
            XLogUtils.i(XActivityTemplateExtend.TAG, "onDismiss for dialog ");
            if (this.mDismiss != null) {
                this.mDismiss.dismiss(0);
            }
        }
    }

    /* loaded from: classes2.dex */
    private static class PanelImpl extends BaseTemplate implements XActivityTemplate.Panel {
        PanelImpl(Activity activity) {
            super(activity);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityLifecycle
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mActivity.requestWindowFeature(14);
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initDismissCauseGroup(XActivityDismissCauseGroup xActivityDismissCauseGroup) {
            super.initDismissCauseGroup(xActivityDismissCauseGroup);
            xActivityDismissCauseGroup.enableOnPauseScene();
            xActivityDismissCauseGroup.enableOutSideScene();
            xActivityDismissCauseGroup.enableSpeedTimeOutScene();
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowAttributes(XActivityWindowAttributes xActivityWindowAttributes) {
            int i = this.mActivity.getResources().getConfiguration().orientation;
            int dip2px = XuiUtils.dip2px(this.mActivity, R.dimen.x_compat_app_panel_x);
            int dip2px2 = XuiUtils.dip2px(this.mActivity, R.dimen.x_compat_app_panel_y);
            int dip2px3 = XuiUtils.dip2px(this.mActivity, R.dimen.x_compat_app_panel_width);
            int dip2px4 = XuiUtils.dip2px(this.mActivity, R.dimen.x_compat_app_panel_height);
            if (i == 1) {
                xActivityWindowAttributes.setX(dip2px).setY(dip2px2).setWidth(dip2px3).setHeight(dip2px4).setGravity(80);
            } else if (i != 2) {
            } else {
                xActivityWindowAttributes.setX(dip2px).setY(dip2px2).setWidth(dip2px3).setHeight(dip2px4).setGravity(8388627);
            }
        }

        @Override // com.xiaopeng.xui.app.delegate.XActivityTemplateExtend.BaseTemplate, com.xiaopeng.xui.app.delegate.XActivityTemplateExtend
        void initWindowVisible(XActivityWindowVisible xActivityWindowVisible) {
            super.initWindowVisible(xActivityWindowVisible);
            xActivityWindowVisible.setAutoVisibleEnableOnPause(true);
        }
    }
}
