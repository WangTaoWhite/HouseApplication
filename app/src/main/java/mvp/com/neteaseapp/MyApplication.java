package mvp.com.neteaseapp;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by wangtao on 2018/6/6.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

}
