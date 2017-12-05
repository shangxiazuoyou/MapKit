package com.robbu.mapkit.map.common;

import android.content.Context;

import com.robbu.mapkit.map.baidu.BaiduLocationService;
import com.robbu.mapkit.map.bean.MapLocation;
import com.robbu.mapkit.map.gaode.GaodeLocationService;

public class LocationService {

    private BaiduLocationService baiduLocationService;
    private GaodeLocationService gaodeLocationService;
    private Object option;
    private MapLocationListener locationListener;

    private com.baidu.location.BDLocationListener bdLocationListener;

    private com.amap.api.location.AMapLocationListener aMapLocationListener;

    public LocationService(Context context) {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            baiduLocationService = new BaiduLocationService(context);
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            gaodeLocationService = new GaodeLocationService(context);
        }
    }

    public void registerListener(MapLocationListener listener) {
        if (listener == null) {
            return;
        }
        this.locationListener = listener;
        final MapLocation location = new MapLocation();

        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            bdLocationListener = new com.baidu.location.BDLocationListener() {
                @Override
                public void onReceiveLocation(com.baidu.location.BDLocation bdLocation) {
                    if (null != bdLocation && bdLocation.getLocType() != com.baidu.location.BDLocation.TypeServerError) {
                        location.setLatitude(bdLocation.getLatitude());
                        location.setLongitude(bdLocation.getLongitude());
                        location.setAddress(bdLocation.getAddrStr());
                        locationListener.onLocationChanged(location);
                    }
                }

//                @Override
//                public void onConnectHotSpotMessage(String s, int i) {
//
//                }
            };
            baiduLocationService.registerListener(bdLocationListener);
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            aMapLocationListener = new com.amap.api.location.AMapLocationListener() {
                @Override
                public void onLocationChanged(com.amap.api.location.AMapLocation aMapLocation) {
                    if (aMapLocation != null) {
                        location.setLatitude(aMapLocation.getLatitude());
                        location.setLongitude(aMapLocation.getLongitude());
                        location.setAddress(aMapLocation.getAddress());
                        locationListener.onLocationChanged(location);
                    }
                }
            };
            gaodeLocationService.registerListener(aMapLocationListener);
        }
    }

    public void unregisterListener() {
        locationListener = null;

        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            baiduLocationService.unregisterListener(bdLocationListener);
            bdLocationListener = null;
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            gaodeLocationService.unregisterListener();
            aMapLocationListener = null;
        }
    }

    public void start() {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            baiduLocationService.start();
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            gaodeLocationService.start();
        }
    }

    public void stop() {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            baiduLocationService.stop();
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            gaodeLocationService.stop();
        }
    }

    public void setLocationOption(Object option) {
        if (option == null) {
            return;
        }
        this.option = option;

        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (option instanceof com.baidu.location.LocationClientOption) {
                baiduLocationService.setLocationOption((com.baidu.location.LocationClientOption) option);
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (option instanceof com.amap.api.location.AMapLocationClientOption) {
                gaodeLocationService.setLocationOption((com.amap.api.location.AMapLocationClientOption) option);
            }
        }
    }

//    public Object getOption() {
//        return this.option;
//    }

    public Object getDefaultLocationClientOption() {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            return baiduLocationService.getDefaultLocationClientOption();
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            return gaodeLocationService.getDefaultLocationClientOption();
        }
        return null;
    }

    public void destroyLocation() {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            baiduLocationService.destroyLocation();
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            gaodeLocationService.destroyLocation();
        }
    }

    public interface MapLocationListener {
        void onLocationChanged(MapLocation location);
    }

}
