package zipkin2.internal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public final class DateUtil {
    static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    public static long midnightUTC(long j) {
        Calendar calendar = Calendar.getInstance(UTC);
        calendar.setTimeInMillis(j);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(11, 0);
        return calendar.getTimeInMillis();
    }

    public static List<Date> getDays(long j, long j2) {
        long midnightUTC = midnightUTC(j);
        if (j2 == 0) {
            j2 = j;
        }
        long j3 = j - j2;
        ArrayList arrayList = new ArrayList();
        for (long midnightUTC2 = j3 > 0 ? midnightUTC(j3) : 0L; midnightUTC2 <= midnightUTC; midnightUTC2 += TimeUnit.DAYS.toMillis(1L)) {
            arrayList.add(new Date(midnightUTC2));
        }
        return arrayList;
    }
}
