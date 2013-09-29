package com.kang.qbicycle2.baidumap;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kang.qbicycle2.model.MyOverlayItem;

public class OverlayItemAdapter extends BaseAdapter {

	private Context context;
	private List<MyOverlayItem> list;
	private TextView textview;

	public OverlayItemAdapter(Context context, List<MyOverlayItem> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return Long
				.parseLong(((MyOverlayItem) getItem(arg0)).getBicycle().stationID);
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		View view = LayoutInflater.from(context).inflate(
				android.R.layout.simple_list_item_1, null);
		textview = (TextView) view.findViewById(android.R.id.text1);
		textview.setText(((MyOverlayItem) getItem(arg0)).getBicycle().station);
		return view;
	}

}
