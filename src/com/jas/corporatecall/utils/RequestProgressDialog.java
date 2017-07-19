package com.jas.corporatecall.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.TextView;

import com.jas.corporatecall.R;

public class RequestProgressDialog extends ProgressDialog implements Parcelable
{
	public int reqCode = 0;
	String message;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progress_dialog_);
		TextView msg = (TextView) findViewById(R.id.progresss_dailog_txt);
		msg.setTypeface(Utils.getFont(getContext()));
		msg.setText(message);

	}

	public RequestProgressDialog(Context context, String message, int requesCode)
	{
		super(context);
		reqCode = requesCode;
		this.message = message;
		// setMessage(message);
		setCancelable(true);
		setCanceledOnTouchOutside(false);
	}

	@Override
	public int describeContents()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		// TODO Auto-generated method stub

	}
}
