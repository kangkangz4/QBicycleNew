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
import com.kang.qbicycle2.model.BugStationInfo;
import com.kang.qbicycle2.model.list.BugStationInfoList;
import com.kang.qbicycle2.util.URLS;
import com.kang.qbicycle2.util.Utils;

public class AlarmFragment extends BaseAdapterFragment<BugStationInfo> {

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
		preferences = getSharedPreferences(Utils.SHARE_PREFRENCES, Context.MODE_PRIVATE);
		startUpdate();
	}

	@Override
	protected BaseAdapter getAdapter(Context context) {
		return new CustomListAdapter<BugStationInfo>(lists) {

			private BugStationInfo info;

			@Override
			public View view(int arg0, View converview, ViewGroup arg2) {
				ListItemView item = null;
				if (converview == null) {
					converview = LayoutInflater.from(getSupportActivity())
							.inflate(R.layout.alarm_list_item);
					item = new ListItemView();
					item.textAlarmView = (TextView) converview
							.findViewById(R.id.alarm_item);
					item.textCountView = (TextView) converview
							.findViewById(R.id.warning);
					converview.setTag(item);
				} else {
					item = (ListItemView) converview.getTag();
				}
				info = (BugStationInfo) getItem(arg0);
				item.textAlarmView.setText(info.address);
				item.textCountView.setText(info.errorCount);
				return converview;
			}
		};
	}

	static class ListItemView {
		TextView textAlarmView;
		TextView textCountView;
	}

	@Override
	protected boolean checkEdit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException {
		String urls = String.format(URLS.METHOD_GetStationKneeAlarmInfo,
				preferences.getString(Utils.KEY_TOKEN, null));
		String response = HttpRequest.get(urls).body();
		lists.clear();
		List<BugStationInfo> mList = BugStationInfoList.parse(response);
		if(mList.size() == 0){
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
