package com.kang.qbicycle2.model;

import java.io.IOException;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class Result {

	/**
	 * <string xmlns="http://tempuri.org/">信息发送失败，请重试...</string>
	 */
	private static final String TAG = "string";

	public static String parse(String s) throws XmlPullParserException,
			IOException {
		String string = null;
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		xpp.setInput(new StringReader(s));
		int eventType = xpp.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			// 开始文档事件
			case XmlPullParser.START_DOCUMENT:
				break;
			// 开始标签
			case XmlPullParser.START_TAG:
				// 获取节点的名称
				String tag = xpp.getName();
				if (TAG.equals(tag)) {
					string = xpp.nextText();
					break;
				}
				break;
			// 结束标签
			case XmlPullParser.END_TAG:
				break;
			}
			// 继续下一个元素
			eventType = xpp.next();
		}
		return string;
	}

}
