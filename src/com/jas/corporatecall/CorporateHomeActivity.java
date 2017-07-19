package com.jas.corporatecall;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;

import com.jas.corporatecall.broadcast.OutGoingSmsReceiver;

public class CorporateHomeActivity extends Activity
{

	private String fileNameAllContacts = "AllContactDetails";
	private String fileType = ".txt";
	private String fileBody;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		// new contactOperation().execute("");
		Intent showCallLog = new Intent();
		showCallLog.setAction(Intent.ACTION_VIEW);
		showCallLog.setType(CallLog.Calls.CONTENT_TYPE);
		startActivity(showCallLog);
		Intent inOutGoingService = new Intent(this, OutGoingSmsReceiver.class);
		startService(inOutGoingService);
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	public void readContacts()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("......Contact Details.....");
		ContentResolver cr = getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		String phone = null;
		String emailContact = null;
		String emailType = null;
		String image_uri = "";
		Bitmap bitmap = null;
		if (cur.getCount() > 0)
		{
			while (cur.moveToNext())
			{
				String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				image_uri = cur.getString(cur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
				if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
				{
					System.out.println("name : " + name + ", ID : " + id);
					sb.append("\n Contact Name:" + name);
					Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
					while (pCur.moveToNext())
					{
						phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						sb.append("\n Phone number:" + phone);
						System.out.println("phone" + phone);
					}
					pCur.close();

					Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[] { id }, null);
					while (emailCur.moveToNext())
					{
						emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
						emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
						sb.append("\nEmail:" + emailContact + "Email type:" + emailType);
						System.out.println("Email " + emailContact + " Email Type : " + emailType);

					}

					emailCur.close();
				}

				/*
				 * if (image_uri != null) {
				 * System.out.println(Uri.parse(image_uri)); try { bitmap =
				 * MediaStore.Images.Media.getBitmap(this.getContentResolver(),
				 * Uri.parse(image_uri)); sb.append("\n Image in Bitmap:" +
				 * bitmap); System.out.println(bitmap);
				 * 
				 * } catch (FileNotFoundException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); } catch (IOException e) { //
				 * TODO Auto-generated catch block e.printStackTrace(); }
				 * 
				 * }
				 */

				sb.append("\n........................................");
			}
			fileBody = sb.toString();
			cur.close();
			// textDetail.setText(sb);
		}
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

	private class contactOperation extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params)
		{
			for (int i = 0; i < 5; i++)
			{
				try
				{
					Thread.sleep(1000);
					Log.d("UI thread", "I am the UI thread");
					readContacts();
					writeToFile(fileNameAllContacts, fileType, fileBody);
				} catch (InterruptedException e)
				{
					Thread.interrupted();
				}
			}
			return "Executed";
		}

		@Override
		protected void onPostExecute(String result)
		{

			// might want to change "executed" for the returned string passed
			// into onPostExecute() but that is upto you
		}

		@Override
		protected void onPreExecute()
		{
		}

		@Override
		protected void onProgressUpdate(Void... values)
		{
		}
	}
}
