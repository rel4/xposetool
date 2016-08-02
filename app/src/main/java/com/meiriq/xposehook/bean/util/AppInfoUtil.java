package com.meiriq.xposehook.bean.util;

import android.content.ContentValues;
import android.database.Cursor;

import com.meiriq.xposehook.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-11-27.
 */
public class AppInfoUtil {

    public static ContentValues wrapApp2Values(AppInfo appInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("pkgname",appInfo.getPname());
        contentValues.put("appname",appInfo.getAppname());

        return contentValues;
    }

    public static List<AppInfo> parse2AppInfoList(Cursor cursor) {
        List<AppInfo> list = new ArrayList<AppInfo>();
        while (cursor.moveToNext()) {
            list.add(parse2AppInfo(cursor));
        }
        // 关闭Cursor，释放资源
        return list;
    }

    private static AppInfo parse2AppInfo(Cursor cursor) {
        AppInfo appInfo = new AppInfo();

        appInfo.setPname(cursor.getString(cursor.getColumnIndex("pkgname")));
        appInfo.setAppname(cursor.getString(cursor.getColumnIndex("appname")));

        return appInfo;
    }
}
