/**
 * Copyright (C) 2014-2015 The MeiRiQ All Right Reserve.
 */
package com.meiriq.xposehook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * @param <T>
 * @author zhangchaoxian@meiriq.com
 * @version 1.0
 * @description 所有数据库操作的基础类，封装了插入、删除一条/多条记录、修改记录、查询记录的方法。<br>
 * 不用数据库时调用 {@link #close()}关闭数据库，释放资源
 * @time 2015-5-12 上午11:34:55
 */
public abstract class BaseDao<T> {

    private DbHelper mDbHelper;
    protected SQLiteDatabase mDatabase;

    public BaseDao(Context context) {
        mDbHelper = new DbHelper(context);
        mDatabase = mDbHelper.getWritableDatabase();

    }

    /**
     * 创建数据表
     *
     * @param sql 建表的sql语句
     */
    public void createTable(String sql) {
        mDatabase.execSQL(sql);
    }

    /**
     * 添加一个item到数据库
     *
     * @param item 存储的对象
     * @return true: 添加成功；false：添加失败
     */
    public abstract boolean add(T item);

    /**
     * 添加一个集合的item到数据库
     *
     * @param list
     * @return
     */
    public abstract boolean addList(List<T> list);

    /**
     * 删除表中指定的记录
     *
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public abstract int delete(String whereClause, String[] whereArgs);

    /**
     * 清空表中所有的记录
     *
     * @return
     */
    public abstract int clean();

    /**
     * 更新条目
     *
     * @param item
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public abstract int update(T item, String whereClause, String[] whereArgs);

    /**
     * 自定义查询数据
     *
     * @param args 查询参数
     * @return 查询结果
     */
    public abstract Cursor query(String[] args);

    /**
     * insert or update；如果不存在则插入记录，如果有记录则更新
     *
     * @param tableName
     * @param values
     * @return
     */
    protected long replace(String tableName, ContentValues values) {
        return mDatabase.replace(tableName, null, values);
    }

    /**
     * 向指定表中插入1条记录
     *
     * @param tableName 表名
     * @param values    待插入的一条记录
     * @return 返回插入记录当前的位置，发生错误时返回-1
     */
    protected long insert(String tableName, ContentValues values) {
        return mDatabase.replace(tableName, null, values);// 使用替换
        // return mDatabase.insert(tableName, null, values);
    }

    /**
     * 向指定表中插入多条记录，使用事务处理
     *
     * @param tableName 表名
     * @param list      待插入的数据结合
     * @return 最后插入的记录的位置，发生错误时返回-1
     */
    protected long insertList(String tableName, List<ContentValues> list) {
        long result = 0;
        // 开始事务处理
        mDatabase.beginTransaction();
        try {
            for (ContentValues values : list) {
                result = insert(tableName, values);
            }
            mDatabase.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 结束事务
            mDatabase.endTransaction();
        }
        return result;
    }

    /**
     * 删除表中指定符合条件的记录
     *
     * @param tableName   表明
     * @param whereClause 操作条件，使用占位符待定参数，如果为null，将删除这个表中的所有记录
     * @param whereArgs   和占位符参数一一对应
     * @return
     */
    protected int delete(String tableName, String whereClause,
                         String[] whereArgs) {
        return mDatabase.delete(tableName, whereClause, whereArgs);
    }

    /**
     * 清空指定表中的所有记录
     *
     * @param tableName 表明
     * @return 删除的记录条目
     */
    protected int clean(String tableName) {
        return delete(tableName, null, null);
    }

    /**
     * 更新指定表中的记录
     *
     * @param tableName   表明
     * @param values      替换就数据的新数据
     * @param whereClause 操作条件
     * @param whereArgs
     * @return
     */
    protected int update(String tableName, ContentValues values,
                         String whereClause, String[] whereArgs) {
        return mDatabase.update(tableName, values, whereClause, whereArgs);
    }

    /**
     * 执行sql语句，表或表中某字段的值
     *
     * @param sql
     */
    protected void update(String sql) {
        try {
            mDatabase.execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提供sql语句查询方式查询数据库
     *
     * @param sql           sql查询语句，可以带占位符查询
     * @param selectionArgs 查询条件，用于对应查询语句中的占位符
     * @return
     */
    protected Cursor query(String sql, String[] selectionArgs) {
        return mDatabase.rawQuery(sql, selectionArgs);
    }

    /**
     * 数据库查询方式
     *
     * @param distinct
     * @param table
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return
     */
    protected Cursor query(boolean distinct, String table, String[] columns,
                           String selection, String[] selectionArgs, String groupBy,
                           String having, String orderBy, String limit) {
        return mDatabase.query(distinct, table, columns, selection,
                selectionArgs, groupBy, having, orderBy, limit);
    }

    /**
     * 查询指定表中数据，很据order排名，限制查询条目数量
     *
     * @param tableName 表名
     * @param orderBy   排序字段
     * @param limit     查询限制字段
     * @return
     */
    protected Cursor query(String tableName, String orderBy, String limit) {
        return query(tableName, null, null, orderBy, limit);
    }

    /**
     * 查询指定表中的记录，指定排序
     *
     * @param tableName     表名
     * @param selection     查询条件
     * @param selectionArgs 查询参数
     * @param orderBy       排序的字段
     * @param limit         查询限制字段
     * @return
     */
    protected Cursor query(String tableName, String selection,
                           String[] selectionArgs, String orderBy, String limit) {
        return mDatabase.query(true, tableName, null, selection, selectionArgs,
                null, null, orderBy, limit);
    }

    /**
     * Execute a single SQL statement that is NOT a SELECT or any other SQL
     * statement that returns data.
     * <p>
     * It has no means to return any data (such as the number of affected rows).
     * Instead, you're encouraged to use insert(String, String, ContentValues),
     * update(String, ContentValues, String, String[]), et al, when possible.
     * <p>
     * When using enableWriteAheadLogging(), journal_mode is automatically
     * managed by this class. So, do not set journal_mode using
     * "PRAGMA journal_mode'" statement if your app is using
     * enableWriteAheadLogging()
     *
     * @param sql the SQL statement to be
     *            executed. Multiple statements separated by semicolons are not supported.
     */
    protected void execSQL(String sql) {
        mDatabase.execSQL(sql);
    }

    /**
     * 获取数据表中记录总数
     *
     * @param tableName 表名
     * @return
     */
    public int getCount(String tableName) {
        Cursor cursor = query("select count(*) from " + tableName, null);
        cursor.moveToFirst();
        int count = (int) cursor.getLong(0);
        cursor.close();
        return count;
    }

    /**
     * 开始事务
     */
    public void beginTransaction() {
        mDatabase.beginTransaction();
    }

    /**
     * 设置事务成功
     */
    public void setTransactionSuccessful() {
        mDatabase.setTransactionSuccessful();
    }

    /**
     * 结束事务
     */
    public void endTransaction() {
        mDatabase.endTransaction();
    }

    /**
     * 关闭数据库连接，释放资源
     */
    public void close() {
        mDbHelper.close();
    }
}
