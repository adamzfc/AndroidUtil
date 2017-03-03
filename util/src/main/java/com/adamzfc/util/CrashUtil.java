package com.adamzfc.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * carsh handle util
 * Created by adamzfc on 3/3/17.
 */

public class CrashUtil implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashUtil";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private volatile static CrashUtil instance = null;
    private Context mContext;
    /**
     * use for save device infos and exception infos
     */
    private Map<String, String> infos = new HashMap<>();


    private CrashUtil() {
    }

    public static CrashUtil getInstance() {
        if (instance == null) {
            synchronized (CrashUtil.class) {
                if (instance == null) {
                    instance = new CrashUtil();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (!handleException(e) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(t, e);
        } else {
            SystemClock.sleep(3000);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        try {
//            // show toast
//            new Thread() {
//                @Override
//                public void run() {
//                    Looper.prepare();
//                    Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
//                    Looper.loop();
//                }
//            }.start();
            collectDeviceInfo(mContext);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private void collectDeviceInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName;
                String versionCode = String.valueOf(pi.versionCode);
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
            } catch (IllegalAccessException e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    private String saveCrashInfoFile(Throwable throwable) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        String data = dateFormat.format(new Date());
        stringBuffer.append("\r\n" + data + "\n");
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            stringBuffer.append(key + "=" + value + "\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.flush();
        printWriter.close();
        String result = writer.toString();
        stringBuffer.append(result);
        String fileName = null;
        try {
            fileName = writeFile(stringBuffer.toString());
        } catch (IOException e) {
            Log.e(TAG, "an error ocured when writing file", e);
            stringBuffer.append("an error ocured when writing file\r\n");
            writeFile(stringBuffer.toString());
        }
        return fileName;
    }

    private String writeFile(String str) throws IOException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String time = formatter.format(new Date());
        String fileName = "crash-" + time + ".log";
        String path = getGlobalPath();
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path + fileName, true);
            fos.write(str.getBytes());
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        return fileName;

    }

    private String getGlobalPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "crash" + File.separator;
    }
}
