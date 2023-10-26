package com.xiaopeng.speech.protocol.node.fm;

import com.xiaopeng.speech.annotation.ICommandProcessor;

/* loaded from: classes2.dex */
public class FMNode_Processor implements ICommandProcessor {
    private FMNode mTarget;

    public FMNode_Processor(FMNode fMNode) {
        this.mTarget = fMNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1616140812:
                if (str.equals("command://fm.local.off")) {
                    c = 0;
                    break;
                }
                break;
            case -627784393:
                if (str.equals("command://fm.network.off")) {
                    c = 1;
                    break;
                }
                break;
            case -297345769:
                if (str.equals("command://fm.network.on")) {
                    c = 2;
                    break;
                }
                break;
            case -190680902:
                if (str.equals("command://fm.local.on")) {
                    c = 3;
                    break;
                }
                break;
            case 616790465:
                if (str.equals("command://fm.play.channel")) {
                    c = 4;
                    break;
                }
                break;
            case 1124241696:
                if (str.equals("command://fm.play.collection")) {
                    c = 5;
                    break;
                }
                break;
            case 2103491480:
                if (str.equals("native://fm.play.channelname")) {
                    c = 6;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onFmLocalOff(str, str2);
                return;
            case 1:
                this.mTarget.onFmNetworkOff(str, str2);
                return;
            case 2:
                this.mTarget.onFmNetworkOn(str, str2);
                return;
            case 3:
                this.mTarget.onFmLocalOn(str, str2);
                return;
            case 4:
                this.mTarget.onFmPlayChannel(str, str2);
                return;
            case 5:
                this.mTarget.onPlayCollectFM(str, str2);
                return;
            case 6:
                this.mTarget.onFmPlayChannelName(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{"command://fm.local.on", "command://fm.local.off", "command://fm.network.on", "command://fm.network.off", "command://fm.play.channel", "native://fm.play.channelname", "command://fm.play.collection"};
    }
}
