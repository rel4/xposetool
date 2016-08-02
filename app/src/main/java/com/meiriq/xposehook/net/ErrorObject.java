/**
 *  Copyright (C) 2014-2015 The MeiRiQ All Right Reserve.
 */
package com.meiriq.xposehook.net;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * @description 错误信息结构体
 * @version 1.0
 * @time 2015-5-19 上午9:31:41
 * @author zhangchaoxian@meiriq.com
 */
public class ErrorObject implements Parcelable {
	// error cause
	private String msg;
	// error desc
	private String description;
	// error code
	private int code;

	public ErrorObject() {
		// Do nothing
	}

	public ErrorObject(String msg, int code) {
		this.msg = msg;
		this.code = code;
	}

	/**
	 * 从json数据中解析错误信息体
	 * 
	 * @param jsonObject
	 */
	public ErrorObject(JSONObject jsonObject) {
		this.msg = jsonObject.optString("error");
		this.code = jsonObject.optInt("code");
		this.description = jsonObject.optString("error_description");
	}

	/**
	 * 解析json数据
	 * 
	 * @param jsonObject
	 * @return
	 */
	public ErrorObject parseJson(JSONObject jsonObject) {
		this.msg = jsonObject.optString("error");
		this.code = jsonObject.optInt("code");
		this.description = jsonObject.optString("error_description");
		return this;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(msg);
		dest.writeString(description);
		dest.writeInt(code);
	}

	public static final Parcelable.Creator<ErrorObject> CREATOR = new Creator<ErrorObject>() {

		@Override
		public ErrorObject createFromParcel(Parcel source) {
			ErrorObject object = new ErrorObject();
			object.msg = source.readString();
			object.description = source.readString();
			object.code = source.readInt();
			return object;
		}

		@Override
		public ErrorObject[] newArray(int size) {
			return new ErrorObject[size];
		}
	};

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ErrorObject [msg=" + msg + ", description=" + description
				+ ", code=" + code + "]";
	}

}
