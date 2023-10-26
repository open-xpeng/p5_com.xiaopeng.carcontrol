package com.xiaopeng.xpmeditation.model;

import android.util.Log;
import com.xiaopeng.xpmeditation.model.MeditationBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class MeditationModel {
    private static final String BIRD_PATH = "/system/media/audio/xiaopeng/cdu/ogg/bird.ogg";
    private static final String CACHE_MEDITATION = "/sdcard/xiaopengmusic/cache/meditation";
    private static final String CREEK_PATH = "/system/media/audio/xiaopeng/cdu/ogg/creek.ogg";
    private static final String FIRE_WORKS_PATH = "/system/media/audio/xiaopeng/cdu/ogg/fireworks.ogg";
    private static final String FROG_PATH = "/system/media/audio/xiaopeng/cdu/ogg/frog.ogg";
    private static final int ID_BCM_DOOR = 1048622;
    private static final int ID_VCU_GEAR_LEVER = 1310742;
    private static final int MEDITAITON_ID_THUNDER = 6;
    private static final int MEDITATION_ID_BIRD = 7;
    private static final int MEDITATION_ID_CREEK = 5;
    private static final int MEDITATION_ID_FIREWORKS = 3;
    private static final int MEDITATION_ID_FROG = 4;
    private static final int MEDITATION_ID_WAVE = 1;
    private static final int MEDITATION_ID_WORM = 2;
    private static final int MEDITATION_ID_YOGA = 8;
    private static final String MEDITATION_KEY = "MEDITATION_KEY";
    private static final String PREF_MEDITATION = "PREF_MEDITATION";
    private static final String TAG = "MeditationModel";
    private static final String THUNDER_PATH = "/system/media/audio/xiaopeng/cdu/ogg/thunder.ogg";
    private static final String WAVE_PATH = "/system/media/audio/xiaopeng/cdu/ogg/wave.ogg";
    private static final String WORM_PATH = "/system/media/audio/xiaopeng/cdu/ogg/worm.ogg";
    private static final String YOGA_PATH = "/system/media/audio/xiaopeng/cdu/ogg/yoga.ogg";
    private Map<Integer, MeditationBean.DataBean.ListBean> mCacheMap;
    private MeditationBean mMeditationBean;
    private boolean mIsInit = false;
    private List<MeditationBean.DataBean.ListBean> mMusicList = new ArrayList();

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        static final MeditationModel INSTANCE = new MeditationModel();

        private SingleHolder() {
        }
    }

    public static MeditationModel getInstance() {
        return SingleHolder.INSTANCE;
    }

    public void init() {
        if (this.mIsInit) {
            return;
        }
        this.mIsInit = true;
    }

    public List<MeditationBean.DataBean.ListBean> getMusicList() {
        if (this.mMusicList.size() > 0) {
            return this.mMusicList;
        }
        MeditationBean.DataBean.ListBean listBean = new MeditationBean.DataBean.ListBean();
        listBean.setBizId(1);
        listBean.setTitle("海浪");
        listBean.setListenUrl(WAVE_PATH);
        listBean.setPicUrl(getResource("wave.png"));
        listBean.setThumbnailUrl(getResource("wave_icon.png"));
        this.mMusicList.add(listBean);
        MeditationBean.DataBean.ListBean listBean2 = new MeditationBean.DataBean.ListBean();
        listBean2.setBizId(2);
        listBean2.setTitle("虫鸣");
        listBean2.setListenUrl(WORM_PATH);
        listBean2.setPicUrl(getResource("worm.png"));
        listBean2.setThumbnailUrl(getResource("worm_icon.png"));
        this.mMusicList.add(listBean2);
        MeditationBean.DataBean.ListBean listBean3 = new MeditationBean.DataBean.ListBean();
        listBean3.setBizId(3);
        listBean3.setTitle("烟花");
        listBean3.setListenUrl(FIRE_WORKS_PATH);
        listBean3.setPicUrl(getResource("fireworks.png"));
        listBean3.setThumbnailUrl(getResource("fireworks_icon.png"));
        this.mMusicList.add(listBean3);
        MeditationBean.DataBean.ListBean listBean4 = new MeditationBean.DataBean.ListBean();
        listBean4.setBizId(4);
        listBean4.setTitle("蛙");
        listBean4.setListenUrl(FROG_PATH);
        listBean4.setPicUrl(getResource("frog.png"));
        listBean4.setThumbnailUrl(getResource("frog_icon.png"));
        this.mMusicList.add(listBean4);
        MeditationBean.DataBean.ListBean listBean5 = new MeditationBean.DataBean.ListBean();
        listBean5.setBizId(5);
        listBean5.setTitle("小溪");
        listBean5.setListenUrl(CREEK_PATH);
        listBean5.setPicUrl(getResource("creek.png"));
        listBean5.setThumbnailUrl(getResource("creek_icon.png"));
        this.mMusicList.add(listBean5);
        MeditationBean.DataBean.ListBean listBean6 = new MeditationBean.DataBean.ListBean();
        listBean6.setBizId(6);
        listBean6.setTitle("雷电");
        listBean6.setListenUrl(THUNDER_PATH);
        listBean6.setPicUrl(getResource("thunder.png"));
        listBean6.setThumbnailUrl(getResource("thunder_icon.png"));
        this.mMusicList.add(listBean6);
        MeditationBean.DataBean.ListBean listBean7 = new MeditationBean.DataBean.ListBean();
        listBean7.setBizId(7);
        listBean7.setTitle("鸟");
        listBean7.setListenUrl(BIRD_PATH);
        listBean7.setPicUrl(getResource("bird.png"));
        listBean7.setThumbnailUrl(getResource("bird_icon.png"));
        this.mMusicList.add(listBean7);
        MeditationBean.DataBean.ListBean listBean8 = new MeditationBean.DataBean.ListBean();
        listBean8.setBizId(8);
        listBean8.setTitle("瑜伽");
        listBean8.setListenUrl(YOGA_PATH);
        listBean8.setPicUrl(getResource("yoga.png"));
        listBean8.setThumbnailUrl(getResource("yoga_icon.png"));
        this.mMusicList.add(listBean8);
        return this.mMusicList;
    }

    private String getResource(String fileName) {
        return String.format("file:///android_asset/meditation/%s", fileName);
    }

    public void clearMusicList() {
        this.mMusicList.clear();
    }

    public void destory() {
        Log.i(TAG, "destory: ");
        this.mIsInit = false;
        this.mMusicList.clear();
    }
}
