package net.callofdroidy.testhanldeuncauhtexception;

import android.app.Application;

/**
 * Created by admin on 06/04/16.
 */
public class MyApplication extends Application{
    private static final String TAG = "MyApplication";

    @Override
    public void onCreate(){
        super.onCreate();

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
