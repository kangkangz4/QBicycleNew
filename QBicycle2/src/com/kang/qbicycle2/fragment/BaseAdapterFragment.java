package com.kang.qbicycle2.fragment;

import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kang.qbicycle2.R;
import com.kang.qbicycle2.util.SafeAsyncTask;
import com.kang.qbicycle2.util.Utils;

public abstract class BaseAdapterFragment<T> extends BaseFragment {

	protected PullToRefreshListView pullToRefreshListView;
	private Context context;
	private BaseAdapter adapter;
	protected List<T> lists;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.context = activity.getApplicationContext();
		lists = new ArrayList<T>();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		adapter = getAdapter(context);
		setListView(adapter);
	}

	@Override
	protected void initListener() {
		pullToRefreshListView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {

						String label = DateUtils.formatDateTime(context,
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);

						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);

						// Do work to refresh the list here.
						startUpdate();
					}
				});
	}

	protected abstract BaseAdapter getAdapter(Context context);

	/**
	 * 设定适配器
	 * 
	 * @param adpater
	 */
	private void setListView(BaseAdapter adpater) {
		ListView listView = pullToRefreshListView.getRefreshableView();
		listView.setAdapter(adapter);
	}

	/**
	 * 因为有下拉刷新，所以不需要LoadFragment，置空
	 */
	@Override
	protected void initLoadFragment() {
	}

	@Override
	protected void startUpdate() {
		new SafeAsyncTask<Integer>() {

			@Override
			public Integer call() throws Exception {
				return postData();
			}

			@Override
			protected void onSuccess(Integer t) throws Exception {
				// Call onRefreshComplete when the list has been refreshed.
				pullToRefreshListView.onRefreshComplete();
				resultHandler(t);
				super.onSuccess(t);
			}

		}.execute();
	}

	/**
	 * 返回结果处理
	 * 
	 * @param t
	 */
	protected void resultHandler(Integer t) {
		switch (t) {
		case Utils.HTTP_ERROR:
			Utils.showToast(getSupportActivity(), R.string.http_exception_error);
			break;
		case Utils.XMLPARSER_ERROR:
			Utils.showToast(getSupportActivity(), R.string.xml_parser_failed);
			break;
		case Utils.NO_DATA:
			clearEdit();
			Utils.showToast(getSupportActivity(), R.string.no_data);
			break;
		case Utils.OK:
			clearEdit();
			adapter.notifyDataSetChanged();
			break;
		}
	};

}
