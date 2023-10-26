package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XTouchAreaUtils;
import com.xiaopeng.xui.utils.XuiUtils;
import java.util.List;

/* loaded from: classes2.dex */
public class XListSingle extends XRelativeLayout {
    private int mLeftRightTouchFull;
    private ViewGroup mLeftView;
    private ViewStub mLeftViewStub;
    private TextView mNum;
    private ViewStub mRightViewStub;
    private ImageView mTag;
    private TextView mText;
    private ViewGroup mViewGroupLeft;
    private ViewGroup mViewGroupRight;

    public XListSingle(Context context) {
        this(context, null);
    }

    public XListSingle(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XListSingle(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XListSingle(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        LayoutInflater.from(context).inflate(R.layout.x_list_single, this);
        initView();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XListSingle);
        setText(obtainStyledAttributes.getString(R.styleable.XListSingle_list_single_text));
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.XListSingle_list_single_left, -1);
        if (resourceId != -1) {
            this.mLeftViewStub.setLayoutResource(resourceId);
            View inflate = this.mLeftViewStub.inflate();
            if (inflate instanceof ViewGroup) {
                this.mViewGroupLeft = (ViewGroup) inflate;
            }
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.XListSingle_list_single_right, -1);
        if (resourceId2 != -1) {
            this.mRightViewStub.setLayoutResource(resourceId2);
            View inflate2 = this.mRightViewStub.inflate();
            if (inflate2 instanceof ViewGroup) {
                this.mViewGroupRight = (ViewGroup) inflate2;
            }
        }
        int resourceId3 = obtainStyledAttributes.getResourceId(R.styleable.XListSingle_list_single_tag_icon, -1);
        if (resourceId3 != -1) {
            setTagIcon(resourceId3);
        }
        boolean z = obtainStyledAttributes.getBoolean(R.styleable.XListSingle_list_single_tag, false);
        showIconTag(z);
        this.mLeftRightTouchFull = obtainStyledAttributes.getInt(R.styleable.XListSingle_list_single_left_right_touch_full, 0);
        obtainStyledAttributes.recycle();
        if (resourceId2 != -1) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mText.getLayoutParams();
            marginLayoutParams.setMarginEnd((int) getResources().getDimension(z ? R.dimen.x_list_tv_has_tag_margin_end : R.dimen.x_list_tv_margin_end));
            this.mText.setLayoutParams(marginLayoutParams);
        } else if (z) {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mText.getLayoutParams();
            marginLayoutParams2.setMarginEnd((int) getResources().getDimension(R.dimen.x_list_tv_has_tag_margin_end));
            this.mText.setLayoutParams(marginLayoutParams2);
        }
    }

    private void initView() {
        this.mText = (TextView) findViewById(R.id.x_list_tv);
        this.mNum = (TextView) findViewById(R.id.x_list_num);
        this.mLeftView = (ViewGroup) findViewById(R.id.x_list_left_lay);
        this.mLeftViewStub = (ViewStub) findViewById(R.id.x_list_left);
        this.mRightViewStub = (ViewStub) findViewById(R.id.x_list_right);
        this.mTag = (ImageView) findViewById(R.id.x_list_tag);
        if (isInEditMode()) {
            return;
        }
        this.mNum.setTextAppearance(R.style.XFont_Number_Bold);
    }

    public void setNum(int i) {
        this.mNum.setText(String.valueOf(i));
    }

    public void showNum(boolean z) {
        this.mNum.setVisibility(z ? 0 : 8);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.x_list_left_width_for_num);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.x_list_left_width);
        ViewGroup viewGroup = this.mLeftView;
        if (viewGroup != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
            if (z) {
                marginLayoutParams.width = dimensionPixelSize;
            } else {
                marginLayoutParams.width = dimensionPixelSize2;
            }
            this.mLeftView.setLayoutParams(marginLayoutParams);
        }
        ViewGroup viewGroup2 = this.mViewGroupLeft;
        if (viewGroup2 != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) viewGroup2.getLayoutParams();
            if (z) {
                marginLayoutParams2.leftMargin = dimensionPixelSize - dimensionPixelSize2;
            } else {
                marginLayoutParams2.leftMargin = 0;
            }
            this.mViewGroupLeft.setLayoutParams(marginLayoutParams2);
        }
        TextView textView = this.mText;
        if (textView != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
            if (z) {
                marginLayoutParams3.leftMargin = dimensionPixelSize;
            } else {
                marginLayoutParams3.leftMargin = dimensionPixelSize2;
            }
            this.mText.setLayoutParams(marginLayoutParams3);
        }
        extendTouchLeft();
    }

    public void setText(CharSequence charSequence) {
        this.mText.setText(charSequence);
    }

    public void setTagIcon(int i) {
        this.mTag.setImageResource(i);
    }

    public void showIconTag(boolean z) {
        this.mTag.setVisibility(z ? 0 : 4);
    }

    public void setTagOnClickListener(View.OnClickListener onClickListener) {
        this.mTag.setOnClickListener(onClickListener);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        extendTouchLeft();
        extendTouchRight();
    }

    private void extendTouchLeft() {
        if (this.mViewGroupLeft != null) {
            if (this.mLeftRightTouchFull == 1) {
                for (Class cls : XTouchAreaUtils.CLASSES) {
                    List<View> findViewsByType = XuiUtils.findViewsByType(this.mViewGroupLeft, cls);
                    if (findViewsByType.size() > 0) {
                        View[] viewArr = new View[findViewsByType.size()];
                        findViewsByType.toArray(viewArr);
                        XTouchAreaUtils.extendTouchAreaAsParentSameSize(viewArr, this);
                    }
                }
            } else {
                XTouchAreaUtils.extendTouchAreaAsParentSameSize(this.mLeftView);
            }
        }
        ViewGroup viewGroup = this.mViewGroupRight;
        if (viewGroup != null) {
            if (this.mLeftRightTouchFull == 2) {
                for (Class cls2 : XTouchAreaUtils.CLASSES) {
                    List<View> findViewsByType2 = XuiUtils.findViewsByType(this.mViewGroupRight, cls2);
                    if (findViewsByType2.size() > 0) {
                        View[] viewArr2 = new View[findViewsByType2.size()];
                        findViewsByType2.toArray(viewArr2);
                        XTouchAreaUtils.extendTouchAreaAsParentSameSize(viewArr2, this);
                    }
                }
                return;
            }
            XTouchAreaUtils.extendTouchAreaAsParentSameSize(viewGroup);
        }
    }

    private void extendTouchRight() {
        ViewGroup viewGroup = this.mViewGroupRight;
        if (viewGroup != null) {
            if (this.mLeftRightTouchFull == 2) {
                for (Class cls : XTouchAreaUtils.CLASSES) {
                    List<View> findViewsByType = XuiUtils.findViewsByType(this.mViewGroupRight, cls);
                    if (findViewsByType.size() > 0) {
                        View[] viewArr = new View[findViewsByType.size()];
                        findViewsByType.toArray(viewArr);
                        XTouchAreaUtils.extendTouchAreaAsParentSameSize(viewArr, this);
                    }
                }
                return;
            }
            XTouchAreaUtils.extendTouchAreaAsParentSameSize(viewGroup);
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        setEnabled(z, true);
    }

    public void setEnabled(boolean z, boolean z2) {
        super.setEnabled(z);
        if (z2) {
            setChildEnabled(this, z);
        }
    }

    private void setChildEnabled(ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                setChildEnabled((ViewGroup) childAt, z);
            }
            childAt.setEnabled(z);
        }
    }
}
