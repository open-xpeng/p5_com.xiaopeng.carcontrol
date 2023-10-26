package com.xiaopeng.xui.app.delegate;

import android.app.Activity;
import android.view.WindowManager;

/* loaded from: classes2.dex */
public class XActivityWindowAttributes {
    private int flags;
    private int gravity;
    private int height;
    private final Activity mActivity;
    private int systemUiVisibility;
    private int width;
    private int x;
    private int y;

    public XActivityWindowAttributes(Activity activity) {
        this.mActivity = activity;
        init();
    }

    private void init() {
        WindowManager.LayoutParams attributes;
        Activity activity = this.mActivity;
        if (activity == null || activity.getWindow() == null || (attributes = this.mActivity.getWindow().getAttributes()) == null) {
            return;
        }
        this.x = attributes.x;
        this.y = attributes.y;
        this.flags = attributes.flags;
        this.width = attributes.width;
        this.height = attributes.height;
        this.gravity = attributes.gravity;
        this.systemUiVisibility = attributes.systemUiVisibility;
    }

    public void apply() {
        WindowManager.LayoutParams attributes;
        Activity activity = this.mActivity;
        if (activity == null || activity.getWindow() == null || (attributes = this.mActivity.getWindow().getAttributes()) == null) {
            return;
        }
        attributes.x = this.x;
        attributes.y = this.y;
        attributes.flags |= this.flags;
        attributes.width = this.width;
        attributes.height = this.height;
        attributes.gravity = this.gravity;
        attributes.systemUiVisibility |= this.systemUiVisibility;
        this.mActivity.getWindow().setAttributes(attributes);
    }

    public XActivityWindowAttributes setX(int i) {
        this.x = i;
        return this;
    }

    public XActivityWindowAttributes setY(int i) {
        this.y = i;
        return this;
    }

    public XActivityWindowAttributes setFlags(int i) {
        this.flags = i;
        return this;
    }

    public XActivityWindowAttributes setWidth(int i) {
        this.width = i;
        return this;
    }

    public XActivityWindowAttributes setHeight(int i) {
        this.height = i;
        return this;
    }

    public XActivityWindowAttributes setGravity(int i) {
        this.gravity = i;
        return this;
    }

    public XActivityWindowAttributes setSystemUiVisibility(int i) {
        this.systemUiVisibility = i;
        return this;
    }

    public String toString() {
        return "{x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", flags=" + this.flags + ", gravity=" + this.gravity + ", systemUiVisibility=" + this.systemUiVisibility + ", mActivity=" + this.mActivity + '}';
    }
}
