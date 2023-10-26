package com.xiaopeng.speech.asr;

import android.os.RemoteException;
import com.xiaopeng.speech.ConnectManager;
import com.xiaopeng.speech.ISpeechEngine;
import com.xiaopeng.speech.SpeechClient;
import com.xiaopeng.speech.common.util.IPCRunner;
import com.xiaopeng.speech.common.util.LogUtils;
import com.xiaopeng.speech.common.util.WorkerHandler;
import com.xiaopeng.speech.jarvisproto.ErrorMsg;

/* loaded from: classes2.dex */
public class Recognizer {
    public static final String ASR_BUFFER = "otp_asr_buffer";
    public static final String AUDIO_EXTERNAL = "otp_external_audio";
    public static final String AUDIO_FORMAT = "otp_audio_format";
    public static final String AUDIO_SAVE_PATH = "otp_audio_save_path";
    public static final String BOS = "otp_bos";
    public static final String DISABLE_ASR = "otp_disable_asr";
    public static final String DISABLE_VAD = "otp_disable_vad";
    public static final String ENABLE_ASR_PUNCT = "otp_enable_punct";
    public static final String EOS = "otp_eos";
    public static final int EXT_ASR_AUDIO = 2;
    public static final int EXT_VOL = 1;
    public static final int FMT_AMR = 1;
    public static final int FMT_PCM = 0;
    public static final String KEEP_AUDIO_RECORD = "otp_keep_audio_record";
    public static final String MAX_ACTIVE_TIME = "otp_max_time";
    public static final int STATE_ASR_END = 5;
    public static final int STATE_BOS = 1;
    public static final int STATE_BOS_TIMEOUT = 3;
    public static final int STATE_END = 6;
    public static final int STATE_EOS = 2;
    public static final int STATE_RECORD_END = 4;
    public static final int STATE_RECORD_START = 0;
    private final WorkerHandler handler;
    private volatile IRecognizer iRecognizer;
    private volatile RecognizeListenerImpl listenerImpl;
    private final String TAG = "Recognizer";
    private IPCRunner<IRecognizer> ipcRunner = new IPCRunner<>("IRecognizerProxy");
    private final ConnectManager.OnConnectCallback connectCallback = new ConnectManager.OnConnectCallback() { // from class: com.xiaopeng.speech.asr.Recognizer.8
        @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
        public void onConnect(ISpeechEngine iSpeechEngine) {
            LogUtils.i("Recognizer", "on connect");
            try {
                IRecognizer recognizer = iSpeechEngine.getRecognizer();
                Recognizer.this.ipcRunner.setProxy(recognizer);
                Recognizer.this.iRecognizer = recognizer;
            } catch (Throwable unused) {
                LogUtils.e("Recognizer", "on connect error");
            }
        }

        @Override // com.xiaopeng.speech.ConnectManager.OnConnectCallback
        public void onDisconnect() {
            Recognizer.this.ipcRunner.setProxy(null);
            Recognizer.this.iRecognizer = null;
        }
    };

    public Recognizer(WorkerHandler workerHandler) {
        this.handler = workerHandler;
        this.ipcRunner.setWorkerHandler(workerHandler);
    }

