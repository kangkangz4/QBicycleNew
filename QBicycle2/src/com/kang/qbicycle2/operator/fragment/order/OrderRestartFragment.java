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

public class OrderRestartFragment extends BaseFragment {

	// private final SparseIntArray mRequestCodes = new SparseIntArray();

	private static final int STATION_SCAN = 0x01;

	private Button restartButton;
	private ImageButton stationScan;
	private String stationStr;
	private SharedPreferences preferences;
	private EditText editRestart;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_order_restart);
		editRestart = (EditText) view.findViewById(R.id.ET_RestartStation);
		stationScan = (ImageButton) view.findViewById(R.id.BT_restart_scan);
		restartButton = (Button) view.findViewById(R.id.BT_Restart);
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
				Utils.startCapterActivity(OrderRestartFragment.this,
						STATION_SCAN);
			}
		});
		restartButton.setOnClickListener(new OnClickListener() {

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
		stationStr = editRestart.getText().toString();
		if (stationStr.length() > 0) {
			return true;
		}
		Utils.showToast(getSupportActivity(), R.string.string_empty);
		return false;
	}

	@Override
	protected Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException {
		String url = String.format(URLS.METHOD_DoStationRoot, stationStr,
				preferences.getString(Utils.KEY_USER, null),
				preferences.getString(Utils.KEY_TOKEN, null));
		String response = HttpRequest.get(url).body();
		result = Result.parse(response);
		return Utils.OK;
	}

	@Override
	protected void clearEdit() {
		editRestart.setText("");
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
			editRestart.setText(event.getScanString());
		}
	}

}
