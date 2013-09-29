package com.kang.qbicycle2.normal.fragment;

import java.io.IOException;
import java.util.List;

import org.holoeverywhere.LayoutInflater;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kang.qbicycle2.R;
import com.kang.qbicycle2.adapter.CustomListAdapter;
import com.kang.qbicycle2.fragment.BaseAdapterFragment;
import com.kang.qbicycle2.model.UserRecords;
import com.kang.qbicycle2.model.list.UserRecordsList;
import com.kang.qbicycle2.util.URLS;
import com.kang.qbicycle2.util.Utils;

public class ResultFragment extends BaseAdapterFragment<UserRecords> {

	private EditText searchView;
	private Button searchButton;
	private String searchStr;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_userrenreconds);
		pullToRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_to_refresh_listview);
		searchView = (EditText) view.findViewById(R.id.ET_Search);
		searchButton = (Button) view.findViewById(R.id.BT_Search);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		pullToRefreshListView.setVisibility(View.GONE);
	}

	/**
	 * 设定适配器
	 */
	@Override
	protected BaseAdapter getAdapter(Context context) {
		return new CustomListAdapter<UserRecords>(lists) {

			private UserRecords listItem;

			@Override
			public View view(int arg0, View converview, ViewGroup arg2) {
				ListItemView item = null;
				if (converview == null) {
					converview = LayoutInflater.inflate(getSupportActivity(),
							R.layout.records_list_item);
					item = new ListItemView();
					item.textDateView = (TextView) converview
							.findViewById(R.id.textDate);
					item.textStartView = (TextView) converview
							.findViewById(R.id.textStart);
					item.textEndView = (TextView) converview
							.findViewById(R.id.textEnd);
					item.textTimeView = (TextView) converview
							.findViewById(R.id.textTime);
					item.textCashView = (TextView) converview
							.findViewById(R.id.textCash);
					converview.setTag(item);
				} else {
					item = (ListItemView) converview.getTag();
				}
				listItem = (UserRecords) getItem(arg0);

				item.textDateView.setText(listItem.date);
				item.textStartView.setText(listItem.startTime);
				item.textEndView.setText(listItem.endTime);
				item.textTimeView.setText(listItem.costTime);
				item.textCashView.setText(listItem.costMoney);

				return converview;
			}
		};
	}

	static class ListItemView {
		TextView textDateView;
		TextView textStartView;
		TextView textEndView;
		TextView textTimeView;
		TextView textCashView;
	}

	@Override
	protected void initListener() {
		super.initListener();
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (checkEdit()) {
					pullToRefreshListView.setVisibility(View.VISIBLE);
					startUpdate();
				}
			}
		});
	}

	@Override
	protected boolean checkEdit() {
		searchStr = searchView.getText().toString();
		if (searchStr.length() > 0) {
			return true;
		}
		Utils.showToast(getSupportActivity(), R.string.string_empty);
		return false;
	}

	@Override
	protected Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException {
		String urls = String.format(URLS.METHOD_GetUserRenReconds,
				URLS.TEMP_USERID, URLS.TEMP_PWD, searchStr);
		String response = HttpRequest.get(urls).body();
		lists.clear();
		List<UserRecords> mList = UserRecordsList.parse(response);
		if (mList.size() == 0) {
			return Utils.NO_DATA;
		}
		lists.addAll(mList);
		return Utils.OK;
	}

	@Override
	protected void clearEdit() {
		searchView.setText("");
	}

}
