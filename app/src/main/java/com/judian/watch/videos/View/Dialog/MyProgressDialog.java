package com.judian.watch.videos.View.Dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.judian.watch.videos.R;


public class MyProgressDialog {


    @SuppressLint("StaticFieldLeak")
    private static MyProgressDialog instance = new MyProgressDialog();

    private MyProgressDialog() {
    }

    /**
     * @return ProgressDialog
     */
    public static MyProgressDialog getInstance() {
        return instance;
    }

    private Dialog mProgressDialog;

    private Context mContext;

    /**
     * @
     */
    public void show(Context context, String msg) {

        showInfo(context, msg);

    }



    private void showInfo(Context context, String msg) {

        mContext = context;

        if (!isValidContext(mContext)) {
            return;
        }

        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }

        mProgressDialog = new Dialog(context, R.style.dialog);

        View view = LayoutInflater.from(context).inflate(
                R.layout.progress_dialog_layout, null);

        TextView tvInfo = view.findViewById(R.id.tv_info);
        tvInfo.setText(msg);

        mProgressDialog.setContentView(view);
        mProgressDialog.show();
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.setOnKeyListener((dialog, keyCode, event) -> true);

    }

    /**
     * dismiss
     */
    public void cancel() {

        if (mContext != null && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private boolean isValidContext(Context c) {

        Activity a = (Activity) c;

        return !(a == null || a.isFinishing());
    }

}

