package com.jas.corporatecall.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPrefrence
{
	private static String jastune_App_PREF = "com.jas.corporate";
	public static final String Auth_TOKEN = "auth_token";
	public static final String EMAIL_ID = "email_id";
	public static final String ISFIRST = "isFirst";
	public static final String IDXCOR = "idxcorporate";
	public static final String IDYCOR ="idycorporate";
	public static final String IDXPER ="idxpersonal";
	public static final String IDYPER ="idypersonal";
	public static final String USER_DETAILS = "user_data";
	public static final String USER_ID = "user_id";
	public static final String CATEGORY_DETAILS = "categories_data";
	public static final String NEWS_STAND_TIMINGS = "timings";
	public static final String PDF_READ_PAGECURL = "pagecurl";
	public static final String PDF_V_SWITCH = "vSwitch";
	public static final String FIRST_TIME = "firsttime";

	public static final String PAGE_NO = "pageno";
	public static final String ADS_URL = "url";
	/**
	 * i am adding preference for testing user count
	 */
	public static final String COUNT_USER = "count_user";
	private static String Theme = "com";

	public static String getAuthToken(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		String login = mSettings.getString(Auth_TOKEN, "");
		return login;
	}

	public static void setAuthToken(Context mContext, String value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putString(Auth_TOKEN, value);
		editor.commit();
		return;
	}

	public static String getEmailId(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		String login = mSettings.getString(EMAIL_ID, "");
		return login;
	}

	public static void setEmailId(Context mContext, String value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putString(EMAIL_ID, value);
		editor.commit();
		return;
	}

	public static String getUserName(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		String login = mSettings.getString(USER_ID, "");
		return login;
	}

	public static void setUserName(Context mContext, String value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putString(USER_ID, value);
		editor.commit();
		return;
	}

	public static String getIsFirst(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		String isfirst = mSettings.getString(ISFIRST, "");
		return isfirst;
	}

	public static void setIsFirst(Context mContext, String value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putString(ISFIRST, value);
		editor.commit();
		return;
	}

	public static int getIdxCorporate(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		int idxcor = mSettings.getInt(IDXCOR, 0);
		//Utils.showToast(mContext, "getIdxcorporate: "+Integer.toString(idxcor));
		return idxcor;

	}

	public static void setIdxCorporate(Context mContext, int value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putInt(IDXCOR, value);
		editor.commit();
		return;

	}
	
	public static int getIdyCorporate(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		int idycor = mSettings.getInt(IDYCOR, 0);
		return idycor;

	}

	public static void setIdyCorporate(Context mContext, int value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putInt(IDYCOR, value);
		editor.commit();
		return;

	}

	
	public static int getIdxPersonal(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		int idxper = mSettings.getInt(IDXPER, 0);
		return idxper;

	}

	public static void setIdxPeronal(Context mContext, int value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putInt(IDXPER, value);
		editor.commit();
		return;

	}
	
	public static int getIdyPersonal(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		int idyper = mSettings.getInt(IDYPER, 0);
		return idyper;

	}
	
	public static void setIdyPersonal(Context mContext, int value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putInt(IDYPER, value);
		editor.commit();
		return;
	}

	public static String getUserDetails(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		String login = mSettings.getString(USER_DETAILS, "");
		return login;
	}

	public static void setUserDetails(Context mContext, String value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putString(USER_DETAILS, value);
		editor.commit();
		return;
	}

	public static String getCategoryDetails(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		String login = mSettings.getString(CATEGORY_DETAILS, "");
		return login;
	}

	public static void setCategoryDetails(Context mContext, String value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putString(CATEGORY_DETAILS, value);
		editor.commit();
		return;
	}

	public static String getNewsStandTiming(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		String login = mSettings.getString(NEWS_STAND_TIMINGS, "");
		return login;
	}

	public static void setNewsStandTiming(Context mContext, String timestamp)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putString(NEWS_STAND_TIMINGS, timestamp);
		editor.commit();
		return;
	}

	public static int getPDFReadStyle(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		int login = mSettings.getInt(PDF_READ_PAGECURL, 3);
		return login;
	}

	public static void setPDFReadStyle(Context mContext, int value)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putInt(PDF_READ_PAGECURL, value);
		editor.commit();
		return;
	}

	public static void setThemeType(Context mContext, int count)
	{
		clearAppPreferences(mContext);
		SharedPreferences mUserCount = mContext.getSharedPreferences(Theme, 0);
		SharedPreferences.Editor editor = mUserCount.edit();
		editor.putInt(COUNT_USER, count);
		editor.commit();
	}

	public static int getThemeType(Context mContext)
	{
		SharedPreferences mUserCount = mContext.getSharedPreferences(Theme, 0);
		return mUserCount.getInt(COUNT_USER, 0);
	}

	public static void clearAppPreferences(Context mContext)
	{
		SharedPreferences mSettings = mContext.getSharedPreferences(Theme, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.clear().commit();
		return;
	}

	public static void setVSwitchLayout(Context context, boolean isVswitch)
	{
		SharedPreferences mSettings = context.getSharedPreferences(jastune_App_PREF, 0);
		SharedPreferences.Editor editor = mSettings.edit();
		editor.putBoolean(PDF_V_SWITCH, isVswitch);
		editor.commit();
	}

	public static boolean getVSwitch(Context context)
	{
		SharedPreferences mSettings = context.getSharedPreferences(jastune_App_PREF, 0);
		return mSettings.getBoolean(PDF_V_SWITCH, false);
	}

}
