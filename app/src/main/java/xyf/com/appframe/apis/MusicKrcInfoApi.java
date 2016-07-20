package xyf.com.appframe.apis;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import xyf.com.appframe.javabean.MusicKrcInfo;

/**
 * Created by sh-xiayf on 16/7/20.
 */
public interface MusicKrcInfoApi {
    @GET("music/krc")
    Observable<MusicKrcInfo> getKrcInfo(@Query("name")String name,@Query("hash") String hash,@Query("time") String time);
}
