package com.robbu.mapkit.map.common;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class MapView extends FrameLayout {

    private com.baidu.mapapi.map.MapView baiduMap;
    private com.amap.api.maps.MapView gaodeMap;

    public MapView(@NonNull Context context) {
        this(context, null);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(@NonNull Context context) {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            baiduMap = new com.baidu.mapapi.map.MapView(context,
                    new com.baidu.mapapi.map.BaiduMapOptions().compassEnabled(false).zoomControlsEnabled(false));
            baiduMap.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            addView(baiduMap);
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            gaodeMap = new com.amap.api.maps.MapView(context);
            gaodeMap.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            addView(gaodeMap);
        }
    }

    public Object getMap() {
        if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_BAIDU) {
            return baiduMap;
        } else if (ConfigConstants.MAP_TYPE == ConfigConstants.MAP_TYPE_GAODE) {
            return gaodeMap;
        }
        return null;
    }
}
