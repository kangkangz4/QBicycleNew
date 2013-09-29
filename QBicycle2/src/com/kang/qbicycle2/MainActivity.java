package com.kang.qbicycle2;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.Button;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.kang.qbicycle2.util.Utils;

public class MainActivity extends Activity {

	private FragmentManager mFragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Utils.setActionBar(this);

		setContentView(R.layout.activity_main);

		initFragment();
	}

	/**
	 * 初始化Fragment
	 */
	private void initFragment() {
		mFragmentManager = getSupportFragmentManager();
		Fragment fragment = Fragment.instantiate(MainFragment.class);
		replaceFragment(fragment, null, false);
	}

	public void replaceFragment(Fragment newFragment) {
		replaceFragment(newFragment, null, true);
	}

	public void replaceFragment(Fragment newFragment, String tag,
			boolean addToBackStack) {
		if (!addToBackStack) {
			clearBackStack();
		}
		replaceFragment(mFragmentManager, newFragment, tag, addToBackStack);
	}

	private void replaceFragment(FragmentManager fm, Fragment fragment,
			String tag, boolean addToBackStack) {
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.contentView, fragment, tag);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		if (addToBackStack) {
			ft.addToBackStack(tag);
			ft.commitAllowingStateLoss();
		} else {
			ft.commit();
		}
	}

	private void clearBackStack() {
		while (mFragmentManager.popBackStackImmediate()) {
			;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			startSetting();
			break;
		case R.id.action_exit:
			exit();
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * 开启设置页面
	 */
	private void startSetting() {
		Intent intent = new Intent();
		intent.setAction(Utils.ACTION_SETTING);
		startActivity(intent);
		Utils.AnimBottomInTopOut(this);
	}

	/**
	 * 程序退出
	 */
	private void exit() {
		// TODO Auto-generated method stub

	}

	public static class MainFragment extends Fragment {

		private Button SearchButton;
		private Button NormalButton;
		private Button OperatButton;

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.frame_main);

			initButtons(view);

			return view;
		}

		private void initButtons(View view) {
			SearchButton = (Button) view.findViewById(R.id.BT_StationSearch);
			NormalButton = (Button) view.findViewById(R.id.BT_NormalUser);
			OperatButton = (Button) view.findViewById(R.id.BT_OperUser);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			initClicks();
		}

		private void initClicks() {
			SearchButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startStationShow();
				}
			});
			NormalButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startNormalUser();
				}
			});
			OperatButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startOperUser();
				}
			});
		}

		/**
		 * 开启站点显示（百度地图）
		 */
		private void startStationShow() {
			Intent intent = new Intent();
			intent.setAction(Utils.ACTION_BAIDUMAP);
			startActivity(intent);
			Utils.AnimRightInLeftOut(getSupportActivity());
		}

		/**
		 * 普通用户
		 */
		private void startNormalUser() {
			Intent intent = new Intent();
			intent.setAction(Utils.ACTION_NORMALUSER);
			startActivity(intent);
			Utils.AnimRightInLeftOut(getSupportActivity());
		}

		/**
		 * 运维人员
		 */
		private void startOperUser() {
			Fragment fragment = Fragment.instantiate(LoginFragment.class);
			((MainActivity) getSupportActivity()).replaceFragment(fragment);
		}
	}

}
