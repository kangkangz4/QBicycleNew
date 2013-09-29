package com.kang.qbicycle2.normal.fragment;

import java.io.IOException;

import org.holoeverywhere.LayoutInflater;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;
import android.util.Log;
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

public class ReturnFragment extends BaseFragment {

	private static final String TAG = ReturnFragment.class.getName();

	private static final int STATION_SCAN = 0x11;
	private static final int CWNO_SCAN = 0x12;
	private static final int BICYCLE_SCAN = 0x13;

	private EditText editMobile;
	private EditText editStation;
	private EditText editCwno;
	private EditText editBicycle;
	private ImageButton scanStation;
	private ImageButton scanCwno;
	private ImageButton scanBicycle;
	private Button btReturn;
	private String mobileStr;
	private String stationStr;
	private String cwnoStr;
	private String bicycleStr;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_bicycle_return);
		editMobile = (EditText) view.findViewById(R.id.ET_RMobile);
		editStation = (EditText) view.findViewById(R.id.ET_RStation);
		scanStation = (ImageButton) view.findViewById(R.id.BT_Rstation_scan);
		editCwno = (EditText) view.findViewById(R.id.ET_RCwno);
		scanCwno = (ImageButton) view.findViewById(R.id.BT_Rcwno_scan);
		editBicycle = (EditText) view.findViewById(R.id.ET_RBicycleNo);
		scanBicycle = (ImageButton) view.findViewById(R.id.BT_Rbicycleno_scan);
		btReturn = (Button) view.findViewById(R.id.BT_Return);
		return view;
	}

	@Override
	protected void initListener() {
		scanStation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startCapterActivity(ReturnFragment.this, STATION_SCAN);
			}
		});
		scanCwno.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startCapterActivity(ReturnFragment.this, CWNO_SCAN);
			}
		});
		scanBicycle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startCapterActivity(ReturnFragment.this, BICYCLE_SCAN);
			}
		});
		btReturn.setOnClickListener(new OnClickListener() {

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
		mobileStr = editMobile.getText().toString();
		stationStr = editStation.getText().toString();
		cwnoStr = editCwno.getText().toString();
		bicycleStr = editBicycle.getText().toString();
		if (mobileStr.length() > 0 && stationStr.length() > 0
				&& cwnoStr.length() > 0 && bicycleStr.length() > 0) {
			return true;
		}
		Utils.showToast(getSupportActivity(), R.string.string_empty);
		return false;
	}

	@Override
	protected Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException {
		String urls = String.format(URLS.METHOD_UserReturnBicycle, mobileStr,
				stationStr, bicycleStr, cwnoStr);
		String response = HttpRequest.get(urls).body();
		Log.i(TAG, response);
		result = Result.parse(response);
		return Utils.OK;
	}

	@Override
	protected void clearEdit() {
		editMobile.setText("");
		editStation.setText("");
		editCwno.setText("");
		editBicycle.setText("");
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
		} else if (BICYCLE_SCAN == event.getRequestCode()) {
			editBicycle.setText(event.getScanString());
		}
	}

}
