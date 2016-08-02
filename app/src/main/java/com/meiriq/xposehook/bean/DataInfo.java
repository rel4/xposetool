package com.meiriq.xposehook.bean;

import java.io.Serializable;

/**
 * Created by tian on 15-12-8.
 */
public class DataInfo implements Serializable{

    private String id;

    private String deviceId;
    private String androidId;
    private String phoneNum;
    private String simId;
    private String imsi;

    private String operator;
    private String netTypeName;
    private String netType;
    private String phoneType;
    private String simStatus;

    private String macAddress;
    private String routeName;
    private String routeAddress;
    private String systemVersion;
    private String systemVersionValue;

    private String systemFramework;
    private String screenSize;
    private String firmwareVersion;
    private String phoneBrand;
    private String phoneModelNumber;

    private String productName;
    private String productor;
    private String equipmentName;
    private String cpu;

    private String hardware;
    private String fingerPrint;
    private String portNumber;
    private String bluetoothAddress;
    private String internalIp;

    private String extraInfo;
    private String saveTime;//保存时间，精确到天
    private String useTime;//使用时间，精确到天，当天时候时，更新到那一天，用来判断那一天的有没有使用这个数据
    private long detailTime;//详细时间，精确到秒

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public long getDetailTime() {
        return detailTime;
    }

    public void setDetailTime(long detailTime) {
        this.detailTime = detailTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSimId() {
        return simId;
    }

    public void setSimId(String simId) {
        this.simId = simId;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getNetTypeName() {
        return netTypeName;
    }

    public void setNetTypeName(String netTypeName) {
        this.netTypeName = netTypeName;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getSimStatus() {
        return simStatus;
    }

    public void setSimStatus(String simStatus) {
        this.simStatus = simStatus;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteAddress() {
        return routeAddress;
    }

    public void setRouteAddress(String routeAddress) {
        this.routeAddress = routeAddress;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getSystemVersionValue() {
        return systemVersionValue;
    }

    public void setSystemVersionValue(String systemVersionValue) {
        this.systemVersionValue = systemVersionValue;
    }

    public String getSystemFramework() {
        return systemFramework;
    }

    public void setSystemFramework(String systemFramework) {
        this.systemFramework = systemFramework;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getPhoneModelNumber() {
        return phoneModelNumber;
    }

    public void setPhoneModelNumber(String phoneModelNumber) {
        this.phoneModelNumber = phoneModelNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductor() {
        return productor;
    }

    public void setProductor(String productor) {
        this.productor = productor;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getBluetoothAddress() {
        return bluetoothAddress;
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        this.bluetoothAddress = bluetoothAddress;
    }

    public String getInternalIp() {
        return internalIp;
    }

    public void setInternalIp(String internalIp) {
        this.internalIp = internalIp;
    }


    @Override
    public String toString() {
        return "DataInfo{" +
                "id='" + id + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", androidId='" + androidId + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                ", simId='" + simId + '\'' +
                ", imsi='" + imsi + '\'' +
                ", operator='" + operator + '\'' +
                ", netTypeName='" + netTypeName + '\'' +
                ", netType='" + netType + '\'' +
                ", phoneType='" + phoneType + '\'' +
                ", simStatus='" + simStatus + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", routeName='" + routeName + '\'' +
                ", routeAddress='" + routeAddress + '\'' +
                ", systemVersion='" + systemVersion + '\'' +
                ", systemVersionValue='" + systemVersionValue + '\'' +
                ", systemFramework='" + systemFramework + '\'' +
                ", screenSize='" + screenSize + '\'' +
                ", firmwareVersion='" + firmwareVersion + '\'' +
                ", phoneBrand='" + phoneBrand + '\'' +
                ", phoneModelNumber='" + phoneModelNumber + '\'' +
                ", productName='" + productName + '\'' +
                ", productor='" + productor + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", cpu='" + cpu + '\'' +
                ", hardware='" + hardware + '\'' +
                ", fingerPrint='" + fingerPrint + '\'' +
                ", portNumber='" + portNumber + '\'' +
                ", bluetoothAddress='" + bluetoothAddress + '\'' +
                ", internalIp='" + internalIp + '\'' +
                ", saveTime='" + saveTime + '\'' +
                ", useTime='" + useTime + '\'' +
                ", detailTime=" + detailTime +
                '}';
    }
}
