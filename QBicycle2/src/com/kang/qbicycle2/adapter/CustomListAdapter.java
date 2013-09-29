package com.kang.qbicycle2.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class CustomListAdapter<T> extends BaseAdapter {

	private List<T> t;

	public CustomListAdapter(List<T> list) {
		this.t = list;
	}

	@Override
	public int getCount() {
		if (t != null) {
			return t.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		if (t != null) {
			return t.get(arg0);
		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		return view(arg0, arg1, arg2);
	}

	public abstract View view(int arg0, View arg1, ViewGroup arg2);

}
