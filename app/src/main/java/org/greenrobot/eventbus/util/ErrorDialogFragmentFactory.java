package org.greenrobot.eventbus.util;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import org.greenrobot.eventbus.util.ErrorDialogFragments;

/* loaded from: classes3.dex */
public abstract class ErrorDialogFragmentFactory<T> {
    protected final ErrorDialogConfig config;

    protected abstract T createErrorFragment(ThrowableFailureEvent throwableFailureEvent, Bundle bundle);

    protected ErrorDialogFragmentFactory(ErrorDialogConfig errorDialogConfig) {
        this.config = errorDialogConfig;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public T prepareErrorFragment(ThrowableFailureEvent throwableFailureEvent, boolean z, Bundle bundle) {
        Bundle bundle2;
        if (throwableFailureEvent.isSuppressErrorUi()) {
            return null;
        }
        if (bundle != null) {
            bundle2 = (Bundle) bundle.clone();
        } else {
            bundle2 = new Bundle();
        }
        if (!bundle2.containsKey(ErrorDialogManager.KEY_TITLE)) {
            bundle2.putString(ErrorDialogManager.KEY_TITLE, getTitleFor(throwableFailureEvent, bundle2));
        }
        if (!bundle2.containsKey(ErrorDialogManager.KEY_MESSAGE)) {
            bundle2.putString(ErrorDialogManager.KEY_MESSAGE, getMessageFor(throwableFailureEvent, bundle2));
        }
        if (!bundle2.containsKey(ErrorDialogManager.KEY_FINISH_AFTER_DIALOG)) {
            bundle2.putBoolean(ErrorDialogManager.KEY_FINISH_AFTER_DIALOG, z);
        }
        if (!bundle2.containsKey(ErrorDialogManager.KEY_EVENT_TYPE_ON_CLOSE) && this.config.defaultEventTypeOnDialogClosed != null) {
            bundle2.putSerializable(ErrorDialogManager.KEY_EVENT_TYPE_ON_CLOSE, this.config.defaultEventTypeOnDialogClosed);
        }
        if (!bundle2.containsKey(ErrorDialogManager.KEY_ICON_ID) && this.config.defaultDialogIconId != 0) {
            bundle2.putInt(ErrorDialogManager.KEY_ICON_ID, this.config.defaultDialogIconId);
        }
        return createErrorFragment(throwableFailureEvent, bundle2);
    }

    protected String getTitleFor(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
        return this.config.resources.getString(this.config.defaultTitleId);
    }

    protected String getMessageFor(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
        return this.config.resources.getString(this.config.getMessageIdForThrowable(throwableFailureEvent.throwable));
    }

    /* loaded from: classes3.dex */
    public static class Support extends ErrorDialogFragmentFactory<Fragment> {
        public Support(ErrorDialogConfig errorDialogConfig) {
            super(errorDialogConfig);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.greenrobot.eventbus.util.ErrorDialogFragmentFactory
        public Fragment createErrorFragment(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
            ErrorDialogFragments.Support support = new ErrorDialogFragments.Support();
            support.setArguments(bundle);
            return support;
        }
    }

    /* loaded from: classes3.dex */
    public static class Honeycomb extends ErrorDialogFragmentFactory<android.app.Fragment> {
        public Honeycomb(ErrorDialogConfig errorDialogConfig) {
            super(errorDialogConfig);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // org.greenrobot.eventbus.util.ErrorDialogFragmentFactory
        public android.app.Fragment createErrorFragment(ThrowableFailureEvent throwableFailureEvent, Bundle bundle) {
            ErrorDialogFragments.Honeycomb honeycomb = new ErrorDialogFragments.Honeycomb();
            honeycomb.setArguments(bundle);
            return honeycomb;
        }
    }
}
