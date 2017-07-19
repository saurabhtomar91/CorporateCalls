package com.jas.corporatecall.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jas.corporatecall.CorporateHomeActivity;
import com.jas.corporatecall.utils.MySharedPrefrence;

public class CorporateInstallReceiver extends BroadcastReceiver
{
	public String ACTION = "android.intent.action.PACKAGE_ADDED";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		String maction = intent.getAction();

		if (intent.getAction().equals(ACTION))
		{
			if (intent.getPackage().equals("com.infraware.polarisoffice4std"))
			{
				context.startActivity(new Intent(context, CorporateHomeActivity.class));
				MySharedPrefrence.setIsFirst(context, "firstime");
			}
		}

	}

}
