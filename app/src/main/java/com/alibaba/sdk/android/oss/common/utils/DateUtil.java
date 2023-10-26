package com.alibaba.sdk.android.oss.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/* loaded from: classes.dex */
public class DateUtil {
    private static final String ALTERNATIVE_ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String RFC822_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
    private static volatile long amendTimeSkewed;

    public static String formatRfc822Date(Date date) {
        return getRfc822DateFormat().format(date);
    }

    public static Date parseRfc822Date(String str) throws ParseException {
        return getRfc822DateFormat().parse(str);
    }

    private static DateFormat getRfc822DateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(RFC822_DATE_FORMAT, Locale.US);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return simpleDateFormat;
    }

    public static String formatIso8601Date(Date date) {
        return getIso8601DateFormat().format(date);
    }

    public static String formatAlternativeIso8601Date(Date date) {
        return getAlternativeIso8601DateFormat().format(date);
    }

    public static Date parseIso8601Date(String str) throws ParseException {
        try {
            return getIso8601DateFormat().parse(str);
        } catch (ParseException unused) {
            return getAlternativeIso8601DateFormat().parse(str);
        }
    }

    private static DateFormat getIso8601DateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO8601_DATE_FORMAT, Locale.US);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return simpleDateFormat;
    }

    private static DateFormat getAlternativeIso8601DateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ALTERNATIVE_ISO8601_DATE_FORMAT, Locale.US);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return simpleDateFormat;
    }

    public static long getFixedSkewedTimeMillis() {
        return System.currentTimeMillis() + amendTimeSkewed;
    }

    public static synchronized String currentFixedSkewedTimeInRFC822Format() {
        String formatRfc822Date;
        synchronized (DateUtil.class) {
            formatRfc822Date = formatRfc822Date(new Date(getFixedSkewedTimeMillis()));
        }
        return formatRfc822Date;
    }

    public static synchronized void setCurrentServerTime(long j) {
        synchronized (DateUtil.class) {
            amendTimeSkewed = j - System.currentTimeMillis();
        }
    }
}
