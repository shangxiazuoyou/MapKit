package com.robbu.mapkit.wrapper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class LatLngWrapper {
    /**
     * 纬度
     */
    public final double latitude;
    /**
     * 经度
     */
    public final double longitude;
    private static DecimalFormat df = new DecimalFormat("0.000000", new DecimalFormatSymbols(Locale.CHINA));

    /**
     * 使用传入的经纬度构造LatLng 对象，一对经纬度值代表地球上一个地点。
     * 如果传入的经纬度不合理，会打印错误日志进行提示，然后转换为接近的合理的经纬度。
     *
     * @param latitude  地点的纬度，在-90 与90 之间的double 型数值。
     * @param longitude 地点的经度，在-180 与180 之间的double 型数值。
     */
    public LatLngWrapper(double latitude, double longitude) {
        this(latitude, longitude, true);
    }

    /**
     * 使用传入的经纬度构造LatLng 对象，一对经纬度值代表地球上一个地点。
     *
     * @param latitude  地点的纬度，在-90 与90 之间的double 型数值。
     * @param longitude 地点的经度，在-180 与180 之间的double 型数值。
     * @param isCheck   是否需要检查经纬度的合理性，建议填写true
     */
    public LatLngWrapper(double latitude, double longitude, boolean isCheck) {
        if (isCheck) {
            if ((-180.0D <= longitude) && (longitude < 180.0D)) {
                this.longitude = parseDouble(longitude);
            } else {
                this.longitude = parseDouble((((longitude - 180.0D) % 360.0D + 360.0D) % 360.0D - 180.0D));
            }

            if (latitude < -90D || latitude > 90D) {
//                try {
//                    throw new AMapException(AMapException.ERROR_ILLEGAL_VALUE);
//                } catch (AMapException e) {
//                    e.printStackTrace();
//                }
            }
            this.latitude = parseDouble(Math.max(-90.0D, Math.min(90.0D, latitude)));
        } else {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    private static double parseDouble(double d) {
        return Double.parseDouble(df.format(d));
    }

    public LatLngWrapper clone() {
        return new LatLngWrapper(latitude, longitude);
    }

    public int hashCode() {
        int i = 31;
        int j = 1;
        long l = Double.doubleToLongBits(this.latitude);
        j = i * j + (int) (l ^ l >>> 32);
        l = Double.doubleToLongBits(this.longitude);
        j = i * j + (int) (l ^ l >>> 32);
        return j;
    }

    /**
     * 判断是否与另一个LatLng 对象相等的方法。
     * 如果两个地点的经纬度全部相同，则返回true。否则返回false。
     *
     * @param paramObject 用于比较的经纬度坐标。
     * @return 对象是否相等。相等则返回true，反之false。
     * @since V2.0
     */
    public boolean equals(Object paramObject) {
        if (this == paramObject)
            return true;
        if (!(paramObject instanceof LatLngWrapper))
            return false;
        LatLngWrapper localLatLng = (LatLngWrapper) paramObject;
        return (Double.doubleToLongBits(this.latitude) == Double
                .doubleToLongBits(localLatLng.latitude))
                && (Double.doubleToLongBits(this.longitude) == Double
                .doubleToLongBits(localLatLng.longitude));
    }

    @Override
    public String toString() {
        return "lat/lng: (" + this.latitude + "," + this.longitude + ")";
    }
}
