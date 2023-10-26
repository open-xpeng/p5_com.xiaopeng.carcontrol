package com.alibaba.mtl.appmonitor;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import com.alibaba.mtl.appmonitor.model.DimensionValueSet;
import com.alibaba.mtl.log.model.LogField;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes.dex */
public class Transaction implements Parcelable {
    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() { // from class: com.alibaba.mtl.appmonitor.Transaction.1
        @Override // android.os.Parcelable.Creator
        /* renamed from: b */
        public Transaction createFromParcel(Parcel parcel) {
            return Transaction.a(parcel);
        }

        @Override // android.os.Parcelable.Creator
        /* renamed from: a */
        public Transaction[] newArray(int i) {
            return new Transaction[i];
        }
    };
    protected Integer a;
    protected DimensionValueSet b;
    protected Map<String, String> e;
    private Object lock;
    protected String o;
    protected String p;
    protected String r;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Transaction(Integer num, String str, String str2, DimensionValueSet dimensionValueSet) {
        this(num, str, str2, dimensionValueSet, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public Transaction(Integer num, String str, String str2, DimensionValueSet dimensionValueSet, String str3) {
        this.a = num;
        this.o = str;
        this.p = str2;
        this.r = UUID.randomUUID().toString();
        this.b = dimensionValueSet;
        if (!TextUtils.isEmpty(str3)) {
            HashMap hashMap = new HashMap();
            this.e = hashMap;
            hashMap.put(LogField.APPKEY.toString(), str3);
        }
        this.lock = new Object();
    }

    public Transaction() {
    }

    public void begin(String str) {
        if (AppMonitor.f18a == null) {
            return;
        }
        try {
            AppMonitor.f18a.transaction_begin(this, str);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void end(String str) {
        if (AppMonitor.f18a == null) {
            return;
        }
        try {
            AppMonitor.f18a.transaction_end(this, str);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void addDimensionValues(DimensionValueSet dimensionValueSet) {
        synchronized (this.lock) {
            DimensionValueSet dimensionValueSet2 = this.b;
            if (dimensionValueSet2 == null) {
                this.b = dimensionValueSet;
            } else {
                dimensionValueSet2.addValues(dimensionValueSet);
            }
        }
    }

    public void addDimensionValues(String str, String str2) {
        synchronized (this.lock) {
            if (this.b == null) {
                this.b = (DimensionValueSet) com.alibaba.mtl.appmonitor.c.a.a().a(DimensionValueSet.class, new Object[0]);
            }
            this.b.setValue(str, str2);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.b, i);
        parcel.writeInt(this.a.intValue());
        parcel.writeString(this.o);
        parcel.writeString(this.p);
        parcel.writeString(this.r);
        parcel.writeMap(this.e);
    }

    static Transaction a(Parcel parcel) {
        Transaction transaction = new Transaction();
        try {
            transaction.b = (DimensionValueSet) parcel.readParcelable(Transaction.class.getClassLoader());
            transaction.a = Integer.valueOf(parcel.readInt());
            transaction.o = parcel.readString();
            transaction.p = parcel.readString();
            transaction.r = parcel.readString();
            transaction.e = parcel.readHashMap(Transaction.class.getClassLoader());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return transaction;
    }
}
