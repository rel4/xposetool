/**
 *  Copyright (C) 2014-2015 The MeiRiQ All Right Reserve.
 */
package com.meiriq.xposehook.net;

import android.content.Context;

import com.meiriq.xposehook.dao.BaseDao;


/**
 * @description 所有数据的处理基类,具体实现由各个子类实现读写数据
 * @version 1.0
 * @time 2015-5-14 下午8:38:44
 * @author zhangchaoxian@meiriq.com
 * @param <T>
 *            访问的数据对象类型
 */
public abstract class BaseService<T> extends VolleyRequestImpl {

	protected Context mContext;
	/** 数据库操作 */
	protected BaseDao<T> mBaseDao;

	/** 数据处理回调接口 */
	protected Callback<T> mCallback;
	/** 是否正在加载 */
	protected boolean isLoading = false;

	/**
	 * 不带回调的构造函数
	 * 
	 * @param context
	 */
	public BaseService(Context context) {
		super(context);
		mContext = context;
		mCallback = new Callback() {

			@Override
			public void onStart() {
				// Do nothing
			}

			@Override
			public void onSuccess(Object object) {
				// Do nothing
			}

			@Override
			public void onError(ErrorObject errorObject) {
				// Do nothing
			}
		};
	}

	/**
	 * 带回调的构造函数
	 * 
	 * @param context
	 * @param callback
	 */
	public BaseService(Context context, Callback<T> callback) {
		super(context);
		mContext = context;
		mCallback = callback;
	}

	/**
	 * 设置回调接口
	 * 
	 * @param callback
	 */
	public void setCallback(Callback<T> callback) {
		mCallback = callback;
	}

	/** 是否正在加载 */
	public boolean isLoading() {
		return isLoading;
	}

	/** 设置加载状态 */
	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}
	
	/**
	 * 关闭连接、数据库，释放资源
	 */
	public void release() {
		// 关闭所有请求
		stopRequest(mContext);

		// 关闭数据库连接
		if (mBaseDao != null) {
			mBaseDao.close();
		}
	}

}
