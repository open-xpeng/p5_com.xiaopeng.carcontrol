package com.xiaopeng.xui.view.touch;

import android.graphics.Rect;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.xiaopeng.xui.utils.XLogUtils;

/* loaded from: classes2.dex */
public class XTouchTargetUtils {
    private static final Rect HIT_RECT = new Rect();
    private static final String TAG = "xpui-touch";

    /* JADX INFO: Access modifiers changed from: private */
    public static void log(String str) {
    }

    public static void extendViewTouchTarget(final View view, final int i, final int i2, final int i3, final int i4, final int i5) {
        view.post(new Runnable() { // from class: com.xiaopeng.xui.view.touch.XTouchTargetUtils.1
            @Override // java.lang.Runnable
            public void run() {
                XTouchTargetUtils.extendViewTouchTarget(view, XTouchTargetUtils.findViewAncestor(view, i), i2, i3, i4, i5);
            }
        });
    }

    public static void extendViewTouchTarget(final View view, final View view2, final int i, final int i2, final int i3, final int i4) {
        if (view == null || view2 == null) {
            return;
        }
        view2.post(new Runnable() { // from class: com.xiaopeng.xui.view.touch.XTouchTargetUtils.2
            @Override // java.lang.Runnable
            public void run() {
                if (!view.isAttachedToWindow()) {
                    XTouchTargetUtils.log("not isAttachedToWindow " + hashCode());
                    return;
                }
                view.getHitRect(XTouchTargetUtils.HIT_RECT);
                if (XTouchTargetUtils.HIT_RECT.width() == 0 || XTouchTargetUtils.HIT_RECT.height() == 0) {
                    XTouchTargetUtils.log(" width or height == 0 " + hashCode());
                    LayoutAttachStateChangeListener layoutAttachStateChangeListener = new LayoutAttachStateChangeListener(view2, i, i2, i3, i4);
                    view.addOnLayoutChangeListener(layoutAttachStateChangeListener);
                    view.addOnAttachStateChangeListener(layoutAttachStateChangeListener);
                    return;
                }
                Rect rect = new Rect();
                rect.set(XTouchTargetUtils.HIT_RECT);
                ViewParent parent = view.getParent();
                while (parent != view2) {
                    if (!(parent instanceof View)) {
                        return;
                    }
                    View view3 = (View) parent;
                    view3.getHitRect(XTouchTargetUtils.HIT_RECT);
                    rect.offset(XTouchTargetUtils.HIT_RECT.left, XTouchTargetUtils.HIT_RECT.top);
                    parent = view3.getParent();
                }
                rect.left -= i;
                rect.top -= i2;
                rect.right += i3;
                rect.bottom += i4;
                XTouchDelegate xTouchDelegate = new XTouchDelegate(rect, view);
                XTouchDelegateGroup orCreateTouchDelegateGroup = XTouchTargetUtils.getOrCreateTouchDelegateGroup(view2);
                orCreateTouchDelegateGroup.addTouchDelegate(xTouchDelegate);
                view2.setTouchDelegate(orCreateTouchDelegateGroup);
                view.addOnAttachStateChangeListener(new MyStateChangeListener(xTouchDelegate, orCreateTouchDelegateGroup));
            }
        });
    }

    /* loaded from: classes2.dex */
    private static class LayoutAttachStateChangeListener implements View.OnLayoutChangeListener, View.OnAttachStateChangeListener {
        private static int msCount;
        private View ancestor;
        int bottom;
        int left;
        int right;
        int top;

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        LayoutAttachStateChangeListener(View view, int i, int i2, int i3, int i4) {
            this.ancestor = view;
            this.left = i;
            this.top = i2;
            this.right = i3;
            this.bottom = i4;
            msCount++;
        }

        protected void finalize() throws Throwable {
            super.finalize();
            int i = msCount - 1;
            msCount = i;
            if (i == 0) {
                XLogUtils.d(XTouchTargetUtils.TAG, " LayoutAttachStateChangeListener  finalize " + msCount);
            }
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (view.getWidth() <= 0 || view.getHeight() <= 0) {
                return;
            }
            XTouchTargetUtils.extendViewTouchTarget(view, this.ancestor, this.left, this.top, this.right, this.bottom);
            this.ancestor = null;
            view.removeOnLayoutChangeListener(this);
            view.removeOnAttachStateChangeListener(this);
            XTouchTargetUtils.log(" LayoutAttachStateChangeListener  onLayoutChange ");
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            view.removeOnLayoutChangeListener(this);
            view.removeOnAttachStateChangeListener(this);
            XTouchTargetUtils.log(" LayoutAttachStateChangeListener  onViewDetachedFromWindow ");
        }
    }

