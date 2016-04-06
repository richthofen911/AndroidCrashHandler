package net.callofdroidy.testhanldeuncauhtexception;

import android.app.Application;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by admin on 06/04/16.
 */
public class MyApplication extends Application{
    private static final String TAG = "MyApplication";
    String content =  "netcallofdroidytesthanldeuncauhtexceptionEMyApplicationuncaughtExceptionjavalangRuntimeExceptionnabletostartactivityComponentInfonetcallofdroidytesthanldeuncauhtexceptionnetcallofdroidytesthanldeuncauhtexceptionMainActivityjavalangNullPointerExceptionAttempt";
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler(){
        @Override
        public void uncaughtException(Thread thread, final Throwable throwable){
            String url = "http://192.168.128.57:3999/" + content ;
            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e(TAG, "onResponse: " + response);
                    Log.e(TAG, "uncaughtException: " + throwable.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "onErrorResponse: " + error.toString());
                    Log.e(TAG, "uncaughtException: " + throwable.toString());
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.this);
            Log.e(TAG, "uncaughtException: " + throwable.toString());
            requestQueue.add(request);
        }
    };

    @Override
    public void onCreate(){
        super.onCreate();

        appInitialization();
    }

    private void appInitialization(){
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
    }

}
