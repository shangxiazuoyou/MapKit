package com.robbu.mapkit.wrapper;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Utils {

    public static Bitmap getBitmapFromView(View view) {
        try {
            processView(view);
            view.destroyDrawingCache();
            view.measure(
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            // view.buildDrawingCache();
            Bitmap bitmap = view.getDrawingCache()
                    .copy(Bitmap.Config.ARGB_8888, false);
            view.destroyDrawingCache();
            return bitmap;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void processView(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                processView(((ViewGroup) view).getChildAt(i));
            }
        } else if (view instanceof TextView) {
            ((TextView) view).setHorizontallyScrolling(false);
        }
    }
}
