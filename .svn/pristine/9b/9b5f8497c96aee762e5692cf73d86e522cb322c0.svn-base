package com.jas.corporatecall.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

public class Utils implements AppConstants
{

	public static void showToast(Context context, String message)
	{
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
	    toast.show();
		toast.setGravity(Gravity.CLIP_HORIZONTAL | Gravity.CENTER_HORIZONTAL, 0, 0);

	}
	
	public static int convertToDIP(Context context, int pixels) {
	    return Math.round(pixels * context.getResources().getDisplayMetrics()
	      .density);
	    // DIP
	  }

	public static String getDeviceName()
	{
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer))
		{
			return capitalize(model);
		}
		else
		{
			return capitalize(manufacturer) + " " + model;
		}
	}

	private static String capitalize(String s)
	{
		if (s == null || s.length() == 0)
		{
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first))
		{
			return s;
		}
		else
		{
			return Character.toUpperCase(first) + s.substring(1);
		}
	}


	public static boolean isEmpty(String key)
	{
		return (key.toString().trim().equals("") || key.toString().trim().equals("null"));
	}

	public static void hideKeyboard(View view, Context context)
	{
		try
		{
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		} catch (Exception e)
		{}
	}

	public static void showKeyboard(View view, Context context)
	{
		try
		{
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
		} catch (Exception e)
		{}
	}

	public static String readtextFile(Context context)
	{
		String json = "";
		try
		{
			InputStream is = context.getAssets().open("mybook.txt");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		} catch (IOException e)
		{}
		return json.toString();
	}

	public static void CopyStream(InputStream is, OutputStream os)
	{
		final int buffer_size = 1024;
		try
		{
			byte[] bytes = new byte[buffer_size];
			for (;;)
			{
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex)
		{}
	}

	public static int getRoundedValue(double value)
	{
		int roundedValue = 0;
		String temp = String.valueOf(value);
		int index = temp.indexOf('.');
		if (index != -1)
		{
			temp = temp.substring(0, index);
			roundedValue = Integer.parseInt(temp);
			roundedValue += 1;
		}
		else
			roundedValue = (int) value;

		return roundedValue;
	}

	public static int dipToPix(Resources resources, float dip)
	{
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, resources.getDisplayMetrics());
	}

	public static boolean isConnectedToInternet(Context _context)
	{
		ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null)
		{
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED)
					{
						return true;
					}

		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static void showAlert(final Context context, String msg)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setMessage(msg);
		alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				((Activity )context).finish();
			}
		});
		alertDialog.show();
	}

	public static String getDeviceIMEI(Context context)
	{
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager.getDeviceId() != null)
			return telephonyManager.getDeviceId();
		else
			return getMacID(context);
	}

	private static String getMacID(Context context)
	{
		WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = manager.getConnectionInfo();

		return info.getMacAddress();
	}

	public static ArrayList<Integer> getDeviceResolution(Context context)
	{
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		sizes.add(metrics.widthPixels);
		sizes.add(metrics.heightPixels);
		return sizes;
	}

	public static String fileSize(int size)
	{
		if (size == -1)
			return null;
		String hrSize = "";
		double m = size / 1024.0;
		m = m / 1024.0;
		DecimalFormat dec = new DecimalFormat("0.00");
		hrSize = dec.format(m).concat("MB");
		return hrSize;
	}

	public static Bitmap getBitmapFromView(View view)
	{
		Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		Drawable bgDrawable = view.getBackground();
		if (bgDrawable != null)
			bgDrawable.draw(canvas);
		else
			canvas.drawColor(Color.WHITE);
		view.draw(canvas);
		return returnedBitmap;
	}

	public static Typeface getFont(Context context)
	{
		return Typeface.createFromAsset(context.getAssets(), "ZawgyiOne.ttf");
	}

	public static boolean isAboveHONEYCOMB()
	{
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
			return true;
		else
			return false;
	}

	public static String readTextFileFromAssets(Context context, String filePath) throws IOException
	{
		AssetManager assetManager = context.getAssets();
		InputStream is = assetManager.open(filePath);
		return streamToString(is);
	}

	public static String streamToString(InputStream is)
	{
		// InputStreamReader is = new InputStreamReader(is);
		InputStreamReader isr = new InputStreamReader(is);// this class is used
		                                                  // to convert byte
		                                                  // into charater
		StringBuilder br = new StringBuilder();// modifing sequece of charater
		                                       // for build the string
		try
		{
			char buf[] = new char[10000];
			int len = 0;
			while ((len = isr.read(buf)) != -1)// read byte data utill reach the
			                                   // end location
			{
				br.append(buf, 0, len);// add byte data and converted into
				                       // string
			}
		} catch (OutOfMemoryError e)
		{
			e.printStackTrace();
		} catch (Exception ee)
		{
			ee.printStackTrace();

		} finally
		{
			try
			{
				is.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}

		}
		return br.toString();

	}

	public static String formatTime(long milsec)
	{
		{
			milsec = milsec * 1000;
			long current = System.currentTimeMillis();
			long diff = (((long) (current - milsec) / 1000));
			if (diff < 60)
				return "just now";
			int min = (int) (diff / 60);
			if (min < 2)
				return "a min ago";
			if (min < 60)
				return min + " mins ago";
			else if (min < 1440)
			{
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -1);
				int lastday = c.get(Calendar.DAY_OF_MONTH);
				c.setTimeInMillis(milsec);
				int d = c.get(Calendar.DAY_OF_MONTH);
				if (lastday == d)
					return (String) android.text.format.DateFormat.format("MMMM dd, yyyy", new Date(milsec));
				else
					return (String) android.text.format.DateFormat.format("h:mm aa", new Date(milsec));
			}
			else
				return (String) android.text.format.DateFormat.format("MMMM dd, yyyy", new Date(milsec));
		}
	}

	public static String doHttpPostRequestSync(String url, Bundle params) throws Exception
	{
		String response = null;
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (params != null)
		{
			Set<String> parameters = params.keySet();
			Iterator<String> it = parameters.iterator();
			while (it.hasNext())
			{
				String key = it.next();
				if (params.containsKey(key))
				{
					// Log.d(key, params.getString(key));
					nameValuePairs.add(new BasicNameValuePair(key, params.getString(key)));
				}
			}
		}
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse resp = httpClient.execute(post);
		if (resp.getStatusLine().getStatusCode() != 200)
			throw new Exception("Request status code is " + resp.getStatusLine().getStatusCode());
		InputStream inStream = resp.getEntity().getContent();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		response = sb.toString();
		inStream.close();
		return response;
	}
	
}
