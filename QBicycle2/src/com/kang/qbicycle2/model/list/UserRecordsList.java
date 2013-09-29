package com.kang.qbicycle2.model.list;

import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.annotation.SuppressLint;

import com.kang.qbicycle2.model.UserRecords;

public class UserRecordsList {

	private static final String USER_RENRECONDS = "UserRenReconds";
	private static final String CARD_ID = "cardID";
	private static final String MEMBER_NAME = "memberName";
	private static final String BICYCLE_ID = "bicycleID";
	private static final String RENT_TIME = "rentTime";
	private static final String RENT_CWNO = "rentCWno";
	private static final String ADDRESS1 = "address1";
	private static final String RETURN_TIME = "ReturnTime";
	private static final String RETURN_CWNO = "ReturnCWno";
	private static final String ADDRESS2 = "address2";
	private static final String RENT_MI = "rentMI";
	private static final String COST_MONEY = "costmoney";
	private static SimpleDateFormat dateFormat;
	private static SimpleDateFormat dayFormat;
	private static SimpleDateFormat timeFormat;

	@SuppressLint("SimpleDateFormat")
	public static List<UserRecords> parse(String string)
			throws XmlPullParserException, IOException {

		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dayFormat = new SimpleDateFormat("MM/dd");
		timeFormat = new SimpleDateFormat("HH:mm");

		List<UserRecords> list = null;
		UserRecords records = null;

		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		xpp.setInput(new StringReader(string));
		int eventType = xpp.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			// 开始文档事件
			case XmlPullParser.START_DOCUMENT:
				list = new ArrayList<UserRecords>();
				break;
			// 开始标签
			case XmlPullParser.START_TAG:
				// 获取节点的名称
				String tag = xpp.getName();
				if (USER_RENRECONDS.equals(tag)) {
					records = new UserRecords();
					break;
				}
				if (null != records) {
					if (CARD_ID.equals(tag)) {
						// 获取下一个text类型的节点
						records.cardId = xpp.nextText();
						break;
					}
					if (MEMBER_NAME.equals(tag)) {
						records.memberName = xpp.nextText();
						break;
					}
					if (BICYCLE_ID.equals(tag)) {
						records.bicycleId = xpp.nextText();
						break;
					}
					if (RENT_TIME.equals(tag)) {
						records.rentTime = xpp.nextText();
						break;
					}
					if (RENT_CWNO.equals(tag)) {
						records.rentCwno = xpp.nextText();
						break;
					}
					if (ADDRESS1.equals(tag)) {
						records.address1 = xpp.nextText();
						break;
					}
					if (RETURN_TIME.equals(tag)) {
						records.returnTime = xpp.nextText();
						break;
					}
					if (RETURN_CWNO.equals(tag)) {
						records.returnCwno = xpp.nextText();
						break;
					}
					if (ADDRESS2.equals(tag)) {
						records.address2 = xpp.nextText();
						break;
					}
					if (RENT_MI.equals(tag)) {
						records.rentMI = xpp.nextText();
						break;
					}
					if (COST_MONEY.equals(tag)) {
						records.costMoney = xpp.nextText();
						break;
					}
				}
				break;
			// 结束标签
			case XmlPullParser.END_TAG:
				if (USER_RENRECONDS.equals(xpp.getName())) {

					records.date = getDate(records.rentTime);
					records.startTime = getTime(records.rentTime);
					records.endTime = getTime(records.returnTime);
					records.costTime = getCostTime(records.rentTime,
							records.returnTime);

					list.add(records);
					records = null;
				}
				break;
			}
			// 继续下一个元素
			eventType = xpp.next();
		}
		return list;
	}

	/**
	 * 取得记录日期
	 * 
	 * @param date
	 *            2013-09-12 12:00:30
	 * @return 09/12
	 */
	private static String getDate(String date) {
		try {
			return dayFormat.format(dateFormat.parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得时间
	 * 
	 * @param time
	 *            2013-09-12 12:00:30
	 * @return 12:00
	 */
	@SuppressLint("SimpleDateFormat")
	private static String getTime(String time) {
		try {
			return timeFormat.format(dateFormat.parse(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得花费时间
	 * 
	 * @param startTime
	 *            2013-09-12 12:00:30
	 * @param endTime
	 *            2013-09-12 12:30:30
	 * @return 30
	 */
	private static String getCostTime(String startTime, String endTime) {
		try {
			Date startDate = dateFormat.parse(startTime);
			Date endDate = dateFormat.parse(endTime);
			return String
					.valueOf((int)(endDate.getTime() - startDate.getTime()) / 1000 * 60);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
