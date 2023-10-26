package com.xiaopeng.speech.jarvisproto;

/* loaded from: classes2.dex */
public class ErrorMsg {
    public static final int ASR_ERROR = 2003;
    public static final int ASR_ERROR_NONE = 2000;
    public static final int ASR_NULL = 2001;
    public static final int ASR_NULL_RETRY_MAX_EXIT = 2004;
    public static final int ASR_OUT_OF_CONFIDENCE = 70802;
    public static final int ASR_RESULT_IS_NULL_OFFLINE = 70801;
    public static final int ASR_RESULT_IS_NULL_ONLINE = 10305;
    public static final int ASR_VAD_TIMEOUT = 2002;
    public static final int DM_ERROR_NONE = 3000;
    public static final int DM_TIMEOUT = 3001;
    public static final String FEED_IS_TRIGGER = "feed is trigger";
    public static final int LOCAL_NLP_MAX_TRY = 80014;
    public static final int LOCAL_NLP_NOT_FOUND = 10400;
    public static final int LOCAL_NLP_UNEXPECTED = 8000;
    public static final int LOCAL_NLP_UNMATCHED = 80004;
    public static final int NLU_ERROR_NONE = 1000;
    public static final int NLU_EVENT_UN_SUBSCRIBE = 1001;
    public static final String SKILL_IS_LOCAL_MSG = "this skill is local";
    public static final int SKILL_IS_LOCAL_OR_NOT_LOCAL = 4;
    public static final String SKILL_IS_NOT_LOCAL_MSG = "this skill is not local";
    public static final String XP_NLU_ACCESS_ERROR_MSG = "access server error";
    public static final int XP_NLU_ACCESS_SERVER_ID = 3;
    public static final int XP_NLU_FEED_EMPTY_ID = 2;
    public static final String XP_NLU_FEED_EMPTY_MSG = "feed text is empty";
    public static final int XP_NLU_NETWORK_ID = 1;
    public static final String XP_NLU_NETWORK_MSG = "network is unavailable";
    public int errId;
    public String errMsg;

    public ErrorMsg() {
        this.errId = -1;
    }

    public ErrorMsg(int i) {
        this.errId = -1;
        this.errId = i;
    }

    public ErrorMsg(int i, String str) {
        this(i);
        this.errMsg = str;
    }

    public String toString() {
        return "ErrorMsg{errId=" + this.errId + ", errMsg='" + this.errMsg + "'}";
    }
}
