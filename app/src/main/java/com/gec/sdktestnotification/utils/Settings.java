package com.gec.sdktestnotification.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tohure on 02/03/17.
 */

public class Settings {

    protected static final String TEST ="com.tohure.test.utils";


    protected static String TOKEN_PREFERENCE = "com.tohure.test.utils.etoken";
    protected static String TOKEN = TOKEN_PREFERENCE + ".token";

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
        return context.getSharedPreferences(TEST, Context.MODE_PRIVATE);
    }

    private static SharedPreferences getSharedPreferencesToken(Context context){
        return context.getSharedPreferences(TOKEN_PREFERENCE, Context.MODE_PRIVATE);
    }

    public static void saveToken(Context context, String token){
        mEditorToken = getEditorToken(context);
        mEditorToken.putString(TOKEN, token);
        mEditorToken.commit();
    }

    public static String getToken(Context context){
        return getSharedPreferencesToken(context).getString(TOKEN, "");
    }

}
