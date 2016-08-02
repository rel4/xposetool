package com.meiriq.xposehook.bean.util;

import android.content.ContentValues;
import android.database.Cursor;

import com.meiriq.xposehook.bean.AppInfo;
import com.meiriq.xposehook.bean.DataInfo;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tian on 15-12-10.
 */
public class SetDataUtil {

    public static ArrayList<DataInfo> parseCursor2List(Cursor cursor){
        ArrayList<DataInfo> dataInfos = new ArrayList<>();

        while (cursor.moveToNext()) { // 循环解析数据为RankUser
            dataInfos.add(parse2DataInfo(cursor));
        }
        return dataInfos;
    }

    private static DataInfo parse2DataInfo(Cursor cursor) {
        DataInfo dataInfo = new DataInfo();

        dataInfo.setSaveTime(cursor.getString(cursor.getColumnIndex("savetime")));
        dataInfo.setUseTime(cursor.getString(cursor.getColumnIndex("usetime")));
        dataInfo.setDetailTime(cursor.getLong(cursor.getColumnIndex("detailtime")));

        dataInfo.setId(cursor.getString(cursor.getColumnIndex("id")));
        dataInfo.setDeviceId(cursor.getString(cursor.getColumnIndex("imei")));
        dataInfo.setAndroidId(cursor.getString(cursor.getColumnIndex("android_id")));
        dataInfo.setPhoneNum(cursor.getString(cursor.getColumnIndex("phoneNum")));
        dataInfo.setSimId(cursor.getString(cursor.getColumnIndex("simId")));

        dataInfo.setImsi(cursor.getString(cursor.getColumnIndex("isim")));
        dataInfo.setOperator(cursor.getString(cursor.getColumnIndex("operator")));
        dataInfo.setNetTypeName(cursor.getString(cursor.getColumnIndex("netTypeName")));
        dataInfo.setNetType(cursor.getString(cursor.getColumnIndex("netType")));
        dataInfo.setPhoneType(cursor.getString(cursor.getColumnIndex("phoneType")));

        dataInfo.setSimStatus(cursor.getString(cursor.getColumnIndex("simStatus")));
        dataInfo.setMacAddress(cursor.getString(cursor.getColumnIndex("macAddress")));
        dataInfo.setRouteName(cursor.getString(cursor.getColumnIndex("routeName")));
        dataInfo.setRouteAddress(cursor.getString(cursor.getColumnIndex("routeAddress")));
        dataInfo.setSystemVersion(cursor.getString(cursor.getColumnIndex("systemVersion")));

        dataInfo.setSystemVersionValue(cursor.getString(cursor.getColumnIndex("systemVersionValue")));
        dataInfo.setSystemFramework(cursor.getString(cursor.getColumnIndex("systemFramework")));
        dataInfo.setScreenSize(cursor.getString(cursor.getColumnIndex("screenSize")));
        dataInfo.setFirmwareVersion(cursor.getString(cursor.getColumnIndex("firmwareVersion")));
        dataInfo.setPhoneBrand(cursor.getString(cursor.getColumnIndex("phoneBrand")));

        dataInfo.setPhoneModelNumber(cursor.getString(cursor.getColumnIndex("phoneModelNumber")));
        dataInfo.setProductName(cursor.getString(cursor.getColumnIndex("productName")));
        dataInfo.setProductor(cursor.getString(cursor.getColumnIndex("productor")));
        dataInfo.setEquipmentName(cursor.getString(cursor.getColumnIndex("equipmentName")));
        dataInfo.setCpu(cursor.getString(cursor.getColumnIndex("cpu")));

        dataInfo.setHardware(cursor.getString(cursor.getColumnIndex("hardware")));
        dataInfo.setFingerPrint(cursor.getString(cursor.getColumnIndex("fingerPrint")));
        dataInfo.setPortNumber(cursor.getString(cursor.getColumnIndex("portNumber")));
        dataInfo.setBluetoothAddress(cursor.getString(cursor.getColumnIndex("bluetoothAddress")));
        dataInfo.setInternalIp(cursor.getString(cursor.getColumnIndex("internalIp")));
        return dataInfo;
    }

