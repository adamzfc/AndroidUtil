package com.adamzfc.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by adamzfc on 2/28/17.
 */

public class LogUtil {
    private static boolean DEBUG = false;

    static String mTag = "LogUtils";

    //for error log
    public static void error(String msg) {
        if (DEBUG) {
            Log.e(mTag, msg);
        }
    }

    //for warming log
    public static void warn(String msg) {
        if (DEBUG) {
            Log.w(mTag, msg);
        }
    }

    //for info log
    public static void info(String msg) {
        if (DEBUG) {
            Log.i(mTag, msg);
        }
    }

    //for debug log
    public static void debug(String msg) {
        if (DEBUG) {
            Log.d(mTag, msg);
        }
    }

    //for verbose log
    public static void verbose(String msg) {
        if (DEBUG) {
            Log.v(mTag, msg);
        }
    }

    //for error log
    public static void e(String msg) {
        if (DEBUG) {
            Log.e(mTag, msg);
        }
    }

    //for warming log
    public static void w(String msg) {
        if (DEBUG) {
            Log.w(mTag, msg);
        }
    }

    //for info log
    public static void i(String msg) {
        if (DEBUG) {
            Log.i(mTag, msg);
        }
    }

    //for debug log
    public static void d(String msg) {
        if (DEBUG) {
            Log.d(mTag, msg);
        }
    }

    //for verbose log
    public static void v(String msg) {
        if (DEBUG) {
            Log.v(mTag, msg);
        }
    }


    //for warming log
    public static void w(String tag, String msg) {
        if (DEBUG) {
            if (tag == null || "".equalsIgnoreCase(tag.trim())) {
                tag = mTag;
            }
            Log.w(tag, msg);
        }
    }

    //for info log
    public static void i(String tag, String msg) {
        if (DEBUG) {
            if (tag == null || "".equalsIgnoreCase(tag.trim())) {
                tag = mTag;
            }
            Log.i(tag, msg);
        }
    }

    //for debug log
    public static void d(String tag, String msg) {
        if (DEBUG) {
            if (tag == null || "".equalsIgnoreCase(tag.trim())) {
                tag = mTag;
            }
            Log.d(tag, msg);
        }
    }

    //for verbose log
    public static void v(String tag, String msg) {
        if (DEBUG) {
            if (tag == null || "".equalsIgnoreCase(tag.trim())) {
                tag = mTag;
            }
            Log.v(tag, msg);
        }
    }

    //for verbose log
    public static void e(String tag, String msg) {
        if (DEBUG) {
            if (tag == null || "".equalsIgnoreCase(tag.trim())) {
                tag = mTag;
            }
            Log.e(tag, msg);
        }
    }

    public static void setDebug(boolean isDebug) {
        DEBUG = isDebug;
    }

    public static boolean isDebug() {
        return DEBUG;
    }

    public static void showLog(String tag, String msg) {
        if (DEBUG && !TextUtils.isEmpty(msg)) {
            if (TextUtils.isEmpty(tag)) tag = mTag;
            StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
            int currentIndex = -1;
            for (int i = 0; i < stackTraceElement.length; i++) {
                if (stackTraceElement[i].getMethodName().compareTo("showLog") == 0) {
                    currentIndex = i + 1;
                    break;
                }
            }
            if (currentIndex >= 0) {
                String fullClassName = stackTraceElement[currentIndex].getClassName();
                String className = fullClassName.substring(fullClassName
                        .lastIndexOf(".") + 1);
                String methodName = stackTraceElement[currentIndex].getMethodName();
                String lineNumber = String
                        .valueOf(stackTraceElement[currentIndex].getLineNumber());

                Log.i(tag, msg + "\n  ---->at " + className + "." + methodName + "("
                        + className + ".java:" + lineNumber + ")");
            } else {
                Log.i(tag, msg);
            }
        }
    }
}
