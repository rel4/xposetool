package com.meiriq.xposehook.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by tian on 15-12-2.
 */
public class Config implements Serializable{


    private  boolean fileHookSwitch = false;//文件修改记录开关
    private  HashMap<String ,String> fileHookNameAndPackage;//文件修改记录的包名,key:package,value:name


    public boolean isFileHookSwitch() {
        return fileHookSwitch;
    }

    public void setFileHookSwitch(boolean fileHookSwitch) {
        this.fileHookSwitch = fileHookSwitch;
    }

    public HashMap<String, String> getFileHookNameAndPackage() {

        return fileHookNameAndPackage;
    }

    public void setFileHookNameAndPackage(HashMap<String, String> fileHookNameAndPackage) {
        this.fileHookNameAndPackage = fileHookNameAndPackage;
    }
}
