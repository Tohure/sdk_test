package com.gec.sdkfirebasenotification;

import android.content.Context;
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

    private ConfigSDK() { }

    public static void initSDK(Context applicationContext){
        context = applicationContext;
    }

    public static void configNotifications(boolean b) {
        if (b){
            String token = FirebaseInstanceId.getInstance().getToken();
            SettingsSDK.saveToken(context,token);
            sendToServer(token);
        }else{
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId();
            } catch (IOException e) {
                Log.e("thr", "configNotifications: "+ e.getMessage());
            }
            SettingsSDK.saveToken(context,"");
        }
    }

    public static String getToken() {
        return FirebaseInstanceId.getInstance().getToken();
    }


    private static void sendToServer(String token) {

        if (token != null && token != ""){
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

                        Log.i("thr", "token creado sdk | "+message);
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