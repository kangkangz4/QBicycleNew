package com.kang.qbicycle2.model.list;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.kang.qbicycle2.model.StationBicycle;

public class StationBicycleList {

	private static final String INFO = "StationBicycleInfo";
	private static final String STATION = "station";
	private static final String STATION_ID = "stationID";
	private static final String CURRENT_NUM = "currentNum";
	private static final String TOTAL_NUM = "totalNum";
	private static final String STATION_X = "stationX";
	private static final String STATION_Y = "stationY";
	private static final String ADDRESS = "address";

	public static List<StationBicycle> parse(String s)
			throws XmlPullParserException, IOException {

		List<StationBicycle> list = null;
		StationBicycle bicycle = null;

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		xpp.setInput(new StringReader(s));
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			// 开始文档事件
			case XmlPullParser.START_DOCUMENT:
				list = new ArrayList<StationBicycle>();
				break;
			// 开始标签
			case XmlPullParser.START_TAG:
				// 获取节点的名称
				String tag = xpp.getName();
				if (INFO.equals(tag)) {
					bicycle = new StationBicycle();
					break;
				}
				if (null != bicycle) {
					if (STATION.equals(tag)) {
						// 获取下一个text类型的节点
						bicycle.station = xpp.nextText();
						break;
					}
					if (STATION_ID.equals(tag)) {
						bicycle.stationID = xpp.nextText();
						break;
					}
					if (CURRENT_NUM.equals(tag)) {
						bicycle.currentNum = xpp.nextText();
						break;
					}
					if (TOTAL_NUM.equals(tag)) {
						bicycle.totalNum = xpp.nextText();
						break;
					}
					if (STATION_X.equals(tag)) {
						bicycle.stationX = Double.parseDouble(xpp.nextText());
						break;
					}
					if (STATION_Y.equals(tag)) {
						bicycle.stationY = Double.parseDouble(xpp.nextText());
						break;
					}
					if (ADDRESS.equals(tag)) {
						bicycle.address = xpp.nextText();
						break;
					}
				}
				break;
			// 结束标签
			case XmlPullParser.END_TAG:
				if (INFO.equals(xpp.getName())) {
					list.add(bicycle);
					bicycle = null;
				}
				break;
			}
			// 继续下一个元素
			eventType = xpp.next();
		}
		return list;
	}

}