    public void startListening(final RecognizeListener recognizeListener) {
        this.ipcRunner.runFunc(new IPCRunner.IIPCFunc<IRecognizer, Object>() { // from class: com.xiaopeng.speech.asr.Recognizer.1
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IRecognizer iRecognizer) throws RemoteException {
                if (SpeechClient.instance().getSpeechState().isDMStarted()) {
                    SpeechClient.instance().getWakeupEngine().stopDialog();
                }
                Recognizer.this._startListening(recognizeListener);
                return null;
            }
        });
    }

    public void stopListening() {
        this.ipcRunner.runFunc(new IPCRunner.IIPCFunc<IRecognizer, Object>() { // from class: com.xiaopeng.speech.asr.Recognizer.2
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IRecognizer iRecognizer) throws RemoteException {
                Recognizer.this._stopListening();
                return null;
            }
        });
    }

    public boolean isListening() {
        try {
            return _isListening();
        } catch (Throwable th) {
            LogUtils.e("Recognizer", "error: ", th);
            return false;
        }
    }

    public void cancel() {
        this.ipcRunner.runFunc(new IPCRunner.IIPCFunc<IRecognizer, Object>() { // from class: com.xiaopeng.speech.asr.Recognizer.3
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IRecognizer iRecognizer) throws RemoteException {
                Recognizer.this._cancel();
                return null;
            }
        });
    }

    public void writeData(byte[] bArr, int i, int i2) {
        try {
            _writeData(bArr, i, i2);
        } catch (RemoteException e) {
            LogUtils.e("Recognizer", "write data error: ", e);
        }
    }

    public void setString(final String str, final String str2) {
        this.ipcRunner.runFunc(new IPCRunner.IIPCFunc<IRecognizer, Object>() { // from class: com.xiaopeng.speech.asr.Recognizer.4
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IRecognizer iRecognizer) throws RemoteException {
                Recognizer.this._setString(str, str2);
                return null;
            }
        });
    }

    public void setInt(final String str, final int i) {
        this.ipcRunner.runFunc(new IPCRunner.IIPCFunc<IRecognizer, Object>() { // from class: com.xiaopeng.speech.asr.Recognizer.5
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IRecognizer iRecognizer) throws RemoteException {
                Recognizer.this._setInt(str, i);
                return null;
            }
        });
    }

    public void setBool(final String str, final boolean z) {
        this.ipcRunner.runFunc(new IPCRunner.IIPCFunc<IRecognizer, Object>() { // from class: com.xiaopeng.speech.asr.Recognizer.6
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IRecognizer iRecognizer) throws RemoteException {
                Recognizer.this._setBool(str, z);
                return null;
            }
        });
    }

    public void setDouble(final String str, final double d) {
        this.ipcRunner.runFunc(new IPCRunner.IIPCFunc<IRecognizer, Object>() { // from class: com.xiaopeng.speech.asr.Recognizer.7
            @Override // com.xiaopeng.speech.common.util.IPCRunner.IIPCFunc
            public Object run(IRecognizer iRecognizer) throws RemoteException {
                Recognizer.this._setDouble(str, d);
                return null;
            }
        });
    }

    public ConnectManager.OnConnectCallback getConnectCallback() {
        return this.connectCallback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _startListening(RecognizeListener recognizeListener) throws RemoteException {
        LogUtils.i("Recognizer", "startListening with RecognizeListener");
        IRecognizer iRecognizer = this.iRecognizer;
        if (iRecognizer == null) {
            LogUtils.e("Recognizer", "service is disconnected");
            recognizeListener.onError(ErrorMsg.ASR_ERROR, "service is disconnected");
        } else if (isListening()) {
            LogUtils.e("Recognizer", "last asr still running");
            recognizeListener.onError(ErrorMsg.ASR_ERROR, "last asr still running");
        } else {
            this.listenerImpl = new RecognizeListenerImpl(recognizeListener);
            iRecognizer.startListening(this.listenerImpl);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _stopListening() throws RemoteException {
        LogUtils.i("Recognizer", "stop listening");
        IRecognizer iRecognizer = this.iRecognizer;
        if (iRecognizer == null || !isListening()) {
            return;
        }
        iRecognizer.stopListening();
    }

    private boolean _isListening() throws RemoteException {
        IRecognizer iRecognizer = this.iRecognizer;
        return iRecognizer != null && iRecognizer.isListening();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _cancel() throws RemoteException {
        LogUtils.i("Recognizer", "cancel");
        IRecognizer iRecognizer = this.iRecognizer;
        if (iRecognizer == null || !isListening()) {
            return;
        }
        iRecognizer.cancel();
    }

    private void _writeData(byte[] bArr, int i, int i2) throws RemoteException {
        IRecognizer iRecognizer = this.iRecognizer;
        if (iRecognizer == null || !isListening()) {
            return;
        }
        iRecognizer.writeData(bArr, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _setString(String str, String str2) throws RemoteException {
        IRecognizer iRecognizer = this.iRecognizer;
        if (iRecognizer != null) {
            iRecognizer.setString(str, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _setInt(String str, int i) throws RemoteException {
        IRecognizer iRecognizer = this.iRecognizer;
        if (iRecognizer != null) {
            iRecognizer.setInt(str, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _setBool(String str, boolean z) throws RemoteException {
        IRecognizer iRecognizer = this.iRecognizer;
        if (iRecognizer != null) {
            iRecognizer.setBool(str, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _setDouble(String str, double d) throws RemoteException {
        IRecognizer iRecognizer = this.iRecognizer;
        if (iRecognizer != null) {
            iRecognizer.setDouble(str, d);
        }
    }
}
