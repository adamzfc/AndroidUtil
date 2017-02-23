package com.adamzfc.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by adamzfc on 2/23/17.
 */

public final class ConvertUtil {
    private ConvertUtil(){}

    /**
     * only use for drawable which is instanceof BitmapDrawable
     * @param drawable input drawable
     * @return bitmap or null
     */
    public static @Nullable Bitmap drawable2Bitmap(@NonNull Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return  ((BitmapDrawable)drawable).getBitmap();
        } else {
            return null;
        }
    }
}
