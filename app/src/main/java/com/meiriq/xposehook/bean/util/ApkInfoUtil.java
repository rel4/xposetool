package com.meiriq.xposehook.bean.util;

import android.content.ContentValues;
import android.database.Cursor;

import com.meiriq.xposehook.bean.ApkInfo;
import com.meiriq.xposehook.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-11-27.
 */
public class ApkInfoUtil {

    public static ContentValues wrapApk2Values(ApkInfo apkInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("directory",apkInfo.getDirectory());
        contentValues.put("name",apkInfo.getName());

        return contentValues;
    }

    public static List<ApkInfo> parse2AppInfoList(Cursor cursor) {
        List<ApkInfo> list = new ArrayList<ApkInfo>();
        while (cursor.moveToNext()) {
            list.add(parse2ApkInfo(cursor));
        }
        // 关闭Cursor，释放资源
        return list;
    }

    private static ApkInfo parse2ApkInfo(Cursor cursor) {
        ApkInfo apkInfo = new ApkInfo();

        apkInfo.setDirectory(cursor.getString(cursor.getColumnIndex("directory")));
        apkInfo.setName(cursor.getString(cursor.getColumnIndex("name")));

        return apkInfo;
    }
}
