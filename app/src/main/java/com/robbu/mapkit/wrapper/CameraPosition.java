package com.robbu.mapkit.wrapper;

/**
 * amap     对应的是 CameraPosition
 * baidulbs 对应的是 MapStatus
 */
public class CameraPosition {

    public final LatLngWrapper target;
    public final float zoom;
    public final float tilt;
    public final float bearing;

    //amap 特有
//    public final boolean isAbroad;

    //bd 特有
//    public final Point targetScreen;
//    public final LatLngBounds bound;

    public CameraPosition(LatLngWrapper latlng, float zoom, float tilt, float bearing) {
        this.target = latlng;
        this.zoom = zoom;
        this.tilt = tilt;
        this.bearing = bearing;
//        this.isAbroad = !dh.a(var1.latitude, var1.longitude);
    }

    public int hashCode() {
        return super.hashCode();
    }

    public static final CameraPosition fromLatLngZoom(LatLngWrapper var0, float var1) {
        return new CameraPosition(var0, var1, 0.0F, 0.0F);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(CameraPosition var0) {
        return new Builder(var0);
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (!(var1 instanceof CameraPosition)) {
            return false;
        } else {
            CameraPosition var2 = (CameraPosition) var1;
            return this.target.equals(var2.target) && Float.floatToIntBits(this.zoom) == Float.floatToIntBits(var2.zoom) && Float.floatToIntBits(this.tilt) == Float.floatToIntBits(var2.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(var2.bearing);
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" target=").append(this.target);
        buffer.append(" zoom=").append(this.zoom);
        buffer.append(" tilt=").append(this.tilt);
        buffer.append(" bearing=").append(this.bearing);

        return buffer.toString();
    }

    public static final class Builder {
        private LatLngWrapper a;
        private float b;
        private float c;
        private float d;

        public Builder() {
        }

        public Builder(CameraPosition var1) {
            this.target(var1.target).bearing(var1.bearing).tilt(var1.tilt).zoom(var1.zoom);
        }

        public Builder target(LatLngWrapper var1) {
            this.a = var1;
            return this;
        }

        public Builder zoom(float var1) {
            this.b = var1;
            return this;
        }

        public Builder tilt(float var1) {
            this.c = var1;
            return this;
        }

        public Builder bearing(float var1) {
            this.d = var1;
            return this;
        }

        public CameraPosition build() {
            if (this.a == null)
                throw new NullPointerException("target is null");
            return new CameraPosition(this.a, this.b, this.c, this.d);
        }
    }
}
