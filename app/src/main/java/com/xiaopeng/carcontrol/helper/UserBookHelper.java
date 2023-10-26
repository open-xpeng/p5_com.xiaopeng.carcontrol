package com.xiaopeng.carcontrol.helper;

import android.content.Intent;
import android.text.TextUtils;
import com.xiaopeng.carcontrol.App;
import com.xiaopeng.carcontrol.config.feature.BaseFeatureOption;
import com.xiaopeng.carcontrol.util.LogUtils;
import com.xiaopeng.carcontrol.viewmodel.ViewModelManager;
import com.xiaopeng.carcontrol.viewmodel.scenario.IScenarioViewModel;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioMode;
import com.xiaopeng.carcontrol.viewmodel.scenario.ScenarioViewModel;
import com.xiaopeng.carcontrolmodule.R;

/* loaded from: classes2.dex */
public class UserBookHelper {
    private static final String INTENT_KEY_CALLJUMP = "callJump";
    private static final String INTENT_KEY_JUMP = "jump";
    private static final String INTENT_KEY_NAME = "name";
    private static final String INTENT_KEY_SHARED = "sharedId";
    private static final String INTENT_KEY_WORDS = "words";
    private static final String KEY_NAME_VALUE1 = "KEY_WORD";
    private static final String KEY_NAME_VALUE2 = "CLOSE_USER_BOOK";
    private static final String TAG = "UserBookHelper";
    private static final String USERBOOK_ACTION = "com.xiaopeng.usermanual.ACTION_LAUNCH";
    private static final String USERBOOK_PACKAGE = "com.xiaopeng.usermanual";
    private static ScenarioViewModel mScenarioViewModel;

    public static void openUserBook(String keywords, boolean jump) {
        openUserBook(keywords, 0, jump);
    }

    public static void openUserBook(String keywords, int sharedId) {
        openUserBook(keywords, sharedId, true);
    }

    private static void openUserBook(String keywords, int sharedId, boolean jump) {
        if (isSupport()) {
            LogUtils.i(TAG, "openUserBook: keywords=" + keywords + ", jump=" + jump);
            if (mScenarioViewModel == null) {
                mScenarioViewModel = (ScenarioViewModel) ViewModelManager.getInstance().getViewModelImpl(IScenarioViewModel.class);
            }
            ScenarioMode currentUserScenarioValue = mScenarioViewModel.getCurrentUserScenarioValue();
            LogUtils.i(TAG, "openUserBook: keywords=" + keywords + ", jump=" + jump + ", scenario: " + currentUserScenarioValue, false);
            if (ScenarioMode.SpaceCapsuleSleep == currentUserScenarioValue || ScenarioMode.SpaceCapsuleCinema == currentUserScenarioValue || ScenarioMode.Meditation == currentUserScenarioValue) {
                NotificationHelper.getInstance().showToastLong(R.string.hvac_user_manual_toast);
                return;
            }
            Intent intent = new Intent(USERBOOK_ACTION);
            intent.setPackage(USERBOOK_PACKAGE);
            intent.putExtra(INTENT_KEY_SHARED, sharedId);
            if (!TextUtils.isEmpty(keywords)) {
                intent.putExtra("name", KEY_NAME_VALUE1);
                intent.putExtra(INTENT_KEY_WORDS, keywords);
                intent.putExtra(INTENT_KEY_CALLJUMP, true);
            }
            intent.putExtra(INTENT_KEY_JUMP, jump);
            App.getInstance().sendBroadcast(intent);
        }
    }

    public static void closeUserBook() {
        LogUtils.d(TAG, "closeUserBook");
        Intent intent = new Intent(USERBOOK_ACTION);
        intent.setPackage(USERBOOK_PACKAGE);
        intent.putExtra("name", KEY_NAME_VALUE2);
        App.getInstance().sendBroadcast(intent);
    }

    public static boolean isSupport() {
        return BaseFeatureOption.getInstance().isSupportShowUserBook();
    }
}
