package com.robbu.mapkit.wrapper;

import android.util.Log;

/**
 * 日志记录模块
 */
public class LogManager {

    public final static String TAG = "MapKit";

    // 锁，是否关闭Log日志输出,在这里设置没有多大的意义，控制移动到ConfigableConst.DEBUG_MODE
    public final static boolean LogOFF = false;

    /**** 5中Log日志类型 *******/
    /**
     * 调试日志类型
     */
    public static final int DEBUG = 111;

    /**
     * 错误日志类型
     */
    public static final int ERROR = 112;
    /**
     * 信息日志类型
     */
    public static final int INFO = 113;
    /**
     * 详细信息日志类型
     */
    public static final int VERBOSE = 114;
    /**
     * 警告调试日志类型
     */
    public static final int WARN = 115;

    /**
     * 使用自带的TAG打印日志
     *
     * @param style 日志类型
     * @param infos 日志信息
     */
    public static void Log(int style, Object... infos) {
        if (!LogOFF) {
            LogTag(TAG, style, infos);
        }
    }

    /**
     * 自定义TAG打印日志
     *
     * @param tag   自定义的tag
     * @param style 日志类型
     * @param infos 日志信息
     */
    public static void LogTag(String tag, int style, Object... infos) {
        if (!LogOFF) {
            StringBuilder builder = new StringBuilder();
            for (Object obj : infos) {
                builder.append(" ");
                builder.append(obj.toString());
            }
            Log(tag, builder.toString(), style);
        }
    }

    /**
     * 显示，打印日志
     */
    public static void Log(String Tag, String Message, int Style) {
        if (!LogOFF) {
            switch (Style) {
                case DEBUG:
                    Log.d(Tag, Message);
                    break;
                case ERROR:
                    Log.e(Tag, Message);
                    break;
                case INFO:
                    Log.i(Tag, Message);
                    break;
                case VERBOSE:
                    Log.v(Tag, Message);
                    break;
                case WARN:
                    Log.w(Tag, Message);
                    break;
            }
        }
    }

    // Log.v (VERBOSE) 詳細訊息
    // Log.d (DEBUG) 除錯訊息
    // Log.i (INFO) 通知訊息
    // Log.w (WARN) 警告訊息
    // Log.e (ERROR) 錯誤訊息
}
