package com.gec.sdktestnotification.rest;

import android.util.Log;

import com.gec.sdktestnotification.rest.models.TokenResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Tohure on 03/03/2017.
 * ApiClient , en esta clase se declaran las url del dominio y los servicios.
 */

public class ApiClient {

    private static String DOMAIN = "http://172.18.60.113:8000/";
    private static String VERSION_API = "";
    private static String PATH = DOMAIN+VERSION_API;
    private static final String TAG = "ApiClient";
    private static Retrofit retrofit;

    public static com.gec.sdkfirebasenotification.rest.ApiClient.ApiInterface getInterface() {
        retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .client(getClientInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(com.gec.sdkfirebasenotification.rest.ApiClient.ApiInterface.class);
    }

    public static  Retrofit getRetrofit(){
        return retrofit;
    }


    public interface ApiInterface {

        @POST("device")
        Call<com.gec.sdkfirebasenotification.rest.models.TokenResponse> sendToken(@Body Object raw);

        @PUT("device/{id}")
        Call<com.gec.sdkfirebasenotification.rest.models.TokenResponse> updateToken(@Body Object raw, @Path("id") int id);

        @DELETE("device")
        Call<com.gec.sdkfirebasenotification.rest.models.TokenResponse> deleteToken(@Body Object raw);
    }

    public static OkHttpClient getClientInterceptor() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(logging);
        return builder.build();
    }
}
