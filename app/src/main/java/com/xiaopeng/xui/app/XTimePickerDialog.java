package com.xiaopeng.xui.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import com.xiaopeng.xpui.R;
import com.xiaopeng.xui.app.XDialogInterface;
import com.xiaopeng.xui.widget.timepicker.XTimePicker;

/* loaded from: classes2.dex */
public class XTimePickerDialog extends XDialog implements XTimePicker.OnTimeChangedListener, XDialogInterface.OnClickListener {
    private final XTimePicker mTimePicker;
    private final OnXTimeSetListener mTimeSetListener;

    /* loaded from: classes2.dex */
    public interface OnXTimeSetListener {
        void onTimeSet(XTimePicker xTimePicker, int i, int i2);
    }

    @Override // com.xiaopeng.xui.widget.timepicker.XTimePicker.OnTimeChangedListener
    public void onTimeChanged(XTimePicker xTimePicker, int i, int i2) {
    }

    public XTimePickerDialog(Context context, OnXTimeSetListener onXTimeSetListener, int i, int i2) {
        this(context, 0, onXTimeSetListener, i, i2);
    }

    public XTimePickerDialog(Context context, int i, OnXTimeSetListener onXTimeSetListener, int i2, int i3) {
        super(context, resolveDialogTheme(context, i));
        this.mTimeSetListener = onXTimeSetListener;
        View inflate = LayoutInflater.from(context).inflate(R.layout.x_time_picker, getContentView(), false);
        setCustomView(inflate, false);
        setPositiveButton(" ", this);
        setNegativeButton(" ", this);
        XTimePicker xTimePicker = (XTimePicker) inflate.findViewById(R.id.x_time_picker);
        this.mTimePicker = xTimePicker;
        xTimePicker.setCurrentHour(Integer.valueOf(i2));
        xTimePicker.setCurrentMinute(Integer.valueOf(i3));
        xTimePicker.setOnTimeChangedListener(this);
    }

    static int resolveDialogTheme(Context context, int i) {
        return i == 0 ? R.style.XDialogView_Large : i;
    }

    public XTimePicker getXTimePicker() {
        return this.mTimePicker;
    }

    public void setPositiveButtonText(String str) {
        setPositiveButton(str, this);
    }

    @Override // com.xiaopeng.xui.app.XDialog
    @Deprecated
    public XDialog setPositiveButton(CharSequence charSequence, XDialogInterface.OnClickListener onClickListener) {
        return super.setPositiveButton(charSequence, this);
    }

    public void setNegativeButtonText(String str) {
        setNegativeButton(str, this);
    }

    @Override // com.xiaopeng.xui.app.XDialog
    @Deprecated
    public XDialog setNegativeButton(CharSequence charSequence, XDialogInterface.OnClickListener onClickListener) {
        return super.setNegativeButton(charSequence, this);
    }

    public void updateTime(int i, int i2) {
        this.mTimePicker.setCurrentHour(Integer.valueOf(i));
        this.mTimePicker.setCurrentMinute(Integer.valueOf(i2));
    }

    @Override // com.xiaopeng.xui.app.XDialogInterface.OnClickListener
    public void onClick(XDialog xDialog, int i) {
        OnXTimeSetListener onXTimeSetListener;
        if (i == -2) {
            super.getDialog().cancel();
        } else if (i == -1 && (onXTimeSetListener = this.mTimeSetListener) != null) {
            XTimePicker xTimePicker = this.mTimePicker;
            onXTimeSetListener.onTimeSet(xTimePicker, xTimePicker.getCurrentHour().intValue(), this.mTimePicker.getCurrentMinute().intValue());
        }
    }
}
