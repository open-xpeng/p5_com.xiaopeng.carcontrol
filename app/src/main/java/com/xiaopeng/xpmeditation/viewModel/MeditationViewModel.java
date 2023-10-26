package com.xiaopeng.xpmeditation.viewModel;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.download.DownloadService;
import com.xiaopeng.carcontrol.download.DownloadServiceImpl;
import com.xiaopeng.carcontrol.download.RequestInfo;
import com.xiaopeng.carcontrol.download.listener.DownloadListener;
import com.xiaopeng.carcontrol.helper.SpeechHelper;
import com.xiaopeng.carcontrol.model.SharedPreferenceUtil;
import com.xiaopeng.carcontrol.util.GsonUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import com.xiaopeng.carcontrolmodule.R;
import com.xiaopeng.speech.speechwidget.ListWidget;
import com.xiaopeng.xpmeditation.model.MeditationBean;
import com.xiaopeng.xpmeditation.model.MeditationItemBean;
import com.xiaopeng.xpmeditation.model.MeditationModel;
import com.xiaopeng.xpmeditation.moreless.IMorelessCallback;
import com.xiaopeng.xpmeditation.moreless.MorelessHelper;
import com.xiaopeng.xpmeditation.moreless.MorelessInfo;
import com.xiaopeng.xpmeditation.moreless.MorelessToken;
import com.xiaopeng.xpmeditation.viewModel.MeditationViewModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

