package com.xiaopeng.xpmeditation.model.plus;

import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.carcontrol.util.GsonUtil;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.lludancemanager.util.FileUtil;
import com.xiaopeng.xpmeditation.model.MeditationBean;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class MeditationPlusModel {
    private static final String TAG = "MeditationPlusModel";
    protected List<MeditationBean.DataBean.ListBeanPlus> mMusicList = new ArrayList();

    /* loaded from: classes2.dex */
    private static class SingleHolder {
        static final MeditationPlusModel INSTANCE = new MeditationPlusModel();

        private SingleHolder() {
        }
    }

    public static MeditationPlusModel getInstance() {
        return SingleHolder.INSTANCE;
    }

    private List<MeditationBean.DataBean.ListBeanPlus> readLocalMusicList() {
        String loadFromAssets = FileUtil.loadFromAssets("local_meditation_list.json");
        if (TextUtils.isEmpty(loadFromAssets)) {
            return null;
        }
        try {
            return (List) GsonUtil.fromJson(loadFromAssets, new TypeToken<List<MeditationBean.DataBean.ListBeanPlus>>() { // from class: com.xiaopeng.xpmeditation.model.plus.MeditationPlusModel.1
            }.getType());
        } catch (Exception e) {
            LogUtils.d(TAG, "requestAiLluData --> " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<MeditationBean.DataBean.ListBeanPlus> getMusicList() {
        if (this.mMusicList.size() > 0) {
            return this.mMusicList;
        }
        List<MeditationBean.DataBean.ListBeanPlus> readLocalMusicList = readLocalMusicList();
        if (readLocalMusicList != null) {
            this.mMusicList.addAll(readLocalMusicList);
        }
        return this.mMusicList;
    }

    public void clearMusicList() {
        this.mMusicList.clear();
    }
}
