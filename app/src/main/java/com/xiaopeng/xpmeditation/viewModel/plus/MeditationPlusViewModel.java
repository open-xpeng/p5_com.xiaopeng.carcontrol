package com.xiaopeng.xpmeditation.viewModel.plus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.carcontrol.helper.NotificationHelper;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.xpmeditation.model.MeditationBean;
import com.xiaopeng.xpmeditation.model.MeditationItemBeanPlus;
import com.xiaopeng.xpmeditation.model.plus.MeditationPlusModel;
import com.xiaopeng.xpmeditation.util.TimeUtil;
import com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel;
import com.xiaopeng.xpmeditation.viewModel.plus.MediaPlayerController;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class MeditationPlusViewModel implements IMeditationViewModel, MediaPlayerController.AudioStatusListener {
    private static final int INVALID = -1;
    private static final String TAG = "MeditationPlusViewModel";
    protected List<MeditationItemBeanPlus> mDataList;
    private boolean mHasRelease;
    private boolean mPsnProcess;
    protected List<MeditationBean.DataBean.ListBeanPlus> mMusicList = new ArrayList();
    protected int mPlayingIndex = -1;
    protected int mPlayState = -1;
    private final MutableLiveData<Integer> mPlayStateData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mPlayIndexData = new MutableLiveData<>();
    private LiveData<List<MeditationBean.DataBean.ListBeanPlus>> mMeditationData = new MutableLiveData();
    private final MutableLiveData<Boolean> mPsnStateData = new MutableLiveData<>();
    private MediaPlayerController mMediaPlayerController = new MediaPlayerController();

    public List<MeditationItemBeanPlus> getMusicList() {
        List<MeditationItemBeanPlus> list = this.mDataList;
        if (list != null) {
            return list;
        }
        this.mDataList = new ArrayList();
        List<MeditationBean.DataBean.ListBeanPlus> musicList = MeditationPlusModel.getInstance().getMusicList();
        int lastPlayIndex = getLastPlayIndex();
        int i = 0;
        while (i < musicList.size()) {
            MeditationItemBeanPlus meditationItemBeanPlus = new MeditationItemBeanPlus();
            meditationItemBeanPlus.setData(musicList.get(i));
            meditationItemBeanPlus.setSelected(i == lastPlayIndex);
            this.mDataList.add(meditationItemBeanPlus);
            i++;
        }
        return this.mDataList;
    }

    public LiveData<List<MeditationBean.DataBean.ListBeanPlus>> getMeditationData() {
        return this.mMeditationData;
    }

    public LiveData<Boolean> getPsnStatusData() {
        return this.mPsnStateData;
    }

    public void pauseOrContinuePsnMeditation() {
        setPsnProcessStatus(!this.mPsnProcess);
    }

    public void setPsnProcessStatus(boolean isEnable) {
        boolean z = this.mPsnProcess;
        boolean z2 = this.mPlayState == 2 && isEnable;
        this.mPsnProcess = z2;
        if (z != z2) {
            this.mPsnStateData.postValue(Boolean.valueOf(isEnable));
        }
        if (this.mPsnProcess) {
            NotificationHelper.getInstance().showToast(R.string.medittaion_psn_enable_msg, 1);
        }
    }

    public boolean getPsnProcessStatus() {
        return this.mPsnProcess;
    }

    public static String[] parseTime(long time) {
        return TimeUtil.parseTimeWithGMT(time, TimeUtil.DATE_FORMAT_EMDHM_THIS_YEAR).split(" ");
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void init() {
        LogUtils.i(TAG, "init ===", false);
        this.mHasRelease = false;
        this.mPlayingIndex = getLastPlayIndex();
        this.mMediaPlayerController.addListener(this);
        this.mMediaPlayerController.initPlayer();
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void release() {
        LogUtils.i(TAG, "release ===", false);
        if (this.mHasRelease) {
            return;
        }
        this.mHasRelease = true;
        this.mPlayingIndex = -1;
        this.mPlayState = -1;
        this.mMusicList.clear();
        List<MeditationItemBeanPlus> list = this.mDataList;
        if (list != null) {
            list.clear();
            this.mDataList = null;
        }
        this.mPsnProcess = false;
        this.mPsnStateData.postValue(null);
        this.mPlayStateData.postValue(null);
        this.mPlayIndexData.postValue(null);
        SpeechHelper.getInstance().stop();
        releasePlayModel();
        MeditationPlusModel.getInstance().clearMusicList();
        this.mMediaPlayerController.removeListener(this);
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void playOrPause() {
        LogUtils.i(TAG, "playOrPause", false);
        this.mMediaPlayerController.playOrPause();
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void play() {
        LogUtils.i(TAG, "play mPlayingIndex = " + this.mPlayingIndex, false);
        prepare(this.mPlayingIndex);
    }

    public void playStartMusic(String source) {
        this.mMediaPlayerController.play(source, false, false);
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void pause() {
        LogUtils.i(TAG, "pause mPlayingIndex = " + this.mPlayingIndex, false);
        this.mMediaPlayerController.pause();
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void playNext() {
        LogUtils.i(TAG, "next mPlayingIndex = " + this.mPlayingIndex, false);
        int i = this.mPlayingIndex + 1;
        prepare(i < this.mMusicList.size() ? i : 0);
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void playPre() {
        LogUtils.i(TAG, "prev mPlayingIndex = " + this.mPlayingIndex, false);
        int i = this.mPlayingIndex - 1;
        if (i < 0) {
            i = this.mMusicList.size() - 1;
        }
        prepare(i);
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void play(int index) {
        play(MeditationPlusModel.getInstance().getMusicList(), index);
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public int getLastPlayIndex() {
        return SharedPreferenceUtil.getMeditationLastPlayIndex();
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void setLastPlayIndex(int index) {
        SharedPreferenceUtil.setMeditationLastPlayIndex(index);
    }

    private void releasePlayModel() {
        LogUtils.i(TAG, "releasePlayModel ===");
        this.mMediaPlayerController.release();
    }

    private void play(List<MeditationBean.DataBean.ListBeanPlus> list, int playIndex) {
        if (list == null || list.size() == 0) {
            return;
        }
        playIndex = (playIndex < 0 || playIndex >= list.size()) ? 0 : 0;
        this.mMusicList.clear();
        this.mMusicList.addAll(list);
        prepare(playIndex);
    }

    private void prepare(int index) {
        LogUtils.i(TAG, "prepare mPlayingIndex = " + index, false);
        List<MeditationBean.DataBean.ListBeanPlus> list = this.mMusicList;
        if (list == null || list.size() == 0) {
            LogUtils.i(TAG, "prepare: list is empty =========", false);
            return;
        }
        if (index != this.mPlayingIndex && index != -1) {
            handlePlayIndexChange(index);
        }
        if (index == -1) {
            this.mPlayingIndex = 0;
        } else {
            this.mPlayingIndex = index;
        }
        SharedPreferenceUtil.setMeditationLastPlayIndex(this.mPlayingIndex);
        MeditationBean.DataBean.ListBeanPlus listBeanPlus = this.mMusicList.get(this.mPlayingIndex);
        if (listBeanPlus != null) {
            this.mMediaPlayerController.play(listBeanPlus.getListenUrl());
        }
    }

    private void changeState(int state) {
        LogUtils.d(TAG, "change state to : " + state + ", current state: " + this.mPlayState);
        if (state != this.mPlayState) {
            if (state != 2) {
                setPsnProcessStatus(false);
            }
            handlePlayStateChange(state);
        }
        this.mPlayState = state;
    }

    private void handlePlayIndexChange(int index) {
        if (this.mDataList == null) {
            return;
        }
        int i = 0;
        while (i < this.mDataList.size()) {
            this.mDataList.get(i).setSelected(index == i);
            i++;
        }
        this.mPlayIndexData.postValue(Integer.valueOf(index));
        LogUtils.d(TAG, "handlePlayIndexChange: " + index);
    }

    private void handlePlayStateChange(int state) {
        this.mPlayStateData.postValue(Integer.valueOf(state));
    }

    public MutableLiveData<Integer> getPlayIndexData() {
        return this.mPlayIndexData;
    }

    public MutableLiveData<Integer> getPlayStateData() {
        return this.mPlayStateData;
    }

    public boolean isPlaying() {
        return this.mMediaPlayerController.isPlaying();
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.plus.MediaPlayerController.AudioStatusListener
    public void onStatusChange(int status) {
        changeState(status);
    }
}
