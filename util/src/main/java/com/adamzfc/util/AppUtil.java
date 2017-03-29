package com.adamzfc.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * app util
 * Created by adamzfc on 3/29/17.
 */

public final class AppUtil {
    /**
     * get application's versionCode
     * @param context context
     * @return versionCode or -1
     */
    public static int getAppVersionCode(@NonNull Context context) {
        checkNotNull(context);
        return getAppVersionCode(context, context.getPackageName());
    }

    /**
     * get application's versionCode
     * @param context context
     * @param packageName packageName
     * @return versionCode or -1
     */
    public static int getAppVersionCode(@NonNull Context context, @Nullable String packageName) {
        checkNotNull(context);
        if (isEmpty(packageName)) return -1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static boolean isEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    private static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }
}
