package com.xiaopeng.xui.view.touch;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class XTouchDelegateGroup extends TouchDelegate {
    private static final Rect IGNORED = new Rect();
    private TouchDelegate currentTouchDelegate;
    private List<XTouchDelegate> touchDelegates;

    public XTouchDelegateGroup(View view) {
        super(IGNORED, view);
        this.touchDelegates = new ArrayList();
    }

    public void addTouchDelegate(XTouchDelegate xTouchDelegate) {
        if (xTouchDelegate == null) {
            return;
        }
        Iterator<XTouchDelegate> it = this.touchDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            XTouchDelegate next = it.next();
            if (next.getDelegateViewHold().equals(xTouchDelegate.getDelegateViewHold())) {
                this.touchDelegates.remove(next);
                break;
            }
        }
        this.touchDelegates.add(xTouchDelegate);
    }

    public void removeTouchDelegate(TouchDelegate touchDelegate) {
        this.touchDelegates.remove(touchDelegate);
        if (touchDelegate == this.currentTouchDelegate) {
            this.currentTouchDelegate = null;
        }
    }

    public void clearTouchDelegates() {
        this.touchDelegates.clear();
    }

    public List<XTouchDelegate> getTouchDelegates() {
        return Collections.unmodifiableList(new ArrayList(this.touchDelegates));
    }

    @Override // android.view.TouchDelegate
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        boolean z = false;
        if (actionMasked == 0) {
            if (motionEvent.getPointerCount() > 1) {
                return false;
            }
            for (int size = this.touchDelegates.size() - 1; size >= 0; size--) {
                XTouchDelegate xTouchDelegate = this.touchDelegates.get(size);
                View delegateViewHold = xTouchDelegate.getDelegateViewHold();
                if (delegateViewHold == null || delegateViewHold.getVisibility() == 0) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    boolean onTouchEvent = xTouchDelegate.onTouchEvent(motionEvent);
                    motionEvent.setLocation(x, y);
                    if (onTouchEvent) {
                        this.currentTouchDelegate = xTouchDelegate;
                        return true;
                    }
                }
            }
            return false;
        }
        TouchDelegate touchDelegate = this.currentTouchDelegate;
        if (touchDelegate != null && touchDelegate.onTouchEvent(motionEvent)) {
            z = true;
        }
        if (actionMasked == 1 || actionMasked == 32) {
            this.currentTouchDelegate = null;
        }
        return z;
    }
}
