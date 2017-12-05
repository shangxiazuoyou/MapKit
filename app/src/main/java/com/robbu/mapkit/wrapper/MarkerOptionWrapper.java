package com.robbu.mapkit.wrapper;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * 这个类可以是共有的，里面可以包含所有SDKMarker的属性，
 * 每次外部调用addMarker时都使用这个Options，在子类中在进行转换
 */
public class MarkerOptionWrapper {

    private LatLngWrapper latLng;
    private String title;
    private String snippet;
    private float anchorU = 0.5F;
    private float anchorV = 1.0F;
    private float zIndex = 0;
    private boolean isDraggable = false;
    private boolean isVisible = true;
    private boolean perspective = false;
    private int offsetX = 0, offsetY = 0;
    private ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    private int period = 20;
    private boolean isGps = false;
    private boolean isFlat = false;
    private boolean isPerspective;

    public MarkerOptionWrapper icons(ArrayList<Bitmap> var1) {
        this.bitmaps = var1;
        return this;
    }

    public ArrayList<Bitmap> getIcons() {
        return bitmaps;
    }

    public MarkerOptionWrapper period(int var1) {
        this.period = var1;
        return this;
    }

    public int getPeriod() {
        return period;
    }

    public boolean isPerspective() {
        return isPerspective;
    }

    public MarkerOptionWrapper perspective(boolean var1) {
        this.isPerspective = var1;
        return this;
    }

    public MarkerOptionWrapper position(LatLngWrapper var1) {
        this.latLng = var1;
        return this;
    }

    public MarkerOptionWrapper setFlat(boolean var1) {
        this.isFlat = var1;
        return this;
    }

    private void initBitmaps() {
        if (bitmaps == null) {
            bitmaps = new ArrayList<Bitmap>();
        }
    }

    /**
     * 设置MarkerOptions 对象的自定义图标。
     * <p/>
     * param icon 设置图标的Bitmap对象
     * return MarkerOptions对象。
     * since V2.0
     */
    public MarkerOptionWrapper icon(Bitmap icon) {
        initBitmaps();
        bitmaps.clear();
        bitmaps.add(icon);
        return this;
    }

    public MarkerOptionWrapper anchor(float anchorX, float anchorY) {
        this.anchorU = anchorX;
        this.anchorV = anchorY;
        return this;
    }

    public MarkerOptionWrapper setInfoWindowOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;

        return this;
    }

    public MarkerOptionWrapper title(String var1) {
        this.title = var1;
        return this;
    }

    public MarkerOptionWrapper snippet(String var1) {
        this.snippet = var1;
        return this;
    }

    public MarkerOptionWrapper draggable(boolean var1) {
        this.isDraggable = var1;
        return this;
    }

    public MarkerOptionWrapper visible(boolean var1) {
        this.isVisible = var1;
        return this;
    }

    public MarkerOptionWrapper setGps(boolean var1) {
        this.isGps = var1;
        return this;
    }

    public LatLngWrapper getPosition() {
        return latLng;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public Bitmap getIcon() {
        if (bitmaps != null && bitmaps.size() != 0) {
            return this.bitmaps.get(0);
        } else {
            return null;
        }
    }

    public float getAnchorU() {
        return anchorU;
    }

    public int getInfoWindowOffsetX() {
        return offsetX;
    }

    public int getInfoWindowOffsetY() {
        return offsetY;
    }

    public float getAnchorV() {
        return anchorV;
    }

    public boolean isDraggable() {
        return isDraggable;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public boolean isGps() {
        return isGps;
    }

    public boolean isFlat() {
        return isFlat;
    }

    public MarkerOptionWrapper zIndex(float var1) {
        this.zIndex = var1;
        return this;
    }

    public float getZIndex() {
        return zIndex;
    }
}
