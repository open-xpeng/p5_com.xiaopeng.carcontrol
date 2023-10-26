package com.airbnb.lottie.parser;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class LottieCompositionParser {
    static JsonReader.Options NAMES = JsonReader.Options.of("w", "h", "ip", "op", "fr", "v", "layers", "assets", "fonts", "chars", "markers");

    public static LottieComposition parse(JsonReader jsonReader) throws IOException {
        HashMap hashMap;
        ArrayList arrayList;
        JsonReader jsonReader2 = jsonReader;
        float dpScale = Utils.dpScale();
        LongSparseArray<Layer> longSparseArray = new LongSparseArray<>();
        ArrayList arrayList2 = new ArrayList();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        HashMap hashMap4 = new HashMap();
        ArrayList arrayList3 = new ArrayList();
        SparseArrayCompat<FontCharacter> sparseArrayCompat = new SparseArrayCompat<>();
        LottieComposition lottieComposition = new LottieComposition();
        jsonReader.beginObject();
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        int i = 0;
        int i2 = 0;
        while (jsonReader.hasNext()) {
            switch (jsonReader2.selectName(NAMES)) {
                case 0:
                    i = jsonReader.nextInt();
                    continue;
                    jsonReader2 = jsonReader;
                case 1:
                    i2 = jsonReader.nextInt();
                    continue;
                    jsonReader2 = jsonReader;
                case 2:
                    f = (float) jsonReader.nextDouble();
                    continue;
                    jsonReader2 = jsonReader;
                case 3:
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    f2 = ((float) jsonReader.nextDouble()) - 0.01f;
                    break;
                case 4:
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    f3 = (float) jsonReader.nextDouble();
                    break;
                case 5:
                    String[] split = jsonReader.nextString().split("\\.");
                    if (!Utils.isAtLeastVersion(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), 4, 4, 0)) {
                        lottieComposition.addWarning("Lottie only supports bodymovin >= 4.4.0");
                    }
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    break;
                case 6:
                    parseLayers(jsonReader2, lottieComposition, arrayList2, longSparseArray);
                default:
                    hashMap = hashMap4;
                    arrayList = arrayList3;
                    jsonReader.skipValue();
                    break;
            }
            hashMap4 = hashMap;
            arrayList3 = arrayList;
            jsonReader2 = jsonReader;
        }
        lottieComposition.init(new Rect(0, 0, (int) (i * dpScale), (int) (i2 * dpScale)), f, f2, f3, arrayList2, longSparseArray, hashMap2, hashMap3, sparseArrayCompat, hashMap4, arrayList3);
        return lottieComposition;
    }

    private static void parseLayers(JsonReader jsonReader, LottieComposition lottieComposition, List<Layer> list, LongSparseArray<Layer> longSparseArray) throws IOException {
        jsonReader.beginArray();
        int i = 0;
        while (jsonReader.hasNext()) {
            Layer parse = LayerParser.parse(jsonReader, lottieComposition);
            if (parse.getLayerType() == Layer.LayerType.IMAGE) {
                i++;
            }
            list.add(parse);
            longSparseArray.put(parse.getId(), parse);
            if (i > 4) {
                Logger.warning("You have " + i + " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
            }
        }
        jsonReader.endArray();
    }
}
