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
import com.kang.qbicycle2.model.SRepairForms;
import com.kang.qbicycle2.model.list.SRepairFormsList;
import com.kang.qbicycle2.util.URLS;
import com.kang.qbicycle2.util.Utils;

public class FormFragment extends BaseAdapterFragment<SRepairForms> {

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
		return new CustomListAdapter<SRepairForms>(lists) {

			private SRepairForms form;

			@Override
			public View view(int arg0, View converview, ViewGroup arg2) {
				ListItemView itemView;
				if (converview == null) {
					converview = LayoutInflater.inflate(getSupportActivity(),
							R.layout.repairforms_list_item);
					itemView = new ListItemView();
					itemView.textstationView = (TextView) converview
							.findViewById(R.id.stationTextView);
					itemView.textcwnoView = (TextView) converview
							.findViewById(R.id.cwnoTextView);
					itemView.textbicycleIdView = (TextView) converview
							.findViewById(R.id.bicycleIdTextView);
					itemView.textcontentsView = (TextView) converview
							.findViewById(R.id.contentsTextView);
					converview.setTag(itemView);
				} else {
					itemView = (ListItemView) converview.getTag();
				}
				form = (SRepairForms) getItem(arg0);
				itemView.textstationView.setText(form.Station);
				itemView.textcwnoView.setText(form.CwNo);
				itemView.textbicycleIdView.setText(form.BicycleID);
				itemView.textcontentsView.setText(form.Contents);
				return converview;
			}
		};
	}

	static class ListItemView {
		TextView textstationView;
		TextView textcwnoView;
		TextView textbicycleIdView;
		TextView textcontentsView;
	}

	@Override
	protected boolean checkEdit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException {
		String urls = String.format(URLS.METHOD_GetRepairForms,
				preferences.getString(Utils.KEY_TOKEN, null));
		String response = HttpRequest.get(urls).body();
		lists.clear();
		List<SRepairForms> mList = SRepairFormsList.parse(response);
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
