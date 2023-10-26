package com.irdeto.securesdk;

import com.xiaopeng.lib.framework.moduleinterface.carcontroller.IInputController;
import com.xiaopeng.lib.framework.moduleinterface.netchannelmodule.remotestorage.StorageException;

/* compiled from: ISFErrorType.java */
/* loaded from: classes.dex */
public enum O000000o {
    ISF_MGR_NONE(0, "no error"),
    ISF_MGR_UNKNOWN(1, "Unknown Error"),
    ISF_MGR_INVALID_PARAMETER(2, "Parameter is invalid"),
    ISF_MGR_ERROR_INT(3, "Initialization failed"),
    ISF_MGR_ALREADY_INITIALIZED(4, "Already initialized"),
    ISF_MGR_NOT_PROVISIONED(5, "Not provisioned"),
    ISF_MGR_INVALID_PROVISION_RESPONSE(6, "Invalid provision response"),
    ISF_MGR_NOT_SUPPORTED(7, "NOT supported"),
    ISF_MGR_NO_PERMISSION(8, "NO permission"),
    ISF_MGR_TOKEN_SAVE_FAILED(9, "Token Save Failed"),
    ISF_MGR_ROOTDETECTED(10, "Root Detected"),
    ISF_MGR_TOKEN_OPERATE_FAILED(11, "Token Operate Failed"),
    ISF_MGR_GENERATE_OPAQUE_DATA_FAILED(12, "Generate Opaque Data Failed"),
    ISF_MGR_SECURE_SDK_INITIALIZE_FAILED(13, "Secure sdk initialize failed"),
    ISF_MGR_SHARED_SECURE_STORE_INVALID(14, "Invalid shared secure store"),
    ISF_MGR_GE_APP_ENV_ERROR(15, "Application flag error"),
    ISF_MGR_LOCAL_SECURE_STORE_INVALID(16, "Invalid local secure store"),
    ISF_CRD_NONE(256, "no error"),
    ISF_IS_NONE(512, "no error"),
    ISF_IS_HELP(513, "Invalid individual helper"),
    ISF_IS_UUID(514, "Invalid UUID of SS2"),
    ISF_IS_CTXP(515, "Invalid preconfig context"),
    ISF_IS_CTX1(IInputController.KEYCODE_LEFT_OK_BUTTON, "Invalid store context of ss1"),
    ISF_IS_CTX2(IInputController.KEYCODE_RIGHT_OK_BUTTON, "Invalid store context of ss2"),
    ISF_IS_PTXT(518, "Invalid plain provision request"),
    ISF_IS_CNFP(519, "Invalid preconfig pub key"),
    ISF_IS_JWEC(IInputController.KEYCODE_KNOB_VOL_UP, "Invalid JWE compact data of response"),
    ISF_IS_JWSC(IInputController.KEYCODE_KNOB_VOL_DOWN, "Invalid JWS compact data of response"),
    ISF_IS_ESTR(522, "Failed to decode response"),
    ISF_IS_HTTP(523, "Failed to send http request"),
    ISF_IS_RESP(IInputController.KEYCODE_WIND_EXIT_MODE, "Failed to get response from IS server"),
    ISF_IS_JFMT(525, "Invalid JSON format of response"),
    ISF_IS_JDAT(IInputController.KEYCODE_KNOB_USB_MUSIC, "Invalid JSON sdata object"),
    ISF_IS_JVAL(IInputController.KEYCODE_KNOB_TALKING_BOOK, "Invalid JSON sdata value"),
    ISF_IS_JWSS(528, "Invalid JWS signature"),
    ISF_IS_ECCD(529, "Failed to decoding ECC KW body"),
    ISF_IS_SAV1(530, "Invalid parameters (data or path) of saving ss2"),
    ISF_IS_SAV2(531, "Invalid data length of saving ss2"),
    ISF_IS_SAV3(532, "Failed to open saving file"),
    ISF_IS_SAV4(533, "Failed to writing data into file"),
    ISF_CS_NONE(768, "no error"),
    ISF_CS_DECRYPT_FAILED(StorageException.REASON_GET_TOKEN_ERROR, "decrypt failed"),
    ISF_CS_ENCRYPT_FAILED(770, "encrypt failed"),
    ISF_SIPC_NOT_INIT(StorageException.REASON_DOWNLOAD_ERROR, "Secure IPC is uninitialized"),
    ISF_SIPC_INIT_FAIL(StorageException.REASON_DOWNLOAD_INCOMPLETE, "Secure IPC initialization failed"),
    ISF_SIPC_OBJ_SRL_FAIL(1283, "Secure IPC object serialization failed"),
    ISF_SIPC_OBJ_PRT_FAIL(1284, "Secure IPC object protection failed"),
    ISF_SIPC_OBJ_DSRL_FAIL(1285, "Secure IPC object deserialization failed"),
    ISF_SIPC_OBJ_DECRYPT_FAIL(1286, "Secure IPC object decryption failed"),
    ISF_SS_CREATE_FAILED(StorageException.REASON_EXCEED_TRAFFIC_QUOTA, "Create secure store failed"),
    ISF_SS_WRITE_FAILED(1538, "Save data to secure store failed"),
    ISF_SS_READ_FAILED(1539, "Get data from secure store failed"),
    ISF_SS_LOAD_SS_FAILED(1540, "Load secure store failed"),
    ISF_SS_INVALID_SS_PATH(1541, "Invalid secure store path");
    
    private int O000OooO;
    private String O000Oooo;

    O000000o(int i, String str) {
        this.O000OooO = i;
        this.O000Oooo = str;
    }

    public static O000000o O000000o(int i) {
        O000000o[] values;
        for (O000000o o000000o : values()) {
            if (o000000o.O000000o() == i) {
                return o000000o;
            }
        }
        throw new IllegalArgumentException("Invalid MediaEventType:" + i);
    }

    public static O000000o O00000Oo(int i) {
        return O000000o(Math.abs(i) | ISF_IS_NONE.O000000o());
    }

    public int O000000o() {
        return this.O000OooO;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.O000Oooo;
    }
}
