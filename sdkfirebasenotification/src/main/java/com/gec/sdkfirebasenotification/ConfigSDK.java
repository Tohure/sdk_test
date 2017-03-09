package com.gec.sdkfirebasenotification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gec.sdkfirebasenotification.rest.ApiClient;
import com.gec.sdkfirebasenotification.rest.models.TokenRaw;
import com.gec.sdkfirebasenotification.rest.models.TokenResponse;
import com.gec.sdkfirebasenotification.utils.SettingsSDK;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        FirebaseInstanceId.getInstance();

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


    public ConfigSDK sKey(String serverkey) {

        if (serverkey.trim().equals("")) {
            throw new IllegalArgumentException("sKey == null");
        } else {
            SettingsSDK.saveServerKey(context, serverkey);
            return singleton;
        }

    }


    public void configNotifications(boolean b) {
        String token;

        if (b) {
            SettingsSDK.saveNotification(context,true);
            token = FirebaseInstanceId.getInstance().getToken();
            sendToServer(token);
        } else {
            //FirebaseInstanceId.getInstance().deleteInstanceId();
            token = "";
            SettingsSDK.saveNotification(context,false);
        }

        SettingsSDK.saveToken(context, token);
    }

    private static void sendToServer(String token) {

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
}