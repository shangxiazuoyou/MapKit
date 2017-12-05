package com.robbu.mapkit.map.baidu;

public class BaiduLocationService {

    private com.baidu.location.LocationClient client = null;
    private com.baidu.location.LocationClientOption mOption, DIYoption;
    private Object objLock = new Object();

    public BaiduLocationService(android.content.Context locationContext) {
        synchronized (objLock) {
            if (client == null) {
                client = new com.baidu.location.LocationClient(locationContext);
                client.setLocOption(getDefaultLocationClientOption());
            }
        }
    }

    public boolean registerListener(com.baidu.location.BDLocationListener listener) {
        boolean isSuccess = false;
        if (listener != null && client != null) {
            client.registerLocationListener(listener);
            isSuccess = true;
        }
        return isSuccess;
    }

    public void unregisterListener(com.baidu.location.BDLocationListener listener) {
        if (listener != null && client != null) {
            client.unRegisterLocationListener(listener);
        }
    }

    public boolean setLocationOption(com.baidu.location.LocationClientOption option) {
        boolean isSuccess = false;
        if (option != null && client != null) {
            if (client.isStarted())
                client.stop();
            DIYoption = option;
            client.setLocOption(option);
        }
        return isSuccess;
    }

    public com.baidu.location.LocationClientOption getOption() {
        return DIYoption;
    }

    /***
     * @return DefaultLocationClientOption
     */
    public com.baidu.location.LocationClientOption getDefaultLocationClientOption() {
        if (mOption == null) {
            mOption = new com.baidu.location.LocationClientOption();
            mOption.setLocationMode(com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return mOption;
    }

    public void start() {
        synchronized (objLock) {
            if (client != null && !client.isStarted()) {
                client.start();
            }
        }
    }

    public void stop() {
        synchronized (objLock) {
            if (client != null && client.isStarted()) {
                client.stop();
            }
        }
    }

    public void destroyLocation() {
        if (client != null) {
            client = null;
            mOption = null;
            DIYoption = null;
        }
    }
}
