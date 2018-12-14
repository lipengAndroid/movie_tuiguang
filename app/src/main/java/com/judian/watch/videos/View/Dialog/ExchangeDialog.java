package com.judian.watch.videos.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.judian.watch.videos.Interface.ExchangeDialogInterface;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.databinding.ExchangeDialogBinding;

/**
 * Created by 李鹏 2017/11/28 0028.
 */

public class ExchangeDialog {

    public static void show(Context context, final ExchangeDialogInterface dialogInterface) {
        final ExchangeDialogBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.exchange_dialog,
                null, false);

        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(binding.getRoot());

        binding.gb.setOnClickListener(v -> dialog.dismiss());

        binding.ok.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.exchang.getText() + "")) {
                ToastUtils.show("请输入兑换码");
                return;
            }
            dialogInterface.Str(binding.exchang.getText() + "");
            dialog.dismiss();
        });
        dialog.show();
    }
}
