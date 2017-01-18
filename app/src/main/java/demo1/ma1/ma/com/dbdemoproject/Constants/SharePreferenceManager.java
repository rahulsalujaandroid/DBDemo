package demo1.ma1.ma.com.dbdemoproject.Constants;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceManager {
    private static String SHARE_PREFERENCES_FILE = "SHARE_PREFERENCES";

    public static void writeToPreferences(Context context, String key, boolean value) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_FILE, Context.MODE_PRIVATE);
        if (mSharedPreferences != null) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(key, value);
            editor.commit();
        }
    }

    public static boolean getPreferences(Context context, String key, boolean defaultValue) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences(SHARE_PREFERENCES_FILE, Context.MODE_PRIVATE);
        boolean b = false;
        if (mSharedPreferences != null) {
            b = mSharedPreferences.getBoolean(key, defaultValue);
        }
        return b;
    }
}
