package cis573.carecoor.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PreferenceUtil {

	public static String getPrimaryAlertNumber(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getString(Const.PREF_PRIMARY_ALERT_NUM, "");
	}
	
	public static void savePrimaryAlertNumber(Context context, String number) {
		Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString(Const.PREF_PRIMARY_ALERT_NUM, number);
		editor.commit();
	}
	
	public static String getSecondaryAlertNumber(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getString(Const.PREF_SECONDARY_ALERT_NUM, "");
	}
	
	public static void saveSecondaryAlertNumber(Context context, String number) {
		Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putString(Const.PREF_SECONDARY_ALERT_NUM, number);
		editor.commit();
	}
	
	public static boolean getEnableAlertCall(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getBoolean(Const.PREF_ALERT_CALL, true);
	}
	
	public static void saveEnableAlertCall(Context context, boolean enabled) {
		Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean(Const.PREF_ALERT_CALL, enabled);
		editor.commit();
	}
	
	public static boolean getEnableAlertText(Context context) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
		return pref.getBoolean(Const.PREF_ALERT_TEXT, true);
	}
	
	public static void saveEnableAlertText(Context context, boolean enabled) {
		Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean(Const.PREF_ALERT_TEXT, enabled);
		editor.commit();
	}
}
