package com.jas.corporatecall.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter
{
	private static final String DATABASE_NAME = "CorporateCall";
	private static final int DATABASE_VERSION = 1;

	public static final String KEY_ROW_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_URI = "uri";
	public static final String KEY_NUMBER = "number";
	public static final String DATABASE_TABLE_CONTACT = "contact";

	public Context context;
	public DBHelper dbHelper;
	private String[] whereArgs;
	private static SQLiteDatabase db = null;
	
	public DBAdapter(Context context)
	{
		this.context = context;
		this.dbHelper = new DBHelper(context);
		open();
	}

	public DBAdapter open() throws SQLException
	{
		if (db == null || !db.isOpen())
			db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() throws SQLException
	{
		if (db == null && db.isOpen())
			dbHelper.close();
	}

	public int getCount()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("select").append(" * ").append("from ").append(DATABASE_TABLE_CONTACT);
		Cursor c = db.rawQuery(sb.toString(), null);
		if (c != null)
		{
			return c.getCount();
		}
		else
		{
			return 0;
		}
	}

	/*	public boolean getIsNumberPresent(String number)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("select").append(" * ").append("from ").append(DATABASE_TABLE_CONTACT);
		sb.append(" where ").append(KEY_NUMBER).append(" ='" + number + "'");
		Cursor c = db.rawQuery(sb.toString(), null);
		System.out.println("show the sql Query... " +sb.toString() );
		
		
	}*/

	public String Exist(String number)
	{        
	     String username="";
	     try 
	     { 
	         Cursor c = db.query(DATABASE_TABLE_CONTACT, null, KEY_NUMBER + "=?", new String[]{String.valueOf(number)},null, null, null);                                               
	    /*     if (c == null) 
	         {                        
	             return username;                                   
	         }
	         else {    
	             c.moveToFirst();               
	             username = c.getString(c.getColumnIndex(KEY_NUMBER)); 
	         }        */
	         while(c.moveToNext())
	         {
	        	  username = c.getString(c.getColumnIndex(KEY_NUMBER)); 
	         }
	         c.close();
	     }
	     
	     catch(Exception e)
	     {
	         e.printStackTrace();
	     }
	     
	     return username; 
	     
	}
	
	public boolean CheckIsDataAlreadyInDBorNot(String numbers) 
	{
	    String Query = "Select * from " + DATABASE_TABLE_CONTACT + " where " + KEY_NUMBER + "="  + numbers;
	    System.out.println("Show the Query...." + Query);
	    Cursor cursor = db.rawQuery(Query, null);
	     if(cursor.getCount() >= 0 )
	     {
	    	 	return false;
	     }
	        	return true;
	}
	
	public ArrayList<ContactObject> getListOfContact()
	{
		ArrayList<ContactObject> contactList = new ArrayList<ContactObject>();
		StringBuilder sb = new StringBuilder();
		sb.append("select").append(" * ").append("from ").append(DATABASE_TABLE_CONTACT);
		Cursor c = db.rawQuery(sb.toString(), null);
		if (c != null)
		{
			c.moveToFirst();
			do
			{
				ContactObject contact = new ContactObject(c);
				contactList.add(contact);
			} while (c.moveToNext());
		}
		return contactList;
		
	}

	public boolean insertRetailerDetails(ContactObject contactObject)
	{
		try
		{
			ContentValues retailerValue = new ContentValues();
			//retailerValue.put(KEY_NAME, contactObject.getName());
			retailerValue.put(KEY_NUMBER, contactObject.getNumber());
			//retailerValue.put(KEY_URI, contactObject.getPath());
			int record = db.update(DATABASE_TABLE_CONTACT, retailerValue, KEY_NUMBER + "='" + contactObject.getNumber() + "'", null);
			if (record == 0)
			{
				db.insert(DATABASE_TABLE_CONTACT, null, retailerValue);
			}
			else
			{
				db.delete(DATABASE_TABLE_CONTACT, KEY_NUMBER + " ='" + contactObject.getNumber() + "'", null);
				db.insert(DATABASE_TABLE_CONTACT, null, retailerValue);
			}
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}
	

	
	public boolean updateContact(ContactObject contactObject)
	{
		//ContactObject contactObject = new ContactObject();
		db = dbHelper.getWritableDatabase();
		ContentValues retailerValue = new ContentValues();
		retailerValue.put(KEY_NAME, contactObject.getName());
		retailerValue.put(KEY_NUMBER, contactObject.getNumber());
		retailerValue.put(KEY_URI, contactObject.getPath());
		return db.update(DATABASE_TABLE_CONTACT, retailerValue, KEY_ROW_ID + "=" + contactObject.getRowId(), null) > 0;
		
	}
	
	
	
	public void deleteContact(String number) 
	{
		Log.v("Key", "Show the deleteContact.........  " +  number);
		ContactObject contanct = new ContactObject();
		db = dbHelper.getWritableDatabase();
		db.delete(DATABASE_TABLE_CONTACT, KEY_NUMBER + " = ?", new String[] { String.valueOf(number)});
		Log.v("Key", "Show the deleteContactsssssssssssssssssss.........  ");
		db.close();
	}
	

	private static class DBHelper extends SQLiteOpenHelper
	{

		public DBHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{

			try
			{
				String contact = "create table " + DATABASE_TABLE_CONTACT + " (" + KEY_ROW_ID + " integer primary key autoincrement," + KEY_NAME + " text not null, " + KEY_URI + " text not null, " + KEY_NUMBER + " text not null);";
				db.execSQL(contact);
				Log.e("DBAdapter...", "database is created successful");
			} catch (Exception e)
			{
				e.printStackTrace();
				Log.e("DBAdapter...", "Exception .....");
			}

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			try
			{
				db.execSQL("DROP TABLE IF EXISTS" + DATABASE_TABLE_CONTACT);
				onCreate(db);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}
}
