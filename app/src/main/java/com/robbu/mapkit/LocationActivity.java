package com.robbu.mapkit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.robbu.mapkit.map.bean.MapLocation;
import com.robbu.mapkit.map.common.LocationService;


public class LocationActivity extends AppCompatActivity implements LocationService.MapLocationListener {

    private LocationService locationService;
    private Button btn_loc;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setMovementMethod(ScrollingMovementMethod.getInstance());

        btn_loc = (Button) findViewById(R.id.btn_loc);
        btn_loc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (btn_loc.getText().toString().equals(getString(R.string.startlocation))) {
                    locationService.start();
                    btn_loc.setText(getString(R.string.stoplocation));
                    tv_content.setText("正在定位...");
                } else {
                    locationService.stop();
                    btn_loc.setText(getString(R.string.startlocation));
                    tv_content.setText("定位停止");
                }
            }
        });

        locationService = new LocationService(this);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationService.registerListener(this);
    }

    @Override
    protected void onStop() {
        locationService.unregisterListener(); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.destroyLocation();
    }

    @Override
    public void onLocationChanged(MapLocation location) {
        if (location != null) {
//            tv_content.setText("经    度    : " + location.getLongitude() + "\n"
//                    + "纬    度    : " + location.getLatitude() + "\n"
//                    + "地    址    : " + location.getAddress());
            Log.e("地址", "经    度    : " + location.getLongitude() + "\n"
                    + "纬    度    : " + location.getLatitude() + "\n"
                    + "地    址    : " + location.getAddress());
        }
    }

}
