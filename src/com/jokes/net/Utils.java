package com.jokes.net;

import android.content.Context;
import android.content.SharedPreferences;


public class Utils {
	public static String getPreferenceStr(Context context, String name) {
		return getPreferenceStr(context, name, "");
	}

	public static String getPreferenceStr(Context context, String name,
			String defValue) {
		SharedPreferences preferences = context.getSharedPreferences(
				"preferences", 0);
		return preferences.getString(name, defValue);
	}

	public static void setPreferenceStr(Context context, String name,
			String value) {
		SharedPreferences preferences = context.getSharedPreferences(
				"preferences", 0);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(name, value);
		editor.commit();
	}
}
