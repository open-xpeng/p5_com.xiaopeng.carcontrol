package com.xiaopeng.speech.protocol.bean.weather;

import com.xiaopeng.speech.common.bean.BaseBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class WeatherBean extends BaseBean {
    private static final String DATA_FORMAT = "yyyy-MM-dd";
    private static final String TEMP_SUPREFIX = "℃";
    private List<WeatherData> mWeatherDatas;
    private String searchDay;
    private WeatherData today;

    public static WeatherBean fromJsonObj(JSONObject jSONObject) {
        WeatherData today;
        WeatherData weatherData = null;
        if (jSONObject != null && jSONObject.optString("name").equals("weather")) {
            WeatherBean weatherBean = new WeatherBean();
            try {
                weatherData = parseWeathData(jSONObject.optJSONArray("forecastChoose").getJSONObject(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (weatherData != null) {
                weatherBean.setSearchDay(weatherData.getDate());
            }
            weatherBean.setTitle(jSONObject.optString("cityName"));
            JSONArray optJSONArray = jSONObject.optJSONArray("forecast");
            ArrayList arrayList = new ArrayList();
            String todaySampleDataFormat = getTodaySampleDataFormat();
            for (int i = 0; i < optJSONArray.length(); i++) {
                try {
                    WeatherData parseWeathData = parseWeathData(optJSONArray.getJSONObject(i));
                    if (parseWeathData != null && todaySampleDataFormat.equals(parseWeathData.getDate())) {
                        weatherBean.setToday(parseWeathData);
                    }
                    if (parseWeathData != null) {
                        arrayList.add(parseWeathData);
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
            if (weatherData == null && (today = weatherBean.getToday()) != null) {
                weatherBean.setSearchDay(today.getDate());
            }
            weatherBean.setWeatherDatas(arrayList);
            return weatherBean;
        }
        return null;
    }

    private static String getTodaySampleDataFormat() {
        return new SimpleDateFormat(DATA_FORMAT).format(new Date());
    }

    private static WeatherData parseWeathData(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        WeatherData weatherData = new WeatherData();
        weatherData.setDate(jSONObject.optString("predictDate"));
        weatherData.setWeather(jSONObject.optString("conditionDayNight"));
        weatherData.setConditionDay(jSONObject.optString("conditionDay"));
        weatherData.setConditionNight(jSONObject.optString("conditionNight"));
        int optInt = jSONObject.optInt("tempDay");
        int optInt2 = jSONObject.optInt("tempNight");
        weatherData.setTempDay(optInt);
        weatherData.setTempNight(optInt2);
        int min = Math.min(optInt, optInt2);
        int max = Math.max(optInt, optInt2);
        StringBuilder sb = new StringBuilder();
        sb.append(min).append("~").append(max).append(TEMP_SUPREFIX);
        weatherData.setTemperature(sb.toString());
        weatherData.setWind(jSONObject.optString("windDirDay"));
        return weatherData;
    }

    public String getSearchDay() {
        return this.searchDay;
    }

    public void setSearchDay(String str) {
        this.searchDay = str;
    }

    public WeatherData getToday() {
        return this.today;
    }

    public void setToday(WeatherData weatherData) {
        this.today = weatherData;
    }

    public List<WeatherData> getWeatherDatas() {
        return this.mWeatherDatas;
    }

    public void setWeatherDatas(List<WeatherData> list) {
        this.mWeatherDatas = list;
    }

    /* loaded from: classes2.dex */
    public static class WeatherData {
        private String conditionDay;
        private String conditionNight;
        private String date;
        private int tempDay;
        private int tempNight;
        private String temperature;
        private String weather;
        private String wind;

        public String getConditionDay() {
            return this.conditionDay;
        }

        public void setConditionDay(String str) {
            this.conditionDay = str;
        }

        public String getConditionNight() {
            return this.conditionNight;
        }

        public void setConditionNight(String str) {
            this.conditionNight = str;
        }

        public int getTempDay() {
            return this.tempDay;
        }

        public void setTempDay(int i) {
            this.tempDay = i;
        }

        public int getTempNight() {
            return this.tempNight;
        }

        public void setTempNight(int i) {
            this.tempNight = i;
        }

        public void setDate(String str) {
            this.date = str;
        }

        public void setWeather(String str) {
            this.weather = str;
        }

        public void setTemperature(String str) {
            this.temperature = str;
        }

        public void setWind(String str) {
            this.wind = str;
        }

        public String getDate() {
            return this.date;
        }

        public String getWeather() {
            return this.weather;
        }

        public String getTemperature() {
            return this.temperature;
        }

        public String getWind() {
            return this.wind;
        }
    }
}
