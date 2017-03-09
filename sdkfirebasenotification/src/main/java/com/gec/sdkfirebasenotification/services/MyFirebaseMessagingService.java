package com.gec.sdkfirebasenotification.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gec.sdkfirebasenotification.R;
import com.gec.sdkfirebasenotification.utils.SettingsSDK;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by tohure on 01/03/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (SettingsSDK.getNotification(getApplicationContext())){
            Log.d("thr sdk", "onMessageReceived: "+remoteMessage.getData().toString());
            String title = remoteMessage.getData().get("title");
            String message = remoteMessage.getData().get("message");
            int id = Math.abs(remoteMessage.getMessageId().hashCode());
            sendNotification(id, title, message);
        }
    }

    private void sendNotification(int i, String title, String message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_test_notification))
                .setSmallIcon(R.drawable.ic_test_push);

        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;

        builder.setDefaults(defaults);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify("SDK Test", i, builder.build());
    }
}
