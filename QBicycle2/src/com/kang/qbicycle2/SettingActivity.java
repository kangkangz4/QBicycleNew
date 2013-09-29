package com.kang.qbicycle2;

import org.holoeverywhere.app.Activity;

import android.os.Bundle;

import com.kang.qbicycle2.util.Utils;

public class SettingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		Utils.setActionBar(this);
		
//		addPreferencesFromResource(R.xml.preferences);
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Utils.AnimTopInBottomOut(this);
	}

}
