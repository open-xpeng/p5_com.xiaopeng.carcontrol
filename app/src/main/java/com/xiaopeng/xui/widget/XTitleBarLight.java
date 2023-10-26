package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.xiaopeng.speech.vui.constants.VuiConstants;
import com.xiaopeng.vui.commons.IVuiElementBuilder;
import com.xiaopeng.vui.commons.IVuiElementListener;
import com.xiaopeng.vui.commons.VuiMode;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XTouchAreaUtils;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class XTitleBarLight extends XRelativeLayout implements View.OnClickListener, IVuiElementListener {
    public static final int MAIN_ACTION_BACK = 1;
    public static final int MAIN_ACTION_CLOSE = 0;
    public static final int MAIN_ACTION_NONE = -1;
    public static final int TYPE_COMPACT = 0;
    public static final int TYPE_SPARSE = 1;
    private XImageButton mBtnBack;
    private XImageButton mBtnClose;
    private ViewGroup mBtnCloseLay;
    private OnTitleBarClickListener mOnTitleBarClickListener;
    private ViewGroup mRightContainer;
    private int mSparsePadding;
    private TextView mTitle;

    /* loaded from: classes2.dex */
    public interface OnTitleBarClickListener {
        void onTitleBarBackClick();

        void onTitleBarCloseClick();
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent vuiEvent) {
        return false;
    }

    public XTitleBarLight(Context context) {
        this(context, null);
    }

    public XTitleBarLight(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XTitleBarLight(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XTitleBarLight(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        LayoutInflater.from(context).inflate(R.layout.x_titlebarlight, this);
        initView();
        this.mSparsePadding = (int) getResources().getDimension(R.dimen.x_title_bar_light_sparse_padding);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XTitleBarLight);
        setTitle(obtainStyledAttributes.getString(R.styleable.XTitleBarLight_title_text));
        setMainAction(obtainStyledAttributes.getInt(R.styleable.XTitleBarLight_title_main_action, 0));
        setTitleType(obtainStyledAttributes.getInt(R.styleable.XTitleBarLight_title_text_type, 0));
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.XTitleBarLight_title_right_action_layout, 0);
        if (resourceId > 0) {
            setRightAction(resourceId);
        }
        obtainStyledAttributes.recycle();
    }

    private void initView() {
        this.mBtnClose = (XImageButton) findViewById(R.id.x_titlebar_btn_close);
        this.mBtnBack = (XImageButton) findViewById(R.id.x_titlebar_btn_back);
        this.mTitle = (TextView) findViewById(R.id.x_titlebar_tv_title);
        this.mRightContainer = (ViewGroup) findViewById(R.id.x_titlebar_right_container);
        this.mBtnCloseLay = (ViewGroup) findViewById(R.id.x_titlebar_btn_close_lay);
        this.mBtnClose.setOnClickListener(this);
        this.mBtnBack.setOnClickListener(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        XTouchAreaUtils.extendTouchAreaAsParentSameSize(this.mBtnClose, this.mBtnCloseLay);
        XTouchAreaUtils.extendTouchAreaAsParentSameSize(this.mBtnBack, this.mBtnCloseLay);
    }

    public void setTitle(int i) {
        this.mTitle.setText(i);
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle.setText(charSequence);
    }

    public void setTitleType(int i) {
        if (i != 1) {
            TextView textView = this.mTitle;
            textView.setPadding(0, textView.getPaddingTop(), this.mTitle.getPaddingRight(), this.mTitle.getPaddingBottom());
            return;
        }
        TextView textView2 = this.mTitle;
        textView2.setPadding(this.mSparsePadding, textView2.getPaddingTop(), this.mTitle.getPaddingRight(), this.mTitle.getPaddingBottom());
    }

    public void setMainAction(int i) {
        if (i == -1) {
            this.mBtnBack.setVisibility(4);
            this.mBtnClose.setVisibility(4);
        } else if (i != 1) {
            this.mBtnClose.setVisibility(0);
            this.mBtnBack.setVisibility(4);
        } else {
            this.mBtnBack.setVisibility(0);
            this.mBtnClose.setVisibility(4);
        }
    }

    public void setRightAction(int i) {
        setRightAction(LayoutInflater.from(getContext()).inflate(i, this.mRightContainer, false));
    }

    public void setRightAction(View view) {
        this.mRightContainer.removeAllViews();
        this.mRightContainer.addView(view);
        this.mRightContainer.setVisibility(0);
    }

    public void clearRightAction() {
        this.mRightContainer.removeAllViews();
        this.mRightContainer.setVisibility(8);
        this.mTitle.requestLayout();
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.mOnTitleBarClickListener = onTitleBarClickListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnTitleBarClickListener onTitleBarClickListener;
        int id = view.getId();
        if (id == R.id.x_titlebar_btn_close) {
            OnTitleBarClickListener onTitleBarClickListener2 = this.mOnTitleBarClickListener;
            if (onTitleBarClickListener2 != null) {
                onTitleBarClickListener2.onTitleBarCloseClick();
            }
        } else if (id != R.id.x_titlebar_btn_back || (onTitleBarClickListener = this.mOnTitleBarClickListener) == null) {
        } else {
            onTitleBarClickListener.onTitleBarBackClick();
        }
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public VuiElement onBuildVuiElement(String str, IVuiElementBuilder iVuiElementBuilder) {
        try {
            XImageButton xImageButton = this.mBtnBack;
            if (xImageButton != null) {
                if (xImageButton.getVisibility() != 0) {
                    this.mBtnBack.setVuiMode(VuiMode.DISABLED);
                } else {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put(VuiConstants.PROPS_GENERALACT, VuiConstants.GENERALACT_RETURN);
                    this.mBtnBack.setVuiProps(jSONObject);
                }
            }
            XImageButton xImageButton2 = this.mBtnClose;
            if (xImageButton2 != null) {
                if (xImageButton2.getVisibility() != 0) {
                    this.mBtnClose.setVuiMode(VuiMode.DISABLED);
                    return null;
                }
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(VuiConstants.PROPS_GENERALACT, VuiConstants.GENERALACT_CLOSE);
                this.mBtnClose.setVuiProps(jSONObject2);
                return null;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
