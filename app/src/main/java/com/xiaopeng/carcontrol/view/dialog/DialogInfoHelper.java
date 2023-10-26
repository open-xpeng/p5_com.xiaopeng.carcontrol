package com.xiaopeng.carcontrol.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import com.xiaopeng.carcontrol.bean.xpilot.XPilotItem;
import com.xiaopeng.carcontrol.helper.UserBookHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ResUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XTextView;

/* loaded from: classes2.dex */
public final class DialogInfoHelper {
    private static final String DIALOG_TITLE_DIRTY = "\n";
    private static final String TAG = "DialogInfoHelper";
    public static final int TYPE_ALC = 7;
    public static final int TYPE_AVH = 10;
    public static final int TYPE_BSD = 2;
    public static final int TYPE_CNGP = 33;
    public static final int TYPE_DOW = 12;
    public static final int TYPE_EBW = 11;
    public static final int TYPE_ELK = 15;
    public static final int TYPE_ESP = 9;
    public static final int TYPE_FCW = 0;
    public static final int TYPE_HDC = 8;
    public static final int TYPE_IHB = 17;
    public static final int TYPE_ISLA = 30;
    public static final int TYPE_ISLC = 4;
    public static final int TYPE_LCC = 6;
    public static final int TYPE_LDW = 1;
    public static final int TYPE_LKA = 14;
    public static final int TYPE_MEM_PARK = 19;
    public static final int TYPE_NGP = 20;
    public static final int TYPE_NRA = 32;
    public static final int TYPE_PARK = 5;
    public static final int TYPE_RCTA = 3;
    public static final int TYPE_RCW = 13;
    public static final int TYPE_SIMPLE_SAS = 34;
    public static final int TYPE_SPECIAL_SAS = 35;
    public static final int TYPE_SSA = 31;
    public static final int TYPE_XPILOT_35_PURCHASE = 35;
    public static final int TYPE_XPILOT_IHB_PURCHASE = 29;
    public static final int TYPE_XPILOT_PARK_PURCHASE = 28;
    public static final int TYPE_XPILOT_PURCHASE = 27;
    private XDialog mInfoDialog;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static final DialogInfoHelper sInstance = new DialogInfoHelper();

