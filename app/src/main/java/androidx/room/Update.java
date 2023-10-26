package androidx.room;

/* loaded from: classes.dex */
public @interface Update {
    int onConflict() default 3;
}
