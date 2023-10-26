package com.xiaopeng.carcontrol.bean.xpilot;

/* loaded from: classes.dex */
public abstract class XPilotItem<T> {
    public static final String KEY_ALC = "alc";
    public static final String KEY_AUTO_PARK = "auto_park";
    public static final String KEY_AVH = "avh";
    public static final String KEY_BSD = "bsd";
    public static final String KEY_CATE_AUXILIARY_SAFE = "cat_auxiliary_safe";
    public static final String KEY_CATE_DRIVE = "cat_drive";
    public static final String KEY_CATE_OTHER_SETTINGS = "cat_other_settings";
    public static final String KEY_CATE_PARK = "cat_park";
    public static final String KEY_CATE_SAFE = "cat_safe";
    public static final String KEY_CNGP = "cngp";
    public static final String KEY_CNGP_MAP = "cngp_map";
    public static final String KEY_DOW = "dow";
    public static final String KEY_EBW = "ebw";
    public static final String KEY_ELK = "elk";
    public static final String KEY_EMPTY = "key_empty";
    public static final String KEY_ESP = "esp";
    public static final String KEY_FCW = "fcw";
    static final String KEY_FOOTER = "footer";
    public static final String KEY_HDC = "hdc";
    static final String KEY_HEADER = "header";
    public static final String KEY_ISLA = "isla";
    public static final String KEY_ISLA_SETTING = "isla_setting";
    public static final String KEY_ISLC = "islc";
    public static final String KEY_LCC = "lcc";
    public static final String KEY_LDW = "ldw";
    public static final String KEY_LKA = "lka";
    public static final String KEY_LSS = "lss";
    public static final String KEY_LSS_SEN = "lss_sen";
    public static final String KEY_MEM_PARK = "mem_park";
    public static final String KEY_NGP = "ngp";
    public static final String KEY_NGP_SETTING = "ngp_setting";
    public static final String KEY_NRA = "nra";
    public static final String KEY_RAEB = "raeb";
    public static final String KEY_RCTA = "rcta";
    public static final String KEY_RCW = "rcw";
    public static final String KEY_SIMPLE_SAS = "simple_sas";
    public static final String KEY_SPECIAL_SAS = "special_sas";
    public static final String KEY_SPECIAL_SAS_SETTING = "special_sas_setting";
    public static final String KEY_SSA = "ssa";
    public static final String KEY_TTS = "tts";
    public static final String KEY_XPILOT_TTS = "xpilot_tts";
    public static final String KEY_XPU_LOW_POWER = "xpu_low_power";
    public static final int TYPE_CARD = 2;
    public static final int TYPE_CATE = 1;
    public static final int TYPE_EMPTY_CARD = 6;
    public static final int TYPE_FOOTER = 5;
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_TAB = 3;
    private int mDescResId;
    private int mDrawableResId;
    private boolean mEnable;
    private boolean mFunAvailable;
    int mHelpPic1ResId;
    int mHelpPic2ResId;
    String mHelpTxt1;
    String mHelpTxt2;
    private final int mIndex;
    private boolean mIsTestFunc;
    private boolean mIsVuiEnable;
    private final String mKey;
    private final int mKeywordId;
    private final int mManualIdx;
    private int mTestDrawableResId;
    protected int mTitleResId;
    private final int mType;
    private T mValue;

    public XPilotItem(int index, String key, int type, int titleResId, int descResId, int drawableResId, int manualIdx) {
        this(index, key, type, titleResId, descResId, drawableResId, manualIdx, 0);
    }

    public XPilotItem(int index, String key, int type, int titleResId, int descResId, int drawableResId, int manualIdx, boolean funAvailable) {
        this(index, key, type, titleResId, descResId, drawableResId, manualIdx, 0, funAvailable);
    }

    public XPilotItem(int index, String key, int type, int titleResId, int descResId, int drawableResId, int manualIdx, int keywordId) {
        this(index, key, type, titleResId, descResId, drawableResId, manualIdx, keywordId, true);
    }

    public XPilotItem(int index, String key, int type, int titleResId, int descResId, int drawableResId, int manualIdx, int keywordId, boolean funAvailable) {
        this.mEnable = true;
        this.mIsTestFunc = false;
        this.mTestDrawableResId = 0;
        this.mIsVuiEnable = true;
        this.mIndex = index;
        this.mKey = key;
        this.mType = type;
        this.mTitleResId = titleResId;
        this.mDescResId = descResId;
        this.mDrawableResId = drawableResId;
        this.mManualIdx = manualIdx;
        this.mKeywordId = keywordId;
        this.mFunAvailable = funAvailable;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public String getKey() {
        return this.mKey;
    }

    public int getType() {
        return this.mType;
    }

    public int getTitleResId() {
        return this.mTitleResId;
    }

    public void setTitleResId(int titleResId) {
        this.mTitleResId = titleResId;
    }

    public int getDescResId() {
        return this.mDescResId;
    }

    public void setDescResId(int resId) {
        this.mDescResId = resId;
    }

    public int getDrawableResId() {
        return this.mDrawableResId;
    }

    public void setDrawableResId(int drawableResId) {
        this.mDrawableResId = drawableResId;
    }

    public int getHelpPic1ResId() {
        return this.mHelpPic1ResId;
    }

    public void setHelpPic1ResId(int picResId) {
        this.mHelpPic1ResId = picResId;
    }

    public String getHelpTxt1() {
        return this.mHelpTxt1;
    }

    public void setHelpTxt1(String txtResId) {
        this.mHelpTxt1 = txtResId;
    }

    public int getHelpPic2ResId() {
        return this.mHelpPic2ResId;
    }

    public String getHelpTxt2() {
        return this.mHelpTxt2;
    }

    public int getManualIdx() {
        return this.mManualIdx;
    }

    public T getValue() {
        return this.mValue;
    }

    public void setValue(T value) {
        this.mValue = value;
    }

    public boolean isEnabled() {
        return this.mEnable;
    }

    public void setEnable(boolean enable) {
        this.mEnable = enable;
    }

    public int getKeywordId() {
        return this.mKeywordId;
    }

    public boolean isFunAvailable() {
        return this.mFunAvailable;
    }

    public void setFunAvailable(boolean funAvailable) {
        this.mFunAvailable = funAvailable;
    }

    public boolean isIsTestFunc() {
        return this.mIsTestFunc;
    }

    public void setIsTestFunc(boolean isTestFunc) {
        this.mIsTestFunc = isTestFunc;
    }

    public int getTestDrawableResId() {
        return this.mTestDrawableResId;
    }

    public void setTestDrawableResId(int resourceId) {
        this.mTestDrawableResId = resourceId;
    }

    public boolean isVuiEnable() {
        return this.mIsVuiEnable;
    }

    public void setIsVuiEnable(boolean isVuiEnable) {
        this.mIsVuiEnable = isVuiEnable;
    }

    public int getVuiLabelResId() {
        return this.mTitleResId;
    }
}
