package com.kang.qbicycle2.model;

import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class MyOverlayItem extends OverlayItem {

	private StationBicycle bicycle;

	public StationBicycle getBicycle() {
		return bicycle;
	}

	public void setBicycle(StationBicycle bicycle) {
		this.bicycle = bicycle;
	}

	public MyOverlayItem(GeoPoint arg0, String arg1, String arg2) {
		super(arg0, arg1, arg2);
	}

	public MyOverlayItem(GeoPoint arg0, String arg1, String arg2,
			StationBicycle bicycle) {
		super(arg0, arg1, arg2);
		this.bicycle = bicycle;
	}

}
