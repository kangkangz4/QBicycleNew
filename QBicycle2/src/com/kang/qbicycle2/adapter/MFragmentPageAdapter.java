package com.kang.qbicycle2.adapter;

import org.holoeverywhere.app.Fragment;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MFragmentPageAdapter extends FragmentPagerAdapter {

	@SuppressWarnings({ "rawtypes" })
	private Class[] Clazz;
	private int[] Content;
	private Context Context;

	@SuppressWarnings("rawtypes")
	public MFragmentPageAdapter(Context context, FragmentManager fm,
			int[] content, Class[] clazz) {
		super(fm);
		this.Content = content;
		this.Clazz = clazz;
		this.Context = context;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Fragment getItem(int position) {
		return Fragment.instantiate(Clazz[position]);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return Context.getString(Content[position]);
	}

	@Override
	public int getCount() {
		return Content.length;
	}

}
