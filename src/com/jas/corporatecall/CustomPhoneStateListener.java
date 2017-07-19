package com.jas.corporatecall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.sax.StartElementListener;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.jas.corporatecall.broadcast.CorporateReceiver;
import com.jas.corporatecall.utils.MySharedPrefrence;
import com.jas.corporatecall.utils.Utils;

public class CustomPhoneStateListener extends PhoneStateListener
{
	int prev_state = 0;
	private static final String TAG = "PhoneStateChanged";
	public static Context context; // Context to make Toast if required
	public int durationIndex;
	String calltypeOut;
	int dircode;
	FileOutputStream out;
	Handler handler;

	public CustomPhoneStateListener(Context context)
	{
		super();
		this.context = context;
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber)
	{
		super.onCallStateChanged(state, incomingNumber);

		Cursor managedCursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
		int callType = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		managedCursor.moveToLast();
		calltypeOut = managedCursor.getString(callType);

		dircode = Integer.parseInt(calltypeOut);

		// String checknumbers = db.Exist(checknumber);
		switch (state) {

			case TelephonyManager.CALL_STATE_RINGING:

				Log.e("mykey", "================= CALL_STATE_RINGING ");
				prev_state = state;
				break;
			case TelephonyManager.CALL_STATE_IDLE:

				if ((prev_state == TelephonyManager.CALL_STATE_OFFHOOK))
				{
					Log.e("mykey", "================= CALL_STATE_IDLE ");

					prev_state = state;
					// Answered Call which is ended
					try
					{
						handler = new Handler();
						handler.postDelayed(new Runnable() {
							public void run()
							{
								try
								{
									if (dircode == CallLog.Calls.OUTGOING_TYPE)
									{		
										Utils.showToast(context, "OutGoing Only");
										context.startService(new Intent(context, ExcelService.class));
									}

								} catch (Exception e)
								{
									e.printStackTrace();
								}
							}
						}, 2000);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				if ((prev_state == TelephonyManager.CALL_STATE_RINGING))
				{
					prev_state = state;
					// Rejected or Missed call
				}
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				Log.e("mykey", "=================CALL_STATE_OFFHOOK");

				prev_state = state;
				// if (checkOffHook == 1)
				// {
				// Log.e("mykey", "=================CALL_STATE_OFFHOOK");
				// isCheck = true;
				// checkOffHook = 2;
				// }
				break;

			default:
				break;
		}

		// super.onCallStateChanged(state, incomingNumber);
		// tm.listen(this, PhoneStateListener.LISTEN_NONE);
		// Utils.showToast(context, "PhoneStateListner Disable");
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

			final File myFile = new File(dir, fileName + fileType);// + ".txt");

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