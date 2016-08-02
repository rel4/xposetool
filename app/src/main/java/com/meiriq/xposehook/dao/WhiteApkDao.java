package com.meiriq.xposehook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.meiriq.xposehook.bean.ApkInfo;
import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.bean.util.ApkInfoUtil;
import com.meiriq.xposehook.bean.util.AppInfoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-11-27.
 */
public class WhiteApkDao extends BaseDao<ApkInfo>{

    private final String TABLE_WHITE_UNINSTALL = DbHelper.TABLE_WHITE_APK;

    public WhiteApkDao(Context context) {
        super(context);
        File file = new File(Environment.getExternalStorageDirectory()+"/.xpose/db/");
        if(!file.exists())
            file.mkdir();
        file = new File(file,"whiteapk.db");

        if(!file.exists()){
            mDatabase = SQLiteDatabase.openOrCreateDatabase
                    (file, null);
            mDatabase.execSQL(DbHelper.CREATE_WHITE_APK_TABLE);
        }else {
            mDatabase = SQLiteDatabase.openOrCreateDatabase
                    (file,null);
        }
    }

    @Override
    public boolean add(ApkInfo item) {
        return insert(TABLE_WHITE_UNINSTALL, ApkInfoUtil.wrapApk2Values(item)) != -1;
    }

    @Override
    public boolean addList(List<ApkInfo> list) {
        List<ContentValues> values = new ArrayList<>();
        for (ApkInfo appinfo:list) {
            values.add(ApkInfoUtil.wrapApk2Values(appinfo));

        }
        return insertList(TABLE_WHITE_UNINSTALL, values) != -1;
    }

    @Override
    public int delete(String whereClause, String[] whereArgs) {
        return delete(TABLE_WHITE_UNINSTALL,whereClause,whereArgs);
    }

    @Override
    public int clean() {
        return clean(TABLE_WHITE_UNINSTALL);
    }

    @Override
    public int update(ApkInfo item, String whereClause, String[] whereArgs) {
        return update(TABLE_WHITE_UNINSTALL, ApkInfoUtil.wrapApk2Values(item), whereClause, whereArgs);
    }

    @Override
    public Cursor query(String[] args) {
        return query(String.format("select * from %s", TABLE_WHITE_UNINSTALL), null);
    }

    /**
     * 获取所有白名单数据
     * @return
     */
    public List<ApkInfo> getAllData(){
        Cursor cursor = query(String.format("select * from %s", TABLE_WHITE_UNINSTALL), null);
        List<ApkInfo> appInfos = ApkInfoUtil.parse2AppInfoList(cursor);
        cursor.close();
        return appInfos;
    }
}
