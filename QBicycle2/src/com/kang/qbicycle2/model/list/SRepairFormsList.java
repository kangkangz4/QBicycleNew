package com.kang.qbicycle2.model.list;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.kang.qbicycle2.model.SRepairForms;

public class SRepairFormsList {

	private static final String FORMS = "SRepairForms";
	private static final String CALL_NO = "CallNo";
	private static final String ADATE = "ADate";
	private static final String ATIME = "ATime";
	private static final String TEL = "Tel";
	private static final String STATION_ID = "StationID";
	private static final String STATION = "Station";
	private static final String CWNO = "CwNo";
	private static final String BICYCLE_ID = "BicycleID";
	private static final String CONTENTS = "Contents";
	private static final String REMARK = "Remark";

	public static List<SRepairForms> parse(String s)
			throws XmlPullParserException, IOException {

		List<SRepairForms> list = null;
		SRepairForms form = null;

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		xpp.setInput(new StringReader(s));
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			// 开始文档事件
			case XmlPullParser.START_DOCUMENT:
				list = new ArrayList<SRepairForms>();
				break;
			// 开始标签
			case XmlPullParser.START_TAG:
				// 获取节点的名称
				String tag = xpp.getName();
				if (FORMS.equals(tag)) {
					form = new SRepairForms();
					break;
				}
				if (null != form) {
					if (CALL_NO.equals(tag)) {
						// 获取下一个text类型的节点
						form.CallNo = xpp.nextText();
						break;
					}
					if (ADATE.equals(tag)) {
						form.ADate = xpp.nextText();
						break;
					}
					if (ATIME.equals(tag)) {
						form.ATime = xpp.nextText();
						break;
					}
					if (TEL.equals(tag)) {
						form.Tel = xpp.nextText();
						break;
					}
					if (STATION_ID.equals(tag)) {
						form.StationID = xpp.nextText();
						break;
					}
					if (STATION.equals(tag)) {
						form.Station = xpp.nextText();
						break;
					}
					if (CWNO.equals(tag)) {
						form.CwNo = xpp.nextText();
						break;
					}
					if (BICYCLE_ID.equals(tag)) {
						form.BicycleID = xpp.nextText();
						break;
					}
					if (CONTENTS.equals(tag)) {
						form.Contents = xpp.nextText();
						break;
					}
					if (REMARK.equals(tag)) {
						form.Remark = xpp.nextText();
						break;
					}
				}
				break;
			// 结束标签
			case XmlPullParser.END_TAG:
				if (FORMS.equals(xpp.getName())) {
					list.add(form);
					form = null;
				}
				break;
			}
			// 继续下一个元素
			eventType = xpp.next();
		}
		return list;
	}
}
