package com.xiaopeng.speech.speechwidget;

/* loaded from: classes2.dex */
public class ContentWidget extends SpeechWidget {
    public ContentWidget() {
        super("content");
    }

    public ContentWidget setTitle(String str) {
        return (ContentWidget) super.addContent(SpeechWidget.WIDGET_TITLE, str);
    }

    public ContentWidget setSubTitle(String str) {
        return (ContentWidget) super.addContent(SpeechWidget.WIDGET_SUBTITLE, str);
    }

    public ContentWidget setLabel(String str) {
        return (ContentWidget) super.addContent("label", str);
    }

    public ContentWidget setImageUrl(String str) {
        return (ContentWidget) super.addContent(SpeechWidget.WIDGET_IMAGEURL, str);
    }

    public String getLinkUrl() {
        return getContent(SpeechWidget.WIDGET_LINKURL);
    }

    public ContentWidget setLinkUrl(String str) {
        return (ContentWidget) super.addContent(SpeechWidget.WIDGET_LINKURL, str);
    }

    @Override // com.xiaopeng.speech.speechwidget.SpeechWidget
    public ContentWidget addExtra(String str, String str2) {
        return (ContentWidget) super.addExtra(str, str2);
    }

    public ContentWidget setOnClickEvent(String str) {
        return addExtra("extraEvent", str);
    }
}
