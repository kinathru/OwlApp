package com.score.owl.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.score.owl.R;
import com.score.owl.pojo.User;


/**
 * Utility class to deal with Share Preferences
 *
 * @author erangaeb@gmail.com (eranga herath)
 */
public class PreferenceUtil {

    /**
     * Save user credentials in shared preference
     *
     * @param context application context
     * @param user    logged-in user
     */
    public static void saveUser(Context context, User user) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //keys should be constants as well, or derived from a constant prefix in a loop.
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.commit();
    }

    /**
     * Get user details from shared preference
     *
     * @param context application context
     * @return user object
     */
    public static User getUser(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file), Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String password = preferences.getString("password", "");

        if (username.isEmpty()) return null;

        User user = new User(username, password);
        user.setUsername(username);
        return user;
    }

    /**
     * Save public/private keys in shared preference,
     *
     * @param context application context
     * @param key     public/private keys(encoded key string)
     * @param keyType public_key, private_key, server_key
     */
    public static void saveKey(Context context, String key, String keyType) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(keyType, key);
        editor.commit();
    }

    /**
     * Get saved RSA key string from shared preference
     *
     * @param context application context
     * @param keyType public_key, private_key, server_key
     * @return key string
     */
    public static String getKey(Context context, String keyType) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file), Context.MODE_PRIVATE);
        return preferences.getString(keyType, "");
    }

}
