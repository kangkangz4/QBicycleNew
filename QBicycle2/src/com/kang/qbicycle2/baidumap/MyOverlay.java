package com.kang.qbicycle2.baidumap;

import org.holoeverywhere.widget.TextView;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapView.LayoutParams;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.kang.qbicycle2.R;
import com.kang.qbicycle2.model.MyOverlayItem;

public class MyOverlay extends ItemizedOverlay<MyOverlayItem> {

	private View popView;
	private TextView titleView;
	private TextView currentView;
	private TextView totalView;
	private MyOverlayItem item;
	private LayoutParams layoutParam;

	public MyOverlay(Drawable arg0, MapView arg1, View popView) {
		super(arg0, arg1);
		this.popView = popView;
		initPopview(popView);
	}

	private void initPopview(View popView) {
		titleView = (TextView) popView.findViewById(R.id.info_windows_title);
		currentView = (TextView) popView.findViewById(R.id.snippet_last);
		totalView = (TextView) popView.findViewById(R.id.snippet_total);
	}

	@Override
	public boolean onTap(GeoPoint arg0, MapView arg1) {
		mMapView.removeView(popView);
		return false;
	}

	@Override
	protected boolean onTap(int arg0) {
		item = (MyOverlayItem) getItem(arg0);
		titleView.setText(item.getBicycle().station);
		totalView.setText(item.getBicycle().totalNum);
		currentView.setText(item.getBicycle().currentNum);
		// 创建布局参数
		layoutParam = new MapView.LayoutParams(
		// 控件宽,继承自ViewGroup.LayoutParams
				MapView.LayoutParams.WRAP_CONTENT,
				// 控件高,继承自ViewGroup.LayoutParams
				MapView.LayoutParams.WRAP_CONTENT,
				// 使控件固定在某个地理位置
				item.getPoint(), 0, -32,
				// 控件对齐方式
				MapView.LayoutParams.BOTTOM_CENTER);
		if(popView.isShown()){
			mMapView.removeView(popView);
		}
		// 添加View到MapView中
		mMapView.addView(popView, layoutParam);
		return true;
	}

}
