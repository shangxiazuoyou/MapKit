package com.robbu.mapkit.wrapper;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;

import java.io.InputStream;

/**
 * 创建Bitmap 对象。
 * 传入的图片资源不会被回收
 */
public final class BitmapFactory {

    /**
     * 根据传入的图片文件的资源ID，创建Bitmap 对象。
     *
     * @param resourceId 图片文件的资源ID。
     * @return 根据传入的图片文件的资源ID，创建的Bitmap 对象，如文件不存在，则返回null。
     */
    public static Bitmap fromResource(Context context, int resourceId) {
        try {
            if (context != null) {
                InputStream is = context.getResources().openRawResource(
                        resourceId);
//				TypedValue value = new TypedValue();
                Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(is);
                return bitmap;
            }
            return null;
        } catch (Throwable localRemoteException) {
            return null;
        }
    }

    /**
     * 根据传入的view，创建Bitmap对象。
     *
     * @param view 要显示的view。
     * @return 根据传入的view，创建的Bitmap对象，如view不存在，则返回null。
     * @since V2.1.3
     */
    public static Bitmap fromView(Context context, View view) {
        try {
            if (context != null) {
                FrameLayout frameLayout = new FrameLayout(context);
                frameLayout.addView(view);
                frameLayout.setDrawingCacheEnabled(true);
                Bitmap bitmap = Utils.getBitmapFromView(frameLayout);
                return bitmap;
            }
            return null;
        } catch (Throwable localRemoteException) {
            return null;
        }
    }

    /**
     * 根据传入的图片文件名的绝对地址，创建的Bitmap 对象。
     *
     * @param absolutePath 图片的绝对地址
     * @return 根据传入的图片文件名的绝对地址，创建的Bitmap 对象，如文件不存在，则返回null。
     * @since V2.0
     */
    public static Bitmap fromPath(String absolutePath) {
        try {
            Bitmap bitmap = android.graphics.BitmapFactory.decodeFile(absolutePath);
            return bitmap;
        } catch (Throwable localRemoteException) {
            return null;
        }
    }

    /**
     * 根据传入的asset 目录内图片文件名称创建图片的Bitmap 对象。
     *
     * @param assetName asset 文件内的一个图片文件的文件名称。
     * @return 根据传入的asset 目录内图片文件名称创建图片的Bitmap 对象，如果图片不存在则返回null。
     * @since V2.0
     */
    public static Bitmap fromAsset(String assetName) {
        try {
            InputStream fis = BitmapFactory.class
                    .getResourceAsStream("/assets/" + assetName);
            Bitmap bitmap = android.graphics.BitmapFactory.decodeStream(fis);
            fis.close();
            return bitmap;
        } catch (Throwable localRemoteException) {
            return null;
        }
    }

}
