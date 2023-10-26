package com.xiaopeng.carcontrol.view.speech;

import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.SeatVentLevel;
import com.xiaopeng.speech.vui.actor.BaseVuiEventActor;
import com.xiaopeng.speech.vui.utils.VuiUtils;
import com.xiaopeng.vui.commons.model.VuiElement;
import com.xiaopeng.vui.commons.model.VuiEvent;

/* loaded from: classes2.dex */
public class SeatAirVuiEventActor extends BaseVuiEventActor {
    private String action;
    private boolean mIsDrv;
    private HvacViewModel mViewModel;
    private VuiEvent vuiEvent;

    public SeatAirVuiEventActor(HvacViewModel viewModel, VuiEvent vuiEvent, boolean isDrv) {
        this.mViewModel = viewModel;
        this.vuiEvent = vuiEvent;
        this.mIsDrv = isDrv;
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
        if (str.equals(VuiActions.SETCHECK)) {
            VuiEvent vuiEvent = this.vuiEvent;
            if (((Boolean) vuiEvent.getEventValue(vuiEvent)).booleanValue()) {
                if (this.mIsDrv) {
                    this.mViewModel.setHvacSeatVentLevel(getSeatAirLevelByValue(3));
                } else {
                    this.mViewModel.setHvacPsnSeatVentLevel(getSeatAirLevelByValue(3));
                }
            } else if (this.mIsDrv) {
                this.mViewModel.setHvacSeatVentLevel(getSeatAirLevelByValue(-1));
            } else {
                this.mViewModel.setHvacPsnSeatVentLevel(getSeatAirLevelByValue(-1));
            }
        } else if (str.equals(VuiActions.SETVALUE)) {
            VuiEvent vuiEvent2 = this.vuiEvent;
            Object eventValue = vuiEvent2.getEventValue(vuiEvent2);
            if ((eventValue instanceof String) && VuiUtils.isNumer(String.valueOf(eventValue))) {
                i = Integer.valueOf(String.valueOf(eventValue)).intValue();
            }
            if (eventValue instanceof Double) {
                VuiEvent vuiEvent3 = this.vuiEvent;
                i = (int) ((Double) vuiEvent3.getEventValue(vuiEvent3)).doubleValue();
            }
            if (this.mIsDrv) {
                this.mViewModel.setHvacSeatVentLevel(getSeatAirLevelByValue(i));
            } else {
                this.mViewModel.setHvacPsnSeatVentLevel(getSeatAirLevelByValue(i));
            }
        }
    }

    public SeatVentLevel getSeatAirLevelByValue(int value) {
        if (value != -1) {
            if (value != 1) {
                if (value != 2) {
                    if (value == 3) {
                        return SeatVentLevel.Level3;
                    }
                    return SeatVentLevel.Level1;
                }
                return SeatVentLevel.Level2;
            }
            return SeatVentLevel.Level1;
        }
        return SeatVentLevel.Off;
    }
}
