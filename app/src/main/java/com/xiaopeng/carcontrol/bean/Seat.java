package com.xiaopeng.carcontrol.bean;

import com.google.gson.Gson;
import com.xiaopeng.carcontrol.config.CarBaseConfig;
import com.xiaopeng.carcontrol.util.LogUtils;

/* loaded from: classes.dex */
public class Seat {
    public static final int DIRECTION_DECREASE = 2;
    public static final int DIRECTION_INCREASE = 1;
    private static final int SEAT_BACKREST_MAX = 98;
    private static final int SEAT_BACKREST_MIN = 2;
    public static final int SEAT_CONTROL_MAX_PERCENT = 98;
    public static final int SEAT_CONTROL_MIN_PERCENT = 2;
    public static final int SEAT_EXHIBITION_MODE_BACKREST = 79;
    public static final int SEAT_EXHIBITION_MODE_CUSHION = 0;
    public static final int SEAT_EXHIBITION_MODE_FORE = 34;
    public static final int SEAT_EXHIBITION_MODE_HEIGHT = 50;
    public static final int SEAT_EXHIBITION_MODE_LEG = 0;
    public static final int SEAT_EXHIBITION_MODE_SEC_ROW_LEG_POS = 0;
    public static final int SEAT_EXHIBITION_MODE_SEC_ROW_TILT_POS = 100;
    private static final int SEAT_FORE_AFT_MAX = 98;
    private static final int SEAT_FORE_AFT_MIN = 2;
    private static final int SEAT_HEIGHT_MAX = 98;
    private static final int SEAT_HEIGHT_MIN = 2;
    public static final int SEAT_POS_DEVIATION = 2;
    public static final int SEAT_POS_LEG_VERTICAL_DEVIATION = 3;
    public static final int SEAT_PSN_SUPPORT_MAX = 3;
    public static final int SEAT_WELCOME_HORZ_POS = 25;
    private static final String TAG = "Seat";
    public static final int TYPE_CUSHION = 7;
    public static final int TYPE_HEADREST_HORZ = 9;
    public static final int TYPE_HEADREST_VER = 8;
    public static final int TYPE_HORZ = 1;
    public static final int TYPE_LEG = 4;
    public static final int TYPE_LUMB_HORZ = 5;
    public static final int TYPE_LUMB_VERT = 6;
    public static final int TYPE_MAX = 4;
    public static final int TYPE_TILT = 3;
    public static final int TYPE_VERT = 2;
    public static final int VIP_SEAT_TILT_DEVIATION = 4;
    private volatile int mAdjustDirection;
    private volatile int mAdjustType;
    private final boolean mIsDriverSeat;
    private volatile int mSeatCushion;
    private volatile int mSeatHeadHor;
    private volatile int mSeatHeadVer;
    private volatile int mSeatHorz;
    private volatile int mSeatLeg;
    private volatile int mSeatTilt;
    private volatile int mSeatVert;
    private volatile int mSeatZeroAngle;

    public Seat(boolean isDriverSeat) {
        this(isDriverSeat, null);
    }

    private Seat(boolean isDriverSeat, int[] locationValue) {
        this.mAdjustType = 0;
        this.mAdjustDirection = 0;
        this.mIsDriverSeat = isDriverSeat;
        if (locationValue == null || locationValue.length < 4) {
            return;
        }
        this.mSeatHorz = locationValue[0];
        this.mSeatVert = locationValue[1];
        this.mSeatTilt = locationValue[2];
        if (isDriverSeat) {
            this.mSeatLeg = locationValue[3];
        }
    }

    public String toString() {
        return new Gson().toJson(getSeatPosition());
    }

    public String toSecRowString() {
        return new Gson().toJson(getSecRowPosition());
    }

    public static Seat fromPosition(int[] position) {
        Seat seat = new Seat(true);
        if (position != null && position.length >= 4) {
            try {
                seat.mSeatHorz = position[0];
                seat.mSeatVert = position[1];
                seat.mSeatTilt = position[2];
                seat.mSeatLeg = position[3];
                if (seat.mIsDriverSeat) {
                    seat.mSeatCushion = position[4];
                }
            } catch (Exception e) {
                LogUtils.w(TAG, e.getMessage() + ", jsonString", false);
            }
        }
        return seat;
    }

    public static Seat fromSecRowPosition(int[] position) {
        Seat seat = new Seat(true);
        if (position != null && position.length >= 4) {
            try {
                seat.mSeatHorz = position[0];
                seat.mSeatZeroAngle = position[1];
                seat.mSeatTilt = position[2];
                seat.mSeatCushion = position[3];
                seat.mSeatLeg = position[4];
                seat.mSeatHeadVer = position[5];
                seat.mSeatHeadHor = position[6];
            } catch (Exception e) {
                LogUtils.w(TAG, e.getMessage() + ", jsonString", false);
            }
        }
        return seat;
    }

