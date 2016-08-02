package com.meiriq.xposehook.utils;

import android.os.Build;

import com.meiriq.xposehook.bean.DataInfo;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * 随机数据生成类
 * Created by tian on 16-1-9.
 */
public class RandomUtil {

    private static final String[] AP1 = {"cmnet","ctnet","uninet","3gnet","3gwap","cmwap"};
    private static final String[] AP2 = {"360WIFI-","miwifi-","",};
    public static String getWIFIName (){
        if((Math.random()*2)<1){
            String prefix = AP2[(int) (Math.random() * 3)];
            StringBuilder sb = new StringBuilder(prefix);
            int length = (int) (Math.random()*6+3);
            for (int i = 0; i < length; i++) {
                sb.append(LETTERSANDNUMBER[(int)(Math.random()*LETTERSANDNUMBER.length)]);
            }
            return sb.toString();
        }else {
            return AP1[(int)(Math.random()*AP1.length)];
        }

    }

    public static final String[] LETTERS = {"a","b","c","d","e","f","g","h","i","j",
            "k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

    public static final String[] NUMBER = {"1","2","3","4","5","6","7","8","9","0"};

    public static final String[] LETTERSANDNUMBER = {"1","2","3","4"
            ,"5","6","7","8","9","0","a","b","c","d","e","f","g","h","i","j",
            "k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","1","2","3","4"
            ,"5","6","7","8","9","0"};

    public static final String[] Hexadecimal = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};

    public static final String[] MODEL = {"三星Galaxy SIII","魅族MX3","三星Galaxy Note II","HUAWEI MT7-TL00"
        ,"红米手机1s","Coolpad 8670","OPPO 3007","华为T8830pro","HUAWEI G750-T20","HUAWEI Y535D-C00","HUAWEI G750-T01"
        ,"Nexus 5","华为荣耀3C","DOOV S2","OPPO R811","华为 S8-701u","金立S5.1","ZTE A880","vivo Y23L","K-Touch T91",
        "酷派8730","Lenovo A880","MI 2S","努比亚NX505J","三星W999","OPPO R823T","魅族MX2","HTC M8t","金立V185","OPPO R1s"
            ,"Redmi Note 2","Lenovo S890","SAMSUNG GT-S5830","Lenovo A770e","HUAWEI G6-C00","Lenovo A360t"
            ,"vivo S7t","金立GN700W","Lenovo A820","OPPO Finder(X907)","HTC 802d","华为荣耀Che1-CL10","Coolpad 9190L",
            "Lenovo A828t","索尼LT22i(Xperia P)","ZTE Q701C","OPPO X9007","HUAWEI T8950","Coolpad 8297D","Nexus 7","TCL P728M"
            ,"红米1S","三星Galaxy Note III","HUAWEI G610-C00","HTC D820u","金立GN708W","HUAWEI P7-L09","三星I8552（Galaxy Win）"
            ,"HUAWEI P7-L07","魅族MX M031","努比亚NX403A","vivo Y22L","Coolpad 8297","vivo S9","OPPO R831t","三星Galaxy Ace"
            ,"Lenovo A830","HUAWEI G700-U00","vivo Xplay3S","中兴红牛（青春版）","vivo E3","海信EG966","MI 3","HM 1S"
            ,"索尼 LT26ii(Xperia SL)","HUAWEI HN3-U01","Coolpad 8297-T01","HUAWEI C8817L","Lenovo A890e","OPPO R831S","三星T800"
            ,"OPPO X909","金立GN708T","OPPO R821t","HTC D820t","Lenovo K50-T5","HUAWEI C8815","华为 H60-L01","K-Touch T789"
            ,"HM 2A","Nexus 6P","nubia NX501","Coolpad 8675"};


