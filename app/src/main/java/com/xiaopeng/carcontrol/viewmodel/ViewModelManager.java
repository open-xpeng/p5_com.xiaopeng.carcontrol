package com.xiaopeng.carcontrol.viewmodel;

import com.xiaopeng.carcontrol.util.CarStatusUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.account.AccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.account.IAccountViewModel;
import com.xiaopeng.carcontrol.viewmodel.audio.AudioViewModel;
import com.xiaopeng.carcontrol.viewmodel.audio.IAudioViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.AvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.avas.IAvasViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.ArsViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IArsViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IMirrorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.ISeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.IWindowDoorViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.MirrorViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModel;
import com.xiaopeng.carcontrol.viewmodel.cabin.SeatViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.cabin.WindowDoorViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.carinfo.CarInfoViewModel;
import com.xiaopeng.carcontrol.viewmodel.carinfo.ICarInfoViewModel;
import com.xiaopeng.carcontrol.viewmodel.carsettings.CarBodyViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.carsettings.ICarBodyViewModel;
import com.xiaopeng.carcontrol.viewmodel.chassis.ChassisViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.chassis.IChassisViewModel;
import com.xiaopeng.carcontrol.viewmodel.ciu.CiuViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.ciu.ICiuViewModel;
import com.xiaopeng.carcontrol.viewmodel.endurance.EnduranceViewModel;
import com.xiaopeng.carcontrol.viewmodel.endurance.IEnduranceViewModel;
import com.xiaopeng.carcontrol.viewmodel.hvac.HvacViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.hvac.IHvacViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.ILluViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LampViewModel;
import com.xiaopeng.carcontrol.viewmodel.lamp.LluViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.light.AtlViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.light.IAtlViewModel;
import com.xiaopeng.carcontrol.viewmodel.locale.ILocaleViewModel;
import com.xiaopeng.carcontrol.viewmodel.locale.LocaleViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.IMeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.meter.MeterViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.IScuViewModel;
import com.xiaopeng.carcontrol.viewmodel.scu.ScuViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.service.ISelfCheckViewModel;
import com.xiaopeng.carcontrol.viewmodel.service.IServiceViewModel;
import com.xiaopeng.carcontrol.viewmodel.service.SelfCheckViewModel;
import com.xiaopeng.carcontrol.viewmodel.service.ServiceViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.sfs.ISfsViewModel;
import com.xiaopeng.carcontrol.viewmodel.sfs.SfsViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.ISpaceCapsuleViewModel;
import com.xiaopeng.carcontrol.viewmodel.space.SpaceCapsuleViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.tbox.ITboxViewModel;
import com.xiaopeng.carcontrol.viewmodel.tbox.TBoxViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.IVcuViewModel;
import com.xiaopeng.carcontrol.viewmodel.vcu.VcuViewModelFactory;
import com.xiaopeng.carcontrol.viewmodel.xpu.IXpuViewModel;
import com.xiaopeng.carcontrol.viewmodel.xpu.XpuViewModelFactory;
import com.xiaopeng.lludancemanager.viewmodel.ILluDanceViewModel;
import com.xiaopeng.lludancemanager.viewmodel.LluDanceViewModelFactory;
import com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel;
import com.xiaopeng.xpmeditation.viewModel.plus.MeditationViewModelFactory;
import java.util.Hashtable;

/* loaded from: classes2.dex */
public final class ViewModelManager {
    private static final String TAG = "ViewModelManager";
    private String mCarType;
    private final Hashtable<Class<?>, IBaseViewModel> mViewModelsCache;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static final ViewModelManager sInstance = new ViewModelManager();

