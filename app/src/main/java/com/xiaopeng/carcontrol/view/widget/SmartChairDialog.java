package com.xiaopeng.carcontrol.view.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.xiaopeng.carcontrol.R;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.xui.app.XDialog;
import com.xiaopeng.xui.app.XDialogInterface;

/* loaded from: classes2.dex */
public class SmartChairDialog extends XDialog implements XDialogInterface.OnClickListener {
    public static final int GENDER_FEMALE = 0;
    public static final int GENDER_MALE = 1;
    private static final int MAX_HIGH = 200;
    private static final int MAX_WEIGHT = 120;
    private static final int MIN_HIGH = 135;
    private static final int MIN_WEIGHT = 30;
    private static final String TAG = "SmartChairDialog";
    private Context mContext;
    private final NumberPickerView mGenderPicker;
    private final NumberPickerView mHeightPicker;
    private final NumberPickerView mWeightPicker;

    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
    public void onClick(XDialog dialog, int which) {
    }

    public SmartChairDialog(Context context) {
        this(context, 0);
    }

    public SmartChairDialog(Context context, int themeResId) {
        super(context, 2131886921);
        this.mContext = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_smart_chail, getContentView(), false);
        setCustomView(inflate, false);
        setPositiveButton(" ", this);
        setNegativeButton(" ", this);
        NumberPickerView numberPickerView = (NumberPickerView) inflate.findViewById(R.id.gender_number_picker);
        this.mGenderPicker = numberPickerView;
        String[] genderData = getGenderData();
        if (genderData != null) {
            setPick(numberPickerView, genderData, null, null);
        }
        NumberPickerView numberPickerView2 = (NumberPickerView) inflate.findViewById(R.id.weight_number_picker);
        this.mWeightPicker = numberPickerView2;
        String[] weightData = getWeightData();
        if (weightData != null) {
            setPick(numberPickerView2, weightData, null, this.mContext.getString(R.string.suffix_weight));
        }
        NumberPickerView numberPickerView3 = (NumberPickerView) inflate.findViewById(R.id.height_number_picker);
        this.mHeightPicker = numberPickerView3;
        String[] heightData = getHeightData();
        if (heightData != null) {
            setPick(numberPickerView3, heightData, null, this.mContext.getString(R.string.suffix_height));
        }
    }

    private void setPick(NumberPickerView picker, String[] datas, String prefix, String suffix) {
        picker.setMinValue(0);
        picker.setMaxValue(datas.length - 1);
        picker.setDisplayedValues(datas, prefix, suffix);
    }

    private String[] getGenderData() {
        return new String[]{this.mContext.getString(R.string.female), this.mContext.getString(R.string.male)};
    }

    private String[] getHeightData() {
        String[] strArr = new String[66];
        for (int i = 200; i >= MIN_HIGH; i--) {
            strArr[200 - i] = i + "";
        }
        return strArr;
    }

    private String[] getWeightData() {
        String[] strArr = new String[91];
        for (int i = 120; i >= 30; i--) {
            strArr[120 - i] = i + "";
        }
        return strArr;
    }

    @Override // com.xiaopeng.xui.app.XDialog
    public XDialog setNegativeButton(CharSequence text, XDialogInterface.OnClickListener listener) {
        return super.setNegativeButton(text, listener);
    }

    @Override // com.xiaopeng.xui.app.XDialog
    public XDialog setPositiveButton(CharSequence text, XDialogInterface.OnClickListener listener) {
        return super.setPositiveButton(text, listener);
    }

    public void setNegativeButtonText(String text) {
        setNegativeButton(text, this);
    }

    public void setPositiveButtonText(String text) {
        setPositiveButton(text, this);
    }

    public int[] getPosition() {
        String charSequence = this.mGenderPicker.getDisplayedValueForCurrentSelection().toString();
        String charSequence2 = this.mWeightPicker.getDisplayedValueForCurrentSelection().toString();
        String charSequence3 = this.mHeightPicker.getDisplayedValueForCurrentSelection().toString();
        int i = (charSequence == null || !charSequence.equals(this.mContext.getString(R.string.female))) ? 1 : 0;
        int parseInt = charSequence3 == null ? 170 : Integer.parseInt(charSequence3.substring(0, charSequence3.length()));
        int parseInt2 = charSequence2 == null ? 60 : Integer.parseInt(charSequence2.substring(0, charSequence2.length()));
        int[] iArr = {i, parseInt, parseInt2};
        LogUtils.d(TAG, " gender=" + i + " weight=" + parseInt2 + " height=" + parseInt);
        return iArr;
    }

    public void setDefaultValue(int gender, int height, int weight) {
        LogUtils.d(TAG, "setDefaultValue gender=" + gender + " height=" + height + " weight=" + weight);
        this.mGenderPicker.setValue(gender);
        this.mHeightPicker.setValue(200 - height);
        this.mWeightPicker.setValue(120 - weight);
    }
}
