package com.meiriq.xposehook.bean;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-12-28.
 */
public class DataStatus {

    private String Date;
    private int count;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static ArrayList<DataStatus> parseCursor2List(Cursor cursor){

        ArrayList<DataStatus> dataStatuses = new ArrayList<>();
        while (cursor.moveToNext()){
            DataStatus dataStatus = new DataStatus();
            dataStatus.setDate(cursor.getString(cursor.getColumnIndex("savetime")));
            dataStatus.setCount(cursor.getInt(cursor.getColumnIndex("counts")));
            dataStatuses.add(dataStatus);
        }

        return dataStatuses;
    }


    @Override
    public String toString() {
        return "DataStatus{" +
                "Date='" + Date + '\'' +
                ", count=" + count +
                '}';
    }
}
