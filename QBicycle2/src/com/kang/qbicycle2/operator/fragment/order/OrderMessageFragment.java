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
import android.widget.RadioButton;

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

public class OrderMessageFragment extends BaseFragment {

	private static final int STATION_SCAN = 0x21;
	private EditText editStation;
	private ImageButton stationScan;
	private EditText editMessage;
	private Button updateButton;
	private RadioButton rbOpen;
	private RadioButton rbStop;
	private boolean isMsgStop;
	private String stationStr;
	private String messageStr;
	private SharedPreferences preferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_order_message);
		editStation = (EditText) view.findViewById(R.id.ET_MsgStation);
		stationScan = (ImageButton) view.findViewById(R.id.BT_msgStation_scan);
		editMessage = (EditText) view.findViewById(R.id.ET_Message);
		rbOpen = (RadioButton) view.findViewById(R.id.RB_open);
		rbStop = (RadioButton) view.findViewById(R.id.RB_stop);
		updateButton = (Button) view.findViewById(R.id.BT_Update);
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
				Utils.startCapterActivity(OrderMessageFragment.this,
						STATION_SCAN);
			}
		});
		updateButton.setOnClickListener(new OnClickListener() {

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
		stationStr = editStation.getText().toString();
		messageStr = editMessage.getText().toString();
		isMsgStop = rbStop.isChecked() ? true : false;
		if (stationStr.length() > 0 && messageStr.length() > 0) {
			return true;
		}
		Utils.showToast(getSupportActivity(), R.string.string_empty);
		return false;
	}

	@Override
	protected Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException {
		String url = String.format(URLS.METHOD_DoStation_Message, messageStr,
				isMsgStop, stationStr,
				preferences.getString(Utils.KEY_USER, null),
				preferences.getString(Utils.KEY_TOKEN, null));
		String response = HttpRequest.get(url).body();
		result = Result.parse(response);
		return Utils.OK;
	}

	@Override
	protected void clearEdit() {
		editStation.setText("");
		editMessage.setText("");
		rbOpen.setChecked(true);
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
		}
	}
}
