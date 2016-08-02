/**
 *  Copyright (C) 2014-2015 The MeiRiQ All Right Reserve.
 */
package com.meiriq.xposehook.net;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.Volley;

/**
 * @description Volley 工具类
 * @version 1.0
 * @time 2015-5-15 下午8:12:08
 * @author zhangchaoxian@meiriq.com
 */
public class VolleyUtil {
	private static RequestQueue sRequestQueue;

	private static ImageLoader sImageLoader;

	/**
	 * 获取Volley RequestQueue的唯一实例
	 * 
	 * @param context
	 * @return
	 */
	public static RequestQueue getRequestQueue(Context context) {
		if (sRequestQueue == null) {
			// 使用application 的Context构建唯一实例
			sRequestQueue = Volley.newRequestQueue(context
					.getApplicationContext());
		}
		return sRequestQueue;
	}

	/**
	 * 获取ImageLoader 的唯一实例，使用同一个Imageloader可以减少Activity重建时发生的闪烁问题
	 * 
	 * @param
	 * @return
	 */
	public static ImageLoader getImageLoader(Context context) {
		if (sImageLoader == null) {
			sImageLoader = new ImageLoader(getRequestQueue(context),
					new LruBitmapCache(LruBitmapCache.getCacheSize(context)));
		}
		return sImageLoader;
	}

	public static class LruBitmapCache extends LruCache<String, Bitmap>
			implements ImageCache {

		public LruBitmapCache(int maxSize) {
			super(maxSize);
		}

		public LruBitmapCache(Context ctx) {
			this(getCacheSize(ctx));
		}

		@Override
		protected int sizeOf(String key, Bitmap value) {
			return value.getRowBytes() * value.getHeight();
		}

		@Override
		public Bitmap getBitmap(String url) {
			return get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			put(url, bitmap);
		}

		// Returns a cache size equal to approximately three screens worth of
		// images.
		public static int getCacheSize(Context ctx) {
			final DisplayMetrics displayMetrics = ctx.getResources()
					.getDisplayMetrics();
			final int screenWidth = displayMetrics.widthPixels;
			final int screenHeight = displayMetrics.heightPixels;
			// 4 bytes per pixel
			final int screenBytes = screenWidth * screenHeight * 4;

			return screenBytes * 3;
		}
	}
}
