/*
 * Copyright (C) 2010, AccessibleNews
 */

package com.jas.corporatecall.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.jas.corporatecall.R;

/**
 * Helps to make Http GET and Http POST request with server
 * 
 * @author Saurabh Tomar
 * */
public class AsyncHttpRequest extends AsyncTask<Void, Void, Void>
{
	public enum Type
	{
		POST, GET, POST_WITH_FILE
	};

	static String TAG = "AsyncHttpRequest";
	DefaultHttpClient mHttpClient = null;
	HttpGet mGet = null;
	HttpPost mPost = null;
	String mResponse = null;
	Exception mError = null;
	String mUrl = "";
	Context mContext = null;
	Bundle mParams = null;
	int mRequestCode = -1;
	Type mType = Type.GET;
	boolean mRunning = false;

	RequestListener mRequestListener = null;

	public interface RequestListener
	{
		void onRequestCompleted(String response, int requestCode);

		void onRequestError(Exception e, int requestCode);

		void onRequestStarted(int requestCode);
	}

	public AsyncHttpRequest(Context context, String baseUrl, Bundle params, int requestCode, Type type)
	{
		this.mParams = params;
		this.mRequestCode = requestCode;
		this.mType = type;
		this.mUrl = baseUrl;
		this.mContext = context;
		mParams.putString("source", "mobile");
		mParams.putString("model", "android");
		mParams.putString("os", Build.VERSION.RELEASE);
	}

	public AsyncHttpRequest()
	{

	}

	public void setRequestListener(RequestListener listener)
	{
		if (listener != null)
			this.mRequestListener = listener;

	}

	public static boolean isConnected(final Context ctx)
	{
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnected();
	}

	@Override
	protected void onCancelled()
	{
		if (mType == Type.GET && mGet != null)
			mGet.abort();
		else if (mType == Type.POST && mPost != null)
			mPost.abort();

		mParams = null;
		mPost = null;
		mGet = null;
		mError = null;
		if (mRequestListener != null)
			mRequestListener.onRequestError(new Exception(mContext.getString(R.string.async_task_error)), mRequestCode);
		mRequestListener = null;
		mRunning = false;
		super.onCancelled();
	}

	public boolean isRunning()
	{
		return mRunning;
	}

	@Override
	protected Void doInBackground(Void... p)
	{
		if (AsyncHttpRequest.isConnected(mContext))

		{
			mRunning = true;
			try
			{
				if (mType == Type.GET)
				{
					mResponse = doHttpGetRequest(mUrl, mParams);
				}
				else if (mType == Type.POST)
					mResponse = doHttpPostRequest(mUrl, mParams);
				else if (mType == Type.POST_WITH_FILE)
					mResponse = doHttpPostWithFileRequest(mUrl, mParams);
			} catch (UnknownHostException e)
			{
				mError = new Exception("Connection to server failed.");
			} catch (Exception e)
			{
				e.printStackTrace();
				mError = e;
			}
		}
		else
		{
			mError = new Exception(mContext.getString(R.string.async_task_no_internet));
		}
		mHandler.sendEmptyMessage(1);
		return null;
	}

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg)
		{
			mRunning = false;
			if (mError != null && mRequestListener != null)
			{
				mRequestListener.onRequestError(mError, mRequestCode);
			}
			else if (mResponse != null && mRequestListener != null)
			{
				mRequestListener.onRequestCompleted(mResponse, mRequestCode);
			}
		}
	};

	@Override
	protected void onPostExecute(Void result)
	{

	}

	@SuppressWarnings("deprecation")
	private String doHttpGetRequest(String url, Bundle params) throws Exception
	{
		String response = null;
		mHttpClient = new DefaultHttpClient();
		if (params != null)
		{
			String paramsString = "";
			Set<String> parameters = params.keySet();
			Iterator<String> it = parameters.iterator();
			while (it.hasNext())
			{
				String key = it.next().trim();
				if (params.containsKey(key))
				{
					if (key.startsWith("<") && key.endsWith(">"))
						paramsString = paramsString + "&" + key.replace("<", "").replace(">", "") + "=" + params.getString(key);
					else
						paramsString = paramsString + "&" + key + "=" + URLEncoder.encode(params.getString(key));
				}
			}
			url = url + "?" + paramsString;
		}

		mGet = new HttpGet(url);
		HttpResponse resp = mHttpClient.execute(mGet);
		if (resp.getStatusLine().getStatusCode() != 200)
			throw new Exception(String.format((mContext.getString(R.string.async_task_request)), resp.getStatusLine().getStatusCode()));
		InputStream inStream = resp.getEntity().getContent();
		response = convertStreamToString(inStream);
		inStream.close();
		return response;
	}

	@Override
	protected void onPreExecute()
	{
		if (mRequestListener != null)
			mRequestListener.onRequestStarted(mRequestCode);
		super.onPreExecute();
	}

	private String doHttpPostRequest(String url, Bundle params) throws Exception
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
					nameValuePairs.add(new BasicNameValuePair(key, params.getString(key)));
				}
			}
		}
		mHttpClient = new DefaultHttpClient();
		mPost = new HttpPost(url);
		mPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse resp = mHttpClient.execute(mPost);
		if (resp.getStatusLine().getStatusCode() != 200)
			throw new Exception(String.format((mContext.getString(R.string.async_task_request)), resp.getStatusLine().getStatusCode()));
		InputStream inStream = resp.getEntity().getContent();
		response = convertStreamToString(inStream);
		inStream.close();
		return response;
	}

	private String doHttpPostWithFileRequest(String url, Bundle params) throws Exception
	{
		String response = null;
		MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		if (params != null)
		{
			Set<String> parameters = params.keySet();
			Iterator<String> it = parameters.iterator();
			FormBodyPart bodyPart;
			while (it.hasNext())
			{
				String key = it.next();
				if (params.containsKey(key))
				{
					if (key.equals("uploadedFile"))
					{
						try
						{
							Bitmap bm = BitmapFactory.decodeFile(params.getString(key));
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							bm.compress(CompressFormat.PNG, 100, bos);
							byte[] data = bos.toByteArray();
							ByteArrayBody bab = new ByteArrayBody(data, key + ".jpg");
							reqEntity.addPart(key, bab);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					else if (key.equals("profilePic") && !params.getString(key).equals(""))
					{
						Bitmap bm = BitmapFactory.decodeFile(params.getString(key));
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						bm.compress(CompressFormat.JPEG, 75, bos);
						byte[] data = bos.toByteArray();
						ByteArrayBody bab = new ByteArrayBody(data, key + ".jpg");
						reqEntity.addPart(key, bab);
					}
					else
					{
						bodyPart = new FormBodyPart(key, new StringBody(params.getString(key)));
						reqEntity.addPart(bodyPart);
					}
				}
			}
		}
		mHttpClient = new DefaultHttpClient();
		mPost = new HttpPost(url);
		mPost.setEntity(reqEntity);
		HttpResponse resp = mHttpClient.execute(mPost);
		if (resp.getStatusLine().getStatusCode() != 200)
			throw new Exception(String.format((mContext.getString(R.string.async_task_request)), resp.getStatusLine().getStatusCode()));
		InputStream inStream = resp.getEntity().getContent();
		response = convertStreamToString(inStream);
		inStream.close();
		return response;
	}

	private String convertStreamToString(InputStream instream)
	{
		String response = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
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
		} finally
		{
			try
			{
				instream.close();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		response = sb.toString();
		return response.trim();
	}

}
