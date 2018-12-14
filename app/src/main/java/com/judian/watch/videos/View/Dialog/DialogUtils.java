package com.judian.watch.videos.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;

import com.judian.watch.videos.Interface.DialogListener;
import com.judian.watch.videos.R;
import com.judian.watch.videos.databinding.TitleDialogLayoutBinding;


/*
 * Created by Administrator on 2017/3/22 0022.
 */

public class DialogUtils {
    private  Dialog dialog;
    private  TitleDialogLayoutBinding binding;

    public  DialogUtils init(Context context) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.title_dialog_layout, null, false);
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(binding.getRoot());
        dialog.show();
        return this;
    }

    public DialogUtils setTitle(String s) {
        binding.tvTitle.setText(s);
        return this;
    }

    public  Dialog getDialog() {
        return dialog;
    }

    public DialogUtils setOne(String s, final DialogListener listener) {
        binding.tvDetermine.setText(s);
        binding.tvDetermine.setOnClickListener(v -> {
            if (listener != null) {
                listener.buttType(1);
            }
            dialog.dismiss();
        });
        return this;
    }

    public DialogUtils setTwo(String s, final DialogListener listener) {
        binding.tvCancel.setText(s);
        binding.tvCancel.setVisibility(View.VISIBLE);
        binding.lineTwo.setVisibility(View.VISIBLE);
        binding.tvCancel.setOnClickListener(v -> {
            if (listener != null) {
                listener.buttType(1);
            }
            dialog.dismiss();
        });
//        binding.tvDetermine.setBackgroundResource(R.drawable.bg_click_dialog_lift_gray);
        return this;
    }


    public DialogUtils isTouchcCancel(final boolean isdismiss) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(isdismiss);
            dialog.setCancelable(isdismiss);

        }
        return this;
    }


}
