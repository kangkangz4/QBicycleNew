package com.kang.qbicycle2.model.list;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.kang.qbicycle2.model.BugStationInfo;

public class BugStationInfoList {

	private static final String INFO = "BugStationInfo";
	private static final String STATION_ID = "stationID";
	private static final String ADDRESS = "address";
	private static final String ERROR_COUNT = "errorcount";
	private static final String STATION_X = "stationX";
	private static final String STATION_Y = "stationY";

	public static List<BugStationInfo> parse(String s)
			throws XmlPullParserException, IOException {

		List<BugStationInfo> list = null;
		BugStationInfo info = null;

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		xpp.setInput(new StringReader(s));
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			// 开始文档事件
			case XmlPullParser.START_DOCUMENT:
				list = new ArrayList<BugStationInfo>();
				break;
			// 开始标签
			case XmlPullParser.START_TAG:
				// 获取节点的名称
				String tag = xpp.getName();
				if (INFO.equals(tag)) {
					info = new BugStationInfo();
					break;
				}
				if (null != info) {
					if (STATION_ID.equals(tag)) {
						// 获取下一个text类型的节点
						info.stationId = xpp.nextText();
						break;
					}
					if (ADDRESS.equals(tag)) {
						info.address = xpp.nextText();
						break;
					}
					if (ERROR_COUNT.equals(tag)) {
						info.errorCount = xpp.nextText();
						break;
					}
					if (STATION_X.equals(tag)) {
						info.stationX = Double.parseDouble(xpp.nextText());
						break;
					}
					if (STATION_Y.equals(tag)) {
						info.stationY = Double.parseDouble(xpp.nextText());
						break;
					}
				}
				break;
			// 结束标签
			case XmlPullParser.END_TAG:
				if (INFO.equals(xpp.getName())) {
					list.add(info);
					info = null;
				}
				break;
			}
			// 继续下一个元素
			eventType = xpp.next();
		}
		return list;
	}
}