    public int getSeatHeadHor() {
        return this.mSeatHeadHor;
    }

    public void setSeatHeadHor(int mSeatHeadHor) {
        this.mSeatHeadHor = mSeatHeadHor;
    }

    public int getSeatHeadVer() {
        return this.mSeatHeadVer;
    }

    public void setSeatHeadVer(int mSeatHeadVer) {
        this.mSeatHeadVer = mSeatHeadVer;
    }

    public int getSeatZeroAngle() {
        return this.mSeatZeroAngle;
    }

    public void setSeatZeroAngle(int mSeatZeroAngle) {
        this.mSeatZeroAngle = mSeatZeroAngle;
    }

    public boolean isDriverSeat() {
        return this.mIsDriverSeat;
    }

    public int getSeatHorzValue() {
        return this.mSeatHorz;
    }

    public void setSeatHorzValue(int pos) {
        this.mSeatHorz = pos;
    }

    public int getSeatVertValue() {
        return this.mSeatVert;
    }

    public void setSeatVertValue(int pos) {
        this.mSeatVert = pos;
    }

    public int getSeatTiltValue() {
        return this.mSeatTilt;
    }

    public void setSeatTiltValue(int pos) {
        this.mSeatTilt = pos;
    }

    public int getSeatLegValue() {
        return this.mSeatLeg;
    }

    public void setSeatLegValue(int pos) {
        this.mSeatLeg = pos;
    }

    public int getSeatCushionValue() {
        return this.mSeatCushion;
    }

    public void setSeatCushionValue(int pos) {
        this.mSeatCushion = pos;
    }

    public int[] getSeatPosition() {
        return new int[]{this.mSeatHorz, this.mSeatVert, this.mSeatTilt, this.mSeatLeg, this.mSeatCushion};
    }

    public int[] getSecRowPosition() {
        return new int[]{this.mSeatHorz, this.mSeatZeroAngle, this.mSeatTilt, this.mSeatCushion, this.mSeatLeg, this.mSeatHeadVer, this.mSeatHeadHor};
    }

    public void setAdjustingParam(int type, int direction) {
        this.mAdjustType = type;
        this.mAdjustDirection = direction;
    }

    public void setAdjustingType(int adjustingType) {
        this.mAdjustType = adjustingType;
    }

    public int getAdjustingType() {
        return this.mAdjustType;
    }

    public int getAdjustingDirection() {
        return this.mAdjustDirection;
    }

    public boolean isSeatReachHorzBorder(int direction) {
        return direction == 1 ? this.mSeatHorz >= 98 : this.mSeatHorz <= 2;
    }

    public boolean isSeatReachVertBorder(int direction) {
        return direction == 1 ? this.mSeatVert >= 98 : this.mSeatVert <= 2;
    }

    public boolean isSeatReachTiltBorder(int direction) {
        return direction == 1 ? this.mSeatTilt <= 2 : this.mSeatTilt >= 98;
    }

    public boolean isPositionEqual(int[] position) {
        if (position == null) {
            return false;
        }
        boolean z = true;
        if (Math.abs(this.mSeatHorz - position[0]) >= 2 || Math.abs(this.mSeatVert - position[1]) >= 2 || Math.abs(this.mSeatTilt - position[2]) >= 2 || ((CarBaseConfig.getInstance().isSupportDrvLeg() && Math.abs(this.mSeatLeg - position[3]) >= 3) || (CarBaseConfig.getInstance().isSupportDrvCushion() && Math.abs(this.mSeatCushion - position[4]) >= 2))) {
            z = false;
        }
        LogUtils.d(TAG, "isDrvSeatEqualMemory, mSeatHorz: " + this.mSeatHorz + ", mSeatVert: " + this.mSeatVert + ", mSeatTilt: " + this.mSeatTilt + ", mSeatCushion: " + this.mSeatCushion + ", result: " + z, false);
        return z;
    }

    public static boolean isPositionEqual(int[] currentPosition, int[] position) {
        if (position != null && Math.abs(currentPosition[0] - position[0]) < 2 && Math.abs(currentPosition[1] - position[1]) < 2 && Math.abs(currentPosition[2] - position[2]) < 2) {
            if (!CarBaseConfig.getInstance().isSupportDrvLeg() || Math.abs(currentPosition[3] - position[3]) < 3) {
                return !CarBaseConfig.getInstance().isSupportDrvCushion() || Math.abs(currentPosition[4] - position[4]) < 2;
            }
            return false;
        }
        return false;
    }
}
