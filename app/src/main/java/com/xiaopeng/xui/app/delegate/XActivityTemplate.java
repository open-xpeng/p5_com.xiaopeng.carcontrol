package com.xiaopeng.xui.app.delegate;

import com.xiaopeng.xui.widget.dialogview.XDialogView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public interface XActivityTemplate {
    public static final int FEATURE_XUI_FULLSCREEN = 14;
    public static final int TEMPLATE_DIALOG = 3;
    public static final int TEMPLATE_FULLSCREEN = 2;
    public static final int TEMPLATE_NORMAL = 0;
    public static final int TEMPLATE_PANEL = 1;
    public static final int TEMPLATE_SUPER_PANEL = 4;

    /* loaded from: classes2.dex */
    public interface Dialog extends XActivityTemplate {
        void useXDialogView(XDialogView xDialogView);
    }

    /* loaded from: classes2.dex */
    public interface FullScreen extends XActivityTemplate {
    }

    /* loaded from: classes2.dex */
    public interface Normal extends XActivityTemplate {
    }

    /* loaded from: classes2.dex */
    public interface Panel extends XActivityTemplate {
    }

    /* loaded from: classes2.dex */
    public interface SuperPanel extends XActivityTemplate {
    }

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface TemplateType {
    }
}
