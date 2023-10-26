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
public class XListTwo extends XRelativeLayout {
    private int mLeftRightTouchFull;
    private ViewGroup mLeftView;
    private ViewStub mLeftViewStub;
    private TextView mNum;
    private ViewStub mRightViewStub;
    private ImageView mTag;
    private ViewStub mTagCustomViewStub;
    private TextView mText;
    private TextView mTextSub;
    private ViewGroup mTvLay;
    private ViewGroup mViewGroupLeft;
    private ViewGroup mViewGroupRight;

    public XListTwo(Context context) {
        this(context, null);
    }

    public XListTwo(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XListTwo(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public XListTwo(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        LayoutInflater.from(context).inflate(R.layout.x_list_two, this);
        initView();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.XListTwo);
        setText(obtainStyledAttributes.getString(R.styleable.XListTwo_list_two_text));
        setTextSub(obtainStyledAttributes.getString(R.styleable.XListTwo_list_two_text_sub));
        this.mTextSub.setMaxLines(obtainStyledAttributes.getInt(R.styleable.XListTwo_list_text_sub_lines, 1));
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.XListTwo_list_two_left, -1);
        if (resourceId != -1) {
            this.mLeftViewStub.setLayoutResource(resourceId);
            View inflate = this.mLeftViewStub.inflate();
            if (inflate instanceof ViewGroup) {
                this.mViewGroupLeft = (ViewGroup) inflate;
            }
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.XListTwo_list_two_right, -1);
        if (resourceId2 != -1) {
            this.mRightViewStub.setLayoutResource(resourceId2);
            View inflate2 = this.mRightViewStub.inflate();
            if (inflate2 instanceof ViewGroup) {
                this.mViewGroupRight = (ViewGroup) inflate2;
            }
        }
        int resourceId3 = obtainStyledAttributes.getResourceId(R.styleable.XListTwo_list_two_tag_icon, -1);
        if (resourceId3 != -1) {
            setTagIcon(resourceId3);
        }
        showIconTag(obtainStyledAttributes.getBoolean(R.styleable.XListTwo_list_two_tag, false));
        int resourceId4 = obtainStyledAttributes.getResourceId(R.styleable.XListTwo_list_two_tag_custom, -1);
        if (resourceId4 != -1) {
            this.mTagCustomViewStub.setLayoutResource(resourceId4);
            this.mTagCustomViewStub.inflate();
        }
        this.mLeftRightTouchFull = obtainStyledAttributes.getInt(R.styleable.XListTwo_list_two_left_right_touch_full, 0);
        obtainStyledAttributes.recycle();
        if (resourceId2 != -1) {
            View findViewById = findViewById(R.id.x_list_tv_lay);
            int dimension = (int) getResources().getDimension(R.dimen.x_list_tv_margin_end);
            findViewById.setPadding(findViewById.getPaddingLeft(), findViewById.getPaddingTop(), dimension, findViewById.getPaddingBottom());
            TextView textView = this.mTextSub;
            textView.setPadding(textView.getPaddingLeft(), this.mTextSub.getPaddingTop(), dimension, this.mTextSub.getPaddingBottom());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        extendTouchLeft();
        extendTouchRight();
    }

    private /* synthetic */ void lambda$onAttachedToWindow$0() {
        logD("list-two this w " + getWidth() + " h " + getHeight());
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
                return;
            }
            XTouchAreaUtils.extendTouchAreaAsParentSameSize(this.mLeftView);
        }
    }

    private /* synthetic */ void lambda$extendTouchLeft$1() {
        logD("list-two mLeftView w " + this.mLeftView.getWidth() + " h " + this.mLeftView.getHeight());
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

    private /* synthetic */ void lambda$extendTouchRight$2() {
        logD("list-two mViewGroupRight w " + this.mViewGroupRight.getWidth() + " h " + this.mViewGroupRight.getHeight());
    }

    private void initView() {
        this.mText = (TextView) findViewById(R.id.x_list_tv);
        this.mNum = (TextView) findViewById(R.id.x_list_num);
        this.mLeftView = (ViewGroup) findViewById(R.id.x_list_left_lay);
        this.mTvLay = (ViewGroup) findViewById(R.id.x_list_tv_lay);
        this.mTextSub = (TextView) findViewById(R.id.x_list_tv_sub);
        this.mLeftViewStub = (ViewStub) findViewById(R.id.x_list_left);
        this.mRightViewStub = (ViewStub) findViewById(R.id.x_list_right);
        this.mTag = (ImageView) findViewById(R.id.x_list_tag);
        this.mTagCustomViewStub = (ViewStub) findViewById(R.id.x_list_tag_custom_lay);
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
        ViewGroup viewGroup3 = this.mTvLay;
        if (viewGroup3 != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) viewGroup3.getLayoutParams();
            if (z) {
                marginLayoutParams3.leftMargin = dimensionPixelSize;
            } else {
                marginLayoutParams3.leftMargin = dimensionPixelSize2;
            }
            this.mTvLay.setLayoutParams(marginLayoutParams3);
        }
        extendTouchLeft();
    }

    public void setText(CharSequence charSequence) {
        this.mText.setText(charSequence);
        this.mText.requestLayout();
    }

    public void setTextSub(CharSequence charSequence) {
        this.mTextSub.setText(charSequence);
    }

    public void setTagIcon(int i) {
        this.mTag.setImageResource(i);
    }

    public void showIconTag(boolean z) {
        this.mTag.setVisibility(z ? 0 : 8);
    }

    public boolean isShowIconTag() {
        return this.mTag.getVisibility() == 0;
    }

    public void setTagOnClickListener(View.OnClickListener onClickListener) {
        this.mTag.setOnClickListener(onClickListener);
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
