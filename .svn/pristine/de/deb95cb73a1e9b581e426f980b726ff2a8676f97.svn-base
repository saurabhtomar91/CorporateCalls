package com.jas.corporatecall;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.jas.corporatecall.utils.MySharedPrefrence;

public class SplashScreen extends Activity
{
	private final static int MSG_CONTINUE = 1234;
	private final static long DELAY = 5000;
	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle args)
	{
		super.onCreate(args);
		setContentView(R.layout.activity_splash_screen);

		if (MySharedPrefrence.getIsFirst(getApplicationContext()).equals("firstime"))
		{
			nextActivity();
		}
		else
		{
			new YourCustomAsyncTask().execute();
		}

	}

	@Override
	protected void onDestroy()
	{
		// mHandler.removeMessages(MSG_CONTINUE);
		super.onDestroy();
	}

	private void nextActivity()
	{
		startActivity(new Intent(this, CorporateHomeActivity.class));
		finish();
	}

	public void loadMyApk()
	{

		copyAssets();
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/" + "polaris_office.apk")), "application/vnd.android.package-archive");
		startActivityForResult(intent, 4);
		MySharedPrefrence.setIsFirst(getApplicationContext(), "firstime");
		finish();
	}

	private void copyAssets()
	{
		AssetManager assetManager = getAssets();
		String[] files = null;
		try
		{
			files = assetManager.list("");
		} catch (IOException e)
		{
			Log.e("tag", e.getMessage());
		}
		for (String filename : files)
		{
			InputStream in = null;
			OutputStream out = null;
			try
			{
				// fileone=filename;
				in = assetManager.open(filename);
				out = new FileOutputStream("/sdcard/" + filename);
				copyFile(in, out);
				in.close();
				in = null;
				out.flush();
				out.close();
				out = null;
			} catch (Exception e)
			{
				Log.e("tag", e.getMessage());
			}
		}
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException
	{
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1)
		{
			out.write(buffer, 0, read);
		}
	}

	private final Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what) {
				case MSG_CONTINUE:
					nextActivity();
					break;
			}
		}
	};

	private class YourCustomAsyncTask extends AsyncTask<String, Void, Void>
	{
		@Override
		protected void onPreExecute()
		{
			try
			{
				progress = new ProgressDialog(SplashScreen.this);
				progress.setMessage("Please Wait Initializing... ");
				progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progress.setIndeterminate(true);
				progress.show();
			} catch (Exception e)
			{
				e.printStackTrace();// Maybe you should call it in
									// ruinOnUIThread in
			}
			// doInBackGround as suggested from a previous
			// answer
		}

		@Override
		protected Void doInBackground(String... strings)
		{
			try
			{
				loadMyApk();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void params)
		{
			try
			{
				progress.dismiss();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}
}