/**
 *  Copyright (C) 2014-2015 The MeiRiQ All Right Reserve.
 */
package com.meiriq.xposehook.net;

import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.meiriq.xposehook.utils.L;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @description VolleyError 错误内容处理
 * @version 1.0
 * @time 2015-5-15 下午2:56:29
 * @author zhangchaoxian@meiriq.com
 */
public class VolleyErrorHandler {

	/**
	 * 解析VolleyError错误信息
	 * 
	 * @param error
	 * @return
	 */
	public static ErrorObject wrap2ErrorObject(VolleyError error) {
		System.out.println("error:\n"+ error);
		ErrorObject errorObject = new ErrorObject();
		try {
			if (error != null && error.networkResponse != null) {
				String content = new String(error.networkResponse.data);
				// 有内容
				if (!TextUtils.isEmpty(content)) {
					JSONObject jsonObject = new JSONObject(content);
					errorObject.setCode(jsonObject.optInt("code"));
					errorObject.setMsg(jsonObject.toString());
					errorObject.setDescription(jsonObject
							.optString("error_description"));
				} else {
					// 没有内容的错误
					errorObject.setMsg("网络请求发生错误了");
					errorObject.setCode(-1);
				}
			}

			// 没有网络的问题
			if (error != null && error.networkResponse == null) {
				errorObject.setCode(-1);// 断网
				errorObject.setMsg("没有网络可以访问");
			}

		} catch (JSONException jsonException) {
			// 数据解析异常错误
			errorObject.setMsg("解析异常");
		} catch (Exception e) {
			// 发生了其他不能处理的错误
			errorObject.setMsg("发生了其它不能处理的错误");
		}

		return errorObject;
	}

}
