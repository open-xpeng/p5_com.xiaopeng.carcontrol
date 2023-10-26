package com.xiaopeng.lib.framework.netchannelmodule.http;

import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IRequest;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.GetRequestAdapter;
import com.xiaopeng.lib.framework.netchannelmodule.http.okgoadapter.PostRequestAdapter;
import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi.BizConstants;
import com.xiaopeng.lib.framework.netchannelmodule.http.xmart.bizapi.BizRequestBuilder;
import java.util.Map;

/* loaded from: classes2.dex */
public final class XmartBizHelper implements IBizHelper {
    private BizRequestBuilder mBizRequestBuilder = null;

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public /* bridge */ /* synthetic */ IBizHelper needAuthorizationInfo(Map map) {
        return needAuthorizationInfo((Map<String, String>) map);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper get(String str) {
        this.mBizRequestBuilder = new BizRequestBuilder(new GetRequestAdapter(str), BizConstants.METHOD.GET);
        return this;
    }

    public XmartBizHelper get(String str, Map<String, String> map) {
        GetRequestAdapter getRequestAdapter = new GetRequestAdapter(str);
        this.mBizRequestBuilder = new BizRequestBuilder(getRequestAdapter, BizConstants.METHOD.GET);
        getRequestAdapter.params(map, true);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper post(String str, String str2) {
        BizRequestBuilder bizRequestBuilder = new BizRequestBuilder(new PostRequestAdapter(str), BizConstants.METHOD.POST);
        this.mBizRequestBuilder = bizRequestBuilder;
        bizRequestBuilder.body(str2);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper enableIrdetoEncoding() {
        this.mBizRequestBuilder.enableIrdetoEncoding();
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public IBizHelper enableSecurityEncoding() {
        this.mBizRequestBuilder.enableSecurityEncoding();
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper needAuthorizationInfo() {
        this.mBizRequestBuilder.needAuthorizationInfo(null);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper needAuthorizationInfo(Map<String, String> map) {
        this.mBizRequestBuilder.needAuthorizationInfo(map);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper appId(String str) {
        this.mBizRequestBuilder.appId(str);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper uid(String str) {
        this.mBizRequestBuilder.uid(str);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper extendBizHeader(String str, String str2) {
        this.mBizRequestBuilder.setExtHeader(str, str2);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public XmartBizHelper customTokensForAuth(String[] strArr) {
        this.mBizRequestBuilder.customTokensForAuth(strArr);
        return this;
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public IRequest build() {
        return this.mBizRequestBuilder.build(null);
    }

    @Override // com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.http.IBizHelper
    public IRequest buildWithSecretKey(String str) {
        return this.mBizRequestBuilder.build(str);
    }
}
