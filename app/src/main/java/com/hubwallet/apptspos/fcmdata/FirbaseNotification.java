package com.hubwallet.apptspos.fcmdata;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hubwallet.apptspos.Activities.SplashScreen;
import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.PrefManager;

import java.util.Map;
import java.util.Objects;

public class FirbaseNotification extends FirebaseMessagingService {
    private PrefManager mSharepreference;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        mSharepreference = new PrefManager(this);
        Map<String, String> data = remoteMessage.getData();
        if (remoteMessage.getData().size() > 0) {
            sendNotification(data.get("title"), Objects.requireNonNull(data.get("message")));
        }

    }


    @Override
    public void onNewToken(@NonNull String token) {
        //updateToken
        mSharepreference = new PrefManager(this);
        mSharepreference.setData(PrefManager.FCMTOKEN, token);
    }

    private void sendNotification(String title, String messageBody) {
        String noHTMLString = messageBody.replaceAll("\\<.*?>", "");

        NotificationManager notificationManager;
        RemoteViews mRemoteViews;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.app_name);
            String description = getString(R.string.app_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("salonpos", name, importance);
            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        } else {
            notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        int num = (int) System.currentTimeMillis();
        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0 /* Request code */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.custom_notification_small);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, "salonpos")
                        .setContentText(noHTMLString)
                        .setSmallIcon(getNotificationIcon())
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setColor(getResources().getColor(R.color.colorAccent))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        if (title != null) {
            if (!title.isEmpty()) {
                mBuilder.setContentTitle(title);
            }
        }

        assert notificationManager != null;
        notificationManager.notify(num, mBuilder.build());
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.logo : R.drawable.logo;
    }

}
