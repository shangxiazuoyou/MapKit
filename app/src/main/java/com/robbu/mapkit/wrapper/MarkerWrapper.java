package com.robbu.mapkit.wrapper;

import android.graphics.Bitmap;

import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

import java.util.ArrayList;

public class MarkerWrapper implements IMarker {

    private Marker marker = null;

    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();

    public MarkerWrapper(Marker marker) {
        this.marker = marker;
    }

    @Override
    public void setPeriod(int var1) {
        if (marker != null)
            marker.setPeriod(var1);
    }

    @Override
    public int getPeriod() {
        if (marker != null)
            return marker.getPeriod();
        return 0;
    }

    @Override
    public void setIcons(ArrayList<Bitmap> var1) {
        if (marker != null && var1 != null && var1.size() > 0) {
            ArrayList<BitmapDescriptor> bitmapDescriptors = new ArrayList<BitmapDescriptor>();
            synchronized (bitmaps) {
//                recycleBitmaps();
                bitmaps.clear();
                bitmaps.addAll(var1);
                for(Bitmap bitmap: bitmaps) {
                    if(bitmap != null && !bitmap.isRecycled()) {
                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                        if(bitmapDescriptor != null) {
                            bitmapDescriptors.add(bitmapDescriptor);
                        }
                    }
                }

                marker.setIcons(bitmapDescriptors);
            }
        }
    }

    @Override
    public ArrayList<Bitmap> getIcons() {
//        if (marker != null)
//            return marker.getIcons();
        return bitmaps;
    }

    @Override
    public void remove() {
        if (marker != null)
            marker.remove();
    }

    /**
     * 只会回收当前记录的图片
     */
    @Override
    public void destroy() {
        if (marker != null)
            marker.destroy();
        recycleBitmaps();
    }

    private void recycleBitmaps() {
        synchronized (bitmaps) {
            for (Bitmap bitmap : bitmaps) {
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                }
            }
            bitmaps.clear();
        }
    }

    @Override
    public String getId() {
        if (marker != null)
            return marker.getId();
        return "";
    }

    @Override
    public void setPerspective(boolean var1) {
        if (marker != null)
            marker.setPerspective(var1);
    }

    @Override
    public boolean isPerspective() {
        if (marker != null)
            return marker.isPerspective();
        return false;
    }

    @Override
    public void setPosition(LatLngWrapper var1) {
        if (marker != null)
            if (var1 != null) {
                marker.setPosition(new LatLng(var1.latitude, var1.longitude));
            }

    }

    @Override
    public void setTitle(String var1) {
        if (marker != null)
            marker.setTitle(var1);
    }

    @Override
    public String getTitle() {
        if (marker != null)
            return marker.getTitle();
        return null;
    }

    @Override
    public void setSnippet(String var1) {
        if (marker != null)
            marker.setSnippet(var1);
    }

    @Override
    public String getSnippet() {
        if (marker != null)
            return marker.getSnippet();
        return null;
    }

    /**
     * 记得自定处理上一张图片，调用此方法对于之前的图片不会进行回收
     * @param var1
     */
    @Override
    public void setIcon(Bitmap var1) {
        if (marker != null && var1 != null && !var1.isRecycled()) {
            ArrayList<BitmapDescriptor> bitmapDescriptors = new ArrayList<BitmapDescriptor>();
            synchronized (bitmaps) {
//                recycleBitmaps();
                bitmaps.clear();
                bitmaps.add(var1);
                for(Bitmap bitmap: bitmaps) {
                    if(bitmap != null && !bitmap.isRecycled()) {
                        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
                        if(bitmapDescriptor != null) {
                            bitmapDescriptors.add(bitmapDescriptor);
                        }
                    }
                }

                marker.setIcons(bitmapDescriptors);
            }
        }
    }

    @Override
    public void setAnchor(float var1, float var2) {

        if (marker != null)
            marker.setAnchor(var1, var2);
    }

    @Override
    public void setDraggable(boolean var1) {
        if (marker != null)
            marker.setDraggable(var1);

    }

    @Override
    public boolean isDraggable() {
        if (marker != null)
            return marker.isDraggable();
        return false;
    }

    @Override
    public void showInfoWindow() {
        if (marker != null)
            marker.showInfoWindow();

    }

    @Override
    public void hideInfoWindow() {

        if (marker != null)
            marker.hideInfoWindow();

    }

    @Override
    public boolean isInfoWindowShown() {
        if (marker != null)
            marker.isInfoWindowShown();
        return false;
    }

    @Override
    public void setVisible(boolean var1) {
        if (marker != null)
            marker.setVisible(var1);

    }

    @Override
    public boolean isVisible() {

        if (marker != null)
            return marker.isVisible();
        return false;
    }

    @Override
    public void setObject(Object var1) {
        if (marker != null)
            marker.setObject(var1);

    }

    @Override
    public Object getObject() {
        if (marker != null)
            marker.getObject();
        return null;
    }

    @Override
    public void setRotateAngle(float var1) {
        if (marker != null)
            marker.setRotateAngle(var1);

    }

    @Override
    public float getRotateAngle() {
        if (marker != null)
            return marker.getRotateAngle();
        return 0;
    }

    @Override
    public void setToTop() {
        if (marker != null)
            marker.setToTop();

    }

    @Override
    public void setGeoPoint(com.autonavi.amap.mapcore.IPoint var1) {
        if (marker != null)
            marker.setGeoPoint(new com.autonavi.amap.mapcore.IPoint(var1.x, var1.y));
    }

    @Override
    public IPoint getGeoPoint() {
        if (marker != null) {
            com.autonavi.amap.mapcore.IPoint iPoint = marker.getGeoPoint();
            if (iPoint != null)
                return new IPoint(iPoint.x, iPoint.y);
        }
        return null;
    }

    @Override
    public void setFlat(boolean var1) {
        if (marker != null)
            marker.setFlat(var1);

    }

    @Override
    public boolean isFlat() {
        if (marker != null)
            return marker.isFlat();
        return false;
    }

    @Override
    public void setPositionByPixels(int var1, int var2) {
        if (marker != null)
            marker.setPositionByPixels(var1, var2);

    }

    @Override
    public void setZIndex(float var1) {
        if (marker != null)
            marker.setZIndex(var1);

    }

    @Override
    public float getZIndex() {
        if (marker != null)
            return marker.getZIndex();
        return 0;
    }
}
