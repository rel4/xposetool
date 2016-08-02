package com.meiriq.xposehook.bean;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置工具类
 * Created by tian on 15-12-2.
 */
public class ConfigHelper {

    public static String FILE_PATH = "/.xpose/file";

    private static ConfigHelper configHelper;
    private static final String DATAINFO = "datainfo";
    private static final String CHANNEL = "channel";


    private ConfigHelper(){};

    public ConfigHelper getInstance(){
        if(configHelper == null){
            configHelper = new ConfigHelper();
        }

        return configHelper;
    }


    private static File getFilePath(String filePath){
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),FILE_PATH);
        if(!file.exists())
            file.mkdirs();
        file = new File(file,File.separator+filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void saveDataInfo(Context context,DataInfo dataInfo){
        File file = getFilePath(DATAINFO);
        if(file.exists()){
            file.delete();
        }
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(dataInfo);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static DataInfo loadDataInfo(Context context){

        DataInfo dataInfo = null;
        File file = getFilePath(DATAINFO);
        if(file.exists()){
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                dataInfo = (DataInfo) inputStream.readObject();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        if(dataInfo == null)
            dataInfo = new DataInfo();

        return dataInfo;
    }

    public static void saveChannel(Context context,List<Channel>channels){
        File file = getFilePath(CHANNEL);
        if(file.exists()){
            file.delete();
        }
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(channels);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Channel> loadChannel(Context context){
        List<Channel>channels = null;
        File file = getFilePath(CHANNEL);
        if(file.exists()){
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                channels = (List<Channel>) inputStream.readObject();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return channels;
    }

}
