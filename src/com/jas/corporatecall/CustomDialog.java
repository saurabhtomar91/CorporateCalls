package com.jas.corporatecall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import com.jas.corporatecall.broadcast.OutGoingSmsReceiver;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class CustomDialog extends Activity
{

	public static String Callperson_or_work;
	public static String SmsPersonal;
	public static String SmsCorporate;
	private String fileNameSmsCorporate = "SmsLogCorporate";
	private String fileNameSmsPersonal = "SmsLogPersonal";
	Dialog dialog;
	static String messageId = "";
	private static final String CONTENT_SMS = "content://sms/";
	FileOutputStream out;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		showPopup();
	}

	void showPopup()
	{
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.custom_dialog_activity);
		dialog.setTitle(getString(R.string.dialog_title));
		// set the custom dialog components - text, image and button
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		ImageView home = (ImageView) dialog.findViewById(R.id.home);
		ImageView off = (ImageView) dialog.findViewById(R.id.office);
		// home.setImageResource(R.drawable.home);
		// off.setImageResource(R.drawable.office);

		home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Callperson_or_work = "Home";
				Intent intent = getIntent();
				String status = intent.getStringExtra("status");
				if (status.equals("smsService"))
				{
					SmsPersonal = "personal";
					writeToFile(fileNameSmsPersonal, "", getLastSms());
				}
				
				dialog.dismiss();
				finish();
			}
		});

		off.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				Callperson_or_work = "office";
				Intent intent = getIntent();
				String status = intent.getStringExtra("status");
				if (status.equals("smsService"))
				{
					SmsCorporate = "corporate";
					writeToFile(fileNameSmsCorporate, ".txt", getLastSms());
				}
				
				dialog.dismiss();
				finish();
			}
		});

		dialog.show();
	}

	@Override
	public void onBackPressed()
	{
		
	}

	public String getLastSms()
	{

		StringBuffer sb = new StringBuffer();
		// Cursor managedCursor =
		// context.managedQuery(CallLog.Calls.CONTENT_URI, null, null, null,
		// null);

		Uri uriSMSURI = Uri.parse(CONTENT_SMS);
		Cursor curSms = getContentResolver().query(uriSMSURI, null, null, null, null);
		// this will make it point to the first record, which is the last
		// SMS sent
		curSms.moveToNext();

		String message_id = curSms.getString(curSms.getColumnIndex("_id"));
		String type = curSms.getString(curSms.getColumnIndex("type"));

		/**
		 * onChange is fired multiple times for a single SMS, this is to prevent
		 * multiple entries in db.
		 * 
		 */
		if (!message_id.equals(messageId))
		{
			String smsContent = curSms.getString(curSms.getColumnIndex("body"));
			String msisdnWithCountryCodeOrPrefix = curSms.getString(curSms.getColumnIndex("address"));
			String date = curSms.getString(curSms.getColumnIndex("date"));
			Long timestamp = Long.parseLong(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(timestamp);
			Date finaldate = calendar.getTime();
			String smsDate = finaldate.toString();
			int dircode = Integer.parseInt(type);
			sb.append("\nSms Log :");

			// if (dircode == CallLog.Calls.OUTGOING_TYPE)
			{
				sb.append("\nPhone Number:--- " + msisdnWithCountryCodeOrPrefix + " \nMessage :--- " + smsContent + "\nDate :---" + smsDate);
				sb.append("\n----------------------------------");

				// String lastCall = sb.toString();

			}

			// textView.setText(lastCall);
			curSms.close();
			Log.i("MyContentObserver", "Sent SMS saved: " + smsContent);

		}
		String lastCall = sb.toString();
		messageId = message_id;

		return lastCall;

	}

	public void writeToFile(String fileName, String fileType, String body)
	{
		try
		{
			final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Corporate/");

			if (!dir.exists())
			{
				dir.mkdirs();
			}

			final File myFile = new File(dir, fileName + fileType);

			if (!myFile.exists())
			{
				myFile.createNewFile();
			}

			// fos = new FileOutputStream(myFile);

			// fos.write(body.getBytes());
			// fos.close();
			FileWriter writer = new FileWriter(myFile, true);
			writer.append(body);
			writer.flush();
			writer.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
