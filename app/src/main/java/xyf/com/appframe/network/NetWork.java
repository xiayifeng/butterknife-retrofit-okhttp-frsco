package xyf.com.appframe.network;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyf.com.appframe.apis.CityListApi;
import xyf.com.appframe.apis.RecentWeathersApi;
import xyf.com.appframe.apis.Urls;

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

}
