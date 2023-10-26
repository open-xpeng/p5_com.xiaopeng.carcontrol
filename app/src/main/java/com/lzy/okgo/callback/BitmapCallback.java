package com.lzy.okgo.callback;

import android.graphics.Bitmap;
import android.widget.ImageView;
import com.lzy.okgo.convert.BitmapConvert;
import okhttp3.Response;

/* loaded from: classes.dex */
public abstract class BitmapCallback extends AbsCallback<Bitmap> {
    private BitmapConvert convert;

    public BitmapCallback() {
        this.convert = new BitmapConvert();
    }

    public BitmapCallback(int i, int i2) {
        this.convert = new BitmapConvert(i, i2);
    }

    public BitmapCallback(int i, int i2, Bitmap.Config config, ImageView.ScaleType scaleType) {
        this.convert = new BitmapConvert(i, i2, config, scaleType);
    }

    @Override // com.lzy.okgo.convert.Converter
    public Bitmap convertResponse(Response response) throws Throwable {
        Bitmap convertResponse = this.convert.convertResponse(response);
        response.close();
        return convertResponse;
    }
}
