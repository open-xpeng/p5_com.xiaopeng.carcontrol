package com.xiaopeng.speech.jarvisproto;

/* loaded from: classes2.dex */
public class FeedUIEvent extends JarvisProto {
    public static final String BACK_PRESSED = "back.press";
    public static final String EXPLORE_CLICK = "explore.click";
    public static final String LIST_ITEM_CANCEL_FOCUS = "list.item.cancel.focus";
    public static final String LIST_ITEM_DETAIL = "list.item.detail";
    public static final String LIST_ITEM_FOCUS = "list.item.focus";
    public static final String LIST_ITEM_SCROLL = "list.item.scroll";
    public static final String LIST_ITEM_SELECT = "list.item.select";
    public static final String LIST_PAGE_SWITCH = "list.page.switch";
    public static final String LIST_ROUT_UPDATE = "list.route.update";
    public static final String LIST_ROUT_UPLOAD = "list.route.upload";
    public static final String MASK_CLICK = "mask.click";
    public static final String NAVI_CLICK = "navi.click";
    public static final String NAVI_START_STATE = "navi.start.state";
    public static final String POI_LIST_EXPEND = "widget.list.expend";
    public static final String POI_LIST_FOLD = "widget.list.fold";
    public static final String SCRIPT_CONTINUE = "scriptContinue";
    public static final String SCRIPT_QUIT = "scriptQuit";
    public String data;
    public String event;

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getEvent() {
        return String.format("context.%s", this.event);
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String getJsonData() {
        return this.data;
    }

    @Override // com.xiaopeng.speech.jarvisproto.JarvisProto
    public String toString() {
        return "FeedUIEvent{event='" + this.event + "', data='" + this.data + "'}";
    }
}
