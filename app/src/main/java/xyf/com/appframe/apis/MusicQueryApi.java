package xyf.com.appframe.apis;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import xyf.com.appframe.javabean.MusicSearchResult;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public interface MusicQueryApi {

    @GET("music/query")
    Observable<MusicSearchResult> getMusic(@Query("s") String key,@Query("size") String size,@Query("page") String page);

}
