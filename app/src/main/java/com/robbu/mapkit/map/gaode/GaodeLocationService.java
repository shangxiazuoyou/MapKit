package com.robbu.mapkit.map.gaode;

import android.content.Context;

public class GaodeLocationService {

    private com.amap.api.location.AMapLocationClient client = null;
    private com.amap.api.location.AMapLocationClientOption mOption, DIYoption;
    private Object objLock = new Object();

    public GaodeLocationService(Context context) {
        synchronized (objLock) {
            if (client == null) {
                client = new com.amap.api.location.AMapLocationClient(context);
                client.setLocationOption(getDefaultLocationClientOption());
            }
        }
    }

    public void registerListener(com.amap.api.location.AMapLocationListener listener) {
        if (client != null) {
            client.setLocationListener(listener);
        }
    }

    public void unregisterListener() {
        if (client != null) {
            client.setLocationListener(null);
        }
    }

    public void setLocationOption(com.amap.api.location.AMapLocationClientOption option) {
        if (client != null) {
            DIYoption = option;
            client.setLocationOption(option);
        }
    }

    public com.amap.api.location.AMapLocationClientOption getOption() {
        return DIYoption;
    }

    public com.amap.api.location.AMapLocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new com.amap.api.location.AMapLocationClientOption();
            mOption.setLocationMode(com.amap.api.location.AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
            mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
            mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
            mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
            mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
            mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
            mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
            com.amap.api.location.AMapLocationClientOption.setLocationProtocol(com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
            mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
            mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
            mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        }
        return mOption;
    }

    public void start() {
        synchronized (objLock) {
            if (client != null) {
                client.startLocation();
            }
        }
    }

    public void stop() {
        synchronized (objLock) {
            if (client != null) {
                client.stopLocation();
            }
        }
    }

    public void destroyLocation() {
        if (client != null) {
            client.onDestroy();
            client = null;
            mOption = null;
            DIYoption = null;
        }
    }
}
