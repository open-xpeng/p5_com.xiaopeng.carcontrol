package com.xiaopeng.carcontrol.view.speech;

import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.SeatHeatLevel;
import com.xiaopeng.speech.vui.actor.BaseVuiEventActor;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;

/* loaded from: classes2.dex */
public class SeatHotVuiEventActor extends BaseVuiEventActor {
    private String action;
    private boolean isDriver;
    private boolean isFront;
    private HvacViewModel mViewModel;
    private VuiEvent vuiEvent;

    public SeatHotVuiEventActor(boolean isDriver, HvacViewModel viewModel, VuiEvent vuiEvent) {
        this(isDriver, true, viewModel, vuiEvent);
    }

    public SeatHotVuiEventActor(boolean isDriver, boolean isFront, HvacViewModel viewModel, VuiEvent vuiEvent) {
        this.mViewModel = viewModel;
        this.vuiEvent = vuiEvent;
        this.isDriver = isDriver;
        this.isFront = isFront;
        VuiElement hitVuiElement = vuiEvent.getHitVuiElement();
        if (hitVuiElement == null || hitVuiElement.resultActions == null || hitVuiElement.resultActions.isEmpty()) {
            return;
        }
        this.action = hitVuiElement.resultActions.get(0);
        vuiEvent.getEventValue(vuiEvent);
    }

    @Override // com.xiaopeng.speech.vui.actor.IVuiEventActor
    public void execute() {
        String str = this.action;
        str.hashCode();
        int i = -1;
        if (!str.equals(VuiActions.SETCHECK)) {
            if (str.equals(VuiActions.SETVALUE)) {
                VuiEvent vuiEvent = this.vuiEvent;
                Object eventValue = vuiEvent.getEventValue(vuiEvent);
                if ((eventValue instanceof String) && VuiUtils.isNumer(String.valueOf(eventValue))) {
                    i = Integer.valueOf(String.valueOf(eventValue)).intValue();
                }
                if (eventValue instanceof Double) {
                    VuiEvent vuiEvent2 = this.vuiEvent;
                    i = (int) ((Double) vuiEvent2.getEventValue(vuiEvent2)).doubleValue();
                }
                if (this.isFront) {
                    if (this.isDriver) {
                        this.mViewModel.setHvacSeatHeatLevel(getSeatHeatLevelByValue(i));
                        return;
                    } else {
                        this.mViewModel.setHvacPsnSeatHeatLevel(getSeatHeatLevelByValue(i));
                        return;
                    }
                } else if (this.isDriver) {
                    this.mViewModel.setRLSeatHeatLevel(getSeatHeatLevelByValue(i).ordinal());
                    return;
                } else {
                    this.mViewModel.setRRSeatHeatLevel(getSeatHeatLevelByValue(i).ordinal());
                    return;
                }
            }
            return;
        }
        VuiEvent vuiEvent3 = this.vuiEvent;
        if (((Boolean) vuiEvent3.getEventValue(vuiEvent3)).booleanValue()) {
            if (this.isFront) {
                if (this.isDriver) {
                    this.mViewModel.setHvacSeatHeatLevel(getSeatHeatLevelByValue(3));
                } else {
                    this.mViewModel.setHvacPsnSeatHeatLevel(getSeatHeatLevelByValue(3));
                }
            } else if (this.isDriver) {
                this.mViewModel.setRLSeatHeatLevel(3);
            } else {
                this.mViewModel.setRRSeatHeatLevel(3);
            }
        } else if (this.isFront) {
            if (this.isDriver) {
                this.mViewModel.setHvacSeatHeatLevel(getSeatHeatLevelByValue(-1));
            } else {
                this.mViewModel.setHvacPsnSeatHeatLevel(getSeatHeatLevelByValue(-1));
            }
        } else if (this.isDriver) {
            this.mViewModel.setRLSeatHeatLevel(0);
        } else {
            this.mViewModel.setRRSeatHeatLevel(0);
        }
    }

    public SeatHeatLevel getSeatHeatLevelByValue(int value) {
        if (value != -1) {
            if (value != 1) {
                if (value != 2) {
                    if (value == 3) {
                        return SeatHeatLevel.Level3;
                    }
                    return SeatHeatLevel.Level1;
                }
                return SeatHeatLevel.Level2;
            }
            return SeatHeatLevel.Level1;
        }
        return SeatHeatLevel.Off;
    }
}
