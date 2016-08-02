package com.meiriq.xposehook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tian on 15-11-27.
 */
public class AppInfo implements Parcelable {
    private boolean isSelect ;
    private String appname = "";
    private String pname = "";
    private int versionCode ;
    private String versionName = "";

    public void setAppname(String appname) {
        this.appname = appname;
    }


    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getAppname() {
        return appname;
    }


    public String getPname() {
        return pname;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "isSelect=" + isSelect +
                ", appname='" + appname + '\'' +
                ", pname='" + pname + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(isSelect ? (byte) 1 : (byte) 0);
        dest.writeString(this.appname);
        dest.writeString(this.pname);
        dest.writeInt(this.versionCode);
        dest.writeString(this.versionName);
    }

    public AppInfo() {
    }

    protected AppInfo(Parcel in) {
        this.isSelect = in.readByte() != 0;
        this.appname = in.readString();
        this.pname = in.readString();
        this.versionCode = in.readInt();
        this.versionName = in.readString();
    }

    public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() {
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
}
