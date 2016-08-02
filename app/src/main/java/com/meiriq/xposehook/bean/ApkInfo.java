package com.meiriq.xposehook.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tian on 15-11-27.
 */
public class ApkInfo implements Parcelable {
    private String name;
    private String directory;
    private boolean isSelect ;

    public boolean isSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

//    @Override
//    public String toString() {
//        return "ApkInfo{" +
//                "name='" + name + '\'' +
//                ", directory='" + directory + '\'' +
//                ", isSelect=" + isSelect +
//                '}';
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.directory);
        dest.writeByte(isSelect ? (byte) 1 : (byte) 0);
    }

    public ApkInfo() {
    }

    protected ApkInfo(Parcel in) {
        this.name = in.readString();
        this.directory = in.readString();
        this.isSelect = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ApkInfo> CREATOR = new Parcelable.Creator<ApkInfo>() {
        public ApkInfo createFromParcel(Parcel source) {
            return new ApkInfo(source);
        }

        public ApkInfo[] newArray(int size) {
            return new ApkInfo[size];
        }
    };
}
