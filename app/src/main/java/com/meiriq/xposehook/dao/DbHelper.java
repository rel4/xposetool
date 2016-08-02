package com.meiriq.xposehook.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tian on 15-11-27.
 */
public class DbHelper extends SQLiteOpenHelper{

    /**
     * 数据库名称
     */
    public static final String DB_NAME = "mrq_xpose.db";
    /**
     * 数据库版本信息
     */
    public static final int DB_VERSION = 1;

    public static final String TABLE_WHITE_UNINSTALL = "whiteuninstall";

    public static final String TABLE_CLEAR_DATA = "cleardata";

    public static final String TABLE_WHITE_APK = "whiteapk";

    public static final String TABLE_LOCAL_DATA = "localdata";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static final String CREATE_LOCAL_DATA_TABLE = "CREATE TABLE "
            + TABLE_LOCAL_DATA
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,savetime VARCHAR(32),usetime VARCHAR(32),detailtime INTEGER,"
            + "id VARCHAR(32) NOT NULL UNIQUE ,imei VARCHAR(32) NOT NULL UNIQUE,android_id VARCHAR(32),phoneNum VARCHAR(32),simId VARCHAR(32),"
            + "isim VARCHAR(32),operator VARCHAR(32),netTypeName VARCHAR(32),netType VARCHAR(32),phoneType VARCHAR(32),"
            + "simStatus VARCHAR(32),macAddress VARCHAR(32),routeName VARCHAR(32),routeAddress VARCHAR(32),systemVersion VARCHAR(32),"
            + "systemVersionValue VARCHAR(32),systemFramework VARCHAR(32),screenSize VARCHAR(32),firmwareVersion VARCHAR(64),phoneBrand VARCHAR(32),"
            + "phoneModelNumber VARCHAR(32),productName VARCHAR(32),productor VARCHAR(32),equipmentName VARCHAR(32),cpu VARCHAR(32),"
            + "hardware VARCHAR(32),fingerPrint VARCHAR(64),portNumber VARCHAR(32),bluetoothAddress VARCHAR(64),internalIp VARCHAR(32)"
            + ")";
    public static final String CREATE_WHITE_UNINSTALL_TABLE = "CREATE TABLE "
            + TABLE_WHITE_UNINSTALL
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,pkgname VARCHAR(100) NOT NULL UNIQUE,appname VARCHAR(32))";
    public static final String CREATE_TCLEAR_DATA_TABLE = "CREATE TABLE "
                + TABLE_CLEAR_DATA
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,pkgname VARCHAR(100) NOT NULL UNIQUE,appname VARCHAR(32))";
    public static final String CREATE_WHITE_APK_TABLE = "CREATE TABLE "
            + TABLE_WHITE_APK
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,name VARCHAR(100) NOT NULL UNIQUE,directory VARCHAR(100))";
    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_LOCAL_DATA_TABLE);

        db.execSQL(CREATE_WHITE_UNINSTALL_TABLE);

        db.execSQL(CREATE_TCLEAR_DATA_TABLE);

        db.execSQL(CREATE_WHITE_APK_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            String DROP_TABLE = "drop table ";
            // 先删除旧表
//            db.execSQL(DROP_TABLE + TABLE_WHITE_UNINSTALL);
//            db.execSQL(DROP_TABLE + TABLE_CLEAR_DATA);
//            db.execSQL(DROP_TABLE + TABLE_WHITE_APK);
//            db.execSQL(DROP_TABLE + TABLE_LOCAL_DATA);


//            onCreate(db); // 建立新的表
        }
    }
}
