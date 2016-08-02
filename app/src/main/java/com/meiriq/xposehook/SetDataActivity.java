package com.meiriq.xposehook;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.meiriq.xposehook.adapter.DataSpinner1Adapter;
import com.meiriq.xposehook.adapter.DataSpinnerAdapter;
import com.meiriq.xposehook.bean.Channel;
import com.meiriq.xposehook.bean.ConfigHelper;
import com.meiriq.xposehook.bean.DataInfo;
import com.meiriq.xposehook.bean.DataKeepStatus;
import com.meiriq.xposehook.bean.SetDay;
import com.meiriq.xposehook.dao.LocalDataDao;
import com.meiriq.xposehook.fragment.KeepDataSettingFragment;
import com.meiriq.xposehook.net.Callback;
import com.meiriq.xposehook.net.ErrorObject;
import com.meiriq.xposehook.net.control.ChannelService;
import com.meiriq.xposehook.net.control.DataService;
import com.meiriq.xposehook.utils.DateUtil;
import com.meiriq.xposehook.utils.DialogUtil;
import com.meiriq.xposehook.utils.RandomUtil;
import com.meiriq.xposehook.utils.XposeUtil;

import org.json.JSONException;

import java.util.List;

public class SetDataActivity extends TimePickActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, AdapterView.OnItemSelectedListener,KeepDataSettingFragment.OnDataCallBack {
    public static final String[] DAY = {"0:0","留存(今天):1","留存(昨天):2","留存:3","留存:4","留存:5","留存:6","留存:7","留存:8","留存:9","留存:10"
            ,"留存:11","留存:12","留存:13","留存:14","留存:15","留存:16","留存:17","留存:18","留存:19","留存:20"
            ,"留存:21","留存:22","留存:23","留存:24","留存:25","留存:26","留存:27","留存:28","留存:29","留存:30","留存:31"};
    private static final String[] HOURS = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
    private static final String[] MINUTE_AND_SECOND = {"00","10","20","30","40","50"};

    public static final int LOCAL_DATA = 0x1 << 2;

    private static List<DataKeepStatus> mDataKeepStatuses;//留存时间管理数据

    Spinner spinnerDay;

