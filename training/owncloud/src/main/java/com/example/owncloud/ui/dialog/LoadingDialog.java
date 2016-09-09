package com.example.owncloud.ui.dialog;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.owncloud.R;


public class LoadingDialog extends DialogFragment {
    private static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";

    private String mMessage;

    public LoadingDialog() {
        super();
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setRetainInstance(true);
        setCancelable(false);

        mMessage = getArguments().getString(EXTRA_MESSAGE);
    }

    public static LoadingDialog newInstance(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_MESSAGE, message);
        LoadingDialog dialog = new LoadingDialog();
        dialog.setArguments(bundle);
        return dialog;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loadingdialog, container, false);

        TextView textView = (TextView) v.findViewById(R.id.loadingText);
        textView.setText(mMessage);

        ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.loadingBar);
        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.color_accent), PorterDuff.Mode.SRC_IN);

        return v;
    }

}
