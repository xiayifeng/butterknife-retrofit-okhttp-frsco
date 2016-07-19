package xyf.com.appframe.javabean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class RecentWeatherRetDataTodayBean {

    public String date;
    public String week;
    public String curTemp;
    public @SerializedName("aqi") String aqi;
    public String fengxiang;
    public String fengli;
    public String hightemp;
    public String lowtemp;
    public String type;
    public @SerializedName("index")
    List<RecentWeatherRetDataTodayIndexBean> message;

}