    public static DataInfo getRandom(){

        DataInfo info = new DataInfo();
        info.setId(System.currentTimeMillis() + "");
        info.setDeviceId(getImei());
        info.setAndroidId(getAndroidId());
        info.setPhoneNum(getPhoneBumber());
        info.setSimId(getSimSerialNumber());
        info.setImsi(getISim());
        String operator = getOperator();
        info.setOperator(operator);
        info.setNetTypeName(getNetTypeName(operator));

        String netType = getNetType();
        info.setNetType(netType);
        info.setPhoneType(getPhoneType());
        info.setSimStatus(getSimStatus());
        info.setMacAddress(getMacAddress());
        info.setRouteName(getSSID());
        info.setRouteAddress(getBSSID());
        String sysVersionValue = getSysVersionValue();
        info.setSystemVersionValue(sysVersionValue);
        info.setSystemVersion(getSysVersion(Integer.parseInt(sysVersionValue)));
        String model = getModel();
        info.setProductName(model);
        info.setPhoneModelNumber(model);
        info.setPhoneBrand(getBrand(model));
        info.setProductor(getManufacturer(model));
        info.setFirmwareVersion(getRadioVersion());
        info.setSystemFramework("armeabi-v7a_arme");

        info.setPortNumber(getSerialNumber());
        info.setBluetoothAddress(getBluetooth());


        return info;
    }

