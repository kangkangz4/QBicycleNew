package com.kang.qbicycle2.normal;

import org.holoeverywhere.app.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.kang.qbicycle2.R;
import com.kang.qbicycle2.adapter.MFragmentPageAdapter;
import com.kang.qbicycle2.normal.fragment.BorrowFragment;
import com.kang.qbicycle2.normal.fragment.ResultFragment;
import com.kang.qbicycle2.normal.fragment.ReturnFragment;
import com.kang.qbicycle2.util.Utils;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitlePageIndicator.IndicatorStyle;

public class NormalMainActivity extends Activity {

	private static final int[] CONTENT = { R.string.recorders,
			R.string.frame_title_bicycle_borrow,
			R.string.frame_title_bicycle_return };
	@SuppressWarnings("rawtypes")
	private static final Class[] CLASS = { ResultFragment.class,
			BorrowFragment.class, ReturnFragment.class };
	private ViewPager page;
	private TitlePageIndicator indicator;
	private MFragmentPageAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		Utils.setActionBar(this);
		setContentView(R.layout.activity_normal);
		adapter = new MFragmentPageAdapter(this, getSupportFragmentManager(),
				CONTENT, CLASS);
		page = (ViewPager) findViewById(R.id.pager);
		page.setAdapter(adapter);
		// 设置顶部标签
		indicator = (TitlePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(page);
		indicator.setFooterIndicatorStyle(IndicatorStyle.Triangle);
		indicator.setCurrentItem(1);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Utils.AnimLeftInRightOut(this);
	}

}
