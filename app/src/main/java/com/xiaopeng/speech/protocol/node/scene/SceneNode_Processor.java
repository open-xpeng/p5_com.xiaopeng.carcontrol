package com.xiaopeng.speech.protocol.node.scene;

import com.xiaopeng.speech.annotation.ICommandProcessor;
import com.xiaopeng.speech.protocol.event.VuiEvent;

/* loaded from: classes2.dex */
public class SceneNode_Processor implements ICommandProcessor {
    private SceneNode mTarget;

    public SceneNode_Processor(SceneNode sceneNode) {
        this.mTarget = sceneNode;
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public void performCommand(String str, String str2) {
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -1722601438:
                if (str.equals(VuiEvent.SCENE_VUI_DISABLE)) {
                    c = 0;
                    break;
                }
                break;
            case -1546876663:
                if (str.equals(VuiEvent.SCENE_VUI_ENABLE)) {
                    c = 1;
                    break;
                }
                break;
            case 720644575:
                if (str.equals(VuiEvent.SCENE_DM_START)) {
                    c = 2;
                    break;
                }
                break;
            case 828241388:
                if (str.equals(VuiEvent.SCENE_CONTROL)) {
                    c = 3;
                    break;
                }
                break;
            case 1045003449:
                if (str.equals("scene.rebuild")) {
                    c = 4;
                    break;
                }
                break;
            case 1301293464:
                if (str.equals(VuiEvent.SCENE_DM_END)) {
                    c = 5;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
                this.mTarget.onVuiDisable(str, str2);
                return;
            case 1:
                this.mTarget.onVuiEnable(str, str2);
                return;
            case 2:
                this.mTarget.onDMStart(str, str2);
                return;
            case 3:
                this.mTarget.onSceneEvent(str, str2);
                return;
            case 4:
                this.mTarget.onRebuild(str, str2);
                return;
            case 5:
                this.mTarget.onDMEnd(str, str2);
                return;
            default:
                return;
        }
    }

    @Override // com.xiaopeng.speech.annotation.ICommandProcessor
    public String[] getSubscribeEvents() {
        return new String[]{VuiEvent.SCENE_CONTROL, VuiEvent.SCENE_DM_START, VuiEvent.SCENE_DM_END, VuiEvent.SCENE_VUI_ENABLE, VuiEvent.SCENE_VUI_DISABLE, "scene.rebuild"};
    }
}
