package com.gec.sdkfirebasenotification.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.gec.sdkfirebasenotification.rest.ApiClient;
import com.gec.sdkfirebasenotification.rest.models.TokenRaw;
import com.gec.sdkfirebasenotification.rest.models.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tohure on 01/03/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

        if (refreshedToken != null && refreshedToken != ""){
            TokenRaw tokenRaw = new TokenRaw();
            tokenRaw.setName("ADV");
            tokenRaw.setOs("Android");
            tokenRaw.setToken(refreshedToken);

            Call<TokenResponse> call = ApiClient.getInterface().updateToken(tokenRaw,0);
            call.enqueue(new Callback<TokenResponse>() {
                @Override
                public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                    if (response.isSuccessful()) {
                        TokenResponse tokenResponse = response.body();
                        String message = tokenResponse.getMessage();

                        Log.i("thr", "token actualizado sdk | "+message);
                    } else {
                        Log.i("thr", "fall al actualizar token sdk 1");
                    }
                }

                @Override
                public void onFailure(Call<TokenResponse> call, Throwable t) {
                    Log.i("thr", "fall al actualizar token sdk 2");
                }
            });
        }


    }
}