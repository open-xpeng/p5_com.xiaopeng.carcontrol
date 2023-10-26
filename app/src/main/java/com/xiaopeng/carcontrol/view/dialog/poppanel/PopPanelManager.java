package com.xiaopeng.carcontrol.view.dialog.poppanel;

import com.xiaopeng.carcontrol.carmanager.CarClientWrapper;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.AmsUtils;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.util.ThreadUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class PopPanelManager {
    public static final String CONFLICT_SCENE_AIR_BED = "airbed";
    public static final String CONFLICT_SCENE_MAIN = "c_c";
    public static final String CONFLICT_SCENE_SEAT = "seat_adjust";
    public static final String PSN_SEAT_MENU = "psn_seat_menu";
    public static final String P_GEAR_MENU = "p_gear_menu";
    public static final String SEAT_MIRROR_GUEST_MENU = "seat_mirror_guest_menu";
    public static final String SEAT_MIRROR_MENU = "seat_mirror_menu";
    private static final String TAG = "PopPanelManager";
    private final Map<String, AbstractPopPanelMenu> mCache;
    private final Map<String, Boolean> mConflictSt;
    private boolean mIsAllowedToShow;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static class SingleHolder {
        private static PopPanelManager sInstance = new PopPanelManager();

        private SingleHolder() {
        }
    }

    private PopPanelManager() {
        this.mIsAllowedToShow = true;
        this.mCache = new ConcurrentHashMap();
        this.mConflictSt = new ConcurrentHashMap();
    }

    public static PopPanelManager getInstance() {
        return SingleHolder.sInstance;
    }

    public void initMenus(final Map<String, Class<? extends AbstractPopPanelMenu>> menuList) {
        ThreadUtils.execute(new Runnable() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$PopPanelManager$Wc_5fPgI7uYg_Yn1V92jQAyYj9I
            @Override // java.lang.Runnable
            public final void run() {
                PopPanelManager.this.lambda$initMenus$1$PopPanelManager(menuList);
            }
        });
    }

    public /* synthetic */ void lambda$initMenus$1$PopPanelManager(final Map menuList) {
        LogUtils.d(TAG, "init start");
        if (CarClientWrapper.getInstance().isCarServiceConnected()) {
            menuList.forEach(new BiConsumer() { // from class: com.xiaopeng.carcontrol.view.dialog.poppanel.-$$Lambda$PopPanelManager$QkVMB-j8Lbr0ssY06JoeV33UEa4
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    PopPanelManager.this.lambda$null$0$PopPanelManager((String) obj, (Class) obj2);
                }
            });
        }
        LogUtils.d(TAG, "init end");
    }

    public /* synthetic */ void lambda$null$0$PopPanelManager(String key, Class menuClass) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.mCache.put(key, createMenu(menuClass));
    }

    private AbstractPopPanelMenu createMenu(Class<? extends AbstractPopPanelMenu> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removeAllDropdownMenu() {
        this.mIsAllowedToShow = false;
        dismissAll();
    }

    public void resumeAllDropdownMenu() {
        this.mIsAllowedToShow = true;
    }

    public void show(String key, int windowType, int mRegionId) {
        show(key, windowType, mRegionId, true);
    }

    public void show(String key, int windowType, int mRegionId, boolean OsdEnable) {
        LogUtils.i(TAG, "Show flag: " + this.mIsAllowedToShow, false);
        if (!this.mIsAllowedToShow) {
            LogUtils.i(TAG, "Not allow to show " + key, false);
        } else if (!BaseFeatureOption.getInstance().isSupportNapa() && AmsUtils.isTopActivityFullscreen()) {
            LogUtils.i(TAG, "can not show drop down for fullscreen Activity showing", false);
        } else if (BaseFeatureOption.getInstance().isSupportNapa() && (AmsUtils.isNapaTopViewFullscreen(0) || AmsUtils.isInParkTopView(0))) {
            LogUtils.i(TAG, "can not show drop down for Napa Limit", false);
        } else {
            AbstractPopPanelMenu dropDownMenu = getDropDownMenu(key);
            if (dropDownMenu != null) {
                if (dropDownMenu.isShow()) {
                    dropDownMenu.onRefresh();
                    dropDownMenu.keepPanelShow();
                    return;
                }
                try {
                    dropDownMenu.show(windowType, mRegionId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0039, code lost:
        if (com.xiaopeng.carcontrol.helper.NotificationHelper.getInstance().getSharedId(r5) <= 0) goto L14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x003b, code lost:
        r1 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x004f, code lost:
        if (com.xiaopeng.carcontrol.helper.NotificationHelper.getInstance().getSharedId(r5) <= 0) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean checkConflictWithNapa(java.lang.String r8) {
        /*
            r7 = this;
            com.xiaopeng.carcontrol.config.feature.BaseFeatureOption r0 = com.xiaopeng.carcontrol.config.feature.BaseFeatureOption.getInstance()
            boolean r0 = r0.isSupportNapa()
            r1 = 0
            if (r0 == 0) goto Lb1
            java.util.Map<java.lang.String, java.lang.Boolean> r0 = r7.mConflictSt
            java.lang.Object r0 = r0.get(r8)
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            r2 = 1
            if (r0 == 0) goto L1e
            boolean r0 = r0.booleanValue()
            if (r0 == 0) goto L1e
            r0 = r2
            goto L1f
        L1e:
            r0 = r1
        L1f:
            java.lang.String r3 = "psn_seat_menu"
            java.lang.String r4 = "seat_mirror_menu"
            if (r0 == 0) goto L81
            java.lang.String r5 = r7.mapConflictKey(r8)
            java.lang.String r6 = "p_gear_menu"
            boolean r6 = r6.equalsIgnoreCase(r8)
            if (r6 == 0) goto L41
            com.xiaopeng.carcontrol.helper.NotificationHelper r8 = com.xiaopeng.carcontrol.helper.NotificationHelper.getInstance()
            int r8 = r8.getSharedId(r5)
            if (r8 > 0) goto L3c
        L3b:
            r1 = r2
        L3c:
            r8 = r0 & r1
            r1 = r8
            goto Lb1
        L41:
            boolean r4 = r4.equalsIgnoreCase(r8)
            if (r4 == 0) goto L52
            com.xiaopeng.carcontrol.helper.NotificationHelper r8 = com.xiaopeng.carcontrol.helper.NotificationHelper.getInstance()
            int r8 = r8.getSharedId(r5)
            if (r8 > 0) goto L3c
            goto L3b
        L52:
            boolean r8 = r3.equalsIgnoreCase(r8)
            if (r8 == 0) goto Lb0
            com.xiaopeng.carcontrol.config.feature.BaseFeatureOption r8 = com.xiaopeng.carcontrol.config.feature.BaseFeatureOption.getInstance()
            boolean r8 = r8.isSupportDualScreen()
            if (r8 == 0) goto L76
            com.xiaopeng.carcontrol.helper.NotificationHelper r8 = com.xiaopeng.carcontrol.helper.NotificationHelper.getInstance()
            int r8 = r8.getSharedId(r5)
            com.xiaopeng.carcontrol.config.feature.BaseFeatureOption r3 = com.xiaopeng.carcontrol.config.feature.BaseFeatureOption.getInstance()
            boolean r3 = r3.isSupportNewPsnSaveHabbitPos()
            r3 = r3 ^ r2
            if (r8 != r3) goto L3c
            goto L3b
        L76:
            com.xiaopeng.carcontrol.helper.NotificationHelper r8 = com.xiaopeng.carcontrol.helper.NotificationHelper.getInstance()
            int r8 = r8.getSharedId(r5)
            if (r8 != 0) goto L3c
            goto L3b
        L81:
            com.xiaopeng.carcontrol.config.feature.BaseFeatureOption r5 = com.xiaopeng.carcontrol.config.feature.BaseFeatureOption.getInstance()
            boolean r5 = r5.isSupportAirBed()
            if (r5 == 0) goto Lb0
            boolean r4 = r4.equalsIgnoreCase(r8)
            java.lang.String r5 = "airbed"
            if (r4 == 0) goto L9f
            com.xiaopeng.carcontrol.helper.NotificationHelper r8 = com.xiaopeng.carcontrol.helper.NotificationHelper.getInstance()
            int r8 = r8.getSharedId(r5)
            if (r8 < 0) goto Lb1
        L9d:
            r1 = r2
            goto Lb1
        L9f:
            boolean r8 = r3.equalsIgnoreCase(r8)
            if (r8 == 0) goto Lb0
            com.xiaopeng.carcontrol.helper.NotificationHelper r8 = com.xiaopeng.carcontrol.helper.NotificationHelper.getInstance()
            int r8 = r8.getSharedId(r5)
            if (r8 < 0) goto Lb1
            goto L9d
        Lb0:
            r1 = r0
        Lb1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.carcontrol.view.dialog.poppanel.PopPanelManager.checkConflictWithNapa(java.lang.String):boolean");
    }

    private String mapConflictKey(String key) {
        return ("seat_mirror_menu".equalsIgnoreCase(key) || "psn_seat_menu".equalsIgnoreCase(key)) ? "seat_adjust" : "c_c";
    }

    public void dismiss(String key) {
        AbstractPopPanelMenu dropDownMenu;
        if (key.equals("seat_mirror_menu") && (dropDownMenu = getDropDownMenu(SEAT_MIRROR_GUEST_MENU)) != null) {
            dropDownMenu.dismiss();
        }
        AbstractPopPanelMenu dropDownMenu2 = getDropDownMenu(key);
        if (dropDownMenu2 != null) {
            dropDownMenu2.dismiss();
        }
    }

    public void dismissAll() {
        for (AbstractPopPanelMenu abstractPopPanelMenu : this.mCache.values()) {
            if (abstractPopPanelMenu != null) {
                abstractPopPanelMenu.dismiss();
            }
        }
    }

    public void setConflictState(String key, boolean shouldConflict) {
        this.mConflictSt.put(key, Boolean.valueOf(shouldConflict));
    }

    private AbstractPopPanelMenu getDropDownMenu(String key) {
        return this.mCache.get(key);
    }

    private void dismissOthers(AbstractPopPanelMenu popPanelMenu) {
        for (Map.Entry<String, AbstractPopPanelMenu> entry : this.mCache.entrySet()) {
            AbstractPopPanelMenu value = entry.getValue();
            if (value != null && value != popPanelMenu && value.getDropdownMenuGroupId() != popPanelMenu.getDropdownMenuGroupId()) {
                value.dismiss();
            }
        }
    }
}
