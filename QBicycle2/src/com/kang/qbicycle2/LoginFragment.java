package com.kang.qbicycle2;

import java.io.IOException;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.kang.qbicycle2.fragment.BaseFragment;
import com.kang.qbicycle2.model.Result;
import com.kang.qbicycle2.util.URLS;
import com.kang.qbicycle2.util.Utils;

public class LoginFragment extends BaseFragment {

	private static final String KEY_PASS = "key_pass";
	private static final String KEY_REMEMBER = "key_remember";

	private static final int START_OPERATOR = 0x11;

	private Button loginButton;
	private EditText userNameView;
	private EditText passwordView;
	private String username;
	private String password;
	private Boolean isRemember;
	private CheckBox checkBox;
	private SharedPreferences preferences;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frame_login);
		userNameView = (EditText) view.findViewById(R.id.et_username);
		passwordView = (EditText) view.findViewById(R.id.et_password);
		loginButton = (Button) view.findViewById(R.id.BT_Login);
		checkBox = (CheckBox) view.findViewById(R.id.ck_remember);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		restoreSetting();
	}

	/**
	 * 取得设置参数
	 */
	private void restoreSetting() {
		preferences = getSharedPreferences(Utils.SHARE_PREFRENCES,
				Context.MODE_PRIVATE);
		isRemember = preferences.getBoolean(KEY_REMEMBER, false);
		if (isRemember) {
			userNameView.setText(preferences.getString(Utils.KEY_USER, null));
			passwordView.setText(preferences.getString(KEY_PASS, null));
			checkBox.setChecked(isRemember);
		}
	}

	/**
	 * 初始化事件
	 */
	@Override
	protected void initListener() {
		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkEdit()) {
					checkRemeber();
					startUpdate();
				}
			}
		});
	}

	/**
	 * 记住密码框
	 */
	protected void checkRemeber() {
		isRemember = checkBox.isChecked();
		if (isRemember) {
			save(isRemember, username, password);
		} else {
			remove();
		}
	}

	/**
	 * 移除记得密码记录
	 * 
	 * @param isRemember2
	 */
	private void remove() {
		Editor editor = preferences.edit();
		editor.remove(KEY_REMEMBER);
		editor.remove(KEY_PASS);
		editor.commit();
	}

	/**
	 * 保存是否记住用户，用户名和密码
	 * 
	 * @param isRemember2
	 * @param username2
	 * @param password2
	 */
	private void save(Boolean isRemember2, String username2, String password2) {
		Editor editor = preferences.edit();
		editor.putBoolean(KEY_REMEMBER, isRemember2);
		editor.putString(Utils.KEY_USER, username2);
		editor.putString(KEY_PASS, password2);
		editor.commit();
	}

	/**
	 * 验证输入框
	 * 
	 * @return
	 */
	@Override
	protected boolean checkEdit() {
		username = userNameView.getText().toString();
		password = passwordView.getText().toString();
		if (username.length() > 0 && password.length() > 0) {
			return true;
		}
		Utils.showToast(getSupportActivity(), R.string.string_empty);
		return false;
	}

	/**
	 * 开始运维人员操作页面
	 */
	protected void startOperatorMain() {
		Intent intent = new Intent();
		intent.setAction(Utils.ACTION_OPERATORMAIN);
		startActivity(intent);
		Utils.AnimRightInLeftOut(getSupportActivity());
	}

	@Override
	protected Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException {
		String url = String.format(URLS.METHOD_CheckUser, username, password);
		String response = HttpRequest.get(url).body();
		result = Result.parse(response);
		if (result.length() == 36) {
			saveToken(username, result);
			return START_OPERATOR;
		}
		return Utils.OK;
	}

	@Override
	protected void resultHandler(Integer t) {
		super.resultHandler(t);
		switch (t) {
		case START_OPERATOR:
			startOperatorMain();
			break;
		}
	}

	/**
	 * 保存令牌
	 */
	private void saveToken(String userid, String token) {
		Editor editor = preferences.edit();
		editor.putString(Utils.KEY_USER, userid);
		editor.putString(Utils.KEY_TOKEN, token);
		editor.commit();
	}

	@Override
	protected void clearEdit() {
		// TODO Auto-generated method stub
	}

}
