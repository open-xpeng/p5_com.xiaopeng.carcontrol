package com.xiaopeng.xvs.xid.sync;

import com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync;

/* loaded from: classes2.dex */
class AiAssistantSyncImpl implements IAiAssistantSync {
    SyncProviderWrapper mWrapper;

    /* JADX INFO: Access modifiers changed from: package-private */
    public AiAssistantSyncImpl(SyncProviderWrapper syncProviderWrapper) {
        this.mWrapper = syncProviderWrapper;
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScenes(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScenes(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScenes(int i, String str) {
        this.mWrapper.put(i, IAiAssistantSync.KEY_SETTING_SCENE, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene1999(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE1999, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene1999(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE1999, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene1030(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE1030, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene1030(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE1030, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene21106(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE21106, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene21106(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE21106, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene1527(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE1527, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene1527(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE1527, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene2001(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE2001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene2001(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE2001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene2501(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE2501, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene2501(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE2501, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene35001(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE35001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene35001(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE35001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene26001(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE26001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene26001(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE26001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene26010(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE26010, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene26010(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE26010, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene35004(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE35004, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene35004(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE35004, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene35006(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE35006, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene35006(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE35006, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene10002(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE10002, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene10002(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE10002, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene9004(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE9004, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene9004(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE9004, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene11008(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE11008, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene11008(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE11008, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene11007(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE11007, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene11007(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE11007, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene9999999(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE9999999, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene9999999(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE9999999, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene999999(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE999999, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene999999(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE999999, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene21101(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE21101, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene21101(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE21101, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene21104(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE21104, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene21104(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE21104, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene21105(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE21105, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene21105(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE21105, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene5007(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE5007, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene5007(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE5007, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene5006(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE5006, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene5006(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE5006, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene2002(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE2002, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene2002(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE2002, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene2003(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE2003, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene2003(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE2003, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene2504(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE2504, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene2504(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE2504, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene2513(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE2513, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene2513(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE2513, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene27001(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE27001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene27001(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE27001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene2505(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE2505, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene2505(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE2505, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene5001(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE5001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene5001(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE5001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene8001(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE8001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene8001(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE8001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene8002(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE8002, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene8002(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE8002, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene5009(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE5009, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene5009(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE5009, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene1013(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE1013, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene1013(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE1013, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene22001(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE22001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene22001(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE22001, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene1021(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE1021, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene1021(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE1021, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene1029(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE1029, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene1029(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE1029, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene1500(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE1500, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene1500(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE1500, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene1002(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE1002, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene1002(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE1002, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene8003(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE8003, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene8003(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE8003, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene1023(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE1023, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene1023(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE1023, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene14210(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE14210, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene14210(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE14210, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public String getScene14204(String str) {
        return this.mWrapper.get(IAiAssistantSync.KEY_SETTING_SCENE14204, str);
    }

    @Override // com.xiaopeng.xvs.xid.sync.api.IAiAssistantSync
    public void putScene14204(String str) {
        this.mWrapper.put(IAiAssistantSync.KEY_SETTING_SCENE14204, str);
    }
}
