package xyf.com.appframe.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public class OKHttpUtils {

    private OKHttpUtils () {}

    private static final class UTILS {
        public static final OKHttpUtils INSTANCES = new OKHttpUtils();
    }

    public static OKHttpUtils getInstaces()
    {
        return UTILS.INSTANCES;
    }

    private static OkHttpClient okHttpClient;

    private void createOKHttpClient()
    {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("apikey","a27a5c07498ef289de20786d9d5cbd86")
                        .build();
                return chain.proceed(request);
            }
        });
        okHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient()
    {
        if (okHttpClient == null)
        {
            createOKHttpClient();
        }
        return okHttpClient;
    }

}
