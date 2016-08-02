/**
 *  Copyright (C) 2014-2015 The MeiRiQ All Right Reserve.
 */
package com.meiriq.xposehook.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.meiriq.xposehook.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @description Volley 请求的实现封装类
 * @version 1.0
 * @time 2015-5-14 下午7:31:41
 * @author zhangchaoxian@meiriq.com
 */
public class VolleyRequestImpl implements VolleyRequest {

	private final String TAG = VolleyRequestImpl.class.getSimpleName();
	public static final String ENCODING = "UTF-8";

	private RequestQueue mQueue;
	protected Context mContext;

	public VolleyRequestImpl(Context context) {
		mContext = context;
		mQueue = VolleyUtil.getRequestQueue(context);
	}

	/**
	 * GET String 请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param listener
	 *            请求的回调
	 */
	public void getStringRequest(String url, Map<String, String> params,
			VolleyListener listener) {
		stringRequest(Method.GET, url, params, listener);
	}

	/**
	 * POST String 请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param listener
	 *            请求的回调
	 */
	public void postStringRequest(String url, Map<String, String> params,
			VolleyListener listener) {
		stringRequest(Method.POST, url, params, listener);
	}

	/**
	 * GET Json 请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param listener
	 *            请求的回调
	 */
	public void getJsonRequest(String url, Map<String, String> params,
			VolleyListener listener) {
		jsonRequest(Method.GET, url, params, null, listener);
	}

	/**
	 * POST Json请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param body
	 *            请求内容
	 * @param listener
	 *            请求的回调
	 */
	public void postJsonRequest(String url, Map<String, String> params,
			JSONObject body, VolleyListener listener) {
		jsonRequest(Method.POST, url, params, body, listener);
	}

	/**
	 * PUT Json 请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param body
	 *            请求内容
	 * @param listener
	 *            请求的回调
	 */
	public void putJsonRequest(String url, Map<String, String> params,
			JSONObject body, VolleyListener listener) {
		jsonRequest(Method.PUT, url, params, body, listener);
	}

	public void deleteJsonRequest(String url, Map<String, String> params,
								  VolleyListener listener) {
		stringRequest(Method.DELETE, url, params, listener);
	}

	@Override
	public void stringRequest(int method, String url,
			Map<String, String> params, final VolleyListener listener) {
		StringRequest request = new StringRequest(method, getRequestUrl(url,
				params), new Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					L.debug("++"+response+"++");
					// 完成响应
					listener.onComplete(new JSONObject(response.trim()));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
		}, new ErrorHandler(listener));

		// 添加一个取消请求队列的标志位
		request.setTag(mContext);
		mQueue.add(request);
	}
	

	@Override
	public void jsonRequest(int method, String url, Map<String, String> params,
			JSONObject body, final VolleyListener listener) {
		JsonObjectRequest request = new JsonObjectRequest(method,
				getRequestUrl(url, params), body, new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						listener.onComplete(response);
					}
				}, new ErrorHandler(listener)){
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {

				Map<String,String> headers = new HashMap<>();
				headers.put("Accept","application/json");
				return headers;
			}
		};

		// 添加一个取消请求队列的标志位
		request.setTag(mContext);
		mQueue.add(request);
	}

	/**
	 * 将请求URL和请求参数整合之后的URL
	 * 
	 * @param url
	 *            原请求地址
	 * @param params
	 *            请求参数体
	 * @return
	 */
	private String getRequestUrl(String url, Map<String, String> params) {
		// 请求参数转换添加到URL中
		StringBuilder sb = new StringBuilder(url);
		if (params != null) {
			sb.append("?");// add first character '?',before add params
			for (Map.Entry<String, String> entry : params.entrySet()) {
				// sb.append(URLEncoder.encode(entry.getKey(), ENCODING));
				sb.append(entry.getKey());
				sb.append('=');
				// sb.append(URLEncoder.encode(entry.getValue(), ENCODING));
				sb.append(entry.getValue());
				sb.append('&');
			}
			sb.deleteCharAt(sb.length() - 1);// remove last character'&'
		}
		return sb.toString();
	}

	@Override
	public void stopRequest(Context context) {
		mQueue.cancelAll(context);
	}

	@Override
	public RequestQueue getRequestQueue() {
		return mQueue;
	}

	/**
	 * Volley 网络请求失败ErrorListener的实现类，将VolleyError错误信息转换为JSONObject类型
	 */
	private class ErrorHandler implements ErrorListener {
		private VolleyListener mListener;

		public ErrorHandler(VolleyListener listener) {
			mListener = listener;
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			mListener.onError(error);
		}

	}

}
