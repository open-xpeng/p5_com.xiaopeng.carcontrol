package com.xiaopeng.carcontrol.direct.action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaopeng.carcontrol.GlobalConstant;
import com.xiaopeng.carcontrol.util.LogUtils;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

/* loaded from: classes2.dex */
public class SpaceCapsuleAction implements PageAction {
    @Override // com.xiaopeng.carcontrol.direct.action.PageAction
    public void doAction(Context context, Uri uri) {
        LogUtils.i("SpaceCapsuleAction", "space capsule uri:" + uri.getPath());
        Intent intent = new Intent(GlobalConstant.ACTION.ACTION_SPACE_CAPSULE);
        if ("/space_capsule_sleep".equals(uri.getPath())) {
            intent.putExtra(GlobalConstant.EXTRA.KEY_MODE_TYPE, 1);
        } else if ("/space_capsule_cinema".equals(uri.getPath())) {
            intent.putExtra(GlobalConstant.EXTRA.KEY_MODE_TYPE, 2);
        }
        intent.setFlags(ClientDefaults.MAX_MSG_SIZE);
        context.startActivity(intent);
    }
}
