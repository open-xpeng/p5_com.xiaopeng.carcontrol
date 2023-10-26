package com.xiaopeng.carcontrol.view;

import com.xiaopeng.carcontrol.view.BaseSeatControlView;

/* loaded from: classes2.dex */
public interface ISeatView {
    boolean needPressedStatus();

    void setControlModeChair();

    void setControlModeCushion();

    void setControlModeLeg();

    void setControlModeLumbar();

    void setHorPosition(int pos);

    void setLegVerPosition(int pos);

    void setOnButtonClickListener(BaseSeatControlView.OnButtonClickListener l);

    void setOnButtonTouchListener(BaseSeatControlView.OnButtonTouchListener l);

    void setSupportMove(boolean support);

    void setTiltPosition(int pos);

    void setVerPosition(int pos);

    void stopControl();
}
