package com.robbu.mapkit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.baidu.mapapi.map.Marker;
import com.robbu.mapkit.map.bean.LatLng;
import com.robbu.mapkit.map.common.ConfigConstants;
import com.robbu.mapkit.map.common.MapHelper;
import com.robbu.mapkit.map.common.MapView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, MapHelper.MapLocationListenner {

    private MapHelper mapHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MapView mMapView = (MapView) findViewById(R.id.map);
        mapHelper = new MapHelper(this, mMapView.getMap());
        mapHelper.onCreate(savedInstanceState, this);

        findViewById(R.id.btn_change).setOnClickListener(this);
        findViewById(R.id.btn_location).setOnClickListener(this);
        findViewById(R.id.btn_zoomin).setOnClickListener(this);
        findViewById(R.id.btn_zoomout).setOnClickListener(this);

        mapHelper.setMapClickListener(new MapHelper.MapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                Log.e("坐标位置", point.getLatitude() + "-" + point.getLongitude());
            }
        });
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout, null);

        mapHelper.addMarkers(view, new LatLng(30.547185, 104.061081));

        mapHelper.setMarkerClickListener(new MapHelper.MarkerClickListener() {
            @Override
            public boolean onMarkerClick(Object marker) {
                if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
                    Marker marker1 = (Marker) marker;
                    Log.e("Marker", marker1.getPosition() + "");
                } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
                    com.amap.api.maps.model.Marker marker1 = (com.amap.api.maps.model.Marker) marker;
                    Log.e("Marker", marker1.getPosition() + "");
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mapHelper.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapHelper.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapHelper.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapHelper.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                ConfigConstants.MAP_TYPE = ConfigConstants.MAP_TYPE_BAIDU;
                break;
            case R.id.btn_location:
                startActivity(new Intent(this, LocationActivity.class));
                break;
            case R.id.btn_zoomin:
                mapHelper.zoomIn();
                break;
            case R.id.btn_zoomout:
                mapHelper.zoomOut();
                break;
            default:
                break;
        }
    }

    @Override
    public void onFirstSuccess(Object location) {
        if (location == null) {
            return;
        }

        if (location instanceof com.baidu.location.BDLocation) {
            com.baidu.location.BDLocation bdLocation = (com.baidu.location.BDLocation) location;
            mapHelper.animateMap(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()), 18);
        }

        if (location instanceof com.amap.api.location.AMapLocation) {
            com.amap.api.location.AMapLocation aMapLocation = (com.amap.api.location.AMapLocation) location;
            mapHelper.animateMap(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()), 18);
        }
    }

    @Override
    public void onLocationSuccess(Object location) {

    }
}

