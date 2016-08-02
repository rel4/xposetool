package com.meiriq.xposehook.utils;

import android.util.Log;

/**
 * Created by tian on 15-11-26.
 */
public class L {
    private static boolean isLog = true;
    private static String TAG = "unlock";
    public static void debug(String log){
        if(false){
            Log.d(TAG,log);
        }
    }

    public static void log(String log){
        if(isLog){
            Log.d(TAG,log);
        }
    }

    public static void xpose(String log){
        if(true){
            Log.d(TAG,log);
        }
    }

}
