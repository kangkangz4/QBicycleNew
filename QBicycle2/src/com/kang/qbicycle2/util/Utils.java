package com.kang.qbicycle2.util;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.Toast;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import com.kang.qbicycle2.R;

public class Utils {

	public static final int OK = 0x00;
	public static final int HTTP_ERROR = 0x01;
	public static final int XMLPARSER_ERROR = 0x02;
	public static final int NO_DATA = 0x03;

	public static final String KEY_USER = "key_user";
	public static final String KEY_TOKEN = "key_token";

	public static final String KEY_RESTART = "key_restart";

	public static final String REQUEST_CODE = "request_code";

	// 百度地图api Key
	public static final String BAIDU_MAP_KEY = "B2403a73a905f0e759a94aadcc745872";

	// Action
	public static final String ACTION_OPERUSER = "com.kang.qbicycle2.operuser";
	public static final String ACTION_NORMALUSER = "com.kang.qbicycle2.normaluser";
	public static final String ACTION_STATIONSHOW = "com.kang.qbicycle2.stationshow";
	public static final String ACTION_SETTING = "com.kang.qbicycle2.setting";

	public static final String ACTION_BAIDUMAP = "com.kang.qbicycle2.baidumap";

	public static final String ACTION_OPERATORMAIN = "com.kang.qbicycle2.operatormain";

	public static final String ACTION_CAPTER = "com.kang.qbicycle2.zxing";

	public static final String ACTION_CAPTER_BROADCAST = "com.kang.qbicycle2.zxing.broadcast";

	public static final String SHARE_PREFRENCES = "com.kang.qbicycle";

	/**
	 * 设置ActionBar
	 */
	public static void setActionBar(Activity activity) {
		// 设置ActionBar背景
		activity.getSupportActionBar().setBackgroundDrawable(
				activity.getResources().getDrawable(R.drawable.main_head_bg));
		changeTitleColor(activity);
	}

	private static void changeTitleColor(Activity activity) {
		// 改变ActionBar Title字体颜色
		int titleId = Resources.getSystem().getIdentifier("action_bar_title",
				"id", "android");
		TextView title = (TextView) activity.findViewById(titleId);
		title.setTextColor(activity.getResources().getColor(R.color.textColor));
	}

	/**
	 * 左进右出动画
	 * 
	 * @param activity
	 */
	public static void AnimLeftInRightOut(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
	}

	/**
	 * 右进左出
	 * 
	 * @param activity
	 */
	public static void AnimRightInLeftOut(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_right,
				R.anim.slide_out_left);
	}

	/**
	 * 上进下出
	 * 
	 * @param activity
	 */
	public static void AnimTopInBottomOut(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_top,
				R.anim.slide_out_bottom);
	}

	/**
	 * 下进上出
	 * 
	 * @param activity
	 */
	public static void AnimBottomInTopOut(Activity activity) {
		activity.overridePendingTransition(R.anim.slide_in_bottom,
				R.anim.slide_out_top);
	}

	/**
	 * 显示Toast
	 * 
	 * @param context
	 * @param string
	 */
	public static void showToast(Context context, String string) {
		Toast.makeText(context, string, Toast.LENGTH_LONG).show();
	}

	public static void showToast(Context context, int stringId) {
		Toast.makeText(context, stringId, Toast.LENGTH_LONG).show();
	}

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	/**
	 * 开启扫描视图
	 * 
	 * @param activity
	 * @param resultCode
	 */
	public static void startCapterActivity(Fragment fragment, int requestCode) {
		Intent intent = new Intent();
		intent.putExtra(Utils.REQUEST_CODE, requestCode);
		intent.setAction(Utils.ACTION_CAPTER);
		fragment.startActivity(intent);
	}

}
