package com.gec.sdkfirebasenotification.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tohure on 02/03/17.
 */

public class SettingsSDK {

    protected static final String SDK_TEST ="com.tohure.sdkfirebasenotification.utils";


    protected static String TOKEN_PREFERENCE = "com.tohure.sdkfirebasenotification.utils.etoken";
    protected static String TOKEN = TOKEN_PREFERENCE + ".token";
    protected static String SERVERKEY = TOKEN_PREFERENCE + ".serverKey";

    protected static SharedPreferences mSettings;
    protected static SharedPreferences.Editor mEditor;
    protected static SharedPreferences.Editor mEditorToken;

    private static SharedPreferences.Editor getEditor(Context context){
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences.Editor getEditorToken(Context context){
        SharedPreferences preferences = getSharedPreferencesToken(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(SDK_TEST, Context.MODE_PRIVATE);
    }

    private static SharedPreferences getSharedPreferencesToken(Context context){
        return context.getSharedPreferences(TOKEN_PREFERENCE, Context.MODE_PRIVATE);
    }



    //Save Token
    public static void saveToken(Context context, String token){
        mEditorToken = getEditorToken(context);
        mEditorToken.putString(TOKEN, token);
        mEditorToken.commit();
    }

    public static String getToken(Context context){
        return getSharedPreferencesToken(context).getString(TOKEN, "");
    }

    //Save Server Token
    public static void saveServerKey(Context context, String token){
        mEditorToken = getEditorToken(context);
        mEditorToken.putString(SERVERKEY, token);
        mEditorToken.commit();
    }

    public static String getServerKey(Context context){
        return getSharedPreferencesToken(context).getString(SERVERKEY, "");
    }

}
