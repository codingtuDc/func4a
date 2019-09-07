package cn.com.codingtu.func4a.core.funcs;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import cn.com.codingtu.func4a.global.App;
import cn.com.codingtu.func4a.global.CoreConfigs;

public class NoticeFunc {

    public static void send(String title, String content, Intent intent) {

        NotificationManager notificationManager = SystemFunc.getNotificationManager();
        //获取PendingIntent
        PendingIntent mainPendingIntent = PendingIntent
                .getActivity(App.APP, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建 Notification.Builder 对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.APP)
                .setSmallIcon(CoreConfigs.configs().getDefaultIcon())
                //点击通知后自动清除
                .setDefaults(NotificationCompat.DEFAULT_ALL).setAutoCancel(true)
                .setContentTitle(title).setContentText(content).setContentIntent(mainPendingIntent);
        //发送通知
        notificationManager.notify(1, builder.build());
    }

}