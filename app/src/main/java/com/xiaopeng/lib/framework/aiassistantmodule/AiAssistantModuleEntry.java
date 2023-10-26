package com.xiaopeng.lib.framework.aiassistantmodule;

import android.content.Context;
import com.xiaopeng.lib.framework.aiassistantmodule.interactive.InteractiveMsgService;
import com.xiaopeng.lib.framework.aiassistantmodule.notification.AINotification;
import com.xiaopeng.lib.framework.aiassistantmodule.sensor.ContextSensor;
import com.xiaopeng.lib.framework.module.IModuleEntry;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.IInteractiveMsgService;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.notification.INotification;
import com.xiaopeng.lib.framework.moduleinterface.aiassistantmodule.sensor.IContextSensor;

/* loaded from: classes2.dex */
public class AiAssistantModuleEntry implements IModuleEntry {
    private Context mContext;
    private volatile IContextSensor mContextSensor;
    private volatile INotification mINotification;
    private volatile IInteractiveMsgService mInteractiveMsgService;

    public AiAssistantModuleEntry(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Override // com.xiaopeng.lib.framework.module.IModuleEntry
    public Object get(Class cls) {
        if (cls == IInteractiveMsgService.class) {
            if (this.mInteractiveMsgService == null) {
                synchronized (this) {
                    if (this.mInteractiveMsgService == null) {
                        this.mInteractiveMsgService = new InteractiveMsgService();
                    }
                }
            }
            return this.mInteractiveMsgService;
        } else if (cls == INotification.class) {
            if (this.mINotification == null) {
                synchronized (this) {
                    if (this.mINotification == null) {
                        this.mINotification = new AINotification(this.mContext);
                    }
                }
            }
            return this.mINotification;
        } else if (cls == IContextSensor.class) {
            if (this.mContextSensor == null) {
                synchronized (this) {
                    if (this.mContextSensor == null) {
                        this.mContextSensor = new ContextSensor(this.mContext);
                    }
                }
            }
            return this.mContextSensor;
        } else {
            return null;
        }
    }
}
