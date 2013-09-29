
package com.kang.qbicycle2.fragment;

import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.DialogFragment;
import org.holoeverywhere.app.ProgressDialog;

import com.kang.qbicycle2.R;

import android.os.Bundle;

public class DialogsProgressDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getSupportActivity(), getTheme());
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getText(R.string.dialog_message));
        return dialog;
    }
}
