package com.jas.corporatecall.db;

import android.database.Cursor;

public class ContactObject
{

	private String name;
	private String number;
	private String Path;
    private String Rowid;
	public ContactObject()
	{
	}

	public ContactObject(Cursor c)
	{
		
		setName(c.getString(c.getColumnIndex(DBAdapter.KEY_NAME)));
		setNumber(c.getString(c.getColumnIndex(DBAdapter.KEY_NUMBER)));
		setPath(c.getString(c.getColumnIndex(DBAdapter.KEY_URI)));
		setRowId(c.getString(c.getColumnIndex(DBAdapter.KEY_ROW_ID)));
	}

	
	public String getRowId()
	{
		return Rowid;
	}
	
	public void setRowId(String Row)
	{
		Rowid= Row;
	}
	
	public String getPath()
	{
		return Path;
	}

	public void setPath(String path)
	{
		Path = path;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

}
