package com.kang.qbicycle2.fragment;

import java.io.IOException;

import org.holoeverywhere.app.DialogFragment;
import org.holoeverywhere.app.Fragment;
import org.xmlpull.v1.XmlPullParserException;

import android.os.Bundle;

import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;
import com.kang.qbicycle2.R;
import com.kang.qbicycle2.util.Ln;
import com.kang.qbicycle2.util.SafeAsyncTask;
import com.kang.qbicycle2.util.Utils;

public abstract class BaseFragment extends Fragment {

	private DialogsProgressDialogFragment dialogFragment;
	protected String result;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initListener();
		initLoadFragment();
	}

	protected void initLoadFragment() {
		dialogFragment = DialogFragment
				.instantiate(DialogsProgressDialogFragment.class);
		dialogFragment.setCancelable(false);
	}

	protected abstract void initListener();

	protected abstract boolean checkEdit();

	/**
	 * 开始更新数据
	 */
	protected void startUpdate() {
		dialogFragment.show(getSupportActivity());
		new SafeAsyncTask<Integer>() {

			@Override
			public Integer call() throws Exception {
				return postData();
			}

			@Override
			protected void onSuccess(Integer t) throws Exception {
				super.onSuccess(t);
				dialogFragment.dismiss();
				resultHandler(t);
			}
		}.execute();
	}

	protected Integer postData() {
		try {
			return doPost();
		} catch (HttpRequestException e) {
			Ln.e(e);
			return Utils.HTTP_ERROR;
		} catch (XmlPullParserException e) {
			Ln.e(e);
			return Utils.XMLPARSER_ERROR;
		} catch (IOException e) {
			Ln.e(e);
			return Utils.XMLPARSER_ERROR;
		}
	}

	protected abstract Integer doPost() throws HttpRequestException,
			XmlPullParserException, IOException;

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
		case Utils.OK:
			clearEdit();
			Utils.showToast(getSupportActivity(), result);
			break;
		}
	}

	protected abstract void clearEdit();
}
