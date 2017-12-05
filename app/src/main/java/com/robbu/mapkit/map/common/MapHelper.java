package com.robbu.mapkit.map.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.robbu.mapkit.map.baidu.BaiduMapHelper;
import com.robbu.mapkit.map.bean.LatLng;
import com.robbu.mapkit.map.gaode.GaodeMapHelper;
import com.robbu.mapkit.map.utils.SensorEventHelper;

public class MapHelper {

    private BaiduMapHelper baiduMapHelper;
    private GaodeMapHelper gaodeMapHelper;
    private Object map;
    private SensorEventHelper mSensorHelper;
    private Context context;

    /**
     * 建议该方法放在Application的初始化方法中
     *
     * @param context
     */
    public static void init(Context context) {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            com.baidu.mapapi.SDKInitializer.initialize(context);
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
        }
    }

    public MapHelper(@NonNull Context context, @NonNull Object mapView) {
        this.context = context;
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (mapView instanceof com.baidu.mapapi.map.MapView) {
                baiduMapHelper = new BaiduMapHelper(context, (com.baidu.mapapi.map.MapView) mapView);
                map = baiduMapHelper.getBaiduMap();
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (mapView instanceof com.amap.api.maps.MapView) {
                gaodeMapHelper = new GaodeMapHelper(context, (com.amap.api.maps.MapView) mapView);
                map = gaodeMapHelper.getAMap();
            }
        }
    }

    public Object getMap() {
        return map;
    }

    public void onCreate(Bundle savedInstanceState, MapLocationListenner locationListenner) {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.onCreate(locationListenner);
                mSensorHelper = new SensorEventHelper(context, baiduMapHelper.getMyOrientationListener());
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.onCreate(savedInstanceState, locationListenner);
                mSensorHelper = new SensorEventHelper(context, gaodeMapHelper.getMyOrientationListener());
            }
        }
    }

    public void onResume() {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.onResume();
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.onResume();
            }
        }

        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
    }

    public void onPause() {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.onPause();
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.onPause();
            }
        }

        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.onSaveInstanceState(outState);
            }
        }
    }

    public void onDestroy() {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.onDestroy();
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.onDestroy();
            }
        }

        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
        }
        mSensorHelper = null;
    }

    /**
     * 移动地图
     *
     * @param target 经纬度
     * @param zoom   缩放级别
     */
    public void animateMap(@NonNull LatLng target, float zoom) {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.animateMap(new com.baidu.mapapi.model.LatLng(target.getLatitude(), target.getLongitude()), zoom);
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.animateMap(new com.amap.api.maps.model.LatLng(target.getLatitude(), target.getLongitude()), zoom);
            }
        }
    }

    /**
     * 地图放大
     */
    public void zoomIn() {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.zoomIn();
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.zoomIn();
            }
        }
    }

    /**
     * 地图缩小
     */
    public void zoomOut() {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.zoomOut();
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.zoomOut();
            }
        }
    }

    public void setMapLoadedListener(MapLoadedListener listener) {
        if (listener == null) {
            return;
        }

        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.setMapLoadedListener(listener);
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.setMapLoadedListener(listener);
            }
        }
    }

    public void setMapClickListener(MapClickListener listener) {
        if (listener == null) {
            return;
        }
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.setMapClickListener(listener);
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.setMapClickListener(listener);
            }
        }
    }

    public void setMarkerClickListener(MarkerClickListener listener) {
        if (listener == null) {
            return;
        }

        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.setMarkerClickListener(listener);
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.setMarkerClickListener(listener);
            }
        }
    }

    public interface MapLoadedListener {
        void onMapLoaded();
    }

    public interface MapClickListener {
        void onMapClick(LatLng point);
    }

    public interface MarkerClickListener {
        boolean onMarkerClick(Object marker);
    }

    public interface MapLocationListenner {
        /**
         * 第一次定位成功
         *
         * @param location 百度-BDLocation，高德-AMapLocation
         */
        void onFirstSuccess(Object location);

        /**
         * 定位成功
         *
         * @param location 百度-BDLocation，高德-AMapLocation
         */
        void onLocationSuccess(Object location);
    }

    /**
     * 我的位置方向朝向监听
     */
    public interface MyOrientationListener {
        void onOrientationChanged(float x);
    }

    public void addMarkers(View view, LatLng latLng) {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            if (baiduMapHelper != null) {
                baiduMapHelper.addMarker(view, latLng);
            }
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            if (gaodeMapHelper != null) {
                gaodeMapHelper.addMarker(view, latLng);
            }
        }
    }
}
