package com.judian.watch.videos.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.judian.watch.videos.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import static com.judian.watch.videos.Http.UrlUtils.WX_APP_ID;


/*
 * Created by Administrator on 2017/9/27 0027.
 */

public class WxShareUtils {
    public static void sendWxWeb(Context context, Resources resources, String uri, String title, String description, int scene, int drawable) {
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = uri;

        WXMediaMessage wxme = new WXMediaMessage(wxWebpageObject);
        wxme.title = title;
        wxme.description = description;
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawable);
        wxme.thumbData = bmpToByteArray(bitmap);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "web";
        req.message = wxme;
        req.scene = scene;

        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        iwxapi.sendReq(req);
    }

    public static void sendWxWeb(Context context, String uri, String title, int scene, Bitmap bitmap) {
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = uri;

        WXMediaMessage wxme = new WXMediaMessage(wxWebpageObject);
        wxme.title = title;
        wxme.thumbData = compressImage(bitmap);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "webpage";
        req.message = wxme;
        req.scene = scene;

        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        iwxapi.sendReq(req);
    }

    public static void sendWxWeb(Context context, String uri, String title, String details, int scene, Bitmap bitmap) {
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = uri;

        WXMediaMessage wxme = new WXMediaMessage(wxWebpageObject);
        wxme.title = title;
        wxme.thumbData = compressImage(bitmap);
        wxme.description = details;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "webpage";
        req.message = wxme;
        req.scene = scene;

        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        iwxapi.sendReq(req);
    }
//    private static byte[] Bitmap2Bytes(Bitmap bm) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 86, baos);
//        return baos.toByteArray();
//    }

    public static void sendWxWeb(Context context, Bitmap bitmap, String uri, String title, String description, int scene) {
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = uri;

        WXMediaMessage wxme = new WXMediaMessage(wxWebpageObject);
        wxme.title = title;
        wxme.description = description;

        wxme.thumbData = bmpToByteArray(bitmap);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "web";
        req.message = wxme;
        req.scene = scene;

        IWXAPI iwxapi = WXAPIFactory.createWXAPI(context, WX_APP_ID, true);
        iwxapi.sendReq(req);
    }

    private static byte[]  compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {    // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
//        try {
//            FileOutputStream out = new FileOutputStream(srcPath);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 5, out);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return baos.toByteArray();
    }
    private static byte[] bmpToByteArray(final Bitmap bmp) {
        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (localBitmap.getByteCount() >= 1024 * 32) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0, i, j), null);

            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 50,
                    localByteArrayOutputStream);
//            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();

//            try {
//                localByteArrayOutputStream.close();
//
//            } catch (Exception e) {
//                // F.out(e);
//            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
        return null;
    }

}
