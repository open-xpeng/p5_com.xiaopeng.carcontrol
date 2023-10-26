package com.xiaopeng.speech.protocol.bean;

import org.json.JSONObject;

/* loaded from: classes2.dex */
public class CardValue {
    private double acDriverTemp;
    private int acSeatHeatingLv;
    private int acSeatVentilateLv;
    private int acWindLv;
    private int atmosphereBrightness;
    private int atmosphereColor;
    private int icmBrightness;
    private int screenBrightness;

    public static CardValue fromJsonForAcTemp(String str) {
        CardValue cardValue = new CardValue();
        try {
            cardValue.acDriverTemp = new JSONObject(str).optDouble("current");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardValue;
    }

    public static CardValue fromJsonForAcWindLv(String str) {
        CardValue cardValue = new CardValue();
        try {
            cardValue.acWindLv = new JSONObject(str).optInt("current");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardValue;
    }

    public static CardValue fromJsonForAtmosphereBrightness(String str) {
        CardValue cardValue = new CardValue();
        try {
            cardValue.atmosphereBrightness = new JSONObject(str).optInt("current");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardValue;
    }

    public static CardValue fromJsonForAtmosphereColor(String str) {
        CardValue cardValue = new CardValue();
        try {
            cardValue.atmosphereColor = new JSONObject(str).optInt("current");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardValue;
    }

    public static CardValue fromJsonForAcSeatHeating(String str) {
        CardValue cardValue = new CardValue();
        try {
            cardValue.acSeatHeatingLv = new JSONObject(str).optInt("current");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardValue;
    }

    public static CardValue fromJsonForAcSeatVentilate(String str) {
        CardValue cardValue = new CardValue();
        try {
            cardValue.acSeatVentilateLv = new JSONObject(str).optInt("current");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardValue;
    }

    public static CardValue fromJsonForScreenBrightness(String str) {
        CardValue cardValue = new CardValue();
        try {
            cardValue.screenBrightness = new JSONObject(str).optInt("current");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardValue;
    }

    public static CardValue fromJsonForIcmBrightness(String str) {
        CardValue cardValue = new CardValue();
        try {
            cardValue.icmBrightness = new JSONObject(str).optInt("current");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cardValue;
    }

    public double getAcDriverTemp() {
        return this.acDriverTemp;
    }

    public int getAcWindLv() {
        return this.acWindLv;
    }

    public int getAtmosphereBrightness() {
        return this.atmosphereBrightness;
    }

    public int getAtmosphereColor() {
        return this.atmosphereColor;
    }

    public int getAcSeatHeatingLv() {
        return this.acSeatHeatingLv;
    }

    public int getAcSeatVentilateLv() {
        return this.acSeatVentilateLv;
    }

    public int getScreenBrightness() {
        return this.screenBrightness;
    }

    public int getIcmBrightness() {
        return this.icmBrightness;
    }

    public String toString() {
        return "CardValue{acWindLv='" + this.acDriverTemp + "', acWindLv=" + this.acWindLv + ", atmosphereBrightness=" + this.atmosphereBrightness + ", atmosphereColor=" + this.atmosphereColor + ", acSeatHeatingLv=" + this.acSeatHeatingLv + ", acSeatVentilateLv=" + this.acSeatVentilateLv + ", screenBrightness=" + this.screenBrightness + ", icmBrightness=" + this.icmBrightness + '}';
    }
}
