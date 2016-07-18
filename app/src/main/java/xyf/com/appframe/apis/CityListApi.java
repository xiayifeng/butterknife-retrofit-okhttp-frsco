package xyf.com.appframe.apis;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import xyf.com.appframe.javabean.CityListBean;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public interface CityListApi {
    @GET("weatherservice/citylist")
    Observable<CityListBean> getcitylist(@Query("cityname") String cityname);
}
