package xyf.com.appframe.network;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyf.com.appframe.apis.CityListApi;
import xyf.com.appframe.apis.MusicKrcInfoApi;
import xyf.com.appframe.apis.MusicPlayInfoApi;
import xyf.com.appframe.apis.MusicQueryApi;
import xyf.com.appframe.apis.MusicSingerInfoApi;
import xyf.com.appframe.apis.RecentWeathersApi;
import xyf.com.appframe.apis.Urls;
import xyf.com.appframe.javabean.MusicSingerInfo;
import xyf.com.appframe.javabean.RecentWeatherBean;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public class NetWork {

    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static CityListApi cityListApi;
    public static CityListApi getClitListApi()
    {
        if (cityListApi == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OKHttpUtils.getInstaces().getOkHttpClient())
                    .baseUrl(Urls.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            cityListApi = retrofit.create(CityListApi.class);
        }
        return cityListApi;
    }

    private static RecentWeathersApi recentWeathersApi;
    public static RecentWeathersApi getRecentWeathersApi()
    {
        if (recentWeathersApi == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OKHttpUtils.getInstaces().getOkHttpClient())
                    .baseUrl(Urls.BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            recentWeathersApi = retrofit.create(RecentWeathersApi.class);
        }
        return recentWeathersApi;
    }

    private static MusicQueryApi musicQueryApi;
    public static MusicQueryApi getMusicQueryApi()
    {
        if (musicQueryApi == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OKHttpUtils.getInstaces().getOkHttpClient())
                    .baseUrl(Urls.MUSIC_BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            musicQueryApi = retrofit.create(MusicQueryApi.class);
        }
        return musicQueryApi;
    }

    private static MusicSingerInfoApi musicSingerInfoApi;
    public static MusicSingerInfoApi getMusicSingerInfoApi()
    {
        if (musicSingerInfoApi == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OKHttpUtils.getInstaces().getOkHttpClient())
                    .baseUrl(Urls.MUSIC_BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            musicSingerInfoApi = retrofit.create(MusicSingerInfoApi.class);
        }
        return musicSingerInfoApi;
    }

    private static MusicKrcInfoApi musicKrcInfoApi;
    public static MusicKrcInfoApi getMusicKrcInfoApi()
    {
        if (musicKrcInfoApi == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OKHttpUtils.getInstaces().getOkHttpClient())
                    .baseUrl(Urls.MUSIC_BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            musicKrcInfoApi = retrofit.create(MusicKrcInfoApi.class);
        }
        return musicKrcInfoApi;
    }

    private static MusicPlayInfoApi musicPlayInfoApi;
    public static MusicPlayInfoApi getMusicPlayInfoApi() {
        if (musicPlayInfoApi == null)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(OKHttpUtils.getInstaces().getOkHttpClient())
                    .baseUrl(Urls.MUSIC_BASE_URL)
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            musicPlayInfoApi = retrofit.create(MusicPlayInfoApi.class);
        }
        return musicPlayInfoApi;
    }
}
