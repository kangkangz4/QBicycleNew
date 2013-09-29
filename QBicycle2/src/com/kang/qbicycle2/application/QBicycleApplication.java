package com.kang.qbicycle2.application;

import org.holoeverywhere.HoloEverywhere;
import org.holoeverywhere.HoloEverywhere.PreferenceImpl;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.ThemeManager;
import org.holoeverywhere.app.Application;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.kang.qbicycle2.R;
import com.kang.qbicycle2.util.Utils;

public class QBicycleApplication extends Application {

	private static final String PACKAGE = QBicycleApplication.class
			.getPackage().getName();
	static {
		HoloEverywhere.DEBUG = true;
		HoloEverywhere.PREFERENCE_IMPL = PreferenceImpl.JSON;
		LayoutInflater.registerPackage(PACKAGE + ".widget");
		ThemeManager.setDefaultTheme(ThemeManager.LIGHT);
		ThemeManager
				.map(ThemeManager.LIGHT, R.style.Holo_QBicycle_Theme_Custom);
	}

	private static QBicycleApplication mInstance = null;
	public boolean m_bKeyRight = true;
	public BMapManager mBMapManager = null;
	private LocationClient mLocClient;
	public LocationData locData;

	public static QBicycleApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		initEngineManager(this);
		startLocation();
	}

	@Override
	public void onTerminate() {
		// 退出时销毁定位
		if (mLocClient != null)
			mLocClient.stop();
		super.onTerminate();
	}

	/**
	 * 开始定位
	 */
	private void startLocation() {
		mLocClient = new LocationClient(this);
		locData = new LocationData();
		mLocClient.registerLocationListener(new MyLocationListenner());
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(30000); // 30秒
		mLocClient.setLocOption(option);
		mLocClient.start();
	}

	public void initEngineManager(Context context) {
		if (mBMapManager == null) {
			mBMapManager = new BMapManager(context);
		}

		if (!mBMapManager.init(Utils.BAIDU_MAP_KEY, new MyGeneralListener())) {
			Utils.showToast(this, "BMapManager  初始化错误!");
		}
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	public static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Utils.showToast(QBicycleApplication.getInstance(), "您的网络出错啦！");
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Utils.showToast(QBicycleApplication.getInstance(), "输入正确的检索条件！");
			}
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Utils.showToast(QBicycleApplication.getInstance(),
						"请在 DemoApplication.java文件输入正确的授权Key！");
				QBicycleApplication.getInstance().m_bKeyRight = false;
			}
		}
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			locData.direction = location.getDerect();
			Log.i(PACKAGE, "latitude = " + locData.latitude + " longitude = "
					+ locData.longitude);
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

}