    List<Channel> channelList;
    int mPositionChannel = 0;
    int mPositionDay = 0;
    int mPositionHour = 0;
    int mPositionMinute = 0;
    int mPositionHourTo = 0;
    int mPositionMinuteTo = 0;
    DataSpinnerAdapter adapter;
    Spinner spinner;
    private EditText mExtrainfo;
    private EditText mDeviceId;
    private EditText mAndroidId;
    private EditText mPhoneNum;
    private EditText mSimId;
    private EditText mIMSI;
    private EditText mOperator;
    EditText mNetTypeName;
    EditText mNetType;
    EditText mPhoneType;
    EditText mSimStatus;
    EditText mMacAddress;
    EditText mRouteName;
    EditText mRouteAddress;
    EditText mSystemVersion;
    EditText mSystemVersionValue;
    EditText mSystemFramework;
    EditText mScreenSize;
    EditText mFirmwareVersion;
    EditText mPhoneBrand;
    EditText mPhoneModelNumber;
    EditText mProductName;
    EditText mProductor;
    EditText mEquipmentName;
    EditText mCpu;
    EditText mHardware;
    EditText mFingerPrint;
    EditText mPortNumber;
    EditText mBluetoothAddress;
    EditText mInternalIp;
    DataService dataService;
    SwipeRefreshLayout mSwipeRefreshLayout;
    LocalDataDao localDataDao;
    ChannelService channelService;
    private DataInfo dataInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_data);
        initActionBar();

        setActionBar();

        initView();

        XposeUtil.initConfigMap();

        getChannelName();

        initData();


    }

    private void initData() {
        mDataKeepStatuses = DataKeepStatus.loadDataKeepStatus(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPositionDay = mLoadSetDay.getDay();
        spinnerDay.setSelection(mPositionDay, true);

    }


    private void getChannelName() {
        mPositionChannel = mLoadSetDay.getChannel();
        channelList = ConfigHelper.loadChannel(this);
        channelService = new ChannelService(this);
        channelService.setCallback(new Callback() {
            @Override
            public void onStart() {
                showLoadingProgressDialog();
            }

            @Override
            public void onSuccess(Object object) {
                dismissProgressDialog();
                mSwipeRefreshLayout.setRefreshing(false);
                channelList = (List<Channel>) object;
                Log.d("unlock", "刷新数据" + channelList.toString());
                ConfigHelper.saveChannel(SetDataActivity.this, channelList);
                setChannelName();
            }

            @Override
            public void onError(ErrorObject error) {
                mSwipeRefreshLayout.setRefreshing(false);
                dismissProgressDialog();
                channelList = Channel.getDefaultChannels();
                Log.d("unlock", "刷新数据" + error.toString());

                setChannelName();
                Toast.makeText(SetDataActivity.this, "获取渠道名失败", Toast.LENGTH_SHORT).show();
            }
        });
        if(channelList == null){
            channelService.getChannelName();
        }else{
            setChannelName();
        }
    }

    private void setChannelName() {
        adapter.setData(channelList);
        adapter.notifyDataSetChanged();

        spinner.setSelection(mPositionChannel);
        spinner.setOnItemSelectedListener(this);
    }


    private void setActionBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.actionbar_custom_spinner);
        spinner = (Spinner) actionBar.getCustomView().findViewById(R.id.spinner);
        adapter = new DataSpinnerAdapter(this);
        spinner.setAdapter(adapter);


    }


    private SetDay mLoadSetDay;
    private AppCompatCheckBox mModifyResolution;
    private AppCompatButton mKeepSetting;
    private void initView() {



        mModifyResolution = (AppCompatCheckBox) findViewById(R.id.modify_resolution);
        mKeepSetting = (AppCompatButton) findViewById(R.id.accb_keep_setting);
        mKeepSetting.setOnClickListener(this);

        mLoadSetDay = SetDay.loadSetDay(this);
        mModifyResolution.setChecked(mLoadSetDay.isResolution());

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        AppCompatButton mBt_LocalData = (AppCompatButton) findViewById(R.id.accb_local);
        mBt_LocalData.setOnClickListener(this);
        AppCompatButton mLocalRandom = (AppCompatButton) findViewById(R.id.accb_random);
        mLocalRandom.setOnClickListener(this);

        localDataDao = new LocalDataDao(this);

        dataService = new DataService(this);

        initSpinnerView();


        TextInputLayout tilExtrainfo = (TextInputLayout) findViewById(R.id.til_extrainfo);
        tilExtrainfo.setHint("接入点名称");
        mExtrainfo = tilExtrainfo.getEditText();

        TextInputLayout tilDeviceId = (TextInputLayout) findViewById(R.id.til_device_id);
        tilDeviceId.setHint("IMEI");
        mDeviceId = tilDeviceId.getEditText();

        TextInputLayout tilAndroidId = (TextInputLayout) findViewById(R.id.til_android_id);
        tilAndroidId.setHint("android_id");
        mAndroidId = tilAndroidId.getEditText();

        TextInputLayout tilPhoneNum = (TextInputLayout) findViewById(R.id.til_phone_num);
        tilPhoneNum.setHint("手机号码");
        mPhoneNum = tilPhoneNum.getEditText();

        TextInputLayout tilSimid = (TextInputLayout) findViewById(R.id.til_sim_id);
        tilSimid.setHint("手机卡序列号");
        mSimId = tilSimid.getEditText();

        TextInputLayout tilISIM = (TextInputLayout) findViewById(R.id.til_isim);
        tilISIM.setHint("ISIM");
        mIMSI = tilISIM.getEditText();

        TextInputLayout tilOperator = (TextInputLayout) findViewById(R.id.til_operator);
        tilOperator.setHint("运营商");
        mOperator = tilOperator.getEditText();


        TextInputLayout t7 = (TextInputLayout) findViewById(R.id.til_net_type_name);
        t7.setHint("网络类型名");
        mNetTypeName = t7.getEditText();

        TextInputLayout t8 = (TextInputLayout) findViewById(R.id.til_net_type);
        t8.setHint("网络类型");
        mNetType = t8.getEditText();

        TextInputLayout t9 = (TextInputLayout) findViewById(R.id.til_phone_type);
        t9.setHint("手机类型");
        mPhoneType = t9.getEditText();

        TextInputLayout t10 = (TextInputLayout) findViewById(R.id.til_sim_status);
        t10.setHint("手机卡状态");
        mSimStatus = t10.getEditText();

        TextInputLayout t11 = (TextInputLayout) findViewById(R.id.til_mac);
        t11.setHint("mac地址");
        mMacAddress = t11.getEditText();

        TextInputLayout t12 = (TextInputLayout) findViewById(R.id.til_route_name);
        t12.setHint("无线路由器名");
        mRouteName = t12.getEditText();

        TextInputLayout t13 = (TextInputLayout) findViewById(R.id.til_route_add);
        t13.setHint("无线路由器地址");
        mRouteAddress = t13.getEditText();

        TextInputLayout t14 = (TextInputLayout) findViewById(R.id.til_sys_version);
        t14.setHint("系统版本");
        mSystemVersion = t14.getEditText();

        TextInputLayout t15 = (TextInputLayout) findViewById(R.id.til_sys_version_value);
        t15.setHint("系统版本值");
        mSystemVersionValue = t15.getEditText();

        TextInputLayout t16 = (TextInputLayout) findViewById(R.id.til_sys_framework);
        t16.setHint("系统构架");
        mSystemFramework = t16.getEditText();

        TextInputLayout t17 = (TextInputLayout) findViewById(R.id.til_screen_size);
        t17.setHint("屏幕分辨率");
        mScreenSize = t17.getEditText();

        TextInputLayout t18 = (TextInputLayout) findViewById(R.id.til_firmware_version);
        t18.setHint("固件版本");
        mFirmwareVersion = t18.getEditText();

        TextInputLayout t19 = (TextInputLayout) findViewById(R.id.til_phone_brand);
        t19.setHint("手机品牌");
        mPhoneBrand = t19.getEditText();

        TextInputLayout t20 = (TextInputLayout) findViewById(R.id.til_phone_model_number);
        t20.setHint("型号");
        mPhoneModelNumber = t20.getEditText();

        TextInputLayout t21 = (TextInputLayout) findViewById(R.id.til_product_name);
        t21.setHint("产品名");
        mProductName = t21.getEditText();

        TextInputLayout t22 = (TextInputLayout) findViewById(R.id.til_productor);
        t22.setHint("制造商");
        mProductor = t22.getEditText();

        TextInputLayout t23 = (TextInputLayout) findViewById(R.id.til_equipment_name);
        t23.setHint("设备名");
        mEquipmentName = t23.getEditText();

        TextInputLayout t24 = (TextInputLayout) findViewById(R.id.til_cpu);
        t24.setHint("CPU型号");
        mCpu = t24.getEditText();

        TextInputLayout t25 = (TextInputLayout) findViewById(R.id.til_hardware);
        t25.setHint("硬件");
        mHardware = t25.getEditText();

        TextInputLayout t26 = (TextInputLayout) findViewById(R.id.til_fingerprint);
        t26.setHint("指纹");
        mFingerPrint = t26.getEditText();

        TextInputLayout t27 = (TextInputLayout) findViewById(R.id.til_port_serial_number);
        t27.setHint("串口序列号");
        mPortNumber = t27.getEditText();

        TextInputLayout t28 = (TextInputLayout) findViewById(R.id.til_bluetooth_address);
        t28.setHint("蓝牙地址");
        mBluetoothAddress = t28.getEditText();

        TextInputLayout t29 = (TextInputLayout) findViewById(R.id.til_internal_work_ip);
        t29.setHint("内网IP");
        mInternalIp = t29.getEditText();

        dataInfo = ConfigHelper.loadDataInfo(this);
        if(dataInfo!=null){
            setDataInfo(dataInfo);
        }

        dataService.setCallback(new Callback() {
            @Override
            public void onStart() {
                showLoadingProgressDialog();
            }

            @Override
            public void onSuccess(Object object) {
                dismissProgressDialog();
                DataInfo data = (DataInfo) object;
                if(data == null){
                    Toast.makeText(SetDataActivity.this,"当前条件下无数据",Toast.LENGTH_LONG).show();
                }else{
                    dataInfo = data;
                    setDataInfo(dataInfo);
                }
            }

            @Override
            public void onError(ErrorObject error) {
                dismissProgressDialog();
                DialogUtil.showOkDialog(SetDataActivity.this, error.getMsg());
            }
        });


    }





    private void initSpinnerView() {
        spinnerDay = (Spinner) findViewById(R.id.spinner_days);
        mPositionDay = mLoadSetDay.getDay();
        DataSpinner1Adapter adapterDay = new DataSpinner1Adapter(this);
        adapterDay.setData(DAY);
        spinnerDay.setSelection(mPositionDay, true);
        spinnerDay.setAdapter(adapterDay);
        spinnerDay.setOnItemSelectedListener(this);

        Spinner spinnerHour = (Spinner) findViewById(R.id.spinner_hours);
        DataSpinner1Adapter adapterHour = new DataSpinner1Adapter(this);
        mPositionHour = mLoadSetDay.getHour();
        adapterHour.setData(HOURS);
        spinnerHour.setAdapter(adapterHour);
        spinnerHour.setSelection(mPositionHour, true);
        spinnerHour.setOnItemSelectedListener(this);

        Spinner spinnerMinute = (Spinner) findViewById(R.id.spinner_minutes);
        DataSpinner1Adapter adapterMinute = new DataSpinner1Adapter(this);
        mPositionMinute = mLoadSetDay.getMinute();
        adapterMinute.setData(MINUTE_AND_SECOND);
        spinnerMinute.setAdapter(adapterMinute);
        spinnerMinute.setSelection(mPositionMinute,true);
        spinnerMinute.setOnItemSelectedListener(this);

        Spinner spinnerMinuteTo = (Spinner) findViewById(R.id.spinner_minutes_to);
        DataSpinner1Adapter adapterMinuteTo = new DataSpinner1Adapter(this);
        mPositionMinuteTo = mLoadSetDay.getMinute();
        adapterMinuteTo.setData(MINUTE_AND_SECOND);
        spinnerMinuteTo.setAdapter(adapterMinuteTo);
        spinnerMinuteTo.setSelection(mPositionMinuteTo, true);
        spinnerMinuteTo.setOnItemSelectedListener(this);

        Spinner spinnerHourTo = (Spinner) findViewById(R.id.spinner_hours_to);
        DataSpinner1Adapter adapterHourTo = new DataSpinner1Adapter(this);
        mPositionHourTo = mLoadSetDay.getHourto();
        adapterHourTo.setData(HOURS);
        spinnerHourTo.setAdapter(adapterHour);
        spinnerHourTo.setSelection(mPositionHourTo, true);
        spinnerHourTo.setOnItemSelectedListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setdata, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            updateDataInfo();
            setDataToLocal();
            saveDataInfo();
            XposeUtil.saveConfigMap();
            Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();

        }else if(id ==R.id.action_get){
            String channel = channelList.get(mPositionChannel).getName();
            int effectiveSpinnerValue = getEffectiveSpinnerValue(DAY[mPositionDay]);
            if(mPositionDay == 0){
                Toast.makeText(this,"当前条件下不能获取数据",Toast.LENGTH_SHORT).show();
            }
            else if(mPositionDay == 1){
                dataService.getSetDateNew(channel);
            }else{
                //昨天是２Integer.parseInt(splitDay[1]) 所以-1
                dataService.getSetDataUsed(channel, effectiveSpinnerValue - 1 ,HOURS[mPositionHour],MINUTE_AND_SECOND[mPositionMinute],HOURS[mPositionHourTo],MINUTE_AND_SECOND[mPositionMinuteTo]);
            }

        }else if(id == R.id.action_get_local){
            if(mPositionDay == 0 || mPositionDay == 1){
                Toast.makeText(this,"当前条件下不能获取本地数据",Toast.LENGTH_SHORT).show();

            }else{
                int weight = mLoadSetDay.getDayByPosition(mPositionDay);
                if(weight == 0){
                    setPosionDayToNext();
                    Toast.makeText(SetDataActivity.this,"超过指定百分比,跳转",Toast.LENGTH_SHORT).show();
                    return super.onOptionsItemSelected(item);
                }

                int effectiveSpinnerValue = getEffectiveSpinnerValue(DAY[mPositionDay]);
                long dateTimeFrom = DateUtil.getDateTimeMills(effectiveSpinnerValue - 1, Integer.parseInt(HOURS[mPositionHour]), Integer.parseInt(MINUTE_AND_SECOND[mPositionMinute]), 0);
                long dateTimeTo = DateUtil.getDateTimeMills(effectiveSpinnerValue - 1, Integer.parseInt(HOURS[mPositionHourTo]), Integer.parseInt(MINUTE_AND_SECOND[mPositionMinuteTo]), 0);

                String thatDay = DateUtil.getCurDate(effectiveSpinnerValue - 1);
                DataInfo localData = localDataDao.getLocalData(thatDay,dateTimeFrom,dateTimeTo);
                //本地有数据
                if(localData != null){
                    dataInfo = localData;
                    setDataInfo(localData);
                    Toast.makeText(SetDataActivity.this,"获取历史数据",Toast.LENGTH_SHORT).show();
                    //根据设定指定天数的权重来限制那一天的本地数据获取数量．
                    try {

                        boolean dataWeight = localDataDao.getDataWeight(thatDay, weight);
                        if(dataWeight){
                            setPosionDayToNext();
                            Toast.makeText(SetDataActivity.this,"超过指定百分比,跳转",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                    }

                }else {
                    Toast.makeText(SetDataActivity.this,"指定天数下没有数据",Toast.LENGTH_SHORT).show();
                    setPosionDayToNext();
                }
            }

        }else if(R.id.end_time == id){
            startActivityForResult(new Intent(this,DataKeepStatusActivity.class),DataKeepStatusActivity.REQ_DATA_KEEP);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setPosionDayToNext(){
        if(mPositionDay > 0 && mPositionDay < DAY.length - 1){
            mPositionDay ++;
        }
        spinnerDay.setSelection(mPositionDay);
    }

    /**
     * 将数据保存到本地数据库
     */
    private void setDataToLocal(){
        if(dataInfo != null && !TextUtils.isEmpty(dataInfo.getId())){
            dataInfo.setUseTime(DateUtil.getCurDate());
            Cursor cursor = localDataDao.queryById(new String[]{dataInfo.getId()});
            if(cursor == null || !cursor.moveToFirst()){
                localDataDao.add(this.dataInfo);
            }else{
                localDataDao.update(dataInfo, "id = ? and imei = ?", new String[]{dataInfo.getId(), dataInfo.getDeviceId()});
            }
        }
    }

    /**
     * 保存信息到hashmap
     */
    private void saveDataInfo() {
        jsonPut(XposeUtil.m_ExtraInfo, mExtrainfo.getText().toString().trim());
        jsonPut(XposeUtil.m_deviceId, mDeviceId.getText().toString().trim());
        jsonPut(XposeUtil.m_androidId, mAndroidId.getText().toString().trim());
        jsonPut(XposeUtil.m_bluetoothaddress, mBluetoothAddress.getText().toString().trim());
        jsonPut(XposeUtil.m_fingerprint, mFingerPrint.getText().toString().trim());
        jsonPut(XposeUtil.m_firmwareversion, mFirmwareVersion.getText().toString().trim());
        jsonPut(XposeUtil.m_hardware, mHardware.getText().toString().trim());
        jsonPut(XposeUtil.m_subscriberId, mIMSI.getText().toString().trim());
        jsonPut(XposeUtil.m_macAddress, mMacAddress.getText().toString().trim());
        jsonPut(XposeUtil.m_networkType, mNetType.getText().toString().trim());
        jsonPut(XposeUtil.m_networkOperatorName, mNetTypeName.getText().toString().trim());
        jsonPut(XposeUtil.m_simOperator, mOperator.getText().toString().trim());
        jsonPut(XposeUtil.m_brand, mPhoneBrand.getText().toString().trim());
        jsonPut(XposeUtil.m_model, mPhoneModelNumber.getText().toString().trim());
        jsonPut(XposeUtil.m_phoneNum, mPhoneNum.getText().toString().trim());
        jsonPut(XposeUtil.m_phoneType, mPhoneType.getText().toString().trim());
        jsonPut(XposeUtil.m_serial, mPortNumber.getText().toString().trim());
        jsonPut(XposeUtil.m_product, mProductName.getText().toString().trim());
        jsonPut(XposeUtil.m_manufacture, mProductor.getText().toString().trim());
        jsonPut(XposeUtil.m_simSerialNumber, mSimId.getText().toString().trim());
        jsonPut(XposeUtil.m_SSID, mRouteName.getText().toString().trim());
        jsonPut(XposeUtil.m_BSSID, mRouteAddress.getText().toString().trim());
        if(mModifyResolution.isChecked()){
            jsonPut(XposeUtil.m_screenSize, mScreenSize.getText().toString().trim());
        }else {
            jsonPut(XposeUtil.m_screenSize, "");
        }
        jsonPut(XposeUtil.m_simState, mSimStatus.getText().toString().trim());
        jsonPut(XposeUtil.m_framework, mSystemFramework.getText().toString().trim());
        jsonPut(XposeUtil.m_RELEASE, mSystemVersion.getText().toString().trim());
        jsonPut(XposeUtil.m_SDK, mSystemVersionValue.getText().toString().trim());

    }

    private void setDataInfo(DataInfo dataInfo) {
        if(TextUtils.isEmpty(dataInfo.getExtraInfo())){

            mExtrainfo.setText(RandomUtil.getWIFIName());
        }else {
            mExtrainfo.setText(dataInfo.getExtraInfo());
        }
        mDeviceId.setText(dataInfo.getDeviceId());
        mAndroidId.setText(dataInfo.getAndroidId());
        mBluetoothAddress.setText(dataInfo.getBluetoothAddress());
        mCpu.setText(dataInfo.getCpu());
        mFingerPrint.setText(dataInfo.getFingerPrint());
        mFirmwareVersion.setText(dataInfo.getFirmwareVersion());
        mHardware.setText(dataInfo.getHardware());
        mInternalIp.setText(dataInfo.getInternalIp());
        mIMSI.setText(dataInfo.getImsi());
        mMacAddress.setText(dataInfo.getMacAddress());
        mNetType.setText(dataInfo.getNetType());
        mEquipmentName.setText(dataInfo.getEquipmentName());
        mNetTypeName.setText(dataInfo.getNetTypeName());
        mOperator.setText(dataInfo.getOperator());
        mPhoneBrand.setText(dataInfo.getPhoneBrand());
        mPhoneModelNumber.setText(dataInfo.getPhoneModelNumber());
        mPhoneNum.setText(dataInfo.getPhoneNum());
        mPhoneType.setText(dataInfo.getPhoneType());
        mPortNumber.setText(dataInfo.getPortNumber());
        mProductName.setText(dataInfo.getProductName());
        mProductor.setText(dataInfo.getProductor());
        mSimId.setText(dataInfo.getSimId());
        mRouteAddress.setText(dataInfo.getRouteAddress());
        mRouteName.setText(dataInfo.getRouteName());
        mScreenSize.setText(dataInfo.getScreenSize());
        mSimStatus.setText(dataInfo.getSimStatus());
        mSystemFramework.setText(dataInfo.getSystemFramework());
        mSystemVersion.setText(dataInfo.getSystemVersion());
        mSystemVersionValue.setText(dataInfo.getSystemVersionValue());

    }

    private void updateDataInfo(){

        dataInfo.setExtraInfo(getTextString(mExtrainfo));
        dataInfo.setDeviceId(getTextString(mDeviceId));
        dataInfo.setAndroidId(getTextString(mAndroidId));
        dataInfo.setBluetoothAddress(getTextString(mBluetoothAddress));
        dataInfo.setCpu(getTextString(mCpu));
        dataInfo.setFingerPrint(getTextString(mFingerPrint));
        dataInfo.setFirmwareVersion(getTextString(mFirmwareVersion));
        dataInfo.setHardware(getTextString(mHardware));
        dataInfo.setInternalIp(getTextString(mInternalIp));
        dataInfo.setImsi(getTextString(mIMSI));
        dataInfo.setMacAddress(getTextString(mMacAddress));
        dataInfo.setNetType(getTextString(mNetType));
        dataInfo.setEquipmentName(getTextString(mEquipmentName));
        dataInfo.setNetTypeName(getTextString(mNetTypeName));
        dataInfo.setOperator(getTextString(mOperator));
        dataInfo.setPhoneBrand(getTextString(mPhoneBrand));
        dataInfo.setPhoneModelNumber(getTextString(mPhoneModelNumber));
        dataInfo.setPhoneNum(getTextString(mPhoneNum));
        dataInfo.setPhoneType(getTextString(mPhoneType));
        dataInfo.setPortNumber(getTextString(mPortNumber));
        dataInfo.setProductName(getTextString(mProductName));
        dataInfo.setProductor(getTextString(mProductor));
        dataInfo.setSimId(getTextString(mSimId));
        dataInfo.setRouteName(getTextString(mRouteName));
        dataInfo.setRouteAddress(getTextString(mRouteAddress));
        dataInfo.setScreenSize(getTextString(mScreenSize));
        dataInfo.setSimStatus(getTextString(mSimStatus));
        dataInfo.setSystemFramework(getTextString(mSystemFramework));
        dataInfo.setSystemVersion(getTextString(mSystemVersion));
        dataInfo.setSystemVersionValue(getTextString(mSystemVersionValue));
    }

    private void jsonPut(String key,String value){
        try {
            XposeUtil.configMap.put(key,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localDataDao.close();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            back();
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void back() {


        saveSetDay();

        updateDataInfo();
        setDataToLocal();
        ConfigHelper.saveDataInfo(this, dataInfo);
        saveDataInfo();
        XposeUtil.saveConfigMap();
        Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
    }

    private void saveSetDay() {

        mLoadSetDay.setOnlySpinner(mPositionChannel,mPositionDay,mPositionHour,mPositionHourTo,mPositionMinute,mPositionMinuteTo);
        mLoadSetDay.setResolution(mModifyResolution.isChecked());
        SetDay.saveSetDay(this,mLoadSetDay);

    }

    @Override
    public void onRefresh() {
        channelService.getChannelName();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == LOCAL_DATA && resultCode == RESULT_OK){
            DataInfo dataInfo = (DataInfo) data.getSerializableExtra(LocalDataDetailActivity.DATA);
            setDataInfo(dataInfo);
        }else if(requestCode == DataKeepStatusActivity.REQ_DATA_KEEP && resultCode == RESULT_OK){
            mDataKeepStatuses = DataKeepStatus.loadDataKeepStatus(this);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.accb_local){
            startActivityForResult(new Intent(this, LocalDataActivity.class), LOCAL_DATA);
        }else if(v.getId() == R.id.accb_random){
            DataInfo random = RandomUtil.getRandom();
            random.setSaveTime(DateUtil.getCurDate());
            random.setDetailTime(System.currentTimeMillis());
            dataInfo = random;
            setDataInfo(dataInfo);
        }else if(v.getId() == R.id.end_time_text){
            showCurTimePickerFragment();
        }else if(v.getId() == R.id.accb_keep_setting){
            KeepDataSettingFragment dataSettingFragment = KeepDataSettingFragment.newInstance(mLoadSetDay);
            dataSettingFragment.show(getSupportFragmentManager(),"dialog");
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int viewId = parent.getId();
        switch (viewId){
            case R.id.spinner_days:
                mPositionDay = position;
                break;
            case R.id.spinner_hours:
                mPositionHour = position;
                break;
            case R.id.spinner_minutes:
                mPositionMinute = position;
                break;
            case R.id.spinner_minutes_to:
                mPositionMinuteTo = position;
                break;
            case R.id.spinner_hours_to:
                mPositionHourTo = position;
                break;
            case R.id.spinner:
                mPositionChannel = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //回调。只修改留存天数的对应百分比(day2-day31)
    @Override
    public void onKeppData(SetDay setDay) {
        Log.d("unlock","onKeppData"+setDay.toString());
        mLoadSetDay.setOnlyDay(setDay);
    }


}
