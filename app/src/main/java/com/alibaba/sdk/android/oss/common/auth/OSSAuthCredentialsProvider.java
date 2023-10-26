package com.alibaba.sdk.android.oss.common.auth;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.common.utils.IOUtils;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class OSSAuthCredentialsProvider extends OSSFederationCredentialProvider {
    private String mAuthServerUrl;
    private AuthDecoder mDecoder;

    /* loaded from: classes.dex */
    public interface AuthDecoder {
        String decode(String str);
    }

    public OSSAuthCredentialsProvider(String str) {
        this.mAuthServerUrl = str;
    }

    public void setAuthServerUrl(String str) {
        this.mAuthServerUrl = str;
    }

    public void setDecoder(AuthDecoder authDecoder) {
        this.mDecoder = authDecoder;
    }

    @Override // com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider, com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider
    public OSSFederationToken getFederationToken() throws ClientException {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.mAuthServerUrl).openConnection();
            httpURLConnection.setConnectTimeout(10000);
            String readStreamAsString = IOUtils.readStreamAsString(httpURLConnection.getInputStream(), "utf-8");
            AuthDecoder authDecoder = this.mDecoder;
            if (authDecoder != null) {
                readStreamAsString = authDecoder.decode(readStreamAsString);
            }
            JSONObject jSONObject = new JSONObject(readStreamAsString);
            if (jSONObject.getInt("StatusCode") == 200) {
                return new OSSFederationToken(jSONObject.getString("AccessKeyId"), jSONObject.getString("AccessKeySecret"), jSONObject.getString("SecurityToken"), jSONObject.getString("Expiration"));
            }
            String string = jSONObject.getString("ErrorCode");
            throw new ClientException("ErrorCode: " + string + "| ErrorMessage: " + jSONObject.getString("ErrorMessage"));
        } catch (Exception e) {
            throw new ClientException(e);
        }
    }
}
