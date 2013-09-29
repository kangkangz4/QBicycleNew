package com.kang.qbicycle2.baidumap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.DialogFragment;
import org.xmlpull.v1.XmlPullParserException;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.kang.qbicycle2.R;
import com.kang.qbicycle2.application.QBicycleApplication;
import com.kang.qbicycle2.model.MyOverlayItem;
import com.kang.qbicycle2.model.StationBicycle;
import com.kang.qbicycle2.model.list.StationBicycleList;
import com.kang.qbicycle2.util.Ln;
import com.kang.qbicycle2.util.SafeAsyncTask;
import com.kang.qbicycle2.util.URLS;
import com.kang.qbicycle2.util.Utils;

public class BaiduMapActivity extends Activity {

	private static final double LATITUDE = 31.496643;
	private static final double LONGITUDE = 120.384614;

	private LocationData locData;
	// 定位图层
	private MyLocationOverlay myLocationOverlay;
	private MapView mMapView;
	private MapController mMapController;
	private MyOverlay mOverlay;
	private View popview;
	private List<MyOverlayItem> mItems;

	private Boolean canSearch = false;
	private AlertDialogListFragment fragment;
	private OverlayItemAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		Utils.setActionBar(this);
		initBMapManger();
		setContentView(R.layout.activity_baidumap);
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		initLocationData(mMapView);
		initMapController(mMapView);
		// 修改定位数据后刷新图层生效
		mMapView.refresh();

		startAsync();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (canSearch) {
			menu.add(R.string.search)
					.setIcon(R.drawable.ic_search_inverse)
					.setShowAsAction(
							MenuItem.SHOW_AS_ACTION_IF_ROOM
									| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		fragment.show(this);
		return true;
	}

	/**
	 * 开始异步取数据
	 */
	private void startAsync() {
		setSupportProgressBarIndeterminateVisibility(true);
		new SafeAsyncTask<Integer>() {

			@Override
			public Integer call() throws Exception {
				return StationOverLay();
			}

			@Override
			protected void onSuccess(Integer tag) throws Exception {
				super.onSuccess(tag);
				setSupportProgressBarIndeterminateVisibility(false);
				resultHandler(tag);
			}
		}.execute();
	}

	/**
	 * 根据返回结果进行相应处理
	 * @param tag
	 */
	protected void resultHandler(Integer tag) {
		switch (tag) {
		// 提示网络异常
		case Utils.HTTP_ERROR:
			Utils.showToast(getApplicationContext(),
					R.string.http_exception_error);
			break;
		// 解析异常
		case Utils.XMLPARSER_ERROR:
			Utils.showToast(getApplicationContext(),
					R.string.xml_parser_failed);
			break;
		case Utils.OK:
			refreshMap();
			canSearch = true;
			supportInvalidateOptionsMenu();
			initAlertDialog();
			break;
		}
	}

	/**
	 * 初始化列表弹出框
	 */
	private void initAlertDialog() {
		fragment = DialogFragment.instantiate(AlertDialogListFragment.class);
		adapter = new OverlayItemAdapter(this, mItems);
		fragment.setAdapter(adapter);
		fragment.setListener(new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				MyOverlayItem item = (MyOverlayItem) adapter.getItem(which);
				mMapController.animateTo(item.getPoint());
				mOverlay.onTap(which);
			}
		});
	}

	/**
	 * 地图站点层
	 * 
	 * @return
	 */
	private int StationOverLay() {
		try {
			String response = HttpRequest.get(URLS.METHOD_GetStationBicycleNum)
					.body();
			List<StationBicycle> list = StationBicycleList.parse(response);

			initOverlay(list);
			return Utils.OK;
		} catch (HttpRequestException e) {
			Ln.e(e);
			return Utils.HTTP_ERROR;
		} catch (XmlPullParserException e) {
			Ln.e(e);
			return Utils.XMLPARSER_ERROR;
		} catch (IOException e) {
			Ln.e(e);
			return Utils.XMLPARSER_ERROR;
		}
	}

	/**
	 * 更新地图
	 */
	protected void refreshMap() {
		if (mOverlay != null) {
			/**
			 * 将overlay 添加至MapView中
			 */
			mMapView.getOverlays().add(mOverlay);
			/**
			 * 刷新地图
			 */
			mMapView.refresh();
		}
	}

	/**
	 * 初始化自定义图标层
	 * 
	 * @param list
	 */
	private void initOverlay(List<StationBicycle> list) {

		popview = getLayoutInflater().inflate(R.layout.popup_info_window);
		/**
		 * 创建自定义overlay
		 */
		mOverlay = new MyOverlay(getResources().getDrawable(
				R.drawable.icon_marka), mMapView, popview);

		Drawable marker = getResources().getDrawable(R.drawable.icon_marka);
		MyOverlayItem item = null;
		GeoPoint point = null;
		mItems = new ArrayList<MyOverlayItem>();
		for (StationBicycle stationBicycle : list) {
			point = new GeoPoint((int) (stationBicycle.stationY * 1E6),
					(int) (stationBicycle.stationX * 1E6));
			item = new MyOverlayItem(point, "", "", stationBicycle);
			item.setMarker(marker);
			mItems.add(item);
			mOverlay.addItem(item);
		}
	}

	/**
	 * 初始化管理器
	 */
	private void initBMapManger() {
		/**
		 * 使用地图sdk前需先初始化BMapManager. BMapManager是全局的，可为多个MapView共用，它需要地图模块创建前创建，
		 * 并在地图地图模块销毁后销毁，只要还有地图模块在使用，BMapManager就不应该销毁
		 */
		QBicycleApplication app = (QBicycleApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			/**
			 * 如果BMapManager没有初始化则初始化BMapManager
			 */
			app.mBMapManager.init(Utils.BAIDU_MAP_KEY,
					new QBicycleApplication.MyGeneralListener());
		}
	}

	/**
	 * 初始化控制器
	 * 
	 * @param mapView
	 */
	private void initMapController(MapView mapView) {
		/**
		 * 获取地图控制器
		 */
		mMapController = mapView.getController();
		/**
		 * 设置地图是否响应点击事件 .
		 */
		mMapController.enableClick(true);
		/**
		 * 设置地图缩放级别
		 */
		mMapController.setZoom(14);
		/**
		 * 设置中心点
		 */
		GeoPoint point = new GeoPoint((int) (locData.latitude * 1E6),
				(int) (locData.longitude * 1E6));
		mMapController.setCenter(point);
	}

	/**
	 * 初始化定位数据
	 */
	private void initLocationData(MapView mapView) {
		locData = QBicycleApplication.getInstance().locData;
		if (locData != null) {
			initLocationOverlay(mapView);
		} else {
			locData = new LocationData();
			locData.longitude = LONGITUDE;
			locData.latitude = LATITUDE;
		}
	}

	/**
	 * 初始化定位图层
	 * 
	 * @param mapView
	 */
	private void initLocationOverlay(MapView mapView) {
		// 定位图层初始化
		myLocationOverlay = new MyLocationOverlay(mapView);
		// 设置定位数据
		myLocationOverlay.setData(locData);
		// 设置默认图标
		myLocationOverlay.setMarker(null);
		// 添加定位图层
		mapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
	}

	/**
	 * List列表中的单击事件
	 */
	public void doListClick() {

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Utils.AnimLeftInRightOut(this);
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

}
