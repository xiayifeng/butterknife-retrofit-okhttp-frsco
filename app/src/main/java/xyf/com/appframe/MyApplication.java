package xyf.com.appframe;

import android.app.Application;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import butterknife.ButterKnife;

/**
 * Created by sh-xiayf on 16/7/18.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(true);


        initFresco();
    }


    private void initFresco()
    {
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this).setBaseDirectoryPath(getExternalCacheDir()).build();
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setMainDiskCacheConfig(diskCacheConfig)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this,imagePipelineConfig);
    }
}
