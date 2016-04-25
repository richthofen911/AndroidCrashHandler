package net.callofdroidy.testhanldeuncauhtexception;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by admin on 22/04/16.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler{

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler INSTANCE;
    private Context mContext;
    private CrashHandler() {}

    public synchronized static CrashHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CrashHandler();
        return INSTANCE;
    }

    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }
    }

    /**
     * @return
     * true,  means process the exception, do not throw exception anymore
     * false, means don't process the exception now, (can be saved) but hand in it to the system to process(pop up the system dialog)
     */
    private boolean handleException(final Throwable throwable) {
        if (throwable == null)
            return false;

        final String message = throwable.getMessage();
        final Throwable cause = throwable.getCause();

        final String directCause = cause.toString();
        final StackTraceElement[] causesStackTrace = cause.getStackTrace();

        StringBuilder stringBuilder = new StringBuilder();
        for(StackTraceElement stackTraceElement : causesStackTrace)
            stringBuilder.append("at ").append(stackTraceElement.toString()).append("\n");
        final String causesTrace = stringBuilder.toString();

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                String loginfo = message + "\nCaused by: " + directCause + "\n" + causesTrace;
                Log.e("loginfo", loginfo);
                Toast.makeText(mContext, "Oops, the program comes across an error:\n" + loginfo, Toast.LENGTH_LONG).show();
                Looper.loop();
            }

        }.start();
        return false;
    }

    // send error to log server by POST packet

}
