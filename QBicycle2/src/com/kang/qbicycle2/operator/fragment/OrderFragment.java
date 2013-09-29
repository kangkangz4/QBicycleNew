package com.kang.qbicycle2.operator.fragment;

import org.holoeverywhere.app.TabSwipeFragment;

import android.content.Intent;
import android.os.Bundle;

import com.kang.qbicycle2.operator.fragment.order.OrderMessageFragment;
import com.kang.qbicycle2.operator.fragment.order.OrderOpenFragment;
import com.kang.qbicycle2.operator.fragment.order.OrderRestartFragment;

public class OrderFragment extends TabSwipeFragment {

	private static final String[] Content = { "重启", "解锁", "消息" };

	// private static final Class[] Class = {};
	//
	// private ViewPager page;
	// private TitlePageIndicator indicator;
	// private OrderFragmentAdapter adapter;
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View view = inflater.inflate(R.layout.activity_normal, null);
	// page = (ViewPager) view.findViewById(R.id.pager);
	// indicator = (TitlePageIndicator) view.findViewById(R.id.indicator);
	// return view;
	// }
	//
	// @Override
	// public void onActivityCreated(Bundle savedInstanceState) {
	// super.onActivityCreated(savedInstanceState);
	// adapter = new OrderFragmentAdapter(getFragmentManager());
	// page.setAdapter(adapter);
	//
	// indicator.setViewPager(page);
	// indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
	// indicator.setCurrentItem(1);
	// }
	//
	// class OrderFragmentAdapter extends FragmentPagerAdapter {
	//
	// public OrderFragmentAdapter(FragmentManager fm) {
	// super(fm);
	// }
	//
	// @Override
	// public android.support.v4.app.Fragment getItem(int arg0) {
	// Fragment fragment = null;
	// switch (arg0) {
	// case 0:
	// // fragment = Fragment.instantiate(OrderRestartFragment.class);
	// fragment = new OrderRestartFragment();
	// break;
	// case 1:
	// // fragment = Fragment.instantiate(OrderOpenFragment.class);
	// fragment = new OrderOpenFragment();
	// break;
	// case 2:
	// // fragment = Fragment.instantiate(OrderMessageFragment.class);
	// fragment = new OrderMessageFragment();
	// break;
	// default:
	// break;
	// }
	// return fragment;
	// }
	//
	// @Override
	// public CharSequence getPageTitle(int position) {
	// return Content[position];
	// }
	//
	// @Override
	// public int getCount() {
	// return Content.length;
	// }
	//
	// }

	@Override
	public void onHandleTabs() {

		// getSupportActionBar().setNavigationMode(mode);

		Bundle b = new Bundle();
		b.putString("a", "fff");
		
		addTab(Content[0], OrderRestartFragment.class, b);
		addTab(Content[1], OrderOpenFragment.class);
		addTab(Content[2], OrderMessageFragment.class);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		
		
	}
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	// if (requestCode < 0x10) {
	// Fragment.instantiate(OrderRestartFragment.class).onActivityResult(
	// requestCode, resultCode, data);
	// } else if (requestCode < 0x20) {
	// Fragment.instantiate(OrderOpenFragment.class).onActivityResult(
	// requestCode, resultCode, data);
	// } else {
	// Fragment.instantiate(OrderMessageFragment.class).onActivityResult(
	// requestCode, resultCode, data);
	// }
	// }

}
