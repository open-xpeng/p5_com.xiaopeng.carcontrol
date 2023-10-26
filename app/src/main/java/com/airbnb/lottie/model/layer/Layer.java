package com.airbnb.lottie.model.layer;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public class Layer {
    private final LottieComposition composition;
    private final boolean hidden;
    private final List<Keyframe<Float>> inOutKeyframes;
    private final long layerId;
    private final String layerName;
    private final LayerType layerType;
    private final List<Mask> masks;
    private final MatteType matteType;
    private final long parentId;
    private final int preCompHeight;
    private final int preCompWidth;
    private final String refId;
    private final List<ContentModel> shapes;
    private final int solidColor;
    private final int solidHeight;
    private final int solidWidth;
    private final float startFrame;
    private final AnimatableTextFrame text;
    private final AnimatableTextProperties textProperties;
    private final AnimatableFloatValue timeRemapping;
    private final float timeStretch;
    private final AnimatableTransform transform;

    /* loaded from: classes.dex */
    public enum LayerType {
        PRE_COMP,
        SOLID,
        IMAGE,
        NULL,
        SHAPE,
        TEXT,
        UNKNOWN
    }

    /* loaded from: classes.dex */
    public enum MatteType {
        NONE,
        ADD,
        INVERT,
        LUMA,
        LUMA_INVERTED,
        UNKNOWN
    }

    public Layer(List<ContentModel> list, LottieComposition lottieComposition, String str, long j, LayerType layerType, long j2, String str2, List<Mask> list2, AnimatableTransform animatableTransform, int i, int i2, int i3, float f, float f2, int i4, int i5, AnimatableTextFrame animatableTextFrame, AnimatableTextProperties animatableTextProperties, List<Keyframe<Float>> list3, MatteType matteType, AnimatableFloatValue animatableFloatValue, boolean z) {
        this.shapes = list;
        this.composition = lottieComposition;
        this.layerName = str;
        this.layerId = j;
        this.layerType = layerType;
        this.parentId = j2;
        this.refId = str2;
        this.masks = list2;
        this.transform = animatableTransform;
        this.solidWidth = i;
        this.solidHeight = i2;
        this.solidColor = i3;
        this.timeStretch = f;
        this.startFrame = f2;
        this.preCompWidth = i4;
        this.preCompHeight = i5;
        this.text = animatableTextFrame;
        this.textProperties = animatableTextProperties;
        this.inOutKeyframes = list3;
        this.matteType = matteType;
        this.timeRemapping = animatableFloatValue;
        this.hidden = z;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public LottieComposition getComposition() {
        return this.composition;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getTimeStretch() {
        return this.timeStretch;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public float getStartProgress() {
        return this.startFrame / this.composition.getDurationFrames();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<Keyframe<Float>> getInOutKeyframes() {
        return this.inOutKeyframes;
    }

    public long getId() {
        return this.layerId;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getName() {
        return this.layerName;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getRefId() {
        return this.refId;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPreCompWidth() {
        return this.preCompWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPreCompHeight() {
        return this.preCompHeight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<Mask> getMasks() {
        return this.masks;
    }

    public LayerType getLayerType() {
        return this.layerType;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public MatteType getMatteType() {
        return this.matteType;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getParentId() {
        return this.parentId;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public List<ContentModel> getShapes() {
        return this.shapes;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AnimatableTransform getTransform() {
        return this.transform;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getSolidColor() {
        return this.solidColor;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getSolidHeight() {
        return this.solidHeight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getSolidWidth() {
        return this.solidWidth;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AnimatableTextFrame getText() {
        return this.text;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AnimatableTextProperties getTextProperties() {
        return this.textProperties;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AnimatableFloatValue getTimeRemapping() {
        return this.timeRemapping;
    }

    public String toString() {
        return toString("");
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public String toString(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str).append(getName()).append("\n");
        Layer layerModelForId = this.composition.layerModelForId(getParentId());
        if (layerModelForId != null) {
            sb.append("\t\tParents: ").append(layerModelForId.getName());
            Layer layerModelForId2 = this.composition.layerModelForId(layerModelForId.getParentId());
            while (layerModelForId2 != null) {
                sb.append("->").append(layerModelForId2.getName());
                layerModelForId2 = this.composition.layerModelForId(layerModelForId2.getParentId());
            }
            sb.append(str).append("\n");
        }
        if (!getMasks().isEmpty()) {
            sb.append(str).append("\tMasks: ").append(getMasks().size()).append("\n");
        }
        if (getSolidWidth() != 0 && getSolidHeight() != 0) {
            sb.append(str).append("\tBackground: ").append(String.format(Locale.US, "%dx%d %X\n", Integer.valueOf(getSolidWidth()), Integer.valueOf(getSolidHeight()), Integer.valueOf(getSolidColor())));
        }
        if (!this.shapes.isEmpty()) {
            sb.append(str).append("\tShapes:\n");
            for (ContentModel contentModel : this.shapes) {
                sb.append(str).append("\t\t").append(contentModel).append("\n");
            }
        }
        return sb.toString();
    }
}