        private SingleHolder() {
        }
    }

    public static ViewModelManager getInstance() {
        return SingleHolder.sInstance;
    }

    private ViewModelManager() {
        this.mViewModelsCache = new Hashtable<>();
        this.mCarType = CarStatusUtils.getNewHardwareCarType();
        LogUtils.d(TAG, "ViewModelManager init: car type: " + this.mCarType + ", cdy type: " + CarStatusUtils.getXpCduType(), false);
    }

    public String getCarType() {
        return this.mCarType;
    }

    public <T extends IBaseViewModel> T getViewModelImpl(Class<?> clazz) throws IllegalArgumentException {
        synchronized (ViewModelManager.class) {
            T t = (T) this.mViewModelsCache.get(clazz);
            if (clazz.isInstance(t)) {
                return t;
            }
            T t2 = (T) createViewModel(clazz);
            this.mViewModelsCache.put(clazz, t2);
            return t2;
        }
    }

    public <T extends IBaseViewModel> T getViewModelImplSync(Class<?> clazz) throws IllegalArgumentException {
        if (clazz == IAudioViewModel.class || clazz == ILampViewModel.class || clazz == ISeatViewModel.class) {
            SeatViewModel seatViewModel = (T) this.mViewModelsCache.get(clazz);
            if (clazz.isInstance(seatViewModel)) {
                return seatViewModel;
            }
            if (clazz == IAudioViewModel.class) {
                seatViewModel = new AudioViewModel();
            } else if (clazz == ILampViewModel.class) {
                seatViewModel = new LampViewModel();
            } else if (clazz == ISeatViewModel.class) {
                seatViewModel = new SeatViewModel();
            }
            this.mViewModelsCache.put(clazz, seatViewModel);
            return seatViewModel;
        }
        return null;
    }

    private <T extends IBaseViewModel> T createViewModel(Class<?> modelClass) {
        if (modelClass == IServiceViewModel.class) {
            return ServiceViewModelFactory.createViewModel(this.mCarType);
        }
        if (modelClass == ILampViewModel.class) {
            return new LampViewModel();
        }
        if (modelClass == ISeatViewModel.class) {
            return SeatViewModelFactory.createSeatViewModel(this.mCarType);
        }
        if (modelClass == IWindowDoorViewModel.class) {
            return WindowDoorViewModelFactory.createWindowDoorViewModel(this.mCarType);
        }
        if (modelClass == IScuViewModel.class) {
            return ScuViewModelFactory.createViewModel();
        }
        if (modelClass == IAvasViewModel.class) {
            return new AvasViewModel();
        }
        if (modelClass == IChassisViewModel.class) {
            return ChassisViewModelFactory.createChassisViewModel();
        }
        if (modelClass == ICarBodyViewModel.class) {
            return CarBodyViewModelFactory.createCarBodyViewModel(this.mCarType);
        }
        if (modelClass == IVcuViewModel.class) {
            return VcuViewModelFactory.createVcuViewModel(this.mCarType);
        }
        if (modelClass == IMeterViewModel.class) {
            return new MeterViewModel();
        }
        if (modelClass == IHvacViewModel.class) {
            return HvacViewModelFactory.createHvacViewModel();
        }
        if (modelClass == IMirrorViewModel.class) {
            return MirrorViewModelFactory.createMirrorViewModel(this.mCarType);
        }
        if (modelClass == ILluViewModel.class) {
            return LluViewModelFactory.createLluViewModel();
        }
        if (modelClass == ILluDanceViewModel.class) {
            return LluDanceViewModelFactory.createLluViewModel(this.mCarType);
        }
        if (modelClass == IAtlViewModel.class) {
            return AtlViewModelFactory.createAtlViewModel();
        }
        if (modelClass == ICiuViewModel.class) {
            return CiuViewModelFactory.createCiuViewModel();
        }
        if (modelClass == ITboxViewModel.class) {
            return new TBoxViewModel();
        }
        if (modelClass == ISelfCheckViewModel.class) {
            return new SelfCheckViewModel();
        }
        if (modelClass == IXpuViewModel.class) {
            return XpuViewModelFactory.createXpuViewModel();
        }
        if (modelClass == ICarInfoViewModel.class) {
            return new CarInfoViewModel();
        }
        if (modelClass == IAccountViewModel.class) {
            return new AccountViewModel();
        }
        if (modelClass == ISfsViewModel.class) {
            return new SfsViewModel();
        }
        if (modelClass == ISpaceCapsuleViewModel.class) {
            return SpaceCapsuleViewModelFactory.createSpaceCapsuleViewModel();
        }
        if (modelClass == IScenarioViewModel.class) {
            return new ScenarioViewModel();
        }
        if (modelClass == IAudioViewModel.class) {
            return new AudioViewModel();
        }
        if (modelClass == IMeditationViewModel.class) {
            return MeditationViewModelFactory.createMeditationViewModel();
        }
        if (modelClass == IEnduranceViewModel.class) {
            return new EnduranceViewModel();
        }
        if (modelClass == ILocaleViewModel.class) {
            return new LocaleViewModel();
        }
        if (modelClass == IArsViewModel.class) {
            return new ArsViewModel();
        }
        throw new IllegalArgumentException("Unknown view model class: " + modelClass.getSimpleName());
    }
}
