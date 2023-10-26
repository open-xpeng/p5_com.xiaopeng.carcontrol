package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

@Deprecated
/* loaded from: classes2.dex */
public class XTitleBar extends XLinearLayout implements View.OnClickListener, IVuiElementListener {
    private static final int MODE_BUTTON = 2;
    private static final int MODE_CUSTOM = 4;
    private static final int MODE_ICON = 1;
    private static final int MODE_LOADING = 3;
    private static final int MODE_NONE = 0;
    private XImageButton mBtnBack;
    private XImageButton mBtnClose;
    private ViewGroup mBtnCloseLay;
    private OnTitleBarClickListener mOnTitleBarClickListener;
    private ViewGroup mRightContainer;
    private TextView mTitle;

    /* loaded from: classes2.dex */
    public interface OnTitleBarClickListener {
        void onTitleBarActionClick(View view, int i);

        void onTitleBarBackClick();

        void onTitleBarCloseClick();
    }

    @Override // com.xiaopeng.vui.commons.IVuiElementListener
    public boolean onVuiElementEvent(View view, VuiEvent vuiEvent) {
        return false;
    }

    public XTitleBar(Context context) {
        this(context, null);
    }

    public XTitleBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XTitleBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XTitleBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        int resourceId;
        LayoutInflater.from(context).inflate(R.layout.x_titlebar, this);
        initView();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XTitleBar);
        setBackVisibility(obtainStyledAttributes.getInt(R.styleable.XTitleBar_title_back, 8));
        setCloseVisibility(obtainStyledAttributes.getInt(R.styleable.XTitleBar_title_close, 0));
        setTitle(obtainStyledAttributes.getString(R.styleable.XTitleBar_title_text));
        int i3 = obtainStyledAttributes.getInt(R.styleable.XTitleBar_title_action_mode, 0);
        if (i3 == 0) {
            this.mRightContainer.setVisibility(8);
        } else if (i3 == 1) {
            int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.XTitleBar_title_action_1, -1);
            int resourceId3 = obtainStyledAttributes.getResourceId(R.styleable.XTitleBar_title_action_2, -1);
            if (resourceId2 != -1) {
                setActionWithIcon(resourceId2);
            }
            if (resourceId2 != -1 && resourceId3 != -1) {
                setActionWithIcon(resourceId2, resourceId3);
            }
        } else if (i3 == 2) {
            String string = obtainStyledAttributes.getString(R.styleable.XTitleBar_title_action_1);
            if (string != null) {
                setActionWithButton(string);
            }
        } else if (i3 == 4 && (resourceId = obtainStyledAttributes.getResourceId(R.styleable.XTitleBar_title_action_1, -1)) != -1) {
            setActionWithCustom(resourceId);
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
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        XTouchAreaUtils.extendTouchAreaAsParentSameSize(this.mBtnClose, this.mBtnCloseLay);
    }

    private void addActionWithIcon(int... iArr) {
        if (iArr != null) {
            for (int i = 0; i < iArr.length; i++) {
                ImageButton imageButton = (ImageButton) LayoutInflater.from(getContext()).inflate(R.layout.x_titlebar_action_icon, this.mRightContainer, false);
                imageButton.setImageResource(iArr[i]);
                imageButton.setOnClickListener(this);
                imageButton.setId(i);
                this.mRightContainer.addView(imageButton);
            }
        }
    }

    private void addActionWithButton(CharSequence... charSequenceArr) {
        for (int i = 0; i < charSequenceArr.length; i++) {
            TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.x_titlebar_action_ghostbtn, this.mRightContainer, false);
            textView.setOnClickListener(this);
            textView.setId(i);
            textView.setText(charSequenceArr[i]);
            this.mRightContainer.addView(textView);
        }
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle.setText(charSequence);
    }

    public void setCloseVisibility(int i) {
        this.mBtnClose.setVisibility(i);
    }

    public void setBackVisibility(int i) {
        this.mBtnBack.setVisibility(i);
    }

    public void setActionWithIcon(int i) {
        this.mRightContainer.removeAllViews();
        addActionWithIcon(i);
        this.mRightContainer.setVisibility(0);
    }

    public void setActionWithIcon(int i, int i2) {
        this.mRightContainer.removeAllViews();
        addActionWithIcon(i, i2);
        this.mRightContainer.setVisibility(0);
    }

    public void setActionWithButton(CharSequence charSequence) {
        this.mRightContainer.removeAllViews();
        addActionWithButton(charSequence);
        this.mRightContainer.setVisibility(0);
    }

    public void setActionWithCustom(int i) {
        setActionWithCustom(LayoutInflater.from(getContext()).inflate(i, this.mRightContainer, false));
    }

    public void setActionWithCustom(View view) {
        this.mRightContainer.removeAllViews();
        this.mRightContainer.addView(view);
        this.mRightContainer.setVisibility(0);
    }

    public void clearAction() {
        this.mRightContainer.removeAllViews();
        this.mRightContainer.setVisibility(8);
        this.mTitle.requestLayout();
    }

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.mOnTitleBarClickListener = onTitleBarClickListener;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.x_titlebar_btn_close) {
            OnTitleBarClickListener onTitleBarClickListener = this.mOnTitleBarClickListener;
            if (onTitleBarClickListener != null) {
                onTitleBarClickListener.onTitleBarCloseClick();
            }
        } else if (id == R.id.x_titlebar_btn_back) {
            OnTitleBarClickListener onTitleBarClickListener2 = this.mOnTitleBarClickListener;
            if (onTitleBarClickListener2 != null) {
                onTitleBarClickListener2.onTitleBarBackClick();
            }
        } else {
            OnTitleBarClickListener onTitleBarClickListener3 = this.mOnTitleBarClickListener;
            if (onTitleBarClickListener3 != null) {
                onTitleBarClickListener3.onTitleBarActionClick(view, id);
            }
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
        } catch (Exception unused) {
            return null;
        }
    }
}
