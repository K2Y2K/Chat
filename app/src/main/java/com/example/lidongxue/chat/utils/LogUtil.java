package com.example.lidongxue.chat.utils;

import android.util.Log;

/**
 * Created by lidongxue on 17-10-17.
 */

public class LogUtil {
    private static boolean isOpen = true;

    /**
     * 设置是否输出Log
     *
     * @param b true or false
     */
    public static void setSwitch(boolean b) {
        isOpen = b;
    }

    //**********************Debug***********//
    public static void d(String tag, String msg) {
        if (isOpen) Log.d(tag, msg);
    }

    public static void d(String tag, int msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, float msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, long msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, double msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, boolean msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    //**********************Info***********//
    public static void i(String tag, String msg) {
        if (isOpen) Log.i(tag, msg);
    }

    public static void i(String tag, int msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, float msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }


    public static void i(String tag, long msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, double msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, boolean msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    //**********************Error***********//
    public static void e(String tag, String msg) {
        if (isOpen) Log.e(tag, msg);
    }

    public static void e(String tag, int msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, float msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, Long msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, double msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, boolean msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

}

