package com.judian.watch.videos.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.judian.watch.videos.Interface.EditTextDialogInterface;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.databinding.EdittextDialogBinding;


/*
 * Created by Administrator on 2017/3/22 0022.
 */

public class EdDialogUtils {
    private Dialog dialog;
    private EdittextDialogBinding binding;

    public EdDialogUtils init(Context context, String title, EditTextDialogInterface editTextDialogInterface) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.edittext_dialog, null, false);
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(binding.getRoot());
        binding.title.setText("请输入" + title);
        binding.ed.setHint("请输入" + title);
        binding.tvDetermine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(binding.ed.getText()+"")) {
                    ToastUtils.show("请输入" + title);
                    return;
                }
                editTextDialogInterface.ED(binding.ed.getText() + "");
                dialog.dismiss();
            }
        });
        binding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDialogInterface.cancel();
                dialog.dismiss();
            }
        });
        dialog.show();
        return this;
    }


    public Dialog getDialog() {
        return dialog;
    }


    public EdDialogUtils isTouchcCancel(final boolean isdismiss) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(isdismiss);
            dialog.setCancelable(isdismiss);

        }
        return this;
    }


}
