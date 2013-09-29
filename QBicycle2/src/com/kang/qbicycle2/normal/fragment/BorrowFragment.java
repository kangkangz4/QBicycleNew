package com.kang.qbicycle2.normal.fragment;

import java.io.IOException;

import org.holoeverywhere.LayoutInflater;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.kang.qbicycle2.R;
import com.kang.qbicycle2.event.BusProvider;
import com.kang.qbicycle2.event.ScanEvent;
import com.kang.qbicycle2.fragment.BaseFragment;
import com.kang.qbicycle2.model.Result;
import com.kang.qbicycle2.util.URLS;
import com.kang.qbicycle2.util.Utils;
import com.squareup.otto.Subscribe;

public class BorrowFragment extends BaseFragment {

	private static final int STATION_SCAN = 0x01;
	private static final int CWNO_SCAN = 0x02;

	private ImageButton scanStation;
	private ImageButton scanCwNo;
	private EditText editStation;
	private EditText editCwNo;
	private Button btBrower;
	private EditText editMobile;
	private String mobileStr;
	private String stationcStr;
	private String cwnoStr;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_bicycle_borrow);
		editMobile = (EditText) view.findViewById(R.id.ET_Mobile);
		editStation = (EditText) view.findViewById(R.id.ET_Station);
		scanStation = (ImageButton) view.findViewById(R.id.BT_station_scan);
		editCwNo = (EditText) view.findViewById(R.id.ET_Cwno);
		scanCwNo = (ImageButton) view.findViewById(R.id.BT_cwno_scan);
		btBrower = (Button) view.findViewById(R.id.BT_Brower);
		return view;
	}

	@Override
	protected void initListener() {
		scanStation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Utils.startCapterActivity(BorrowFragment.this, STATION_SCAN);
			}
		});
		scanCwNo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startCapterActivity(BorrowFragment.this, CWNO_SCAN);
			}
		});
		btBrower.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (checkEdit()) {
					startUpdate();
				}
			}
		});
	}

	/**
	 * 检查输入框内容
	 * 
	 * @return
	 */
	protected boolean checkEdit() {
		mobileStr = editMobile.getText().toString();
		stationcStr = editStation.getText().toString();
		cwnoStr = editCwNo.getText().toString();
		if (mobileStr.length() > 0 && stationcStr.length() > 0
				&& cwnoStr.length() > 0) {
			return true;
		}
		Utils.showToast(getSupportActivity(), R.string.string_empty);
		return false;
	}

	/**
	 * 清除输入框中内容
	 */
	@Override
	protected void clearEdit() {
		editMobile.setText("");
		editStation.setText("");
		editCwNo.setText("");
	}

	@Override
	protected Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException {
		String urls = String.format(URLS.METHOD_UserRentBicycle, mobileStr,
				stationcStr, cwnoStr);
		String response = HttpRequest.get(urls).body();
		result = Result.parse(response);
		return Utils.OK;
	}

	@Override
	public void onResume() {
		super.onResume();
		BusProvider.getInstance().register(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		BusProvider.getInstance().unregister(this);
	}

	@Subscribe
	public void onScanResult(ScanEvent event) {
		if (STATION_SCAN == event.getRequestCode()) {
			editStation.setText(event.getScanString());
		} else if (CWNO_SCAN == event.getRequestCode()) {
			editCwNo.setText(event.getScanString());
		}
	}

}
