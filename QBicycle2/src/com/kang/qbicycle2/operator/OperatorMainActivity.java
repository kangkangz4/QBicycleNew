package com.kang.qbicycle2.operator;

import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.AddonSlider.AddonSliderA;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Activity.Addons;
import org.holoeverywhere.slider.SliderMenu;

import android.os.Bundle;
import android.util.Log;

import com.kang.qbicycle2.R;
import com.kang.qbicycle2.operator.fragment.AlarmFragment;
import com.kang.qbicycle2.operator.fragment.FormFragment;
import com.kang.qbicycle2.operator.fragment.InfoFragment;
import com.kang.qbicycle2.operator.fragment.OrderFragment;
import com.kang.qbicycle2.util.Utils;

@Addons(Activity.ADDON_SLIDER)
public class OperatorMainActivity extends Activity {

	private static final String TAG = OperatorMainActivity.class.getName();

	public AddonSliderA addonSlider() {
		return addon(AddonSlider.class);
	}

	// private static final int[] Content = {
	// R.string.frame_title_order_restart,
	// R.string.frame_title_order_open, R.string.frame_title_order_message };
	// private static final Class[] Clazz = { OrderRestartFragment.class,
	// OrderOpenFragment.class, OrderMessageFragment.class };
	//
	// private MFragmentPageAdapter adapter;
	// private ViewPager page;
	// private TabPageIndicator indicator;
	//
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// Utils.setActionBar(this);
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_operator);
	//
	// adapter = new MFragmentPageAdapter(this, getSupportFragmentManager(),
	// Content, Clazz);
	// page = (ViewPager) findViewById(R.id.pager);
	// page.setAdapter(adapter);
	// // 设置顶部标签
	// indicator = (TabPageIndicator) findViewById(R.id.indicator);
	// indicator.setViewPager(page);
	// }
	//
	// @Override
	// public void onBackPressed() {
	// super.onBackPressed();
	// Utils.AnimLeftInRightOut(this);
	// }

	// class MAdapter extends FragmentPagerAdapter{
	//
	// private Context Context;
	// private int[] Content;
	// private Class[] Clazz;
	//
	// public MAdapter(Context context, FragmentManager fm, int[] content,
	// Class[] clazz) {
	// super(fm);
	// this.Context = context;
	// this.Content = content;
	// this.Clazz = clazz;
	// }
	//
	// @Override
	// public Fragment getItem(int arg0) {
	// try {
	// return (Fragment)Clazz[arg0].newInstance();
	// } catch (InstantiationException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @Override
	// public int getCount() {
	// return Content.length;
	// }
	//
	// }

	// private static final

	// private MenuDrawer menuDrawer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.i(TAG, "OperatorMainActivity Create");

		Utils.setActionBar(this);

		// menuDrawer = MenuDrawer.attach(this);
		// menuDrawer.setMenuView(R.layout.navigation_drawer);
		// menuDrawer.setContentView(R.layout.carousel_view);
		// menuDrawer.setSlideDrawable(R.drawable.ic_drawer);
		// menuDrawer.setDrawerIndicatorEnabled(true);

		final SliderMenu sliderMenu = addonSlider().obtainDefaultSliderMenu(
				R.layout.slider_menu);

		sliderMenu.add(R.string.alarm, AlarmFragment.class, SliderMenu.BLUE);
		sliderMenu.add(R.string.repair, FormFragment.class, SliderMenu.PURPLE);
		sliderMenu.add(R.string.info, InfoFragment.class, SliderMenu.GREEN);
		sliderMenu.add(R.string.order, OrderFragment.class, SliderMenu.ORANGE);

		// sliderMenu.add(R.string.other, OtherFragment.class,
		// SliderMenu.ORANGE);
		// sliderMenu.add(R.string.about, AboutFragment.class,
		// SliderMenu.PURPLE);

		// setContentView(R.layout.activity_operator);

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Utils.AnimLeftInRightOut(this);
	}

}
