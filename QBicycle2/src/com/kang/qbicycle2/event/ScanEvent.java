package com.kang.qbicycle2.event;

public class ScanEvent {

	private int requestCode;
	private String scanString;

	public ScanEvent(int requestCode, String s) {
		this.requestCode = requestCode;
		this.scanString = s;
	}

	public String getScanString() {
		return scanString;
	}

	public int getRequestCode() {
		return requestCode;
	}

	@Override
	public String toString() {
		return "ScanEvent [requestCode=" + requestCode + ", scanString="
				+ scanString + "]";
	}

}
