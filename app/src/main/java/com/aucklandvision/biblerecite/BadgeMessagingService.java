package com.aucklandvision.biblerecite;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.aucklandvision.biblerecite.utils.BadgeUtil;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class BadgeMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String lastDateServer = remoteMessage.getData().get("lastDateServer");
        if (!lastDateServer.equals(BadgeUtil.lastDateClient)) {
            ArrayList<Object> list = arrVersesExceptAlready(remoteMessage.getData());
            String title = (String)list.get(0);
            String body = (String)list.get(1);
            int badgeCount = (Integer)list.get(2);
            receiveNotification(title, body);
            setBadgeCount(badgeCount);
        }
    }

    private void receiveNotification(String title, String body) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SharedPreferences sharedPreferences = this.getSharedPreferences("push", MODE_PRIVATE);
            String channelId = sharedPreferences.getString("channelId", "");
            if (channelId.equals("")) {
                NotificationChannel channel = new NotificationChannel("biblereciting", "biblereciting", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("channelId", channel.getId());
                channelId = channel.getId();
            }
            notificationBuilder = new NotificationCompat.Builder(this, channelId);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this);
        }
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSmallIcon(R.drawable.logo)
                           .setContentTitle(title)
                           .setContentText(body)
                           .setAutoCancel(true)
                           .setSound(defaultSoundUri)
                           .setContentIntent(pendingIntent);
        notificationManager.notify(0, notificationBuilder.build());
    }

    public void setBadgeCount(int badgeCount) {
        BadgeUtil.badgeCount += badgeCount;
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", BadgeUtil.badgeCount);
        intent.putExtra("badge_count_package_name","com.aucklandvision.biblerecite");
        intent.putExtra("badge_count_class_name", "com.aucklandvision.biblerecite.MainActivity");
        sendBroadcast(intent);
    }

    public ArrayList<Object> arrVersesExceptAlready(Map<String, String> mapData) {
        int cnt;
        String body_kr = "";
        String body_en = "";
        String[] body_kr_tmp = mapData.get("body_kr").split(",");
        String[] body_en_tmp = mapData.get("body_en").split(",");
        String[] lastdate_tmp = mapData.get("lastdate").split(",");
        for (cnt = 0; cnt < lastdate_tmp.length; cnt++) {
            if (lastdate_tmp[cnt].equals(BadgeUtil.lastDateClient)) {
                break;
            }
        }
        for (int j = 0 ; j < cnt; j++) {
            body_kr += body_kr_tmp[j];
            body_en += body_en_tmp[j];
            if (j == cnt - 1) {
                break;
            }
            body_kr += ", ";
            body_en += ", ";
        }

        // get language
        String lang = Locale.getDefault().getDisplayLanguage();
        String title = null;
        String body = null;
        if (lang.equals("한국어")) {
            title = mapData.get("title_kr");
            body = body_kr;
        } else {
            title = mapData.get("title_en");
            body = body_en;
        }
        ArrayList<Object> list = new ArrayList<>();
        list.add(title);
        list.add(body);
        list.add(cnt);
        return list;
    }
}
