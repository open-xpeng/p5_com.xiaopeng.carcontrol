package com.xiaopeng.carcontrol.bean.xpilot.map;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;
import java.util.HashMap;

/* loaded from: classes.dex */
public class CngpMapName {
    private static HashMap<Integer, String> cngpMapName = new HashMap<Integer, String>() { // from class: com.xiaopeng.carcontrol.bean.xpilot.map.CngpMapName.1
        {
            put(20, "广州");
            put(755, "深圳");
            put(21, "上海");
            put(571, "杭州");
            put(10, "北京");
            put(28, "成都");
            put(23, "重庆");
            put(27, "武汉");
            put(25, "南京");
            put(757, "佛山");
            put(Integer.valueOf((int) StorageException.REASON_GET_TOKEN_ERROR), "东莞");
            put(752, "惠州");
            put(756, "珠海");
            put(760, "中山");
            put(758, "肇庆");
            put(750, "江门");
        }
    };

    public static String convertIdToName(int id) {
        return cngpMapName.get(Integer.valueOf(id));
    }
}
