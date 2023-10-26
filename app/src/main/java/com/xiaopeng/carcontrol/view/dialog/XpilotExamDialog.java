package com.xiaopeng.carcontrol.view.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.speech.VuiManager;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.QrCodeUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.XLoading;

/* loaded from: classes2.dex */
public class XpilotExamDialog extends XDialog {
    private static final int EXAM_URL_EXPIRED_TIME = 600000;
    private static final int QR_CODE_MARGIN = 1;
    private static final int QR_CODE_SIZE = 320;
    private static final String TAG = "XpilotExamDialog";
    private final Context mContext;
    private int mDismissResId;
    private ImageView mErrorIcon;
    private Bitmap mExamQrBitmap;
    private final Runnable mExamUrlExpireTask;
    private boolean mIsExamMode;
    private ExamDialogListener mListener;
    private XLoading mLoading;
    private ImageView mQrContent;
    private ImageView mRetryIcon;
    private long mTaskId;
    private TextView mTvContent;

    /* loaded from: classes2.dex */
    public interface ExamDialogListener {
        void dismissDialog();

        void retry(long taskId);
    }

    public /* synthetic */ void lambda$new$0$XpilotExamDialog() {
        updateErrorState(this.mTaskId);
    }

    public XpilotExamDialog(Context context) {
        super(context);
        this.mDismissResId = R.string.laa_exam_dismiss_dialog_tips;
        this.mTaskId = 2L;
        this.mIsExamMode = false;
        this.mExamUrlExpireTask = new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$XpilotExamDialog$q_5Cs92ahS3sYtxogPg4bBrF4mo
            @Override // java.lang.Runnable
            public final void run() {
                XpilotExamDialog.this.lambda$new$0$XpilotExamDialog();
            }
        };
        this.mContext = context;
        setSystemDialog(2048);
        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_exam_qr, (ViewGroup) null);
        bindView(inflate);
        setCustomView(inflate, false);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$XpilotExamDialog$RZkh5YcBEVikzDuB088vKBaNNIE
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                XpilotExamDialog.this.lambda$new$1$XpilotExamDialog(dialogInterface);
            }
        });
        setPositiveButton(R.string.dialog_ngp_exam_qr_confirm, new XDialogInterface.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$XpilotExamDialog$bZ9sebJ7uZNiBh4jKUxE9B2zhVY
            @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
            public final void onClick(XDialog xDialog, int i) {
                XpilotExamDialog.this.lambda$new$2$XpilotExamDialog(xDialog, i);
            }
        });
    }

    public /* synthetic */ void lambda$new$1$XpilotExamDialog(DialogInterface dialog) {
        QrCodeUtils.releaseQrBitmap(this.mExamQrBitmap);
        toastDismissTips();
        ThreadUtils.removeRunnable(this.mExamUrlExpireTask);
        ExamDialogListener examDialogListener = this.mListener;
        if (examDialogListener != null) {
            examDialogListener.dismissDialog();
        }
    }

    public /* synthetic */ void lambda$new$2$XpilotExamDialog(XDialog xDialog, int i) {
        dismiss();
    }

    public void autoCloseDialog(long taskId) {
        if (!this.mIsExamMode) {
            LogUtils.i(TAG, "Not exam mode,ignore the auto close event！");
            return;
        }
        updateDismissTips(taskId, true);
        dismiss();
    }

    private void toastDismissTips() {
        if (!this.mIsExamMode) {
            LogUtils.i(TAG, "Not exam mode,needn't toast!");
        } else {
            NotificationHelper.getInstance().showToast(this.mDismissResId);
        }
    }

    private void bindView(View view) {
        this.mLoading = (XLoading) view.findViewById(R.id.xl_qr_loading);
        this.mQrContent = (ImageView) view.findViewById(R.id.iv_qr_exam);
        this.mErrorIcon = (ImageView) view.findViewById(R.id.iv_qr_error);
        this.mRetryIcon = (ImageView) view.findViewById(R.id.iv_qr_refresh);
        this.mTvContent = (TextView) view.findViewById(R.id.tv_content);
    }

    public void showStudyDialog(String studyUrl, String title, String content) {
        LogUtils.i(TAG, "Show study dialog. url:" + studyUrl);
        this.mLoading.setVisibility(4);
        this.mQrContent.setVisibility(0);
        this.mErrorIcon.setVisibility(4);
        this.mRetryIcon.setVisibility(4);
        setTitle(title);
        Bitmap createQRImage = QrCodeUtils.createQRImage(studyUrl, QR_CODE_SIZE, QR_CODE_SIZE, 1);
        this.mExamQrBitmap = createQRImage;
        this.mQrContent.setImageBitmap(createQRImage);
        this.mTvContent.setText(content);
        showStudyDialog();
    }

    public void showLoading(long taskId) {
        this.mTaskId = taskId;
        updateDismissTips(taskId, false);
        setTitle(R.string.dialog_ngp_exam_qr_title);
        this.mLoading.setVisibility(0);
        this.mQrContent.setVisibility(4);
        this.mErrorIcon.setVisibility(4);
        this.mRetryIcon.setVisibility(4);
        this.mTvContent.setText(R.string.dialog_ngp_exam_qr_tips);
        showExamDialog();
    }

    public void updateExamUrl(String examUrl, long taskId) {
        if (!isShowing()) {
            LogUtils.i(TAG, "Dialog is dismiss,ignore the examUrl.");
        } else if (!this.mIsExamMode) {
            LogUtils.w(TAG, "Dialog not exam mode,ignore the examUrl.");
        } else {
            ThreadUtils.removeRunnable(this.mExamUrlExpireTask);
            ThreadUtils.postDelayed(1, this.mExamUrlExpireTask, 600000L);
            this.mLoading.setVisibility(4);
            this.mQrContent.setVisibility(0);
            this.mErrorIcon.setVisibility(4);
            this.mRetryIcon.setVisibility(4);
            Bitmap createQRImage = QrCodeUtils.createQRImage(examUrl, QR_CODE_SIZE, QR_CODE_SIZE, 1);
            this.mExamQrBitmap = createQRImage;
            this.mQrContent.setImageBitmap(createQRImage);
            updateDismissTips(taskId, false);
        }
    }

    public void updateErrorState(final long taskId) {
        if (!isShowing()) {
            LogUtils.i(TAG, "Dialog is dismiss,ignore the error.");
        } else if (!this.mIsExamMode) {
            LogUtils.w(TAG, "Dialog not exam mode,ignore the error.");
        } else {
            this.mLoading.setVisibility(4);
            this.mQrContent.setVisibility(4);
            this.mErrorIcon.setVisibility(0);
            this.mRetryIcon.setVisibility(0);
            this.mRetryIcon.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.carcontrol.view.dialog.-$$Lambda$XpilotExamDialog$eJ4wx2lrF9CGzzJAWWJpDndkFhc
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    XpilotExamDialog.this.lambda$updateErrorState$3$XpilotExamDialog(taskId, view);
                }
            });
            updateDismissTips(taskId, false);
        }
    }

    public /* synthetic */ void lambda$updateErrorState$3$XpilotExamDialog(final long taskId, View v) {
        ExamDialogListener examDialogListener = this.mListener;
        if (examDialogListener != null) {
            examDialogListener.retry(taskId);
            this.mLoading.setVisibility(0);
            this.mQrContent.setVisibility(4);
            this.mErrorIcon.setVisibility(4);
            this.mRetryIcon.setVisibility(4);
        }
    }

    private void updateDismissTips(long taskId, boolean passExam) {
        if (taskId == 1) {
            this.mDismissResId = passExam ? R.string.ngp_exam_pass : R.string.ngp_exam_dismiss_dialog_tips;
        } else if (taskId == 2 || taskId == 8) {
            this.mDismissResId = passExam ? R.string.laa_exam_pass : R.string.laa_exam_dismiss_dialog_tips;
        } else if (taskId == 3 || taskId == 7) {
            this.mDismissResId = passExam ? R.string.mem_exam_pass : R.string.mem_exam_dismiss_dialog_tips;
        } else if (taskId == 4) {
            this.mDismissResId = passExam ? R.string.apa_exam_pass : R.string.apa_exam_dismiss_dialog_tips;
        } else if (taskId == 6) {
            this.mDismissResId = passExam ? R.string.cngp_exam_pass : R.string.cngp_exam_dismiss_dialog_tips;
        }
    }

    private void showExamDialog() {
        this.mIsExamMode = true;
        VuiManager.instance().initVuiDialog(this, this.mContext, VuiManager.SCENE_XPILOT_EXAM_DIALOG);
        show();
    }

    private void showStudyDialog() {
        this.mIsExamMode = false;
        VuiManager.instance().initVuiDialog(this, this.mContext, VuiManager.SCENE_XPILOT_STUDY_DIALOG);
        show();
    }

    public void setExamDialogListener(ExamDialogListener listener) {
        this.mListener = listener;
    }
}
