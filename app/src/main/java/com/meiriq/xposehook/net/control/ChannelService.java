package com.meiriq.xposehook.net.control;

import android.content.Context;

import com.android.volley.VolleyError;
import com.meiriq.xposehook.bean.Constant;
import com.meiriq.xposehook.bean.util.ChannelUtil;
import com.meiriq.xposehook.bean.util.SetDataUtil;
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
public class ChannelService extends BaseService{
    public ChannelService(Context context) {
        super(context);
    }



    public void getChannelName(){
        mCallback.onStart();


        getStringRequest(Constant.url_getChannelName, null, new VolleyListener() {
            @Override
            public void onComplete(JSONObject jsonObject) {
                mCallback.onSuccess(ChannelUtil.parse2Channel(jsonObject.optJSONArray("data")));
            }

            @Override
            public void onError(VolleyError error) {
                mCallback.onError(VolleyErrorHandler.wrap2ErrorObject(error));
            }
        });
    }
}
