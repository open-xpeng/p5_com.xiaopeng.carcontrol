package com.alibaba.mtl.log.sign;

/* loaded from: classes.dex */
public interface IRequestAuth {
    String getAppkey();

    String getSign(String str);
}
