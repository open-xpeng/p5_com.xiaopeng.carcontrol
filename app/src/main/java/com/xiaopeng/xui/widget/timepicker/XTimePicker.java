package com.xiaopeng.xui.widget.timepicker;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import androidx.core.math.MathUtils;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.utils.XLogUtils;
import com.xiaopeng.xui.widget.XFrameLayout;
import java.util.Locale;

/* loaded from: classes2.dex */
public class XTimePicker extends XFrameLayout {
    private static final String LOG_TAG = "XTimePicker";
    private final XTimePickerDelegate mDelegate;

    /* loaded from: classes2.dex */
    public interface OnTimeChangedListener {
        void onTimeChanged(XTimePicker xTimePicker, int i, int i2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface XTimePickerDelegate {
        void autofill(AutofillValue autofillValue);

        boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        AutofillValue getAutofillValue();

        int getBaseline();

        int getHour();

        int getMinute();

        boolean isEnabled();

        void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent);

        void onRestoreInstanceState(Parcelable parcelable);

        Parcelable onSaveInstanceState(Parcelable parcelable);

        void setAutoFillChangeListener(OnTimeChangedListener onTimeChangedListener);

        void setDate(int i, int i2);

        void setEnabled(boolean z);

        void setHour(int i);

        void setMinute(int i);

        void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener);
    }

    public XTimePicker(Context context) {
        this(context, null);
    }

    public XTimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public XTimePicker(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.XTimePicker);
    }

    public XTimePicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        if (getImportantForAutofill() == 0) {
            setImportantForAutofill(1);
        }
        XTimePickerSpinnerDelegate xTimePickerSpinnerDelegate = new XTimePickerSpinnerDelegate(this, context, attributeSet, i, i2);
        this.mDelegate = xTimePickerSpinnerDelegate;
        xTimePickerSpinnerDelegate.setAutoFillChangeListener(new OnTimeChangedListener() { // from class: com.xiaopeng.xui.widget.timepicker.XTimePicker.1
            @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.OnTimeChangedListener
            public void onTimeChanged(XTimePicker xTimePicker, int i3, int i4) {
                AutofillManager autofillManager = (AutofillManager) XTimePicker.this.getContext().getSystemService(AutofillManager.class);
                if (autofillManager != null) {
                    autofillManager.notifyValueChanged(XTimePicker.this);
                }
            }
        });
    }

    public void setHour(int i) {
        this.mDelegate.setHour(MathUtils.clamp(i, 0, 23));
    }

    public int getHour() {
        return this.mDelegate.getHour();
    }

    public void setMinute(int i) {
        this.mDelegate.setMinute(MathUtils.clamp(i, 0, 59));
    }

    public int getMinute() {
        return this.mDelegate.getMinute();
    }

    public void setCurrentHour(Integer num) {
        setHour(num.intValue());
    }

    public Integer getCurrentHour() {
        return Integer.valueOf(getHour());
    }

    public void setCurrentMinute(Integer num) {
        setMinute(num.intValue());
    }

    public Integer getCurrentMinute() {
        return Integer.valueOf(getMinute());
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.mDelegate.setOnTimeChangedListener(onTimeChangedListener);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mDelegate.setEnabled(z);
    }

    @Override // android.view.View
    public boolean isEnabled() {
        return this.mDelegate.isEnabled();
    }

    @Override // android.view.View
    public int getBaseline() {
        return this.mDelegate.getBaseline();
    }

    @Override // android.view.View
    protected Parcelable onSaveInstanceState() {
        return this.mDelegate.onSaveInstanceState(super.onSaveInstanceState());
    }

    @Override // android.view.View
    protected void onRestoreInstanceState(Parcelable parcelable) {
        View.BaseSavedState baseSavedState = (View.BaseSavedState) parcelable;
        super.onRestoreInstanceState(baseSavedState.getSuperState());
        this.mDelegate.onRestoreInstanceState(baseSavedState);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return XTimePicker.class.getName();
    }

    /* loaded from: classes2.dex */
    static abstract class AbstractTimePickerDelegate implements XTimePickerDelegate {
        protected OnTimeChangedListener mAutoFillChangeListener;
        private long mAutofilledValue;
        protected final Context mContext;
        protected final XTimePicker mDelegator;
        protected final Locale mLocale;
        protected OnTimeChangedListener mOnTimeChangedListener;

        public AbstractTimePickerDelegate(XTimePicker xTimePicker, Context context) {
            this.mDelegator = xTimePicker;
            this.mContext = context;
            this.mLocale = context.getResources().getConfiguration().getLocales().get(0);
        }

        @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
        public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
            this.mOnTimeChangedListener = onTimeChangedListener;
        }

        @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
        public void setAutoFillChangeListener(OnTimeChangedListener onTimeChangedListener) {
            this.mAutoFillChangeListener = onTimeChangedListener;
        }

        @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
        public final void autofill(AutofillValue autofillValue) {
            if (autofillValue == null || !autofillValue.isDate()) {
                XLogUtils.w(XTimePicker.LOG_TAG, autofillValue + " could not be autofilled into " + this);
                return;
            }
            long dateValue = autofillValue.getDateValue();
            Calendar calendar = Calendar.getInstance(this.mLocale);
            calendar.setTimeInMillis(dateValue);
            setDate(calendar.get(11), calendar.get(12));
            this.mAutofilledValue = dateValue;
        }

        @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.XTimePickerDelegate
        public final AutofillValue getAutofillValue() {
            long j = this.mAutofilledValue;
            if (j != 0) {
                return AutofillValue.forDate(j);
            }
            Calendar calendar = Calendar.getInstance(this.mLocale);
            calendar.set(11, getHour());
            calendar.set(12, getMinute());
            return AutofillValue.forDate(calendar.getTimeInMillis());
        }

        /* JADX INFO: Access modifiers changed from: protected */
        public void resetAutofilledValue() {
            this.mAutofilledValue = 0L;
        }

        /* loaded from: classes2.dex */
        protected static class SavedState extends View.BaseSavedState {
            private final int mCurrentItemShowing;
            private final int mHour;
            private final int mMinute;

            public SavedState(Parcelable parcelable, int i, int i2) {
                this(parcelable, i, i2, 0);
            }

            public SavedState(Parcelable parcelable, int i, int i2, int i3) {
                super(parcelable);
                this.mHour = i;
                this.mMinute = i2;
                this.mCurrentItemShowing = i3;
            }

            private SavedState(Parcel parcel) {
                super(parcel);
                this.mHour = parcel.readInt();
                this.mMinute = parcel.readInt();
                this.mCurrentItemShowing = parcel.readInt();
            }

            public int getHour() {
                return this.mHour;
            }

            public int getMinute() {
                return this.mMinute;
            }

            @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.mHour);
                parcel.writeInt(this.mMinute);
                parcel.writeInt(this.mCurrentItemShowing);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i) {
        viewStructure.setAutofillId(getAutofillId());
        onProvideAutofillStructure(viewStructure, i);
    }

    @Override // android.view.View
    public void autofill(AutofillValue autofillValue) {
        if (isEnabled()) {
            this.mDelegate.autofill(autofillValue);
        }
    }

    @Override // android.view.View
    public AutofillValue getAutofillValue() {
        if (isEnabled()) {
            return this.mDelegate.getAutofillValue();
        }
        return null;
    }
}
