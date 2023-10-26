package com.xiaopeng.xui.widget.timepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.icu.util.Calendar;
import android.os.Parcelable;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.widget.XNumberPicker;
import com.xiaopeng.xui.widget.timepicker.XTimePicker;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes2.dex */
public class XTimePickerSpinnerDelegate extends XTimePicker.AbstractTimePickerDelegate {
    private static final boolean DEFAULT_ENABLED_STATE = true;
    private final XNumberPicker mHourSpinner;
    private boolean mIsEnabled;
    private final XNumberPicker mMinuteSpinner;
    private final Calendar mTempCalendar;

    public XTimePickerSpinnerDelegate(XTimePicker xTimePicker, Context context, AttributeSet attributeSet, int i, int i2) {
        super(xTimePicker, context);
        this.mIsEnabled = true;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, R.styleable.XTimePicker, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(R.styleable.XTimePicker_tp_xTimePickerLayout, R.layout.x_time_picker_layout);
        obtainStyledAttributes.recycle();
        LayoutInflater.from(this.mContext).inflate(resourceId, (ViewGroup) this.mDelegator, true).setSaveFromParentEnabled(false);
        XNumberPicker xNumberPicker = (XNumberPicker) xTimePicker.findViewById(R.id.hour);
        this.mHourSpinner = xNumberPicker;
        xNumberPicker.setMinValue(0);
        xNumberPicker.setMaxValue(23);
        xNumberPicker.setOnLongPressUpdateInterval(100L);
        String[] strArr = new String[24];
        for (int i3 = 0; i3 < 24; i3++) {
            strArr[i3] = context.getResources().getString(R.string.x_time_picker_hour, Integer.valueOf(i3));
        }
        this.mHourSpinner.setDisplayedValues(strArr);
        this.mHourSpinner.setOnValueChangedListener(new XNumberPicker.OnValueChangeListener() { // from class: com.xiaopeng.xui.widget.timepicker.XTimePickerSpinnerDelegate.1
            @Override // com.xiaopeng.xui.widget.XNumberPicker.OnValueChangeListener
            public void onValueChange(XNumberPicker xNumberPicker2, int i4, int i5) {
                XTimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        XNumberPicker xNumberPicker2 = (XNumberPicker) this.mDelegator.findViewById(R.id.minute);
        this.mMinuteSpinner = xNumberPicker2;
        xNumberPicker2.setMinValue(0);
        xNumberPicker2.setMaxValue(59);
        xNumberPicker2.setOnLongPressUpdateInterval(100L);
        xNumberPicker2.setFormatter(XNumberPicker.getTwoDigitFormatter());
        String[] strArr2 = new String[60];
        for (int i4 = 0; i4 < 60; i4++) {
            strArr2[i4] = context.getResources().getString(R.string.x_time_picker_minute, Integer.valueOf(i4));
        }
        this.mMinuteSpinner.setDisplayedValues(strArr2);
        this.mMinuteSpinner.setOnValueChangedListener(new XNumberPicker.OnValueChangeListener() { // from class: com.xiaopeng.xui.widget.timepicker.XTimePickerSpinnerDelegate.2
            @Override // com.xiaopeng.xui.widget.XNumberPicker.OnValueChangeListener
            public void onValueChange(XNumberPicker xNumberPicker3, int i5, int i6) {
                int minValue = XTimePickerSpinnerDelegate.this.mMinuteSpinner.getMinValue();
                int maxValue = XTimePickerSpinnerDelegate.this.mMinuteSpinner.getMaxValue();
                if (i5 == maxValue && i6 == minValue) {
                    XTimePickerSpinnerDelegate.this.mHourSpinner.setValue(XTimePickerSpinnerDelegate.this.mHourSpinner.getValue() + 1);
                } else if (i5 == minValue && i6 == maxValue) {
                    XTimePickerSpinnerDelegate.this.mHourSpinner.setValue(XTimePickerSpinnerDelegate.this.mHourSpinner.getValue() - 1);
                }
                XTimePickerSpinnerDelegate.this.onTimeChanged();
            }
        });
        Calendar calendar = Calendar.getInstance(this.mLocale);
        this.mTempCalendar = calendar;
        setHour(calendar.get(11));
        setMinute(calendar.get(12));
        if (!isEnabled()) {
            setEnabled(false);
        }
        if (this.mDelegator.getImportantForAccessibility() == 0) {
            this.mDelegator.setImportantForAccessibility(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onTimeChanged() {
        this.mDelegator.sendAccessibilityEvent(4);
        if (this.mOnTimeChangedListener != null) {
            this.mOnTimeChangedListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
        if (this.mAutoFillChangeListener != null) {
            this.mAutoFillChangeListener.onTimeChanged(this.mDelegator, getHour(), getMinute());
        }
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public void setHour(int i) {
        setCurrentHour(i, true);
    }

    private void setCurrentHour(int i, boolean z) {
        if (i == getHour()) {
            return;
        }
        resetAutofilledValue();
        this.mHourSpinner.setValue(i);
        if (z) {
            onTimeChanged();
        }
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public int getHour() {
        return this.mHourSpinner.getValue();
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public void setMinute(int i) {
        setCurrentMinute(i, true);
    }

    private void setCurrentMinute(int i, boolean z) {
        if (i == getMinute()) {
            return;
        }
        resetAutofilledValue();
        this.mMinuteSpinner.setValue(i);
        if (z) {
            onTimeChanged();
        }
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public int getMinute() {
        return this.mMinuteSpinner.getValue();
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public void setDate(int i, int i2) {
        setCurrentHour(i, false);
        setCurrentMinute(i2, false);
        onTimeChanged();
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public void setEnabled(boolean z) {
        this.mMinuteSpinner.setEnabled(z);
        this.mHourSpinner.setEnabled(z);
        this.mIsEnabled = z;
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public int getBaseline() {
        return this.mHourSpinner.getBaseline();
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public Parcelable onSaveInstanceState(Parcelable parcelable) {
        return new XTimePicker.AbstractTimePickerDelegate.SavedState(parcelable, getHour(), getMinute());
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof XTimePicker.AbstractTimePickerDelegate.SavedState) {
            XTimePicker.AbstractTimePickerDelegate.SavedState savedState = (XTimePicker.AbstractTimePickerDelegate.SavedState) parcelable;
            setHour(savedState.getHour());
            setMinute(savedState.getMinute());
        }
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        this.mTempCalendar.set(11, getHour());
        this.mTempCalendar.set(12, getMinute());
        accessibilityEvent.getText().add(DateUtils.formatDateTime(this.mContext, this.mTempCalendar.getTimeInMillis(), 129));
    }
}
