package xyf.com.appframe.apis;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import xyf.com.appframe.javabean.MusicSingerInfo;

/**
 * Created by sh-xiayf on 16/7/20.
 */
public interface MusicSingerInfoApi {
    @GET("music/singer")
    Observable<MusicSingerInfo> getSingerInfo(@Query("name") String name);
}
