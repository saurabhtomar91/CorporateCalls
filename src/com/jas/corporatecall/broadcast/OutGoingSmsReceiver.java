package com.jas.corporatecall.broadcast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;

import com.jas.corporatecall.CustomDialog;
import com.jas.corporatecall.utils.AppConstants;

public final class OutGoingSmsReceiver extends Service implements AppConstants
{

	private static final String CONTENT_SMS = "content://sms/";
	static String messageId = "";
	int checkSms = 1;

	private IntentFilter mIntentFilter;

	private class MyContentObserver extends ContentObserver
	{

		Context context;

		public MyContentObserver(Context context)
		{
			super(null);
			this.context = context;
		}

		@Override
		public void onChange(boolean selfChange)
		{
			super.onChange(selfChange);

			try
			{
				if (checkSms == 1)
				{
					checkSms = 2;
				}
				else if (checkSms == 2)
				{
					Intent inCust = new Intent(context, CustomDialog.class);
					inCust.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					inCust.putExtra("status", "smsService");
					context.startActivity(inCust);
				}
			} catch (Exception e)
			{
				System.out.println("Hi............... ");

			}
		}

		@Override
		public boolean deliverSelfNotifications()
		{
			return false;
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		MyContentObserver contentObserver = new MyContentObserver(getApplicationContext());
		ContentResolver contentResolver = getBaseContext().getContentResolver();
		contentResolver.registerContentObserver(Uri.parse(CONTENT_SMS), true, contentObserver);
		// Log.v("Caller History: Service Started.",
		// "OutgoingSMSReceiverService");
	}

	@Override
	public void onDestroy()
	{
		// Log.v("Caller History: Service Stopped.",
		// "OutgoingSMSReceiverService");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// Log.v("Caller History: Service Started.",
		// "OutgoingSMSReceiverService");
		/**
		 * Constant to return from onStartCommand(Intent, int, int): if this
		 * service's process is killed while it is started (after returning from
		 * onStartCommand(Intent, int, int)), then leave it in the started state
		 * but don't retain this delivered intent. Later the system will try to
		 * re-create the service. Because it is in the started state, it will
		 * guarantee to call onStartCommand(Intent, int, int) after creating the
		 * new service instance; if there are not any pending start commands to
		 * be delivered to the service, it will be called with a null intent
		 * object, so you must take care to check for this. This mode makes
		 * sense for things that will be explicitly started and stopped to run
		 * for arbitrary periods of time, such as a service performing
		 * background music playback.
		 */
		return START_STICKY;

	}

	@Override
	public void onStart(Intent intent, int startid)
	{
		Log.v("Caller History: Service Started.", "OutgoingSMSReceiverService");
	}

	/*
	 * public String getLastCallDetails() {
	 * 
	 * StringBuffer sb = new StringBuffer(); // Cursor managedCursor = //
	 * context.managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, //
	 * null);
	 * 
	 * Uri uriSMSURI = Uri.parse(CONTENT_SMS); Cursor curSms =
	 * getContentResolver().query(uriSMSURI, null, null, null, null); // this
	 * will make it point to the first record, which is the last // SMS sent
	 * curSms.moveToNext();
	 * 
	 * String message_id = curSms.getString(curSms.getColumnIndex("_id"));
	 * String type = curSms.getString(curSms.getColumnIndex("type"));
	 *//**
	 * onChange is fired multiple times for a single SMS, this is to prevent
	 * multiple entries in db.
	 * 
	 */
	/*
	 * if (!message_id.equals(messageId)) { String smsContent =
	 * curSms.getString(curSms.getColumnIndex("body")); String
	 * msisdnWithCountryCodeOrPrefix =
	 * curSms.getString(curSms.getColumnIndex("address")); String date =
	 * curSms.getString(curSms.getColumnIndex("date")); Long timestamp =
	 * Long.parseLong(date); Calendar calendar = Calendar.getInstance();
	 * calendar.setTimeInMillis(timestamp); Date finaldate = calendar.getTime();
	 * String smsDate = finaldate.toString(); int dircode =
	 * Integer.parseInt(type); sb.append("\nSms Log :");
	 * 
	 * // if (dircode == CallLog.Calls.OUTGOING_TYPE) {
	 * sb.append("\nPhone Number:--- " + msisdnWithCountryCodeOrPrefix +
	 * " \nMessage :--- " + smsContent + "\nDate :---" + smsDate);
	 * sb.append("\n----------------------------------");
	 * 
	 * // String lastCall = sb.toString();
	 * 
	 * }
	 * 
	 * // textView.setText(lastCall); curSms.close(); Log.i("MyContentObserver",
	 * "Sent SMS saved: " + smsContent);
	 * 
	 * } String lastCall = sb.toString(); messageId = message_id;
	 * 
	 * return lastCall;
	 * 
	 * }
	 * 
	 * public void writeToFile(String fileName, String body) { try { final File
	 * dir = new
	 * File(Environment.getExternalStorageDirectory().getAbsolutePath() +
	 * "/Corporate/");
	 * 
	 * if (!dir.exists()) { dir.mkdirs(); }
	 * 
	 * final File myFile = new File(dir, fileName + ".txt");
	 * 
	 * if (!myFile.exists()) { myFile.createNewFile(); }
	 * 
	 * // fos = new FileOutputStream(myFile);
	 * 
	 * // fos.write(body.getBytes()); // fos.close(); FileWriter writer = new
	 * FileWriter(myFile, true); writer.append(body); writer.flush();
	 * writer.close(); } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } }
	 */
}