/**
 *  Copyright (C) 2014-2015 The MeiRiQ All Right Reserve.
 */
package com.meiriq.xposehook.net;

/**
 * 数据处理的回调接口
 * <p>
 * {@link Callback#onStart()} 开始数据处理<br>
 * {@link Callback#onSuccess(Object)} 当读取数据完成后调用<br>
 * {@link Callback#onError(ErrorObject)} 当读取数据发生错误时调用<br>
 * </p>
 * 
 * @version 1.0
 * @time 2015-5-15 下午6:13:46
 * @author zhangchaoxian@meiriq.com
 * @param <T>
 *            访问的数据对象类型
 */
public interface Callback<T> {
	/**
	 * 开始
	 */
	public void onStart();

	/**
	 * 成功后回调
	 * 
	 * @param object
	 *            单个的数据对象
	 */
	public void onSuccess(Object object);

	/**
	 * 失败后回调
	 * 
	 * @param error
	 *            返回ErrorObject内容
	 */
	public void onError(ErrorObject error);
}