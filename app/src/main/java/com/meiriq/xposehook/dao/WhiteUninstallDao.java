package com.meiriq.xposehook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.bean.util.AppInfoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-11-27.
 */
public class WhiteUninstallDao extends BaseDao<AppInfo>{

    private final String TABLE_WHITE_UNINSTALL = DbHelper.TABLE_WHITE_UNINSTALL;

    public WhiteUninstallDao(Context context) {
        super(context);
        File file = new File(Environment.getExternalStorageDirectory()+"/.xpose/db/");
        if(!file.exists())
            file.mkdir();
        file = new File(file,"whiteuninstall.db");

        if(!file.exists()){
            mDatabase = SQLiteDatabase.openOrCreateDatabase
                    (file, null);
            mDatabase.execSQL(DbHelper.CREATE_WHITE_UNINSTALL_TABLE);
        }else {
            mDatabase = SQLiteDatabase.openOrCreateDatabase
                    (file,null);
        }
    }

    @Override
    public boolean add(AppInfo item) {
        return insert(TABLE_WHITE_UNINSTALL, AppInfoUtil.wrapApp2Values(item)) != -1;
    }

    @Override
    public boolean addList(List<AppInfo> list) {
        List<ContentValues> values = new ArrayList<>();
        for (AppInfo appinfo:list) {
            values.add(AppInfoUtil.wrapApp2Values(appinfo));

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
    public int update(AppInfo item, String whereClause, String[] whereArgs) {
        return update(TABLE_WHITE_UNINSTALL, AppInfoUtil.wrapApp2Values(item), whereClause, whereArgs);
    }

    @Override
    public Cursor query(String[] args) {
        return query(String.format("select * from %s", TABLE_WHITE_UNINSTALL), null);
    }

    /**
     * 获取所有白名单数据
     * @return
     */
    public List<AppInfo> getAllData(){
        Cursor cursor = query(String.format("select * from %s", TABLE_WHITE_UNINSTALL), null);
        List<AppInfo> appInfos = AppInfoUtil.parse2AppInfoList(cursor);
        cursor.close();
        return appInfos;
    }
}
