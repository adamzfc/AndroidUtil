package com.adamzfc.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * convert util
 * Created by adamzfc on 2/23/17.
 */

public final class ConvertUtil {
    private ConvertUtil(){}

    /**
     * only use for drawable which is instanceof BitmapDrawable
     * @param drawable input drawable
     * @return bitmap or null
     */
    public static @Nullable Bitmap drawable2Bitmap(@Nullable Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return  ((BitmapDrawable)drawable).getBitmap();
        } else {
            return null;
        }
    }

    /**
     * convert view to bitmap
     * @param view view
     * @return bitmap or null
     */
    public static @Nullable Bitmap view2Bitmap(@Nullable View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }
}
