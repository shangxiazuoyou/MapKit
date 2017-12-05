package com.robbu.mapkit.map.utils;

import com.robbu.mapkit.map.bean.LatLng;
import com.robbu.mapkit.map.common.ConfigConstants;

public class MapUtils {

    public static double getDistance(LatLng latLng1, LatLng latLng2) {
        if (latLng1 == null || latLng2 == null) {
            return 0;
        }
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            return com.baidu.mapapi.utils.DistanceUtil.getDistance(
                    new com.baidu.mapapi.model.LatLng(latLng1.getLatitude(), latLng1.getLongitude()),
                    new com.baidu.mapapi.model.LatLng(latLng2.getLatitude(), latLng2.getLongitude()));
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            return com.amap.api.maps.AMapUtils.calculateLineDistance(
                    new com.amap.api.maps.model.LatLng(latLng1.getLatitude(), latLng1.getLongitude()),
                    new com.amap.api.maps.model.LatLng(latLng2.getLatitude(), latLng2.getLongitude()));
        }

        return 0;
    }

}
