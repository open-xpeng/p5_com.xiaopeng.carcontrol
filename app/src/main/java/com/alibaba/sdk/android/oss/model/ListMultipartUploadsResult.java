package com.alibaba.sdk.android.oss.model;

import android.util.Xml;
import com.alibaba.sdk.android.oss.common.utils.DateUtil;
import com.alibaba.sdk.android.oss.common.utils.OSSUtils;
import com.alibaba.sdk.android.oss.internal.ResponseMessage;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class ListMultipartUploadsResult extends OSSResult {
    private String bucketName;
    private String delimiter;
    private boolean isTruncated;
    private String keyMarker;
    private int maxUploads;
    private String nextKeyMarker;
    private String nextUploadIdMarker;
    private String prefix;
    private String uploadIdMarker;
    private List<MultipartUpload> multipartUploads = new ArrayList();
    private List<String> commonPrefixes = new ArrayList();

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketName(String str) {
        this.bucketName = str;
    }

    public String getKeyMarker() {
        return this.keyMarker;
    }

    public void setKeyMarker(String str) {
        this.keyMarker = str;
    }

    public String getUploadIdMarker() {
        return this.uploadIdMarker;
    }

    public void setUploadIdMarker(String str) {
        this.uploadIdMarker = str;
    }

    public String getNextKeyMarker() {
        return this.nextKeyMarker;
    }

    public void setNextKeyMarker(String str) {
        this.nextKeyMarker = str;
    }

    public String getNextUploadIdMarker() {
        return this.nextUploadIdMarker;
    }

    public void setNextUploadIdMarker(String str) {
        this.nextUploadIdMarker = str;
    }

    public int getMaxUploads() {
        return this.maxUploads;
    }

    public void setMaxUploads(int i) {
        this.maxUploads = i;
    }

    public boolean isTruncated() {
        return this.isTruncated;
    }

    public void setTruncated(boolean z) {
        this.isTruncated = z;
    }

    public List<MultipartUpload> getMultipartUploads() {
        return this.multipartUploads;
    }

    public void setMultipartUploads(List<MultipartUpload> list) {
        this.multipartUploads.clear();
        if (list == null || list.isEmpty()) {
            return;
        }
        this.multipartUploads.addAll(list);
    }

    public void addMultipartUpload(MultipartUpload multipartUpload) {
        this.multipartUploads.add(multipartUpload);
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String str) {
        this.delimiter = str;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public void setPrefix(String str) {
        this.prefix = str;
    }

    public List<String> getCommonPrefixes() {
        return this.commonPrefixes;
    }

    public void setCommonPrefixes(List<String> list) {
        this.commonPrefixes.clear();
        if (list == null || list.isEmpty()) {
            return;
        }
        this.commonPrefixes.addAll(list);
    }

    public void addCommonPrefix(String str) {
        this.commonPrefixes.add(str);
    }

    public ListMultipartUploadsResult parseData(ResponseMessage responseMessage) throws Exception {
        ArrayList arrayList = new ArrayList();
        XmlPullParser newPullParser = Xml.newPullParser();
        newPullParser.setInput(responseMessage.getContent(), "utf-8");
        int eventType = newPullParser.getEventType();
        MultipartUpload multipartUpload = null;
        boolean z = false;
        while (eventType != 1) {
            if (eventType == 2) {
                String name = newPullParser.getName();
                if ("Bucket".equals(name)) {
                    setBucketName(newPullParser.nextText());
                } else if ("Delimiter".equals(name)) {
                    setDelimiter(newPullParser.nextText());
                } else if ("Prefix".equals(name)) {
                    if (z) {
                        String nextText = newPullParser.nextText();
                        if (!OSSUtils.isEmptyString(nextText)) {
                            addCommonPrefix(nextText);
                        }
                    } else {
                        setPrefix(newPullParser.nextText());
                    }
                } else if ("MaxUploads".equals(name)) {
                    String nextText2 = newPullParser.nextText();
                    if (!OSSUtils.isEmptyString(nextText2)) {
                        setMaxUploads(Integer.valueOf(nextText2).intValue());
                    }
                } else if ("IsTruncated".equals(name)) {
                    String nextText3 = newPullParser.nextText();
                    if (!OSSUtils.isEmptyString(nextText3)) {
                        setTruncated(Boolean.valueOf(nextText3).booleanValue());
                    }
                } else if ("KeyMarker".equals(name)) {
                    setKeyMarker(newPullParser.nextText());
                } else if ("UploadIdMarker".equals(name)) {
                    setUploadIdMarker(newPullParser.nextText());
                } else if ("NextKeyMarker".equals(name)) {
                    setNextKeyMarker(newPullParser.nextText());
                } else if ("NextUploadIdMarker".equals(name)) {
                    setNextUploadIdMarker(newPullParser.nextText());
                } else if ("Upload".equals(name)) {
                    multipartUpload = new MultipartUpload();
                } else if ("Key".equals(name)) {
                    multipartUpload.setKey(newPullParser.nextText());
                } else if ("UploadId".equals(name)) {
                    multipartUpload.setUploadId(newPullParser.nextText());
                } else if ("Initiated".equals(name)) {
                    multipartUpload.setInitiated(DateUtil.parseIso8601Date(newPullParser.nextText()));
                } else if (CreateBucketRequest.TAB_STORAGECLASS.equals(name)) {
                    multipartUpload.setStorageClass(newPullParser.nextText());
                } else if ("CommonPrefixes".equals(name)) {
                    z = true;
                }
            } else if (eventType == 3) {
                if ("Upload".equals(newPullParser.getName())) {
                    arrayList.add(multipartUpload);
                } else if ("CommonPrefixes".equals(newPullParser.getName())) {
                    z = false;
                }
            }
            eventType = newPullParser.next();
            if (eventType == 4) {
                eventType = newPullParser.next();
            }
        }
        if (arrayList.size() > 0) {
            setMultipartUploads(arrayList);
        }
        return this;
    }
}
