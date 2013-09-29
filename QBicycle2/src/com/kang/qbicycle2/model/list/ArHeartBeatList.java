package com.kang.qbicycle2.model.list;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.kang.qbicycle2.model.ArHeartBeat;

public class ArHeartBeatList {

	private static final String BEAT = "ArHeartBeat";
	private static final String STATION_ID = "stationID";
	private static final String STATION = "station";
	private static final String VOLT = "volt";
	private static final String TEMPERATURE = "temperature";
	private static final String BICYCLE_NUM = "bicycleNum";
	private static final String ALERT_REASON = "alertReason";
	private static final String UPDATE_TIME = "updateTime";
	private static final String BICYCLENUM_ALERTTIME = "bicycleNumAlertTime";
	private static final String ALARMTIME_DISPATCH = "alarmTime_Dispatch";
	private static final String ALARMTIME_VOLT = "alarmTime_Volt";
	private static final String ALARMTIME_TEMPERATURE = "alarmTime_Temperature";

	public static List<ArHeartBeat> parse(String s)
			throws XmlPullParserException, IOException {

		List<ArHeartBeat> list = null;
		ArHeartBeat beat = null;

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		xpp.setInput(new StringReader(s));
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			// 开始文档事件
			case XmlPullParser.START_DOCUMENT:
				list = new ArrayList<ArHeartBeat>();
				break;
			// 开始标签
			case XmlPullParser.START_TAG:
				// 获取节点的名称
				String tag = xpp.getName();
				if (BEAT.equals(tag)) {
					beat = new ArHeartBeat();
					break;
				}
				if (null != beat) {
					if (STATION_ID.equals(tag)) {
						// 获取下一个text类型的节点
						beat.stationID = xpp.nextText();
						break;
					}
					if (STATION.equals(tag)) {
						beat.station = xpp.nextText();
						break;
					}
					if (VOLT.equals(tag)) {
						beat.volt = xpp.nextText();
						break;
					}
					if (TEMPERATURE.equals(tag)) {
						beat.temperature = xpp.nextText();
						break;
					}
					if (BICYCLE_NUM.equals(tag)) {
						beat.bicycleNum = xpp.nextText();
						break;
					}
					if (ALERT_REASON.equals(tag)) {
						beat.alertReason = xpp.nextText();
						break;
					}
					if (UPDATE_TIME.equals(tag)) {
						beat.updateTime = xpp.nextText();
						break;
					}
					if (BICYCLENUM_ALERTTIME.equals(tag)) {
						beat.bicycleNumAlertTime = xpp.nextText();
						break;
					}
					if (ALARMTIME_DISPATCH.equals(tag)) {
						beat.alarmTime_Dispatch = xpp.nextText();
						break;
					}
					if (ALARMTIME_VOLT.equals(tag)) {
						beat.alarmTime_Volt = xpp.nextText();
						break;
					}
					if (ALARMTIME_TEMPERATURE.equals(tag)) {
						beat.alarmTime_Temperature = xpp.nextText();
						break;
					}
				}
				break;
			// 结束标签
			case XmlPullParser.END_TAG:
				if (BEAT.equals(xpp.getName())) {
					list.add(beat);
					beat = null;
				}
				break;
			}
			// 继续下一个元素
			eventType = xpp.next();
		}
		return list;
	}
}
