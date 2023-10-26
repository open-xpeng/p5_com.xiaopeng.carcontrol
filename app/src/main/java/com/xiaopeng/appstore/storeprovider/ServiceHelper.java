package com.xiaopeng.appstore.storeprovider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/* loaded from: classes.dex */
public class ServiceHelper {
    private static final String ACTION_DATA_SCHEMA = "xp://";
    public static final String PACKAGE_NAME = "com.xiaopeng.resourceservice";
    private static final String SCHEMA_ACTION_CANCEL = "cancel/";
    private static final String SCHEMA_ACTION_OPEN = "open/";
    private static final String SCHEMA_ACTION_PAUSE = "pause/";
    private static final String SCHEMA_ACTION_RESUME = "resume/";
    private static final String SCHEMA_ACTION_RETRY = "retry/";
    public static final String SERVICE = "com.xiaopeng.appstore.resourceservice.ResourceServiceV2";
    private static final String TAG = "ServiceHelper";

    private ServiceHelper() {
    }

    public static Intent pauseIntent(int i, String str, String str2) {
        Intent intent = new Intent(AssembleConstants.ACTION_PAUSE);
        intent.setClassName("com.xiaopeng.resourceservice", "com.xiaopeng.appstore.resourceservice.ResourceServiceV2");
        intent.putExtra(AssembleConstants.INTENT_KEY_RES_TYPE, i);
        intent.putExtra(AssembleConstants.INTENT_KEY_ASSEMBLE_KEY, str);
        intent.putExtra(AssembleConstants.INTENT_KEY_CALLING_PACKAGE, str2);
        intent.setData(Uri.parse("xp://pause/" + str));
        return intent;
    }

    public static Intent resumeIntent(int i, String str, String str2) {
        Intent intent = new Intent(AssembleConstants.ACTION_RESUME);
        intent.setClassName("com.xiaopeng.resourceservice", "com.xiaopeng.appstore.resourceservice.ResourceServiceV2");
        intent.putExtra(AssembleConstants.INTENT_KEY_RES_TYPE, i);
        intent.putExtra(AssembleConstants.INTENT_KEY_ASSEMBLE_KEY, str);
        intent.putExtra(AssembleConstants.INTENT_KEY_CALLING_PACKAGE, str2);
        intent.setData(Uri.parse("xp://resume/" + str));
        return intent;
    }

    public static Intent cancelIntent(int i, String str, String str2) {
        Intent intent = new Intent(AssembleConstants.ACTION_CANCEL);
        intent.setClassName("com.xiaopeng.resourceservice", "com.xiaopeng.appstore.resourceservice.ResourceServiceV2");
        intent.putExtra(AssembleConstants.INTENT_KEY_RES_TYPE, i);
        intent.putExtra(AssembleConstants.INTENT_KEY_ASSEMBLE_KEY, str);
        intent.putExtra(AssembleConstants.INTENT_KEY_CALLING_PACKAGE, str2);
        intent.setData(Uri.parse("xp://cancel/" + str));
        return intent;
    }

    public static Intent retryIntent(int i, String str, String str2) {
        Intent intent = new Intent(AssembleConstants.ACTION_RETRY);
        intent.setClassName("com.xiaopeng.resourceservice", "com.xiaopeng.appstore.resourceservice.ResourceServiceV2");
        intent.putExtra(AssembleConstants.INTENT_KEY_RES_TYPE, i);
        intent.putExtra(AssembleConstants.INTENT_KEY_ASSEMBLE_KEY, str);
        intent.putExtra(AssembleConstants.INTENT_KEY_CALLING_PACKAGE, str2);
        intent.setData(Uri.parse("xp://retry/" + str));
        return intent;
    }

    public static Intent successIntent(int i, String str, String str2) {
        Intent intent = new Intent(AssembleConstants.ACTION_SUCCESS);
        intent.setClassName("com.xiaopeng.resourceservice", "com.xiaopeng.appstore.resourceservice.ResourceServiceV2");
        intent.putExtra(AssembleConstants.INTENT_KEY_RES_TYPE, i);
        intent.putExtra(AssembleConstants.INTENT_KEY_ASSEMBLE_KEY, str);
        intent.putExtra(AssembleConstants.INTENT_KEY_CALLING_PACKAGE, str2);
        intent.setData(Uri.parse("xp://open/" + str));
        return intent;
    }

    public static void startServiceToPause(Context context, int i, String str) {
        context.startForegroundService(pauseIntent(i, str, context.getPackageName()));
    }

    public static void startServiceToResume(Context context, int i, String str) {
        startServiceToResume(context, i, str, context.getPackageName());
    }

    public static void startServiceToResume(Context context, int i, String str, String str2) {
        Log.d(TAG, "startServiceToResume: resType:" + i + ", key:" + str + ", calling:" + str2);
        context.startForegroundService(resumeIntent(i, str, context.getPackageName()));
    }

    public static void startServiceToCancel(Context context, int i, String str) {
        context.startForegroundService(cancelIntent(i, str, context.getPackageName()));
    }

    public static void startServiceToRetry(Context context, int i, String str) {
        context.startForegroundService(retryIntent(i, str, context.getPackageName()));
    }

    public static void startServiceSuccess(Context context, int i, String str) {
        context.startForegroundService(successIntent(i, str, context.getPackageName()));
    }
}
