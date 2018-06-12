package huxibianjie.com.gonggong;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2016/10/21.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SDKInitializer.initialize(context);
    }

    public static Context getContext() {
        return context;
    }
}
