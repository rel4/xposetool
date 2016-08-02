package com.meiriq.xposehook.bean.util;

import android.text.TextUtils;

import com.meiriq.xposehook.bean.Channel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 15-12-26.
 */
public class ChannelUtil {

    public static List<Channel> parse2Channel(JSONArray datas){
        if(datas == null){
            return null;
        }
        int length = datas.length();
        ArrayList<Channel> channels = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            JSONObject jsonObject = datas.optJSONObject(i);
            String name = jsonObject.optString("name");
            if(!TextUtils.isEmpty(name)){
                Channel channel = new Channel();
                channel.setName(name);
                channels.add(channel);
            }
        }
        return channels;
    }
}
