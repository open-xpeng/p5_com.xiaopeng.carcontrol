package com.xiaopeng.speech.vui.model;

import android.view.View;
import com.xiaopeng.speech.vui.listener.IVuiEventListener;
import com.xiaopeng.vui.commons.IVuiElementChangedListener;
import com.xiaopeng.vui.commons.IVuiSceneListener;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class VuiSceneInfo {
    private IVuiElementChangedListener elementChangedListener;
    private int state = VuiSceneState.IDLE.getState();
    private View rootView = null;
    private IVuiSceneListener listener = null;
    private List<String> subSceneList = null;
    private List<String> idList = null;
    private List<String> wholeSceneIds = null;
    private boolean isWholeScene = true;
    private int addSubSceneNum = 0;
    private boolean isContainNotChildrenView = false;
    private List<SoftReference<View>> notChildrenViewList = null;
    private List<String> notChildrenViewIdList = null;
    private String lastUpdateStr = null;
    private String lastAddStr = null;
    private IVuiEventListener eventListener = null;
    private boolean isBuild = false;
    private boolean isBuildComplete = false;

    public void updateAddSubSceneNum() {
        this.addSubSceneNum++;
    }

    public boolean isFull() {
        List<String> list;
        return !this.isWholeScene || (list = this.subSceneList) == null || this.addSubSceneNum == list.size();
    }

    public boolean isWholeScene() {
        return this.isWholeScene;
    }

    public void setWholeScene(boolean z) {
        this.isWholeScene = z;
    }

    public void setWholeSceneId(String str) {
        if (this.wholeSceneIds == null) {
            this.wholeSceneIds = new ArrayList();
        }
        if (this.wholeSceneIds.contains(str)) {
            return;
        }
        this.wholeSceneIds.add(str);
    }

    public List<String> getWholeSceneId() {
        return this.wholeSceneIds;
    }

    public List<String> getIdList() {
        return this.idList;
    }

    public void setIdList(List<String> list) {
        this.idList = list;
    }

    public int getState() {
        return this.state;
    }

    public View getRootView() {
        return this.rootView;
    }

    public IVuiSceneListener getListener() {
        return this.listener;
    }

    public List<String> getSubSceneList() {
        return this.subSceneList;
    }

    public void setState(int i) {
        this.state = i;
    }

    public void setRootView(View view) {
        this.rootView = view;
    }

    public void setListener(IVuiSceneListener iVuiSceneListener) {
        this.listener = iVuiSceneListener;
    }

    public void setSubSceneList(List<String> list) {
        this.subSceneList = list;
    }

    public void setContainNotChildrenView(boolean z) {
        this.isContainNotChildrenView = z;
    }

    public boolean isContainNotChildrenView() {
        return this.isContainNotChildrenView;
    }

    public void setNotChildrenViewList(List<SoftReference<View>> list) {
        this.notChildrenViewList = list;
    }

    public List<SoftReference<View>> getNotChildrenViewList() {
        return this.notChildrenViewList;
    }

    public List<String> getNotChildrenViewIdList() {
        return this.notChildrenViewIdList;
    }

    public void setNotChildrenViewIdList(List<String> list) {
        this.notChildrenViewIdList = list;
    }

    public void resetViewInfo() {
        this.rootView = null;
        this.listener = null;
        this.eventListener = null;
        this.elementChangedListener = null;
    }

    public void reset(boolean z) {
        if (z) {
            this.state = VuiSceneState.IDLE.getState();
        }
        this.isContainNotChildrenView = false;
        this.notChildrenViewList = null;
        this.notChildrenViewIdList = null;
        this.addSubSceneNum = 0;
        this.lastUpdateStr = null;
        this.lastAddStr = null;
        this.isBuild = false;
        this.subSceneList = null;
        this.wholeSceneIds = null;
        this.idList = null;
        this.isBuildComplete = false;
        if (z) {
            resetViewInfo();
        }
    }

    public String getLastUpdateStr() {
        return this.lastUpdateStr;
    }

    public String getLastAddStr() {
        return this.lastAddStr;
    }

    public void setLastUpdateStr(String str) {
        this.lastUpdateStr = str;
    }

    public void setLastAddStr(String str) {
        this.lastAddStr = str;
    }

    public void setEventListener(IVuiEventListener iVuiEventListener) {
        this.eventListener = iVuiEventListener;
    }

    public IVuiEventListener getEventListener() {
        return this.eventListener;
    }

    public boolean isBuild() {
        return this.isBuild;
    }

    public void setBuild(boolean z) {
        this.isBuild = z;
    }

    public boolean isBuildComplete() {
        return this.isBuildComplete;
    }

    public void setBuildComplete(boolean z) {
        this.isBuildComplete = z;
    }

    public IVuiElementChangedListener getElementChangedListener() {
        return this.elementChangedListener;
    }

    public void setElementChangedListener(IVuiElementChangedListener iVuiElementChangedListener) {
        this.elementChangedListener = iVuiElementChangedListener;
    }
}
