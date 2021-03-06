package com.gec.sdkfirebasenotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gec.sdkfirebasenotification.rest.ApiClient;
import com.gec.sdkfirebasenotification.rest.models.TokenRaw;
import com.gec.sdkfirebasenotification.rest.models.TokenResponse;
import com.gec.sdkfirebasenotification.utils.SettingsSDK;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by tohure on 01/03/17.
 */

public class ConfigSDK {

    private static Context context;
    private static volatile ConfigSDK singleton = null;

    public ConfigSDK(Context applicationContext) {
        context = applicationContext;
    }

    public static ConfigSDK initSDK(@NonNull Context applicationContext) {

        if (applicationContext == null) {
            throw new IllegalArgumentException("context == null");
        }

        if (singleton == null) {
            synchronized (ConfigSDK.class) {
                if (singleton == null) {
                    singleton = new ConfigSDK(applicationContext);
                }
            }
        }

        return singleton;
    }

    public void showSDKnotification(RemoteMessage remoteMessage) {

        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        int id = Math.abs(remoteMessage.getMessageId().hashCode());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_test_notification))
                .setSmallIcon(R.drawable.ic_test_push);

        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;

        builder.setDefaults(defaults);

        NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        manager.notify("SDK Test", id, builder.build());
    }

    public ConfigSDK sKey(String serverkey) {

        if (serverkey.trim().equals("")) {
            throw new IllegalArgumentException("sKey == null");
        } else {
            SettingsSDK.saveServerKey(context, serverkey);
            return singleton;
        }

    }

    public void configNotifications(boolean b) {

        if (b) {
            SettingsSDK.saveNotification(context,true);
            new FCMTokenTask().execute();
            //token = FirebaseInstanceId.getInstance().getToken();
            //sendToServer(token);
        } else {
            SettingsSDK.saveToken(context, "");
            SettingsSDK.saveNotification(context,false);
        }
    }

    private static void sendToServer(final String token) {

        if (token != null && !token.equals("")) {
            TokenRaw tokenRaw = new TokenRaw();
            tokenRaw.setName("ADV");
            tokenRaw.setOs("Android");
            tokenRaw.setToken(token);

            Call<TokenResponse> call = ApiClient.getInterface().sendToken(tokenRaw);
            call.enqueue(new Callback<TokenResponse>() {

                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                    if (response.isSuccessful()) {
                        TokenResponse tokenResponse = response.body();
                        String message = tokenResponse.getMessage();
                        SettingsSDK.saveToken(context, token);
                        Log.i("thr", "token creado sdk | " + message);
                    } else {
                        Log.i("thr", "fallo al crear token sdk 1");
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    Log.i("thr", "fallo al crear token sdk 2");
                }
            });
        }

    }

    private class FCMTokenTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String token = "";
            try {
                String authorizedEntity = "995158750717";
                String scope = FirebaseMessaging.INSTANCE_ID_SCOPE;
                token = FirebaseInstanceId.getInstance().getToken(authorizedEntity, scope);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            sendToServer(s);
        }
    }

}