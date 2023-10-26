package com.xiaopeng.carcontrol.viewmodel.account.bean;

/* loaded from: classes2.dex */
public class ExamUrlResult {
    public int examState = 1;
    public String examUrl;

    /* loaded from: classes2.dex */
    public @interface ExamState {
        public static final int EXAM_URL_EXPIRED = 3;
        public static final int EXAM_URL_FAIL = 2;
        public static final int EXAM_URL_SUCCESS = 1;
    }

    public String toString() {
        return "NgpExamUrl{examUrl='" + this.examUrl + "', examState=" + this.examState + '}';
    }
}
