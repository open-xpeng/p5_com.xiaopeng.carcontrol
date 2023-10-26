package com.xiaopeng.speech.proxy;

import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ISoundLockState;
import com.xiaopeng.speech.ISpeechEngine;

/* loaded from: classes2.dex */
public class SoundLockStateProxy extends ISoundLockState.Stub implements ConnectManager.OnConnectCallback {
    private ISoundLockState mSoundLockState;

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onConnect(ISpeechEngine iSpeechEngine) {
        try {
            this.mSoundLockState = iSpeechEngine.getSoundLockState();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
    public void onDisconnect() {
        this.mSoundLockState = null;
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public int getDriveSoundLocation() {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                return iSoundLockState.getDriveSoundLocation();
            } catch (RemoteException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void setDriveSoundLocation(int i) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.setDriveSoundLocation(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void setSoundLocationAngle(int i) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.setSoundLocationAngle(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public int getSoundLocationAngle() {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                return iSoundLockState.getSoundLocationAngle();
            } catch (RemoteException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public boolean isDefaultEnableSoundLocation() {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                return iSoundLockState.isDefaultEnableSoundLocation();
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void setDefaultSoundLocationEnabled(boolean z) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.setDefaultSoundLocationEnabled(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void setSupportSoundLock(boolean z) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.setSupportSoundLock(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public boolean isSupportSoundLock() {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                return iSoundLockState.isSupportSoundLock();
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public int getSoundSourceAngle() {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                return iSoundLockState.getSoundSourceAngle();
            } catch (RemoteException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void setVoiceLockAngle(int i, int i2) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.setVoiceLockAngle(i, i2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void setDspMode(int i) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.setDspMode(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public int getDspMode() {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                return iSoundLockState.getDspMode();
            } catch (RemoteException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void setData(int i, long j, int i2) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.setData(i, j, i2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void setDefaultSoundLockEnabled(boolean z) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.setDefaultSoundLockEnabled(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public boolean isDefaultEnableSoundLock() {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                return iSoundLockState.isDefaultEnableSoundLock();
            } catch (RemoteException e) {
                e.printStackTrace();
                return true;
            }
        }
        return true;
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void lockSoundLocation(int i) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.lockSoundLocation(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void lockSoundLocationByWakeup(int i) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.lockSoundLocationByWakeup(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void setMode(int i) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.setMode(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public int getMode() {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                return iSoundLockState.getMode();
            } catch (RemoteException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void setIsNeedResetSoundLock(boolean z) {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.setIsNeedResetSoundLock(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public boolean isVoicePositionSet() {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                return iSoundLockState.isVoicePositionSet();
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    @Override // com.xiaopeng.speech.ISoundLockState
    public void initSoundConfig() {
        ISoundLockState iSoundLockState = this.mSoundLockState;
        if (iSoundLockState != null) {
            try {
                iSoundLockState.initSoundConfig();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