/* loaded from: classes2.dex */
public class MeditationViewModel implements IMeditationViewModel {
    private static final int INVALID = -1;
    private static final String TAG = "MeditationViewModel";
    private DownloadServiceImpl downloadService;
    private List<MeditationItemBean> mDataList;
    private boolean mHasRelease;
    private boolean mPnsState;
    private List<MeditationBean.DataBean.ListBean> mMusicList = new ArrayList();
    private int mPlayingIndex = -1;
    private int mPlayState = -1;
    private MediaPlayer mPlayer = null;
    private final MutableLiveData<Integer> mPlayStateData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mPlayIndexData = new MutableLiveData<>();
    private final MutableLiveData<List<MorelessInfo>> mMorelessInfoData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mPnsPlayData = new MutableLiveData<>();
    private List<MorelessInfo> morelessInfoList = new ArrayList();
    private volatile boolean mDownloadServiceBound = false;
    private final ServiceConnection downloadConnection = new ServiceConnection() { // from class: com.xiaopeng.xpmeditation.viewModel.MeditationViewModel.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            MeditationViewModel.this.downloadService = (DownloadServiceImpl) service;
            LogUtils.i(MeditationViewModel.TAG, "Connect download service.");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i(MeditationViewModel.TAG, "Disconnect download service.");
        }
    };
    private final AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() { // from class: com.xiaopeng.xpmeditation.viewModel.-$$Lambda$MeditationViewModel$ETsp1U_HBOHtQzP-S4LgD3an_lc
        @Override // android.media.AudioManager.OnAudioFocusChangeListener
        public final void onAudioFocusChange(int i) {
            MeditationViewModel.this.lambda$new$0$MeditationViewModel(i);
        }
    };
    private AudioManager mAudioManager = (AudioManager) App.getInstance().getApplicationContext().getSystemService(ListWidget.EXTRA_TYPE_AUDIO);

    public /* synthetic */ void lambda$new$0$MeditationViewModel(int focusChange) {
        LogUtils.d(TAG, "audio focus change...  focus change type: " + focusChange, false);
        if (focusChange == -2 || focusChange == -1) {
            pauseMusic();
        } else if (focusChange != 1) {
        } else {
            resumeMusic();
        }
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void init() {
        this.mHasRelease = false;
        initPlayer();
        stopMedia();
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
        List<MeditationItemBean> list = this.mDataList;
        if (list != null) {
            list.clear();
            this.mDataList = null;
        }
        this.mPlayStateData.postValue(null);
        this.mPlayIndexData.postValue(null);
        this.mPnsPlayData.postValue(null);
        SpeechHelper.getInstance().stop();
        releasePlayModel();
        MeditationModel.getInstance().clearMusicList();
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void playOrPause() {
        if (isMusicPlaying()) {
            pause();
        } else {
            play();
        }
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void play() {
        LogUtils.i(TAG, "play mPlayingIndex = " + this.mPlayingIndex, false);
        if (!requestMusicFocus()) {
            changeState(3);
            LogUtils.i(TAG, "request audio focus failure!");
            return;
        }
        prepare(this.mPlayingIndex);
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void pause() {
        LogUtils.i(TAG, "pause mPlayingIndex = " + this.mPlayingIndex, false);
        pauseMusic();
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void playNext() {
        if (!requestMusicFocus()) {
            changeState(3);
            LogUtils.i(TAG, "request audio focus failure!");
            return;
        }
        LogUtils.i(TAG, "next mPlayingIndex = " + this.mPlayingIndex, false);
        int i = this.mPlayingIndex + 1;
        prepare(i < this.mMusicList.size() ? i : 0);
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void playPre() {
        if (!requestMusicFocus()) {
            changeState(3);
            LogUtils.i(TAG, "request audio focus failure!");
            return;
        }
        LogUtils.i(TAG, "prev mPlayingIndex = " + this.mPlayingIndex, false);
        int i = this.mPlayingIndex - 1;
        if (i < 0) {
            i = this.mMusicList.size() - 1;
        }
        prepare(i);
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void play(int index) {
        if (this.mPlayingIndex == index && this.mPlayState != -1) {
            LogUtils.i(TAG, "play: ========== " + index, false);
        } else {
            play(MeditationModel.getInstance().getMusicList(), index);
        }
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public int getLastPlayIndex() {
        return SharedPreferenceUtil.getMeditationLastPlayIndex();
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void setLastPlayIndex(int index) {
        SharedPreferenceUtil.setMeditationLastPlayIndex(index);
    }

    public boolean getDownloadResult(String name) {
        return SharedPreferenceUtil.getMeditationDownloadResult(name);
    }

    public void setDownloadResult(String name) {
        SharedPreferenceUtil.setMeditationDownloadResult(name);
    }

    public long getVenusDownloadId() {
        return SharedPreferenceUtil.getVenusDownloadId();
    }

    public void setVenusDownloadId(long id) {
        SharedPreferenceUtil.setVenusDownloadId(id);
    }

    public void setMedDownloadId(long id) {
        SharedPreferenceUtil.setMedDownloadId(id);
    }

    public void setForestDownloadId(long id) {
        SharedPreferenceUtil.setForestDownloadId(id);
    }

    public void setSeaDownloadId(long id) {
        SharedPreferenceUtil.setSeaDownloadId(id);
    }

    public long getMomentDownloadId() {
        return SharedPreferenceUtil.getMomentDownloadId();
    }

    public void setMomentDownloadId(long id) {
        SharedPreferenceUtil.setMomentDownloadId(id);
    }

    public long getMedDownloadId() {
        return SharedPreferenceUtil.getMedDownloadId();
    }

    public long getForestDownloadId() {
        return SharedPreferenceUtil.getForestDownloadId();
    }

    public long getSeaDownloadId() {
        return SharedPreferenceUtil.getSeaDownloadId();
    }

    public List<MeditationItemBean> getMusicList() {
        List<MeditationItemBean> list = this.mDataList;
        if (list != null) {
            return list;
        }
        this.mDataList = new ArrayList();
        List<MeditationBean.DataBean.ListBean> musicList = MeditationModel.getInstance().getMusicList();
        for (int i = 0; i < musicList.size(); i++) {
            MeditationItemBean meditationItemBean = new MeditationItemBean();
            meditationItemBean.setData(musicList.get(i));
            meditationItemBean.setSelected(false);
            this.mDataList.add(meditationItemBean);
        }
        return this.mDataList;
    }

    private void initPlayer() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.mPlayer = mediaPlayer;
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: com.xiaopeng.xpmeditation.viewModel.-$$Lambda$MeditationViewModel$2tNuwXprmON5OqGVhxbAmIP7qzQ
            @Override // android.media.MediaPlayer.OnErrorListener
            public final boolean onError(MediaPlayer mediaPlayer2, int i, int i2) {
                return MeditationViewModel.lambda$initPlayer$1(mediaPlayer2, i, i2);
            }
        });
        this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: com.xiaopeng.xpmeditation.viewModel.-$$Lambda$MeditationViewModel$05_ppDi8FeEB_95lgGaNLnXQwSQ
            @Override // android.media.MediaPlayer.OnPreparedListener
            public final void onPrepared(MediaPlayer mediaPlayer2) {
                MeditationViewModel.this.lambda$initPlayer$2$MeditationViewModel(mediaPlayer2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ boolean lambda$initPlayer$1(MediaPlayer mp, int what, int extra) {
        LogUtils.w(TAG, "mediaPlayer on error what: " + what + ", extra: " + extra);
        return false;
    }

    public /* synthetic */ void lambda$initPlayer$2$MeditationViewModel(MediaPlayer mp) {
        mp.start();
        if (requestMusicFocus()) {
            return;
        }
        LogUtils.w(TAG, "MediaPlayer prepared and auto start. We need pause,when meditation can not request audio focus.");
        mp.pause();
    }

    private void releasePlayer() {
        if (this.mPlayer.isPlaying()) {
            this.mPlayer.stop();
        }
        this.mPlayer.release();
        this.mPlayer = null;
        changeState(-1);
    }

    private void stopMedia() {
        LogUtils.i(TAG, "stopMedia ===== ", false);
        requestMusicFocus();
        SpeechHelper.getInstance().speak(App.getInstance().getString(R.string.meditation_speak));
    }

    private boolean requestMusicFocus() {
        return this.mAudioManager.requestAudioFocus(this.mAudioFocusChangeListener, 3, 2) != 0;
    }

    private void releasePlayModel() {
        LogUtils.i(TAG, "releasePlayModel ===");
        pauseMusic();
        releasePlayer();
        this.mAudioManager.abandonAudioFocus(this.mAudioFocusChangeListener);
    }

    private void play(List<MeditationBean.DataBean.ListBean> list, int playIndex) {
        if (list == null || list.size() == 0) {
            return;
        }
        playIndex = (playIndex < 0 || playIndex >= list.size()) ? 0 : 0;
        this.mMusicList.clear();
        this.mMusicList.addAll(list);
        if (!requestMusicFocus()) {
            changeState(3);
            LogUtils.i(TAG, "request audio focus failure!");
            return;
        }
        prepare(playIndex);
    }

    private void resumeMusic() {
        prepare(this.mPlayingIndex);
    }

    private void prepare(int index) {
        LogUtils.i(TAG, "prepare mPlayingIndex = " + index, false);
        List<MeditationBean.DataBean.ListBean> list = this.mMusicList;
        if (list == null || list.size() == 0) {
            LogUtils.i(TAG, "prepare: list is empty =========", false);
            return;
        }
        if (index == -1) {
            this.mPlayingIndex = 0;
        } else {
            this.mPlayingIndex = index;
        }
        SharedPreferenceUtil.setMeditationLastPlayIndex(this.mPlayingIndex);
        handlePlayIndexChange(this.mPlayingIndex);
        MeditationBean.DataBean.ListBean listBean = this.mMusicList.get(this.mPlayingIndex);
        if (listBean != null) {
            playMusic(listBean.getListenUrl());
        }
    }

    private void playMusic(String dataSource) {
        if (this.mPlayer == null) {
            initPlayer();
        } else {
            resetPlayer();
        }
        this.mPlayer.setLooping(true);
        try {
            this.mPlayer.setDataSource(dataSource);
            this.mPlayer.prepareAsync();
            changeState(2);
        } catch (IOException e) {
            LogUtils.w(TAG, "set data source occurs an exception,play failure");
            e.printStackTrace();
            this.mPlayer.reset();
        }
    }

    public boolean isMusicPlaying() {
        MediaPlayer mediaPlayer = this.mPlayer;
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    private void changeState(int state) {
        this.mPlayState = state;
        handlePlayStateChange(state);
    }

    private void resetPlayer() {
        if (this.mPlayer.isPlaying()) {
            this.mPlayer.stop();
        }
        this.mPlayer.reset();
        changeState(-1);
    }

    private void pauseMusic() {
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mPlayer.pause();
        }
        changeState(3);
    }

    protected void handlePlayIndexChange(int index) {
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

    protected void handlePlayStateChange(int state) {
        this.mPlayStateData.postValue(Integer.valueOf(state));
        LogUtils.d(TAG, "handlePlayStateChange: " + state);
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public void handlePnsStateChange(boolean state) {
        LogUtils.d(TAG, "handlePnsStateChange: " + state + " pnsState: " + this.mPnsState);
        if (state == this.mPnsState) {
            LogUtils.w(TAG, "handlePnsStateChange:  duplicate.");
            return;
        }
        setPnsState(state);
        this.mPnsPlayData.postValue(Boolean.valueOf(state));
    }

    public MutableLiveData<Integer> getPlayIndexData() {
        return this.mPlayIndexData;
    }

    public MutableLiveData<Integer> getPlayStateData() {
        return this.mPlayStateData;
    }

    public MutableLiveData<Boolean> getPnsPlayStateData() {
        return this.mPnsPlayData;
    }

    public MutableLiveData<List<MorelessInfo>> getMorelessData() {
        return this.mMorelessInfoData;
    }

    public void bindDownloadService(Context context) {
        context.bindService(new Intent(context, DownloadService.class), this.downloadConnection, 1);
        this.mDownloadServiceBound = true;
    }

    public void unDownloadBindService(Context context) {
        if (this.mDownloadServiceBound) {
            context.unbindService(this.downloadConnection);
            this.mDownloadServiceBound = false;
            return;
        }
        LogUtils.w(TAG, "unDownloadBindService, not bound yet!");
    }

    public void setDownloadListener(DownloadListener listener) {
        DownloadServiceImpl downloadServiceImpl = this.downloadService;
        if (downloadServiceImpl != null) {
            downloadServiceImpl.setDownloadListener(listener);
        }
    }

    public void startDownload(RequestInfo info) {
        DownloadServiceImpl downloadServiceImpl = this.downloadService;
        if (downloadServiceImpl != null) {
            downloadServiceImpl.start(info);
        }
    }

    public void pauseDownload(long id) {
        DownloadServiceImpl downloadServiceImpl = this.downloadService;
        if (downloadServiceImpl != null) {
            downloadServiceImpl.pause(id);
        }
    }

    public void resumeDownload(long id) {
        DownloadServiceImpl downloadServiceImpl = this.downloadService;
        if (downloadServiceImpl != null) {
            downloadServiceImpl.resume(id);
        }
    }

    public void queryDownload(long id) {
        DownloadServiceImpl downloadServiceImpl = this.downloadService;
        if (downloadServiceImpl != null) {
            downloadServiceImpl.query(id);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.xpmeditation.viewModel.MeditationViewModel$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass2 implements IMorelessCallback {
        AnonymousClass2() {
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException e) {
            LogUtils.d(MeditationViewModel.TAG, "refreshMorelessToken failed, " + e.toString());
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.xpmeditation.viewModel.-$$Lambda$MeditationViewModel$2$b7rC65P6-e2qPglohMOkVW8XbXM
                @Override // java.lang.Runnable
                public final void run() {
                    MeditationViewModel.AnonymousClass2.this.lambda$onFailure$0$MeditationViewModel$2();
                }
            }, 10000L);
        }

        public /* synthetic */ void lambda$onFailure$0$MeditationViewModel$2() {
            MeditationViewModel.this.refreshMorelessToken();
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            if (response == null) {
                LogUtils.d(MeditationViewModel.TAG, "Get token,response is empty!");
            } else if (response.code() != 200) {
                LogUtils.d(MeditationViewModel.TAG, "Get token,error response code " + response.code());
            } else {
                LogUtils.d(MeditationViewModel.TAG, response.toString());
                String string = response.body().string();
                LogUtils.d(MeditationViewModel.TAG, string);
                try {
                    MeditationViewModel.this.getMorelessSoundList(((MorelessToken) GsonUtil.fromJson(string, (Class<Object>) MorelessToken.class)).getAccess_token());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshMorelessToken() {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.xpmeditation.viewModel.-$$Lambda$MeditationViewModel$5CCslWA5P3r5nhW-MA58yiaoF6k
            @Override // java.lang.Runnable
            public final void run() {
                MeditationViewModel.this.lambda$refreshMorelessToken$3$MeditationViewModel();
            }
        });
    }

    public /* synthetic */ void lambda$refreshMorelessToken$3$MeditationViewModel() {
        MorelessHelper.getInstance().getToken(App.getInstance().getApplicationContext(), new AnonymousClass2());
    }

    public void getMorelessSoundList() {
        refreshMorelessToken();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.xpmeditation.viewModel.MeditationViewModel$3  reason: invalid class name */
    /* loaded from: classes2.dex */
    public class AnonymousClass3 implements IMorelessCallback {
        final /* synthetic */ String val$token;

        AnonymousClass3(final String val$token) {
            this.val$token = val$token;
        }

        @Override // okhttp3.Callback
        public void onFailure(Call call, IOException e) {
            LogUtils.d(MeditationViewModel.TAG, "getMorelessSoundList failed, " + e.toString());
            final String str = this.val$token;
            ThreadUtils.postDelayed(2, new Runnable() { // from class: com.xiaopeng.xpmeditation.viewModel.-$$Lambda$MeditationViewModel$3$jLHKYhEdluHbshbNff24CBjBdJ0
                @Override // java.lang.Runnable
                public final void run() {
                    MeditationViewModel.AnonymousClass3.this.lambda$onFailure$0$MeditationViewModel$3(str);
                }
            }, 10000L);
        }

        public /* synthetic */ void lambda$onFailure$0$MeditationViewModel$3(final String token) {
            MeditationViewModel.this.getMorelessSoundList(token);
        }

        @Override // okhttp3.Callback
        public void onResponse(Call call, Response response) throws IOException {
            if (response == null) {
                LogUtils.d(MeditationViewModel.TAG, "Get list,response is empty!");
            } else if (response.code() != 200) {
                LogUtils.d(MeditationViewModel.TAG, "Get list,error response code" + response.code());
            } else {
                LogUtils.d(MeditationViewModel.TAG, response.toString());
                String string = response.body().string();
                LogUtils.d(MeditationViewModel.TAG, string);
                try {
                    MeditationViewModel.this.morelessInfoList = (List) GsonUtil.fromJson(string, new TypeToken<List<MorelessInfo>>() { // from class: com.xiaopeng.xpmeditation.viewModel.MeditationViewModel.3.1
                    }.getType());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MeditationViewModel.this.mMorelessInfoData.postValue(MeditationViewModel.this.morelessInfoList);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getMorelessSoundList(final String token) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.xpmeditation.viewModel.-$$Lambda$MeditationViewModel$GfL59baj1qeBJUQLleSDH0riLDs
            @Override // java.lang.Runnable
            public final void run() {
                MeditationViewModel.this.lambda$getMorelessSoundList$4$MeditationViewModel(token);
            }
        });
    }

    public /* synthetic */ void lambda$getMorelessSoundList$4$MeditationViewModel(final String token) {
        MorelessHelper.getInstance().getSoundList(App.getInstance().getApplicationContext(), token, new AnonymousClass3(token));
    }

    public void setPnsState(boolean state) {
        this.mPnsState = state;
    }

    @Override // com.xiaopeng.xpmeditation.viewModel.IMeditationViewModel
    public boolean getPnsState() {
        LogUtils.d(TAG, "getPnsState: " + this.mPnsState);
        return this.mPnsState;
    }
}
