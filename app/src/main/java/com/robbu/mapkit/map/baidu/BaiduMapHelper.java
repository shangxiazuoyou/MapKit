package com.robbu.mapkit.map.baidu;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.robbu.mapkit.R;
import com.robbu.mapkit.map.common.MapHelper;

public class BaiduMapHelper {

    // 定位相关
    private com.baidu.location.LocationClient mLocClient = null;
    private com.baidu.mapapi.map.MapView mMapView = null;
    private com.baidu.mapapi.map.BaiduMap mBaiduMap = null;
    private boolean isFirstLoc; // 是否首次定位
    private Context context;
    private MapHelper.MyOrientationListener myOrientationListener;

    public BaiduMapHelper(@NonNull Context context, @NonNull com.baidu.mapapi.map.MapView mapView) {
        this.context = context;
        mMapView = mapView;
        mBaiduMap = mMapView.getMap();
        initMapSet();
    }

    private void initMapSet() {
        com.baidu.mapapi.map.UiSettings uiSettings = mBaiduMap.getUiSettings();
        uiSettings.setOverlookingGesturesEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);

//        mBaiduMap.setMaxAndMinZoomLevel(21, 3);
    }

    public com.baidu.mapapi.map.BaiduMap getBaiduMap() {
        return mBaiduMap;
    }

    public void onCreate(final MapHelper.MapLocationListenner listenner) {
        if (listenner != null) {
            isFirstLoc = true;
            // 定位初始化
            mLocClient = new com.baidu.location.LocationClient(context);
            mLocClient.registerLocationListener(new com.baidu.location.BDLocationListener() {
                @Override
                public void onReceiveLocation(com.baidu.location.BDLocation bdLocation) {
                    // mMapView 销毁后不在处理新接收的位置
                    if (bdLocation == null || mMapView == null) {
                        return;
                    }
                    mBaiduMap.setMyLocationData(new com.baidu.mapapi.map.MyLocationData.Builder()
                            .latitude(bdLocation.getLatitude())
                            .longitude(bdLocation.getLongitude()).build());
                    listenner.onLocationSuccess(bdLocation);
                    if (isFirstLoc) {
                        isFirstLoc = false;
                        listenner.onFirstSuccess(bdLocation);
                    }
                }

//                @Override
//                public void onConnectHotSpotMessage(String s, int i) {
//
//                }
            });

            myOrientationListener = new MapHelper.MyOrientationListener() {
                @Override
                public void onOrientationChanged(float x) {
                    MyLocationData locData = mBaiduMap.getLocationData();
                    if (locData != null) {
                        mBaiduMap.setMyLocationData(new MyLocationData.Builder()
                                .direction(x)
                                .latitude(locData.latitude)
                                .longitude(locData.longitude).build());
                    }
                }
            };

            // 开启定位图层
            mBaiduMap.setMyLocationEnabled(true);

            mBaiduMap.setMyLocationConfigeration(new com.baidu.mapapi.map.MyLocationConfiguration(
                    com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.NORMAL, true,
                    com.baidu.mapapi.map.BitmapDescriptorFactory.fromResource(R.mipmap.navi_map_gps_locked)));

            com.baidu.location.LocationClientOption option = new com.baidu.location.LocationClientOption();
            option.setOpenGps(true); // 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setScanSpan(1000);
            mLocClient.setLocOption(option);
        }
    }

    public void onResume() {
        mMapView.onResume();
        if (mLocClient != null) {
            mLocClient.start();
        }
    }

    public void onPause() {
        mMapView.onPause();
        if (mLocClient != null) {
            mLocClient.stop();
        }
    }

    public void onDestroy() {
        if (mLocClient != null) {
            // 退出时销毁定位
            mLocClient.stop();
            mLocClient = null;
        }
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    public void animateMap(com.baidu.mapapi.model.LatLng target, float zoom) {
        mBaiduMap.animateMapStatus(com.baidu.mapapi.map.MapStatusUpdateFactory.newMapStatus(
                new com.baidu.mapapi.map.MapStatus.Builder().target(target).zoom(zoom).build()));
    }

    public void zoomIn() {
        mBaiduMap.animateMapStatus(com.baidu.mapapi.map.MapStatusUpdateFactory.zoomIn());
    }

    public void zoomOut() {
        mBaiduMap.animateMapStatus(com.baidu.mapapi.map.MapStatusUpdateFactory.zoomOut());
    }

    public void setMapLoadedListener(final MapHelper.MapLoadedListener listener) {
        mBaiduMap.setOnMapLoadedCallback(new com.baidu.mapapi.map.BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                listener.onMapLoaded();
            }
        });
    }

    public void setMapClickListener(final MapHelper.MapClickListener listener) {
        mBaiduMap.setOnMapClickListener(new com.baidu.mapapi.map.BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(com.baidu.mapapi.model.LatLng latLng) {
                if (latLng != null) {
                    listener.onMapClick(new com.robbu.mapkit.map.bean.LatLng(latLng.latitude, latLng.longitude));
                }
            }

            @Override
            public boolean onMapPoiClick(com.baidu.mapapi.map.MapPoi mapPoi) {
                return false;
            }
        });
    }

    public void setMarkerClickListener(final MapHelper.MarkerClickListener listener) {
        mBaiduMap.setOnMarkerClickListener(new com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(com.baidu.mapapi.map.Marker marker) {
                if (marker != null) {
                    listener.onMarkerClick(marker);
                }
                return false;
            }
        });
    }

    public MapHelper.MyOrientationListener getMyOrientationListener() {
        return myOrientationListener;
    }

    private Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.UNSPECIFIED);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public void addMarker(View view, com.robbu.mapkit.map.bean.LatLng latLng) {
        com.baidu.mapapi.model.LatLng latLng1 = new com.baidu.mapapi.model.LatLng(latLng.getLatitude(), latLng.getLongitude());
        BitmapDescriptor bd1 = BitmapDescriptorFactory.fromBitmap(getBitmapFromView(view));
        MarkerOptions ooA = new MarkerOptions().position(latLng1).anchor(0.5f, 0.5f).icon(bd1).draggable(false);
        mBaiduMap.addOverlay(ooA);
    }

}
