package xyf.com.appframe.apis;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import xyf.com.appframe.javabean.MusicPlayInfo;

/**
 * Created by sh-xiayf on 16/7/20.
 */
public interface MusicPlayInfoApi {
    @GET("music/playinfo")
    Observable<MusicPlayInfo> getPlayInfo(@Query("hash") String hash);
}
