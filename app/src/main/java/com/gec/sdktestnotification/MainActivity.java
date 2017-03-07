package com.gec.sdktestnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gec.sdkfirebasenotification.ConfigSDK;
import com.gec.sdkfirebasenotification.rest.ApiClient;
import com.gec.sdkfirebasenotification.rest.models.TokenRaw;
import com.gec.sdkfirebasenotification.rest.models.TokenResponse;
import com.gec.sdktestnotification.utils.Settings;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fireBaseConf();
        ConfigSDK.initSDK(this);
        ConfigSDK.configNotifications(true);
    }

    private void fireBaseConf() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Settings.saveToken(this,token);
        sendToServer(token);
    }

    private void sendToServer(String token) {

        if (token != null && !token.equals("")){
            TokenRaw tokenRaw = new TokenRaw();
            tokenRaw.setName("Motorola");
            tokenRaw.setOs("Android");
            tokenRaw.setToken(token);

            Call<TokenResponse> call = ApiClient.getInterface().sendToken(tokenRaw);
            call.enqueue(new Callback<TokenResponse>() {

                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                    if (response.isSuccessful()) {
                        TokenResponse tokenResponse = response.body();
                        String message = tokenResponse.getMessage();

                        Log.i("thr", "token creado main | "+message);
                    } else {
                        Log.i("thr", "fallo al crear token main 1");
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    Log.i("thr", "fallo al crear token main 2");
                }
            });
        }
    }
}
