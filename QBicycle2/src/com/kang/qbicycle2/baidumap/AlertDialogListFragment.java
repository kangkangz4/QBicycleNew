package com.kang.qbicycle2.baidumap;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.AlertDialog.Builder;
import org.holoeverywhere.app.DialogFragment;

import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.ListAdapter;

import com.kang.qbicycle2.R;

public class AlertDialogListFragment extends DialogFragment {

	private ListAdapter adapter;
	private OnClickListener listener;

	public OnClickListener getListener() {
		return listener;
	}

	public void setListener(OnClickListener listener) {
		this.listener = listener;
	}

	public ListAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(ListAdapter adapter) {
		this.adapter = adapter;
	}

	public AlertDialogListFragment() {
		setDialogType(DialogType.AlertDialog);
	}

	@Override
	public AlertDialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				getSupportActivity());
		builder.setTitle(R.string.app_name);
		builder.setIcon(R.drawable.ic_launcher);
		prepareBuilder(builder);
		return builder.create();
	}

	protected void prepareBuilder(Builder builder) {
		builder.setAdapter(adapter, listener);
	}
}
