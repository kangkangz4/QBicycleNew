package com.kang.qbicycle2.operator.fragment;

import java.io.IOException;
import java.util.List;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.preference.SharedPreferences;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kang.qbicycle2.R;
import com.kang.qbicycle2.adapter.CustomListAdapter;
import com.kang.qbicycle2.fragment.BaseAdapterFragment;
import com.kang.qbicycle2.model.ArHeartBeat;
import com.kang.qbicycle2.model.list.ArHeartBeatList;
import com.kang.qbicycle2.util.URLS;
import com.kang.qbicycle2.util.Utils;

public class InfoFragment extends BaseAdapterFragment<ArHeartBeat> {

	private SharedPreferences preferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_alarm);
		pullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_to_refresh_listview);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		preferences = getSharedPreferences(Utils.SHARE_PREFRENCES,
				Context.MODE_PRIVATE);
		startUpdate();
	}

	@Override
	protected BaseAdapter getAdapter(Context context) {
		return new CustomListAdapter<ArHeartBeat>(lists) {

			private ArHeartBeat beat;

			@Override
			public View view(int arg0, View converview, ViewGroup arg2) {
				ListItemView itemView;
				if (converview == null) {
					converview = LayoutInflater.inflate(getSupportActivity(),
							R.layout.info_list_item);
					itemView = new ListItemView();
					itemView.textStationView = (TextView) converview
							.findViewById(R.id.StationName);
					itemView.textBicyleNumView = (TextView) converview
							.findViewById(R.id.bicyleNum);
					itemView.textVoltView = (TextView) converview
							.findViewById(R.id.volt);
					itemView.textTemperatureView = (TextView) converview
							.findViewById(R.id.temperature);
					itemView.textBicycleNumAlertTimeView = (TextView) converview
							.findViewById(R.id.bicycleNumAlertTime);
					itemView.textAlarmTime_VoltView = (TextView) converview
							.findViewById(R.id.alarmTime_Volt);
					itemView.textAlertReasonView = (TextView) converview
							.findViewById(R.id.alertReason);
					converview.setTag(itemView);
				} else {
					itemView = (ListItemView) converview.getTag();
				}
				beat = (ArHeartBeat) getItem(arg0);
				itemView.textStationView.setText(beat.station);
				itemView.textBicyleNumView.setText(beat.bicycleNum);
				itemView.textVoltView.setText(beat.volt);
				itemView.textTemperatureView.setText(beat.temperature);
				itemView.textBicycleNumAlertTimeView
						.setText(beat.bicycleNumAlertTime);
				itemView.textAlarmTime_VoltView.setText(beat.alarmTime_Volt);
				itemView.textAlertReasonView.setText(beat.alertReason);

				return converview;
			}
		};
	}

	static class ListItemView {
		TextView textStationView;
		TextView textBicyleNumView;
		TextView textVoltView;
		TextView textTemperatureView;
		TextView textBicycleNumAlertTimeView;
		TextView textAlarmTime_VoltView;
		TextView textAlertReasonView;
	}

	@Override
	protected boolean checkEdit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException {
		String urls = String.format(URLS.METHOD_GetHeartBeat,
				preferences.getString(Utils.KEY_TOKEN, null));
		String response = HttpRequest.get(urls).body();
		lists.clear();
		List<ArHeartBeat> mList = ArHeartBeatList.parse(response);
		if (mList.size() == 0) {
			return Utils.NO_DATA;
		}
		lists.addAll(mList);
		return Utils.OK;
	}

	@Override
	protected void clearEdit() {
		// TODO Auto-generated method stub

	}

}
