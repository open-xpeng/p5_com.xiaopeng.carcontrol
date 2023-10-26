package com.xiaopeng.carcontrol.view.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;

/* loaded from: classes2.dex */
public final class CiuInfoHelper {
    public static final int CAMERA_MAIN = 0;
    public static final int DISTRACTION_REMIND = 3;
    public static final int FACE_RECOGNITION = 1;
    public static final int FATIGUE_REMIND = 2;
    private static final String TAG = "CiuInfoHelper";
    private XDialog mInfoDialog;
    private int mType;

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        private static final CiuInfoHelper sInstance = new CiuInfoHelper();

        private SingleHolder() {
        }
    }

    public static CiuInfoHelper getInstance() {
        return SingleHolder.sInstance;
    }

    private CiuInfoHelper() {
        this.mType = -1;
    }

    public void showCiuInfoPanel(Context context, int type) {
        if (context == null) {
            LogUtils.w(TAG, "showCiuInfoPanel failed: context is null");
            return;
        }
        if (this.mInfoDialog == null) {
            XDialog xDialog = new XDialog(context, 2131886921);
            this.mInfoDialog = xDialog;
            xDialog.setSystemDialog(2048);
            this.mInfoDialog.setCustomView(LayoutInflater.from(context).inflate(R.layout.layout_xpilot_info_dialog_view, (ViewGroup) null), false);
            this.mInfoDialog.setPositiveButton(context.getResources().getString(R.string.btn_close), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.helper.-$$Lambda$CiuInfoHelper$jcPzcsUuDBsKgZfejc6ASndvRGo
                @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                public final void onClick(XDialog xDialog2, int i) {
                    CiuInfoHelper.this.lambda$showCiuInfoPanel$0$CiuInfoHelper(xDialog2, i);
                }
            });
            this.mInfoDialog.setNegativeButtonEnable(false);
        }
        initData(type);
        this.mInfoDialog.show();
    }

    public /* synthetic */ void lambda$showCiuInfoPanel$0$CiuInfoHelper(XDialog xDialog, int i) {
        this.mInfoDialog.dismiss();
    }

    private void initData(int type) {
        this.mType = type;
        this.mInfoDialog.getContentView().findViewById(R.id.scrollView).scrollTo(0, 0);
        int title = getTitle();
        if (title != 0) {
            this.mInfoDialog.setTitle(title);
        }
        ((ImageView) this.mInfoDialog.getContentView().findViewById(R.id.help_pic_1)).setImageResource(getTopPic());
        int desc = getDesc();
        if (desc != 0) {
            ((TextView) this.mInfoDialog.getContentView().findViewById(R.id.help_text_1)).setText(desc);
        }
    }

    private int getTitle() {
        int i = this.mType;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return 0;
                    }
                    return R.string.ciu_inattention_tv;
                }
                return R.string.ciu_fatigue_driving_tv;
            }
            return R.string.ciu_face_recognition_tv;
        }
        return R.string.ciu_inner_camera_title_tv;
    }

    private int getTopPic() {
        int i = this.mType;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return 0;
                    }
                    return R.drawable.img_control_distract;
                }
                return R.drawable.img_control_zzz;
            }
            return R.drawable.img_control_face;
        }
        return R.drawable.img_control_camera;
    }

    private int getDesc() {
        int i = this.mType;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return 0;
                    }
                    return R.string.ciu_inattention_dialog_content_tv;
                }
                return R.string.ciu_fatigue_driving_dialog_content_tv;
            }
            return R.string.ciu_face_recognition_dialog_content_tv;
        }
        return R.string.ciu_inner_camera_dialog_content_tv;
    }
}
