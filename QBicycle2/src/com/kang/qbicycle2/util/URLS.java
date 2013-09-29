package com.kang.qbicycle2.util;

import java.io.File;

public class URLS {

	private static final String USERID = "WXHompe";
	private static final String PWD = "Wanzi@Hp123";

	// 用户借车还车时的临时用户名和密码
	public final static String TEMP_USERID = "Hompe";
	public final static String TEMP_PWD = "Hongpu@Hp456";

	public static String URLS = "61.160.125.14:7141";

	private static final String HTTP = "http://";
	private static final String YUNWEI = HTTP + URLS + File.separator
			+ "yunwei.asmx" + File.separator;

	/**
	 * 取得所有车站信息
	 */
	public static final String METHOD_GetStationBicycleNum = YUNWEI
			+ "GetStationBicycleNum?userid=" + USERID + "&pwd=" + PWD;

	/**
	 * 提交借车信息
	 */
	public static final String METHOD_UserRentBicycle = YUNWEI
			+ "UserRentBicycle?userid=" + TEMP_USERID + "&pwd=" + TEMP_PWD
			+ "&mobilephone=%s&stationid=%s&cwno=%s&bicycleid=";

	/**
	 * 提交还车信息
	 */
	public static final String METHOD_UserReturnBicycle = YUNWEI
			+ "UserReturnBicycle?userid=" + TEMP_USERID + "&pwd=" + TEMP_PWD
			+ "&mobilephone=%s&stationid=%s&bicycleid=%s&cwno=%s";

	/**
	 * 查询借车记录
	 */
	public static final String METHOD_GetUserRenReconds = YUNWEI
			+ "GetUserRenReconds?userid=%s&pwd=%s&keyword=%s";

	/**
	 * 验证用户
	 */
	public static final String METHOD_CheckUser = YUNWEI
			+ "CheckUser?userid=%s&password=%s";

	/**
	 * 取得报警信息
	 */
	public static final String METHOD_GetStationKneeAlarmInfo = YUNWEI
			+ "GetStationKneeAlarmInfo?UserGuid=%s";

	/**
	 * 取得派工信息
	 */
	public static final String METHOD_GetRepairForms = YUNWEI
			+ "GetRepairForms?UserGuid=%s";

	/**
	 * 取得站点心跳信息
	 */
	public static final String METHOD_GetHeartBeat = YUNWEI
			+ "GetHeartBeat?UserGuid=%s";

	/**
	 * 发送站点重启指令
	 */
	public static final String METHOD_DoStationRoot = YUNWEI
			+ "DoStationRoot?stationid=%s&userid=%s&UserGuid=%s";

	/**
	 * 发送站点解锁指令
	 */
	public static final String METHOD_DoStation_RemoteUnlock = YUNWEI
			+ "DoStation_RemoteUnlock?stationid=%s&CWno=%s&tel_mobi=%s&userid=%s&UserGuid=%s";

	/**
	 * 发送站点消息指令
	 */
	public static final String METHOD_DoStation_Message = YUNWEI
			+ "DoStation_Message?message=%s&available=%s&stationid=%s&userid=%s&UserGuid=%s";

}