    public static ContentValues wrapLocalData2Values(DataInfo dataInfo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("savetime",dataInfo.getSaveTime());
        contentValues.put("usetime",dataInfo.getUseTime());
        contentValues.put("detailtime",dataInfo.getDetailTime());

        contentValues.put("id",dataInfo.getId());
        contentValues.put("imei",dataInfo.getDeviceId());
        contentValues.put("android_id",dataInfo.getAndroidId());
        contentValues.put("phoneNum",dataInfo.getPhoneNum());
        contentValues.put("simId",dataInfo.getSimId());

        contentValues.put("isim",dataInfo.getImsi());
        contentValues.put("operator",dataInfo.getOperator());
        contentValues.put("netTypeName",dataInfo.getNetTypeName());
        contentValues.put("netType",dataInfo.getNetType());
        contentValues.put("phoneType",dataInfo.getPhoneType());

        contentValues.put("simStatus",dataInfo.getSimStatus());
        contentValues.put("macAddress",dataInfo.getMacAddress());
        contentValues.put("routeName",dataInfo.getRouteName());
        contentValues.put("routeAddress",dataInfo.getRouteAddress());
        contentValues.put("systemVersion",dataInfo.getSystemVersion());

        contentValues.put("systemVersionValue",dataInfo.getSystemVersionValue());
        contentValues.put("systemFramework",dataInfo.getSystemFramework());
        contentValues.put("screenSize",dataInfo.getScreenSize());
        contentValues.put("firmwareVersion",dataInfo.getFirmwareVersion());
        contentValues.put("phoneBrand",dataInfo.getPhoneBrand());

        contentValues.put("phoneModelNumber",dataInfo.getPhoneModelNumber());
        contentValues.put("productName",dataInfo.getProductName());
        contentValues.put("productor",dataInfo.getProductor());
        contentValues.put("equipmentName",dataInfo.getEquipmentName());
        contentValues.put("cpu",dataInfo.getCpu());

        contentValues.put("hardware",dataInfo.getHardware());
        contentValues.put("fingerPrint",dataInfo.getFingerPrint());
        contentValues.put("portNumber",dataInfo.getPortNumber());
        contentValues.put("bluetoothAddress",dataInfo.getBluetoothAddress());
        contentValues.put("internalIp",dataInfo.getInternalIp());


        return contentValues;
    }


    public static DataInfo parseJsonObject2DataInfo(JSONObject jsonObject){
        DataInfo dataInfo = new DataInfo();
        dataInfo.setId(jsonObject.optString("id"));
        dataInfo.setDeviceId(jsonObject.optString("imei"));
        dataInfo.setAndroidId(jsonObject.optString("android_id"));
        dataInfo.setPhoneNum(jsonObject.optString("phone_number"));
        dataInfo.setSimId(jsonObject.optString("sim_card_id"));
        dataInfo.setImsi(jsonObject.optString("imsi"));
        dataInfo.setOperator(jsonObject.optString("operator"));
        dataInfo.setNetTypeName(jsonObject.optString("networks_type_name"));
        dataInfo.setNetType(jsonObject.optString("networks_type_id"));
        dataInfo.setPhoneType(jsonObject.optString("type"));
        dataInfo.setSimStatus(jsonObject.optString("status"));
        dataInfo.setMacAddress(jsonObject.optString("mac"));
        dataInfo.setRouteName(jsonObject.optString("wireless_router_name"));
        dataInfo.setRouteAddress(jsonObject.optString("wireless_router_address"));
        dataInfo.setSystemVersion(jsonObject.optString("android_version"));
        dataInfo.setSystemVersionValue(jsonObject.optString("system_value"));
        dataInfo.setSystemFramework(jsonObject.optString("system_architecture"));
        dataInfo.setScreenSize(jsonObject.optString("resolution"));
        dataInfo.setFirmwareVersion(jsonObject.optString("hardware_version"));
        dataInfo.setPhoneBrand(jsonObject.optString("brand"));
        dataInfo.setPhoneModelNumber(jsonObject.optString("model_number"));
        dataInfo.setProductName(jsonObject.optString("product_name"));
        dataInfo.setProductor(jsonObject.optString("manufacturer"));
        dataInfo.setEquipmentName(jsonObject.optString("device_number"));
        dataInfo.setCpu(jsonObject.optString("cpu"));
        dataInfo.setHardware(jsonObject.optString("hardware"));
        dataInfo.setFingerPrint(jsonObject.optString("fingerprint_key"));
        dataInfo.setPortNumber(jsonObject.optString("serial_interface_number"));
        dataInfo.setBluetoothAddress(jsonObject.optString("bluetooth_address"));
        dataInfo.setInternalIp(jsonObject.optString("local_area_networks_ip"));



        return dataInfo;
    }

}
