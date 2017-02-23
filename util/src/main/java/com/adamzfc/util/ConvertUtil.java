package com.adamzfc.util;

import android.content.Context;
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

    /**
     * dp to int px
     * @param dp dp
     * @param context context
     * @return int px
     */
    public static int dp2px(float dp, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px to int dp
     * @param px px
     * @param context context
     * @return int dp
     */
    public static int px2dp(float px, Context context) {
        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px * scaledDensity + 0.5f);
    }

    /**
     * px 2 int sp
     * @param px px
     * @param context context
     * @return int sp
     */
    public static int px2sp(float px, Context context) {
        final float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scaledDensity + 0.5f);
    }

}
