/**
 *  Copyright (C) 2014-2015 The MeiRiQ All Right Reserve.
 */
package com.meiriq.xposehook.net;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * @description 使用Volley网络请求的监听器回调:@ {@link #onStart()} 开始请求<br>
 *              {@link #onComplete(JSONObject)} 在volley请求完成时调用;
 *              {@link #onError(JSONObject)}在Volley请求失败/错误时调用
 * @version 1.0
 * @time 2015-5-14 下午7:18:49
 * @author zhangchaoxian@meiriq.com
 */
public interface VolleyListener {

	/**
	 * 网络请求结束
	 * 
	 * @param jsonObject
	 */
	public void onComplete(JSONObject jsonObject);

	/**
	 * 网络请求发生错误
	 * 
	 * @param
	 */
	public void onError(VolleyError error);
}
