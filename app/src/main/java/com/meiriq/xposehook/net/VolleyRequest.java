/**
 *  Copyright (C) 2014-2015 The MeiRiQ All Right Reserve.
 */
package com.meiriq.xposehook.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.Map;

/**
 * @description volley网络请求,NOTE:在使用该请求的Activity被销毁时，需要调用
 *              {@link #stopRequest(Context)} 来停止所有的请求
 * @version 1.0
 * @time 2015-5-14 下午8:37:02
 * @author zhangchaoxian@meiriq.com
 */
public interface VolleyRequest {

	/**
	 * String 类型的请求
	 * 
	 * @param method
	 *            请求方式 {@link Request.Method}
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param listener
	 *            请求的回调接口
	 * @param Exception
	 *            请求发生的异常
	 */
	public void stringRequest(int method, String url,
							  Map<String, String> params, VolleyListener listener)
			throws Exception;

	/**
	 * Json 类型的请求
	 * 
	 * @param method
	 *            请求方式 {@link Request.Method}
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param body
	 *            POST/PUT请求内容
	 * @param listener
	 *            请求的回调接口
	 * @throws Exception
	 *             请求发生的异常
	 */
	public void jsonRequest(int method, String url, Map<String, String> params,
							JSONObject body, VolleyListener listener) throws Exception;

	/**
	 * 取消当前所有的请求，释放资源
	 * 
	 * @param context
	 *            当前请求队列的上下文，用于标记取消队列
	 */
	public void stopRequest(Context context);

	/**
	 * 获取当前的volley网络请求队列
	 * 
	 * @return
	 */
	public RequestQueue getRequestQueue();
}
