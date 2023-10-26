package com.xiaopeng.speech.protocol.node.camera;

import com.xiaopeng.speech.SpeechNode;
import com.xiaopeng.speech.protocol.bean.ChangeValue;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class CameraNode extends SpeechNode<CameraListener> {
    /* JADX INFO: Access modifiers changed from: protected */
    public void onOverallOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        boolean z = true;
        try {
            z = new JSONObject(str2).optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onOverallOn(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearTake(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        boolean z = true;
        try {
            z = new JSONObject(str2).optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onRearTake(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearRecord(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        boolean z = true;
        try {
            z = new JSONObject(str2).optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onRearRecord(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFrontTake(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onFrontTake();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFrontRecord(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onFrontRecord();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftTake(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onLeftTake();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftRecord(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onLeftRecord();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightTake(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onRightTake();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightRecord(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onRightRecord();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onLeftOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onLeftOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRightOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onRightOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onRearOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearOnNew(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onRearOnNew();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onFrontOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onFrontOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onRearOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onRearOff();
            }
        }
    }

    protected void onCarcorderTake(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onCarcorderTake();
            }
        }
    }

    protected void onCarcorderLock(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onCarcorderLock();
            }
        }
    }

    protected void onCarcorderRecord(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onCarcorderRecord();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        boolean z = true;
        try {
            z = new JSONObject(str2).optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onTopOff(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        boolean z = true;
        try {
            z = new JSONObject(str2).optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onTopOn(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopTake(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onTopTake();
            }
        }
    }

    protected void onTopRecord(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onTopRecord();
            }
        }
    }

    protected void onTopRecordEnd(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onTopRecordEnd();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopRotateLeft(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        boolean z = true;
        try {
            z = new JSONObject(str2).optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onTopRotateLeft(fromJson, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopRotateRight(String str, String str2) {
        ChangeValue fromJson = ChangeValue.fromJson(str2);
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        boolean z = true;
        try {
            z = new JSONObject(str2).optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onTopRotateRight(fromJson, z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopRotateFront(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        boolean z = true;
        try {
            z = new JSONObject(str2).optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onTopRotateFront(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTopRotateRear(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        boolean z = true;
        try {
            z = new JSONObject(str2).optBoolean("isTTS", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onTopRotateRear(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onTransparentChassisCMD(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onTransparentChassisCMD(str2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCameraThreeDOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onCameraThreeDOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCameraThreeDOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onCameraThreeDOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCameraTransparentChassisOn(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onCameraTransparentChassisOn();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCameraTransparentChassisOff(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onCameraTransparentChassisOff();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void onCameraPhotoalbumOpen(String str, String str2) {
        Object[] collectCallbacks = this.mListenerList.collectCallbacks();
        if (collectCallbacks != null) {
            for (Object obj : collectCallbacks) {
                ((CameraListener) obj).onCameraPhotoalbumOpen();
            }
        }
    }
}
