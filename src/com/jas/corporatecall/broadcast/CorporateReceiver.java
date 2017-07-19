package com.jas.corporatecall.broadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.jas.corporatecall.CorporateHomeActivity;
import com.jas.corporatecall.CustomDialog;
import com.jas.corporatecall.CustomPhoneStateListener;
import com.jas.corporatecall.db.DBAdapter;
import com.jas.corporatecall.utils.MySharedPrefrence;
import com.jas.corporatecall.utils.Utils;

@SuppressLint("NewApi")
public class CorporateReceiver extends BroadcastReceiver
{

	public static String outgoingno;
	public static String carrierName;
	DBAdapter db;
	public String ACTION = "android.intent.action.NEW_OUTGOING_CALL";
	public String ACTIONADDED = "android.intent.action.PACKAGE_ADDED";
	static CustomPhoneStateListener phoneStateListener;

	@Override
	public void onReceive(final Context context, Intent intent)
	{
		
		if (intent.getAction().equals(ACTION))
		{

			/* The SMS-Messages are 'hiding' within the extras of the Intent. */
			Bundle bundle = intent.getExtras();
			if (bundle != null)
			{

				outgoingno = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

				TelephonyManager manager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
				carrierName = manager.getNetworkOperatorName();
				Utils.showToast(context, "Network Opeartor Name: "+ carrierName);
				if (phoneStateListener == null)
				{
					Log.e("mykey", "=================phoneStateListener");
					phoneStateListener = new CustomPhoneStateListener(context);
					manager.listen(phoneStateListener, android.telephony.PhoneStateListener.LISTEN_CALL_STATE);
				}

				try
				{
					Thread pageTimer = new Thread() {
						public void run()
						{
							try
							{
								sleep(500);
								Log.v("key", "OnRecive Thread Methods");

							} catch (InterruptedException e)
							{
								e.printStackTrace();
							} finally
							{
								try
								{
									Intent inCust = new Intent(context, CustomDialog.class);
									inCust.putExtra("status", "PhoneCall");
									inCust.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									context.startActivity(inCust);
								} catch (Exception e)
								{
									System.out.println("Hi............... ");

								}

							}
						}
					};
					pageTimer.start();
				} catch (Exception e)
				{
					e.printStackTrace();
				}

			}
			;
		}
		else
		{
			Toast.makeText(context, "Incoming Call", Toast.LENGTH_LONG).show();
		}
//		if (intent.getAction().equals(ACTIONADDED))
//		{
//			if (intent.getPackage().equals("com.infraware.polarisoffice4std"))
//			{
//				context.startActivity(new Intent(context, CorporateHomeActivity.class));
//				MySharedPrefrence.setIsFirst(context, "firstime");
//			}
//		}
	}
	
	
}