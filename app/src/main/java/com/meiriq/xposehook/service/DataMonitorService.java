package com.meiriq.xposehook.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.meiriq.xposehook.SetDataActivity;
import com.meiriq.xposehook.bean.DataKeepStatus;
import com.meiriq.xposehook.bean.SetDay;
import com.meiriq.xposehook.dao.LocalDataDao;
import com.meiriq.xposehook.utils.DateUtil;

import java.util.Calendar;
import java.util.List;

/**
 * 轮询当前留存天数是否符合ＥＮＤＴＩＭＥ里面设置的条件
 */
public class DataMonitorService extends Service {

    public DataMonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    private LocalDataDao localDataDao;
    @Override
    public void onCreate() {
        super.onCreate();
        localDataDao = new LocalDataDao(this);

//        Log.d("unlock","onCreate");
        new MonitorThread().start();
    }

    class MonitorThread extends Thread{
        @Override
        public void run() {
            while (true){//轮询更新留存数据
//                Log.d("unlock","run");
                long timeInMillis = Calendar.getInstance().getTimeInMillis();
                List<DataKeepStatus> mDataKeepStatuses = DataKeepStatus.loadDataKeepStatus(DataMonitorService.this);
                SetDay loadSetDay = SetDay.loadSetDay(DataMonitorService.this);
                if(mDataKeepStatuses != null){


                    int mPositionDay = loadSetDay.getDay();
                    String curDate = DateUtil.getCurDate();
                    if(mPositionDay != 0){
                        //获取当前存储的留存天数是哪一天
                        int keepDay = getEffectiveSpinnerValue(SetDataActivity.DAY[mPositionDay]);
                        //查询留存那一天的数据在今天用了几个
                        int queryInWhichDayCount = localDataDao.queryInWhichDayCount(new String[]{DateUtil.getCurDate(keepDay - 1), curDate});
                        int size = mDataKeepStatuses.size();
                        for (int i = 0; i < size; i++) {
                            DataKeepStatus dataKeepStatus = mDataKeepStatuses.get(i);
                            //ENDTIME有设置数据
                            if(dataKeepStatus.getKeepCount() > 0){
                                //今天留存使用量超过指定值且今天没针对这个指定值换过留存
                                if( queryInWhichDayCount >= dataKeepStatus.getKeepCount() && !curDate.equals(dataKeepStatus.getUseDay())){
                                    mPositionDay = dataKeepStatus.getKeepDayStatus();
                                    dataKeepStatus.setUseDay(curDate);
                                    DataKeepStatus.saveDataKeepStatus(DataMonitorService.this, mDataKeepStatuses);
//                                    SP.set(SP.KEY_DAY, mPositionDay);
                                    loadSetDay.setDay(mPositionDay);
                                    SetDay.saveSetDay(DataMonitorService.this,loadSetDay);
                                    Log.d("unlock","DataMonitorService="+"留存天使设置"+mPositionDay);
                                    break;
                                }
                            }
                        }
                    }
                    //判断时间是否符合要求
                    int size = mDataKeepStatuses.size();
                    for (int j = 0; j < size; j++) {
                        DataKeepStatus dataKeepStatus = mDataKeepStatuses.get(j);
                        String[] split = dataKeepStatus.getKeepTime().split(":");
                        Calendar instance = Calendar.getInstance();
                        instance.set(Calendar.HOUR_OF_DAY,Integer.parseInt(split[0]));
                        instance.set(Calendar.MINUTE, Integer.parseInt(split[1]));
                        if(Math.abs(instance.getTimeInMillis() - timeInMillis) < 1000 * 60 * 3 && !curDate.equals(dataKeepStatus.getUseDay())){
                            mPositionDay = dataKeepStatus.getKeepDayStatus();
                            Log.d("unlock", "时间符合" + mPositionDay);
                            dataKeepStatus.setUseDay(curDate);
                            DataKeepStatus.saveDataKeepStatus(DataMonitorService.this, mDataKeepStatuses);
//                            SP.set(SP.KEY_DAY, mPositionDay);
                            loadSetDay.setDay(mPositionDay);
                            SetDay.saveSetDay(DataMonitorService.this, loadSetDay);
                            Log.d("unlock", "DataMonitorService=" + "留存时间设置设置" + mPositionDay);
                        }
                    }

                }

                SystemClock.sleep(1000 * 60);
            }
        }
    }


    public int getEffectiveSpinnerValue(String spinnerValue){
        try{
            String[] split = spinnerValue.split(":");
            if(split.length == 2){
                return Integer.parseInt(split[1]);
            }
        }catch (Exception e){

        }

        return 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
