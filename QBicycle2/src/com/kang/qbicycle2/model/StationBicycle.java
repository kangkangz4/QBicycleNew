package com.kang.qbicycle2.model;


/**
 * 车站车辆信息和坐标
 * 
 * @author kangkangz4
 * @version 1.0
 * 
 */
public class StationBicycle {
	public String station;
	public String stationID;
	public String currentNum;
	public String totalNum;
	public double stationX;
	public double stationY;
	public String address;

	@Override
	public String toString() {
		return "StationBicycle [station=" + station + ", stationID="
				+ stationID + ", currentNum=" + currentNum + ", totalNum="
				+ totalNum + ", stationX=" + stationX + ", stationY="
				+ stationY + ", address=" + address + "]";
	}

}
