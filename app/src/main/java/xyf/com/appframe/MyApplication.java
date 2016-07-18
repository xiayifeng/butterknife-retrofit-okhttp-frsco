package xyf.com.appframe;

import android.app.Application;

import butterknife.ButterKnife;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(true);
    }
}
