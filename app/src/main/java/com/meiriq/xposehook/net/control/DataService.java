package com.meiriq.xposehook.net.control;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.meiriq.xposehook.bean.Constant;
import com.meiriq.xposehook.bean.DataInfo;
import com.meiriq.xposehook.bean.util.ChannelUtil;
import com.meiriq.xposehook.bean.util.SetDataUtil;
import com.meiriq.xposehook.dao.LocalDataDao;
import com.meiriq.xposehook.net.BaseService;
import com.meiriq.xposehook.net.VolleyErrorHandler;
import com.meiriq.xposehook.net.VolleyListener;
import com.meiriq.xposehook.utils.DateUtil;
import com.meiriq.xposehook.utils.L;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by tian on 15-12-8.
 */
public class DataService extends BaseService{
    public DataService(Context context) {
        super(context);
    }


    public void getSetDateNew(String channel){
        mCallback.onStart();
        try {
            channel = URLEncoder.encode(channel,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String param = "used=" + channel ;
        String url = Constant.url_getDataRandom + "?" + param;
        getStringRequest(url, null, new VolleyListener() {
            @Override
            public void onComplete(JSONObject jsonObject) {
                L.debug(jsonObject.toString());
                if (jsonObject.optString("data").equals("null")) {
                    mCallback.onSuccess(null);
                } else {
                    DataInfo dataInfo = SetDataUtil.parseJsonObject2DataInfo(jsonObject.optJSONObject("data"));
                    dataInfo.setSaveTime(DateUtil.getCurDate());
                    dataInfo.setDetailTime(System.currentTimeMillis());
                    mCallback.onSuccess(dataInfo);
                }
                L.debug("----" + jsonObject.toString());
            }

            @Override
            public void onError(VolleyError error) {
                mCallback.onError(VolleyErrorHandler.wrap2ErrorObject(error));
            }
        });
    }


    public void getSetDataUsed(String channel, final int diff, String hour_from,String minute_from, String hour_to,String minute_to) {
        mCallback.onStart();


        String dateTimeFrom = DateUtil.getDateTime(diff,Integer.parseInt(hour_from),Integer.parseInt(minute_from));
        String dateTimeTo = DateUtil.getDateTime(diff,Integer.parseInt(hour_to),Integer.parseInt(minute_to));

        try {
            channel = URLEncoder.encode(channel, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String param = "used=" + channel + "&beginDate=" + dateTimeFrom
                + "&endDate=" + dateTimeTo;

        String url = Constant.url_getDataUsed + "?" + param;
        getStringRequest(url, null, new VolleyListener() {
            @Override
            public void onComplete(JSONObject jsonObject) {
                if (jsonObject.optString("data").equals("null")) {
                    mCallback.onSuccess(null);
                } else {
                    DataInfo dataInfo = SetDataUtil.parseJsonObject2DataInfo(jsonObject.optJSONObject("data"));
                    dataInfo.setSaveTime(DateUtil.getCurDate(diff));
                    dataInfo.setDetailTime(DateUtil.getTimeMillis(diff));
                    mCallback.onSuccess(dataInfo);
                }
                L.debug("----" + jsonObject.toString());
            }

            @Override
            public void onError(VolleyError error) {
                mCallback.onError(VolleyErrorHandler.wrap2ErrorObject(error));
            }
        });
    }


    /**
     * 通知某ｉｄ的数据从服务器数据库删除
     * @param id
     */
    public void sendDataDeprecated(final Context context, final String id) {
        mCallback.onStart();

        deleteJsonRequest(Constant.url_sendDataDeprecated + id, null, new VolleyListener() {
            @Override
            public void onComplete(JSONObject jsonObject) {
                L.debug(jsonObject.toString());
                LocalDataDao localDataDao = new LocalDataDao(context);
                localDataDao.delete("id = ?",new String[]{id});
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }

}