    //固件版本
    private static String getRadioVersion(){
        int length = (int) (Math.random() * 10 + 8);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(LETTERS[(int)(Math.random() * LETTERS.length)]);
        }
        return builder.toString();
    }

    private static String getBrand(String model){
        if(model.contains("vivo")){
            return "vivo";
        }else if(model.contains("Lenovo")){
            return "Lenovo";
        }else if(model.contains("三星")){
            return "samsung";
        }else if(model.contains("HUAWEI")){
            return "Huawei";
        }else if(model.contains("Nexus")){
            return "google";
        }else if(model.contains("ZTE")){
            return "ZTE";
        }else if(model.contains("金立")){
            return "";
        }else if(model.contains("乐视")){
            return "Letv";
        }else if(model.contains("OPPO")){
            return "OPPO";
        }else if(model.contains("酷派")||model.contains("Coolpad")){
            return "Coolpad";
        }else if(model.contains("米") || model.contains("MI")){
            return "Xiaomi";
        }else {
            return "HTC";
        }
    }

    private static String getManufacturer(String model){
        if(model.contains("vivo")){
            return "VIVO";
        }else if(model.contains("Lenovo")){
            return "LENOVO";
        }else if(model.contains("三星")){
            return "samsung";
        }else if(model.contains("HUAWEI")){
            return "HUAWEI";
        }else if(model.contains("Nexus")){
            return "google";
        }else if(model.contains("ZTE")){
            return "ZTE";
        }else if(model.contains("金立")){
            return "";
        }else if(model.contains("乐视")){
            return "Letv";
        }else if(model.contains("OPPO")){
            return "OPPO";
        }else if(model.contains("酷派")||model.contains("Coolpad")){
            return "Coolpad";
        }else if(model.contains("米") || model.contains("MI")){
            return "Xiaomi";
        }else {
            return "HTC";
        }
    }

    private static String getModel(){
        int num = (int) (Math.random() * MODEL.length);
        return MODEL[num];
    }

    private static String getBluetooth(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                int num = (int) (Math.random() * 16);
                stringBuilder.append(Hexadecimal[num]);
            }
            if(i != 5)
                stringBuilder.append(":");
        }
        return stringBuilder.toString();
    }
    private static String getSerialNumber(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int num = (int) (Math.random() * 16);
            stringBuilder.append(Hexadecimal[num]);
        }
        return stringBuilder.toString();
    }

    private static String getSysVersion(int value){
        return getVersion(value);
    }

    private static String getSysVersionValue(){
        int value = (int) (Math.random() * 11 + 11);
        return value+"";
    }

    //路由器地址
    private static String getBSSID(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                int num = (int) (Math.random() * 16);
                stringBuilder.append(Hexadecimal[num]);
            }
            if(i != 5)
                stringBuilder.append(":");
        }
        return stringBuilder.toString();
    }

    //路由器名
    private static String getSSID(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int num = (int) (Math.random() * 16);
            stringBuilder.append(Hexadecimal[num]);
        }
        return stringBuilder.toString();
    }

    private static String getMacAddress(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                int num = (int) (Math.random() * 16);
                stringBuilder.append(Hexadecimal[num]);
            }
            if(i != 5)
                stringBuilder.append(":");
        }
        return stringBuilder.toString();
    }

    private static String getSimStatus(){
        return "5";
    }

    private static String getPhoneType(){
        return "1";
    }

    private static String getNetType(){
        int num = (int) (Math.random() * 16 + 1);
        return num +"";
    }

    private static String getNetTypeName(String operator){
        if("46000".equals(operator) || "46002".equals(operator) || "46007".equals(operator))
            return "中国移动";
        else if("46001".equals(operator))
            return "中国联通";
        else
            return "中国电信";
    }

    private static final String[] OPERATOR = {"46000","46001","46002","46003","46007"};
    private static String getOperator(){
        int num = (int) (Math.random() * OPERATOR.length);
        return OPERATOR[num];
    }

    private static String getISim(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(4600);
        for (int i = 0; i < 11; i++) {
            if(i == 0){
                int num = (int) (Math.random() * 4);
                stringBuilder.append(num);
            }else {
                int num = (int) (Math.random() * 10);
                stringBuilder.append(num);
            }
        }
        return stringBuilder.toString();
    }

    private static String getSimSerialNumber(){
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(89860);
        for (int i = 0; i < 15; i++) {
            int num = (int) (Math.random() * 10);
            if(i == 0){
                if(num < 6)
                    stringBuilder.append(0);
                else
                    stringBuilder.append(1);
            }else {
                stringBuilder.append(num);
            }
        }
        return stringBuilder.toString();
    }

    private static String getPhoneBumber(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(1);

        for (int i = 0; i < 10; i++) {
            int num = (int) (Math.random() * 10);

            if(i == 0){
                if(num < 4)
                    stringBuilder.append(3);
                else if(num < 7)
                    stringBuilder.append(5);
                else
                    stringBuilder.append(8);
            }else {
                stringBuilder.append(num);
            }

        }
        return stringBuilder.toString();
    }

    private static String getAndroidId(){
            StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            stringBuilder.append(LETTERSANDNUMBER[(int)(Math.random()*LETTERSANDNUMBER.length)]);
        }
        return stringBuilder.toString();
    }

    private static String getImei(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            stringBuilder.append((int)(Math.random() * 10));
        }

        return stringBuilder.toString();
    };

    private static String getVersion(int value){
        String result = "";
        switch (value){
            case Build.VERSION_CODES.GINGERBREAD_MR1:
                result = "2.3.3";
                break;
            case Build.VERSION_CODES.HONEYCOMB:
                result = "3.0";
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR1:
                result = "3.1";
                break;
            case Build.VERSION_CODES.HONEYCOMB_MR2:
                result = "3.2";
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH:
                result = "4.0";
                break;
            case Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:
                result = "4.0.3";
                break;
            case Build.VERSION_CODES.JELLY_BEAN:
                result = "4.1";
                break;
            case Build.VERSION_CODES.JELLY_BEAN_MR1:
                result = "4.2";
                break;
            case Build.VERSION_CODES.JELLY_BEAN_MR2:
                result = "4.3";
                break;
            case Build.VERSION_CODES.KITKAT:
                result = "4.4";
                break;
            case Build.VERSION_CODES.KITKAT_WATCH:
                result = "4.4W";
                break;
            case Build.VERSION_CODES.LOLLIPOP:
                result = "5.0";
                break;
            default:
                result = "4.2.2";
        }
        return result;
    }

}
