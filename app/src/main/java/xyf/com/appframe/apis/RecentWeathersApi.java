package xyf.com.appframe.apis;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import xyf.com.appframe.javabean.RecentWeatherBean;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public interface RecentWeathersApi {
    @GET("weatherservice/recentweathers")
    Observable<RecentWeatherBean> getRecentWeather(@Query("cityname") String cityanme, @Query("cityid") String cityid);
}