        private SingleHolder() {
        }
    }

    public static DialogInfoHelper getInstance() {
        return SingleHolder.sInstance;
    }

    private DialogInfoHelper() {
    }

    public void releaseInfoPanel() {
        XDialog xDialog = this.mInfoDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mInfoDialog = null;
        }
    }

    public void showInfoPanel(Context context, int type, DialogInterface.OnShowListener onShowListener, DialogInterface.OnDismissListener onDismissListener) {
        showInfoDialog(context, null, type, onShowListener, onDismissListener, false);
    }

    public void showXPilotInfoPanel(Context context, int type, DialogInterface.OnShowListener onShowListener, DialogInterface.OnDismissListener onDismissListener) {
        showInfoDialog(context, null, type, onShowListener, onDismissListener, true);
    }

    public void showXPilotInfoPanel(Context context, XPilotItem<?> item, DialogInterface.OnShowListener onShowListener, DialogInterface.OnDismissListener onDismissListener) {
        showInfoDialog(context, item, 0, onShowListener, onDismissListener, true);
    }

    public void dismissXPilotInfoPanel() {
        XDialog xDialog = this.mInfoDialog;
        if (xDialog != null) {
            xDialog.dismiss();
            this.mInfoDialog = null;
        }
    }

    private void showInfoDialog(Context context, XPilotItem<?> item, final int type, DialogInterface.OnShowListener onShowListener, DialogInterface.OnDismissListener onDismissListener, boolean isXpilot) {
        final int keyWords;
        dismissXPilotInfoPanel();
        if (context == null) {
            LogUtils.w(TAG, "showTipsDialog failed: context is null");
            return;
        }
        XDialog xDialog = new XDialog(context, R.style.XDialogView_Large);
        this.mInfoDialog = xDialog;
        xDialog.getDialog().setOnKeyListener(null);
        this.mInfoDialog.setSystemDialog(2048);
        this.mInfoDialog.setCustomView(LayoutInflater.from(context).inflate(R.layout.layout_xpilot_info_dialog_view, (ViewGroup) null), false);
        int i = R.string.btn_close;
        if (item == null) {
            i = getDialogBtn(type);
        }
        this.mInfoDialog.setPositiveButton(context.getResources().getString(i), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$DialogInfoHelper$8Zw-kkgs6Vlk5HlfBHNSPuhsrDw
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog2, int i2) {
                DialogInfoHelper.this.lambda$showInfoDialog$0$DialogInfoHelper(xDialog2, i2);
            }
        });
        if (isXpilot) {
            final Resources resources = context.getResources();
            if (item != null) {
                keyWords = item.getKeywordId();
            } else {
                keyWords = getKeyWords(type);
            }
            if (UserBookHelper.isSupport()) {
                this.mInfoDialog.setNegativeButton(resources.getString(R.string.btn_user_manual), new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$DialogInfoHelper$ZGkeKIlAbjkbYJJeUYNXcfYYbhM
                    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
                    public final void onClick(XDialog xDialog2, int i2) {
                        DialogInfoHelper.lambda$showInfoDialog$1(keyWords, resources, type, xDialog2, i2);
                    }
                });
            }
            this.mInfoDialog.getDialog().setOnShowListener(onShowListener);
            this.mInfoDialog.getDialog().setOnDismissListener(onDismissListener);
        } else {
            this.mInfoDialog.setNegativeButton((CharSequence) null, (XDialogInterface.OnClickListener) null);
        }
        initData(item, type);
        this.mInfoDialog.show();
        VuiManager.instance().initVuiDialog(this.mInfoDialog, context, VuiManager.SCENE_XPILOT_INFO_DIALOG + type + VuiManager.SUFFIX_OF_DIALOG_SCENE);
    }

    public /* synthetic */ void lambda$showInfoDialog$0$DialogInfoHelper(XDialog xDialog, int i) {
        this.mInfoDialog.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$showInfoDialog$1(final int keywordResId, final Resources res, final int type, XDialog xDialog, int i) {
        if (keywordResId > 0) {
            UserBookHelper.openUserBook(res.getString(keywordResId), true);
        } else {
            LogUtils.e(TAG, "can not jump to user book, type=" + type + ", keywordResId=" + keywordResId, false);
        }
    }

    private void initData(XPilotItem<?> item, int type) {
        this.mInfoDialog.getContentView().findViewById(R.id.scrollView).scrollTo(0, 0);
        String titleStr = getTitleStr(item, type);
        if (titleStr != null) {
            this.mInfoDialog.setTitle(titleStr);
        }
        ImageView imageView = (ImageView) this.mInfoDialog.getContentView().findViewById(R.id.help_pic_1);
        int helpPic1ResId = item != null ? item.getHelpPic1ResId() : getHelpPicPart1(type);
        if (helpPic1ResId != 0) {
            imageView.setImageResource(helpPic1ResId);
            imageView.setVisibility(0);
        } else {
            imageView.setVisibility(8);
        }
        final String helpTxt1 = item != null ? item.getHelpTxt1() : ResUtils.getString(getHelpTextPart1(type));
        if (!TextUtils.isEmpty(helpTxt1)) {
            final XTextView xTextView = (XTextView) this.mInfoDialog.getContentView().findViewById(R.id.help_text_1);
            xTextView.setText(helpTxt1);
            xTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$DialogInfoHelper$Xf8Ied3WIcbig-fZhKn0JFnipsk
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public final void onGlobalLayout() {
                    DialogInfoHelper.lambda$initData$2(XTextView.this, helpTxt1);
                }
            });
        }
        ImageView imageView2 = (ImageView) this.mInfoDialog.getContentView().findViewById(R.id.help_pic_2);
        int helpPic2ResId = item != null ? item.getHelpPic2ResId() : 0;
        if (helpPic2ResId != 0) {
            imageView2.setImageResource(helpPic2ResId);
            imageView2.setVisibility(0);
        } else {
            imageView2.setVisibility(8);
        }
        String helpTxt2 = item != null ? item.getHelpTxt2() : null;
        TextView textView = (TextView) this.mInfoDialog.getContentView().findViewById(R.id.help_text_2);
        if (!TextUtils.isEmpty(helpTxt2)) {
            textView.setText(helpTxt2);
            textView.setVisibility(0);
            return;
        }
        textView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initData$2(final XTextView content, final String helpText1) {
        TextPaint paint = content.getPaint();
        paint.setTextSize(content.getTextSize());
        if (((int) paint.measureText(helpText1)) > content.getWidth()) {
            content.setGravity(GravityCompat.START);
        } else {
            content.setGravity(17);
        }
    }

    private String getTitleStr(XPilotItem<?> item, int type) {
        if (item != null) {
            return ResUtils.getString(item.getTitleResId()).replace(DIALOG_TITLE_DIRTY, "");
        }
        return ResUtils.getString(getTitle(type));
    }

    private int getKeyWords(int type) {
        if (type == 17) {
            return R.string.ihb_keyword;
        }
        return 0;
    }

    private int getTitle(int type) {
        if (type == 17) {
            return R.string.ihb_title;
        }
        if (type != 35) {
            switch (type) {
                case 27:
                case 28:
                case 29:
                    break;
                default:
                    return 0;
            }
        }
        return R.string.xpilot_3_purchase_title;
    }

    private int getHelpPicPart1(int type) {
        if (type == 17) {
            return R.drawable.img_xpilot_ihb_1;
        }
        if (type != 35) {
            switch (type) {
                case 27:
                case 28:
                case 29:
                    break;
                default:
                    return 0;
            }
        }
        return R.drawable.img_xpilot_purchase;
    }

    private int getHelpTextPart1(int type) {
        if (type != 17) {
            if (type != 35) {
                switch (type) {
                    case 27:
                        return R.string.xpilot_3_purchase_help_1;
                    case 28:
                        return R.string.mem_park_purchase_help_1;
                    case 29:
                        return R.string.ihb_purchase_help_1;
                    default:
                        return 0;
                }
            }
            return R.string.xpilot_35_purchase_help_1;
        }
        return R.string.ihb_feature_help_1;
    }

    private int getDialogBtn(int type) {
        if (type != 35) {
            switch (type) {
                case 27:
                case 28:
                case 29:
                    break;
                default:
                    return R.string.btn_close;
            }
        }
        return R.string.btn_ok;
    }
}
