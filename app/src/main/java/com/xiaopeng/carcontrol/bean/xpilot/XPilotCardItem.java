package com.xiaopeng.carcontrol.bean.xpilot;

import com.xiaopeng.carcontrol.util.ResUtils;

/* loaded from: classes.dex */
public class XPilotCardItem extends XPilotItem<Boolean> {
    private int mExtBtnRes;
    private boolean mNeedPurchase;
    private boolean mNeedShowSw;
    private boolean mSoundFlag;
    private int mVuiLabelResId;

    public XPilotCardItem(int index, String key, int titleResId, int descResId, int drawableResId, int manualIdx) {
        super(index, key, 2, titleResId, descResId, drawableResId, manualIdx, 0, true);
        this.mExtBtnRes = 0;
        this.mNeedPurchase = false;
        this.mNeedShowSw = true;
    }

    public XPilotCardItem(int index, String key, int titleResId, int descResId, int drawableResId, int manualIdx, int helpPic1ResId, int helpTxt1ResId, int helpPic2ResId, int helpTxt2ResId, int keywordId) {
        this(index, key, titleResId, descResId, drawableResId, manualIdx, helpPic1ResId, ResUtils.getString(helpTxt1ResId), helpPic2ResId, ResUtils.getString(helpTxt2ResId), keywordId);
    }

    public XPilotCardItem(int index, String key, int titleResId, int descResId, int drawableResId, int manualIdx, int helpPic1ResId, String helpTxt1, int helpPic2ResId, String helpTxt2, int keywordId) {
        super(index, key, 2, titleResId, descResId, drawableResId, manualIdx, keywordId, true);
        this.mExtBtnRes = 0;
        this.mNeedPurchase = false;
        this.mNeedShowSw = true;
        this.mHelpPic1ResId = helpPic1ResId;
        this.mHelpPic2ResId = helpPic2ResId;
        this.mHelpTxt1 = helpTxt1;
        this.mHelpTxt2 = helpTxt2;
    }

    public XPilotCardItem(int index, String key, int titleResId, int descResId, int drawableResId, int manualIdx, int helpPic1ResId, int helpTxt1ResId, int helpPic2ResId, int helpTxt2ResId, int keywordId, int extBtnResId) {
        this(index, key, titleResId, descResId, drawableResId, manualIdx, helpPic1ResId, helpTxt1ResId, helpPic2ResId, helpTxt2ResId, keywordId, extBtnResId, true);
    }

    public XPilotCardItem(int index, String key, int titleResId, int descResId, int drawableResId, int manualIdx, int helpPic1ResId, int helpTxt1ResId, int helpPic2ResId, int helpTxt2ResId, int keywordId, int extBtnResId, boolean showSw) {
        this(index, key, titleResId, descResId, drawableResId, manualIdx, helpPic1ResId, helpTxt1ResId, helpPic2ResId, helpTxt2ResId, keywordId, extBtnResId, showSw, 0);
    }

    public XPilotCardItem(int index, String key, int titleResId, int descResId, int drawableResId, int manualIdx, int helpPic1ResId, int helpTxt1ResId, int helpPic2ResId, int helpTxt2ResId, int keywordId, int extBtnResId, boolean showSw, int vuiResId) {
        this(index, key, titleResId, descResId, drawableResId, manualIdx, helpPic1ResId, ResUtils.getString(helpTxt1ResId), helpPic2ResId, ResUtils.getString(helpTxt2ResId), keywordId, extBtnResId, showSw, vuiResId);
    }

    public XPilotCardItem(int index, String key, int titleResId, int descResId, int drawableResId, int manualIdx, int helpPic1ResId, String helpTxt1, int helpPic2ResId, String helpTxt2, int keywordId, int extBtnResId, boolean showSw, int vuiResId) {
        super(index, key, 2, titleResId, descResId, drawableResId, manualIdx, keywordId, true);
        this.mExtBtnRes = 0;
        this.mNeedPurchase = false;
        this.mNeedShowSw = true;
        this.mHelpPic1ResId = helpPic1ResId;
        this.mHelpPic2ResId = helpPic2ResId;
        this.mExtBtnRes = extBtnResId;
        this.mNeedShowSw = showSw;
        this.mVuiLabelResId = vuiResId;
        this.mHelpTxt1 = helpTxt1;
        this.mHelpTxt2 = helpTxt2;
    }

    public void setExtBtnRes(int btnResId) {
        this.mExtBtnRes = btnResId;
    }

    public int getExtBtnRes() {
        return this.mExtBtnRes;
    }

    public boolean isNeedPurchase() {
        return this.mNeedPurchase;
    }

    public void setNeedPurchase(boolean needPurchase) {
        this.mNeedPurchase = needPurchase;
    }

    public boolean isNeedShowSw() {
        return this.mNeedShowSw;
    }

    public void setNeedShowSw(boolean needShowSw) {
        this.mNeedShowSw = needShowSw;
    }

    public void setValueWithSound(boolean value, boolean soundFlag) {
        super.setValue(Boolean.valueOf(value));
        this.mSoundFlag = soundFlag;
    }

    public void clearSoundFlag() {
        this.mSoundFlag = false;
    }

    public boolean getSoundFlag() {
        return this.mSoundFlag;
    }

    @Override // com.xiaopeng.carcontrol.bean.xpilot.XPilotItem
    public int getVuiLabelResId() {
        int i = this.mVuiLabelResId;
        return i > 0 ? i : this.mTitleResId;
    }
}
