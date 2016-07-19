package xyf.com.appframe.javabean;

import java.util.List;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class RecentWeatherRetDataBean {

    public String city;
    public String cityid;
    public RecentWeatherRetDataTodayBean today;
    public List<RecentWeatherRetDataforecastBean> forecast;
    public List<RecentWeatherRetDataforecastBean> history;

}
