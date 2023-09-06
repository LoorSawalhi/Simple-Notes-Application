package com.example.a1190075_1190245_courseproject.helpers;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.a1190075_1190245_courseproject.R;

public class MyNotificationManager extends AppCompatActivity {

    private static final String MY_CHANNEL_ID = "twitter_channel_id";
    private static final String MY_CHANNEL_NAME = "Twitter Channel";
    private static int NOTIFICATION_ID = 1;
    private static MyNotificationManager instance = null;


    private MyNotificationManager() {

    }

    public static MyNotificationManager getInstance() {
        if (instance == null) {
            instance = new MyNotificationManager();
        }

        return instance;
    }

    public Intent sendEmail(String email, String subject, String content) {
        Intent gmailIntent = new Intent();
        gmailIntent.setAction(Intent.ACTION_SENDTO);
        gmailIntent.setDataAndType(Uri.parse("mailto:"), "message/rfc822");
        gmailIntent.putExtra(Intent.EXTRA_EMAIL, email);
        gmailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        gmailIntent.putExtra(Intent.EXTRA_TEXT, content);
        startActivity(gmailIntent);
        return gmailIntent;
    }

    private void createNotificationChannel() {
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(MY_CHANNEL_ID,
                MY_CHANNEL_NAME, importance);
        NotificationManager notificationManager =
                getSystemService(NotificationManager.class);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createNotification(String title, String body, Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx,
                MY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(ctx);
        try {
            notificationManager.notify(NOTIFICATION_ID++, builder.build());
        } catch (SecurityException e) {
            Log.e("Notification Error", "Permission denied" + e);
        }
    }

}
