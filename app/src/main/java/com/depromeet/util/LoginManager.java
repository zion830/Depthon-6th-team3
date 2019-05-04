package com.depromeet.util;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginManager {
    private static final String sharedPrefName = "mannaja_sharedpref";
    private static final String KEY_NAME = "key_name";

    private static LoginManager mInstance;
    private SharedPreferences sharedPreferences;

    public LoginManager(Context context) {
        sharedPreferences = context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
    }

    public static synchronized LoginManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LoginManager(context);
        }

        return mInstance;
    }

    public void userLogin(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    public String getUserName() {
        return sharedPreferences.getString(KEY_NAME, "");
    }

    public boolean isLoggedIn() {
        return !getUserName().isEmpty();
    }
}