    public static void extendTouchAreaAsParentSameSize(final View view, final ViewGroup viewGroup) {
        if (view == null || viewGroup == null) {
            return;
        }
        view.post(new Runnable() { // from class: com.xiaopeng.xui.view.touch.XTouchTargetUtils.3
            @Override // java.lang.Runnable
            public void run() {
                if (!view.isAttachedToWindow()) {
                    XTouchTargetUtils.log(" as not isAttachedToWindow " + hashCode());
                } else if (viewGroup.getWidth() == 0 || viewGroup.getHeight() == 0) {
                    XTouchTargetUtils.log(" as width or height == 0 " + hashCode());
                    LayoutAttachStateChangeListener2 layoutAttachStateChangeListener2 = new LayoutAttachStateChangeListener2(viewGroup);
                    view.addOnLayoutChangeListener(layoutAttachStateChangeListener2);
                    view.addOnAttachStateChangeListener(layoutAttachStateChangeListener2);
                } else {
                    XTouchDelegate xTouchDelegate = new XTouchDelegate(new Rect(0, 0, viewGroup.getWidth(), viewGroup.getHeight()), view);
                    XTouchDelegateGroup orCreateTouchDelegateGroup = XTouchTargetUtils.getOrCreateTouchDelegateGroup(viewGroup);
                    orCreateTouchDelegateGroup.addTouchDelegate(xTouchDelegate);
                    viewGroup.setTouchDelegate(orCreateTouchDelegateGroup);
                    view.addOnAttachStateChangeListener(new MyStateChangeListener(xTouchDelegate, orCreateTouchDelegateGroup));
                }
            }
        });
    }

    /* loaded from: classes2.dex */
    private static class LayoutAttachStateChangeListener2 implements View.OnLayoutChangeListener, View.OnAttachStateChangeListener {
        private static int msCount;
        private ViewGroup ancestor;

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        LayoutAttachStateChangeListener2(ViewGroup viewGroup) {
            this.ancestor = viewGroup;
            msCount++;
        }

        protected void finalize() throws Throwable {
            super.finalize();
            int i = msCount - 1;
            msCount = i;
            if (i == 0) {
                XLogUtils.d(XTouchTargetUtils.TAG, " LayoutAttachStateChangeListener2  finalize " + msCount);
            }
        }

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (view.getWidth() <= 0 || view.getHeight() <= 0) {
                return;
            }
            XTouchTargetUtils.extendTouchAreaAsParentSameSize(view, this.ancestor);
            this.ancestor = null;
            view.removeOnLayoutChangeListener(this);
            view.removeOnAttachStateChangeListener(this);
            XTouchTargetUtils.log(" LayoutAttachStateChangeListener2  onLayoutChange ");
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            view.removeOnLayoutChangeListener(this);
            view.removeOnAttachStateChangeListener(this);
            XTouchTargetUtils.log(" LayoutAttachStateChangeListener2  onViewDetachedFromWindow ");
        }
    }

    /* loaded from: classes2.dex */
    private static class MyStateChangeListener implements View.OnAttachStateChangeListener {
        private static int msCount;
        private XTouchDelegate touchDelegate;
        private XTouchDelegateGroup touchDelegateGroup;

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view) {
        }

        MyStateChangeListener(XTouchDelegate xTouchDelegate, XTouchDelegateGroup xTouchDelegateGroup) {
            this.touchDelegate = xTouchDelegate;
            this.touchDelegateGroup = xTouchDelegateGroup;
            msCount++;
        }

        protected void finalize() throws Throwable {
            super.finalize();
            int i = msCount - 1;
            msCount = i;
            if (i == 0) {
                XLogUtils.d(XTouchTargetUtils.TAG, " MyStateChangeListener finalize " + msCount);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view) {
            view.removeOnAttachStateChangeListener(this);
            this.touchDelegateGroup.removeTouchDelegate(this.touchDelegate);
            XTouchTargetUtils.log("  MyStateChangeListener onViewDetachedFromWindow " + view.hashCode());
            this.touchDelegateGroup = null;
            this.touchDelegate = null;
        }
    }

    public static XTouchDelegateGroup getOrCreateTouchDelegateGroup(View view) {
        TouchDelegate touchDelegate = view.getTouchDelegate();
        if (touchDelegate != null) {
            if (touchDelegate instanceof XTouchDelegateGroup) {
                return (XTouchDelegateGroup) touchDelegate;
            }
            XTouchDelegateGroup xTouchDelegateGroup = new XTouchDelegateGroup(view);
            if (touchDelegate instanceof XTouchDelegate) {
                xTouchDelegateGroup.addTouchDelegate((XTouchDelegate) touchDelegate);
            }
            return xTouchDelegateGroup;
        }
        return new XTouchDelegateGroup(view);
    }

    public static View findViewAncestor(View view, int i) {
        while (view != null && view.getId() != i) {
            if (!(view.getParent() instanceof View)) {
                return null;
            }
            view = (View) view.getParent();
        }
        return view;
    }
}
