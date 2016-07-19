package xyf.com.appframe.adapter;

import retrofit2.http.Url;
import xyf.com.appframe.apis.Urls;

/**
 * Created by sh-xiayf on 16/7/19.
 */
public class WeatherPicAdapter {

    enum WeatherPic
    {
        qing,
        yu,
        lei,
        yin,
        xue,
    }

    public static String getPicUrl(String type)
    {
        if (type.indexOf("晴") != -1)
        {
            return Urls.qing;
        }
        else if (type.indexOf("雷") != -1)
        {
            return Urls.lei;
        }
        else if (type.indexOf("雪") != -1)
        {
            return Urls.xue;
        }
        else if (type.indexOf("阴") != -1)
        {
            return Urls.yin;
        }
        else if (type.indexOf("雨") != -1)
        {
            return Urls.yu;
        }
        else if (type.indexOf("云") != -1)
        {
            return Urls.yin;
        }

        return Urls.qing;
    }

    public static String getPicUrl(WeatherPic enumPic)
    {
        if (enumPic == WeatherPic.qing)
        {
            return Urls.qing;
        }
        else if (enumPic == WeatherPic.yu)
        {
            return Urls.yu;
        }
        else if (enumPic == WeatherPic.lei)
        {
            return Urls.lei;
        }
        else if (enumPic == WeatherPic.yin)
        {
            return Urls.yin;
        }
        else if (enumPic == WeatherPic.xue)
        {
            return Urls.xue;
        }

        return "";
    }


}
