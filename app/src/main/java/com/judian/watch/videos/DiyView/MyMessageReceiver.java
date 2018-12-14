package com.judian.watch.videos.DiyView;

import android.content.Context;
import android.content.Intent;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.View.Home.HomeListItemDetailsActivity;
import com.judian.watch.videos.View.Home.TitleWebActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

//import com.alibaba.sdk.android.push.MessageReceiver;
//import com.alibaba.sdk.android.push.notification.CPushMessage;

/**
 * Created by Administrator on 2017/9/28 0028.
 */

public class MyMessageReceiver extends MessageReceiver {


    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {//
        // TODO 处理推送通知
        LogUtils.e("MyMessageReceiver" + "Receive notification, title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);

//        NotificationUtils.show(context, title, summary, extraMap);
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        LogUtils.e("MyMessageReceiver" + "onMessage, messageId: "
                + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        LogUtils.e("MyMessageReceiver" +
                "onNotificationOpened, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
        KEY.PUSH_JSON = extraMap;
        try {
            JSONObject jsonObject = new JSONObject(extraMap);
            int type = jsonObject.getInt("type");
            if (type == 6) {
//                context.startActivity(new Intent(context, PAYWebActivity.class)
//                        .putExtra(KEY.TITLE, jsonObject.getString("title"))
//                        .putExtra(KEY.URL, jsonObject.getString("url")));
//                Intent userIntent = new Intent(mContext, HomeListItemDetail/*sActivity.class)
//                        .putExtra(KEY.TITLE, topics.get(position).getSpecial_name())
//                        .putExtra(KEY.URL, topics.get(position).getSpecial_banner())
//                        .putExtra(KEY.ID, topics.get(position).getSpecial_id());*/
                context.startActivity(new Intent(context, HomeListItemDetailsActivity.class)
                        .putExtra(KEY.TITLE, jsonObject.getString("title"))
                        .putExtra(KEY.ID, jsonObject.getString("url")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                context.startActivity(new Intent(context, TitleWebActivity.class)
                        .putExtra(KEY.TITLE, jsonObject.getString("title"))
                        .putExtra(KEY.URL, jsonObject.getString("url")).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        context.startService(new Intent(context, myService.class));
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        LogUtils.e("MyMessageReceiver" +
                "onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);

    }


    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary,
                                               Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        LogUtils.e("MyMessageReceiver" +
                "onNotificationReceivedInApp, title: " + title + ", " +
                "summary: " + summary + ", extraMap:" + extraMap + ", openType:" +
                openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }


    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        LogUtils.e("MyMessageReceiver" + "onNotificationRemoved" + messageId);
    }
}
