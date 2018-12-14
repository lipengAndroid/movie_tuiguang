package com.judian.watch.videos.Http;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Interface.UpFileInterface;
import com.judian.watch.videos.Utils.FileUtils;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.DialogUtils;

import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;
import static com.judian.watch.videos.Http.UrlUtils.URI.UPLOAD_IMG;

/*
 * Created by Administrator on 2017/2/21 0021.
 */

public class ImgChoiceUtils {


    private Activity activity;

    private static int permissionCAMERA = 1011;

    public static ImgChoiceUtils isNew(Activity activity) {
        return new ImgChoiceUtils(activity);
    }

    private ImgChoiceUtils(Activity activity) {
        this.activity = activity;
    }

    private UpFileInterface upFileInterface;

    public ImgChoiceUtils show(UpFileInterface upFileInterface) {
        this.upFileInterface = upFileInterface;

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != 0) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, permissionCAMERA);
        } else {
            showXC();
        }
        return this;

    }

    private void showXC() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, permissionCAMERA);
    }

    public void onPermissions(int requestCode, int[] grantResults) {
        switch (requestCode) {
            case 1011:
                if (grantResults[0] == 0) {
                    showXC();
                } else {
                    new DialogUtils().init(activity)
                            .setTitle("请同意文件读写权限")
                            .setOne("确定", null);
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == permissionCAMERA) {

                Uri uri = data.getData();
                Bitmap bit;
                try {
                    bit = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(uri));
                    new OkHttpUtils(251, new MyHttpCallBack() {
                        @Override
                        public void ok(String jsonString, int httpTY) {
                            ToastUtils.show("上传成功");
                            upFileInterface.ok(jsonString);
                        }

                        @Override
                        public void error(String e, int uriType) {
                            ToastUtils.show("上传失败");
                        }

                        @Override
                        public void downloadUpProgress(long Percentile, int httpTY) {

                        }

                    }).SetApiUrl(UPLOAD_IMG)
                            .SetFile(FileUtils.CPSCC(FileUtils.saveImageToApp(bit).getPath()))
                            .UpFile();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    ToastUtils.show("无法获取这张照片");
                }
            }
        }
    }
}
