package com.kang.qbicycle2.operator.fragment.order;

import java.io.IOException;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.preference.SharedPreferences;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
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

public class OrderOpenFragment extends BaseFragment {

	private static final int STATION_SCAN = 0x11;
	private static final int CWNO_SCAN = 0x12;

	private EditText editStation;
	private EditText editCwno;
	private EditText editPhone;
	private Button openButton;
	private ImageButton stationScan;
	private ImageButton cwnoScan;
	private String phoneStr;
	private String stationStr;
	private String cwnoStr;
	private SharedPreferences preferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_order_open);
		editPhone = (EditText) view.findViewById(R.id.ET_OpenPhone);
		editStation = (EditText) view.findViewById(R.id.ET_OpneStation);
		stationScan = (ImageButton) view.findViewById(R.id.BT_openStation_scan);
		editCwno = (EditText) view.findViewById(R.id.ET_OpenCwno);
		cwnoScan = (ImageButton) view.findViewById(R.id.BT_openCwno_scan);
		openButton = (Button) view.findViewById(R.id.BT_Open);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		preferences = getSharedPreferences(Utils.SHARE_PREFRENCES,
				Context.MODE_PRIVATE);
	}

	@Override
	protected void initListener() {
		stationScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startCapterActivity(OrderOpenFragment.this, STATION_SCAN);
			}
		});
		cwnoScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startCapterActivity(OrderOpenFragment.this, CWNO_SCAN);
			}
		});
		openButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (checkEdit()) {
					startUpdate();
				}
			}
		});
	}

	@Override
	protected boolean checkEdit() {
		phoneStr = editPhone.getText().toString();
		stationStr = editStation.getText().toString();
		cwnoStr = editCwno.getText().toString();
		if (phoneStr.length() > 0 && stationStr.length() > 0
				&& cwnoStr.length() > 0) {
			return true;
		}
		Utils.showToast(getSupportActivity(), R.string.string_empty);
		return false;
	}

	@Override
	protected Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException {
		String url = String.format(URLS.METHOD_DoStation_RemoteUnlock,
				stationStr, cwnoStr, phoneStr,
				preferences.getString(Utils.KEY_USER, null),
				preferences.getString(Utils.KEY_TOKEN, null));
		String response = HttpRequest.get(url).body();
		result = Result.parse(response);
		return Utils.OK;
	}

	@Override
	protected void clearEdit() {
		editPhone.setText("");
		editStation.setText("");
		editCwno.setText("");
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
			editCwno.setText(event.getScanString());
		}
	}
}
