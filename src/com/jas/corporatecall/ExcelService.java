package com.jas.corporatecall;

import java.io.File;
import java.io.FileInputStream;
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
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.jas.corporatecall.broadcast.CorporateReceiver;
import com.jas.corporatecall.utils.MySharedPrefrence;
import com.jas.corporatecall.utils.Utils;

public class ExcelService extends Service
{

	//private static int idxp = 0;
	private static int idyp = 0;
	// private static int idx = 0;
	private static int idy = 0;
	// private static File myFile;
	private Context context;
	private HSSFWorkbook wbcorporate;
	private Cell cellCorporate;
	private HSSFSheet sheetCorporate;
	private Row rowCorporate;
	CellStyle cssCorporate;
	Font fontCorporate;
	private HSSFWorkbook wbPersonal;
	private Cell cellPersonal;
	private HSSFSheet sheetPersonal;
	private Row rowPersonal;
	CellStyle cssPersonal;
	Font fontPersonal;
	POIFSFileSystem myFileSystem;
	private String fileNameCorporate = "CorporateCallLog";
	private String fileNamePersonal = "cscriptpersnlg";
	private int totalKyats = 0;
	public int durationIndex;
	String callName;
	String phNumber;
	String callType;
	String dateTime;
	String actualCallDuration;
	String typeOutgoing;
	CorporateReceiver myreciver = new CorporateReceiver();
	FileOutputStream out;

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId)
	{
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		System.out.println("WebService Start ID=" + startId);

		final int currentID = startId;
		final Intent currentIntent = intent;
		try
		{

			if (CustomDialog.Callperson_or_work.equals("office"))
			{
				getLastCallDetails();
				Log.e("mykey", "================= office");
				try
				{
					createExcelSheetCorporate(fileNameCorporate, ".xls");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				// writeToFile(fileNameCorporate,
				// ".txt", getLastCallDetails());
			}
			if (CustomDialog.Callperson_or_work.equals("Home"))
			{
				getLastCallDetails();
				Log.e("mykey", "================= Home");
				createExcelSheetPersonal(fileNamePersonal, "");
				// writeToFile(fileNamePersonal, "",
				// getLastCallDetails());
			}
			if (callName == null)
			{

				Intent nintent = new Intent(ContactsContract.Intents.SHOW_OR_CREATE_CONTACT, Uri.parse("tel:" + phNumber));
				intent.putExtra(ContactsContract.Intents.EXTRA_FORCE_CREATE, true);
				nintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				getApplication().startActivity(nintent);
				Utils.showToast(getApplication(), "Please Add Contact for Future Calls");

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			stopSelf(currentID);
		}

		return START_NOT_STICKY;
	}

	@SuppressLint("SimpleDateFormat")
	public String getLastCallDetails()
	{

		StringBuffer sb = new StringBuffer();
		Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		sb.append("Call Log :");

		managedCursor.moveToLast();
		try
		{
			callName = managedCursor.getString(managedCursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		phNumber = managedCursor.getString(number);
		callType = managedCursor.getString(type);
		String callDate = managedCursor.getString(date);
		Date callDayTime = new Date(Long.valueOf(callDate));
		Format formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss aa");
		dateTime = formatter.format(callDayTime);
		String callDuration = managedCursor.getString(duration);
		int orgDuration = Integer.parseInt(callDuration);
		actualCallDuration = getDurationString(orgDuration);

		typeOutgoing = null;
		int dircode = Integer.parseInt(callType);

		if (dircode == CallLog.Calls.OUTGOING_TYPE)
		{
			typeOutgoing = "OUTGOING";
			sb.append("\nContact Name:--- " + callName + "\nPhone Number:--- " + phNumber + " \nCall Type:--- " + typeOutgoing + " \nCall Date:--- " + dateTime + " \nCall duration :--- " + actualCallDuration + "\n Call Cost : -- " + totalKyats + " Kyats");
			Log.e("mykey", "=================Call Log" + sb);
			sb.append("\n----------------------------------");

			// String lastCall = sb.toString();

		}
		String lastCall = sb.toString();
		// textView.setText(lastCall);
		managedCursor.close();
		// callCost;

		return lastCall;

	}

	private void createExcelSheetCorporate(String fileName, String fileType) throws IOException
	{
		try
		{
			final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Corporate/");

			if (!dir.exists())
			{
				dir.mkdirs();
			}

			File myFile = new File(dir, fileName + fileType);// + ".txt");

			int idx;
			if (!myFile.exists())
			{
				// Row and column indexes Zero Because File is Deleted or New
				// Created,Set the Indexes Zero
				idx = 0;
				MySharedPrefrence.setIdxCorporate(getApplicationContext(), idx);
				idy = 0;
				// MySharedPrefrence.setIdyCorporate(context, idy);

				myFile.createNewFile();

				// New Workbook
				wbcorporate = new HSSFWorkbook();

				cellCorporate = null;

				// Cell style for header row
				CellStyle cs = wbcorporate.createCellStyle();
				cs.setFillForegroundColor(IndexedColors.LIME.getIndex());
				cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				fontCorporate = wbcorporate.createFont();
				fontCorporate.setBoldweight(Font.BOLDWEIGHT_BOLD);
				fontCorporate.setFontHeightInPoints((short) 8);
				cs.setFont(fontCorporate);

				// Cell style for summary row
				cssCorporate = wbcorporate.createCellStyle();
				fontCorporate = wbcorporate.createFont();
				fontCorporate.setBoldweight(Font.BOLDWEIGHT_BOLD);
				fontCorporate.setFontHeightInPoints((short) 6);
				cssCorporate.setFont(fontCorporate);
				
				// New Sheet
				sheetCorporate = null;
				sheetCorporate = (HSSFSheet) wbcorporate.createSheet("Calculate Corporate Call");

				// Row and column indexes
				// idx = 0;
				// idy = 0;

				// Generate column headings
				rowCorporate = sheetCorporate.createRow(MySharedPrefrence.getIdxCorporate(getApplicationContext()));
				cellCorporate = rowCorporate.createCell(idy);
				cellCorporate.setCellValue("Contact Name");
				cellCorporate.setCellStyle(cs);
				sheetCorporate.setColumnWidth(idy, 10 * 300);
				idy++;
				
				cellCorporate = rowCorporate.createCell(idy);
				cellCorporate.setCellValue("Phone Number");
				cellCorporate.setCellStyle(cs);
				sheetCorporate.setColumnWidth(idy, 10 * 300);
				idy++;
			
				cellCorporate = rowCorporate.createCell(idy);
				cellCorporate.setCellValue("Call Type");
				cellCorporate.setCellStyle(cs);
				sheetCorporate.setColumnWidth(idy, 10 * 300);
				idy++;
				
				cellCorporate = rowCorporate.createCell(idy);
				cellCorporate.setCellValue("Call Date");
				cellCorporate.setCellStyle(cs);
				sheetCorporate.setColumnWidth(idy, 10 * 300);
				idy++;
		
				cellCorporate = rowCorporate.createCell(idy);
				cellCorporate.setCellValue("Call Duration");
				cellCorporate.setCellStyle(cs);
				sheetCorporate.setColumnWidth(idy, 10 * 300);
				idy++;
				
				cellCorporate = rowCorporate.createCell(idy);
				cellCorporate.setCellValue("Call Cost");
				cellCorporate.setCellStyle(cs);
				sheetCorporate.setColumnWidth(idy, 10 * 250);
				idy++;
				
			}
			else
			{
				FileInputStream myInput = new FileInputStream(myFile);

				// Create a POIFSFileSystem object
				 myFileSystem = new POIFSFileSystem(myInput);

				// Create a workbook using the File System
				wbcorporate = new HSSFWorkbook(myFileSystem);

				fontCorporate = wbcorporate.createFont();
				
				// Cell style for summary row
				cssCorporate = wbcorporate.createCellStyle();
				fontCorporate = wbcorporate.createFont();
				fontCorporate.setBoldweight(Font.BOLDWEIGHT_BOLD);
				fontCorporate.setFontHeightInPoints((short) 6);
				cssCorporate.setFont(fontCorporate);
				
				// Get the first sheet from workbook
				sheetCorporate = wbcorporate.getSheetAt(0);
			}
			
			// Skip 1 rows and reset column
			int idxget = MySharedPrefrence.getIdxCorporate(getApplicationContext());
			idx = idxget + 1;
			MySharedPrefrence.setIdxCorporate(getApplicationContext(), idx);
			Utils.showToast(getApplicationContext(), "Row Index Number:" + idx);
			idy = 0;

			Log.e("Row and Column Index ---------------------", "idx" + idx + ",,," + "idy" + idy);
			// Populate detail row data
			// int firstRow = idx + 1;
			
			rowCorporate = sheetCorporate.createRow(MySharedPrefrence.getIdxCorporate(getApplicationContext()));
			cellCorporate = rowCorporate.createCell(idy);
			cellCorporate.setCellStyle(cssCorporate);
			cellCorporate.setCellValue(callName);
			idy++;
		
			cellCorporate = rowCorporate.createCell(idy);
			cellCorporate.setCellStyle(cssCorporate);
			cellCorporate.setCellValue(phNumber);
			idy++;
			
			cellCorporate = rowCorporate.createCell(idy);
			cellCorporate.setCellStyle(cssCorporate);
			cellCorporate.setCellValue(typeOutgoing);
			idy++;
			
			cellCorporate = rowCorporate.createCell(idy);
			cellCorporate.setCellStyle(cssCorporate);
			cellCorporate.setCellValue(dateTime);
			idy++;
		
			cellCorporate = rowCorporate.createCell(idy);
			cellCorporate.setCellStyle(cssCorporate);
			cellCorporate.setCellValue(actualCallDuration);
			idy++;
		
			cellCorporate = rowCorporate.createCell(idy);
			cellCorporate.setCellStyle(cssCorporate);
			cellCorporate.setCellValue(totalKyats);
			idy++;
		

			sheetCorporate.protectSheet("jasadmin");
			FileOutputStream out = new FileOutputStream(myFile);
			wbcorporate.write(out);
			out.close();
			Utils.showToast(getApplicationContext(), "Excel written successfully..");
			System.out.println("Excel written successfully..");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void createExcelSheetPersonal(String fileName, String fileType)
	{
		try
		{
			final File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Corporate/");

			if (!dir.exists())
			{
				dir.mkdirs();
			}

			File myFile = new File(dir, fileName + fileType);// + ".txt");

			int idxp;
			if (!myFile.exists())
			{
				// Row and column indexes Zero Because File is Deleted or New
				// Created,Set the Indexes Zero
				idxp = 0;
				MySharedPrefrence.setIdxPeronal(getApplicationContext(), idxp);
				idyp = 0;
				// MySharedPrefrence.setIdyCorporate(context, idy);

				myFile.createNewFile();

				// New Workbook
				wbPersonal = new HSSFWorkbook();

				cellPersonal = null;

				// Cell style for header row
				CellStyle cs = wbPersonal.createCellStyle();
				cs.setFillForegroundColor(IndexedColors.LIME.getIndex());
				cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				fontPersonal = wbPersonal.createFont();
				fontPersonal.setBoldweight(Font.BOLDWEIGHT_BOLD);
				fontPersonal.setFontHeightInPoints((short) 8);
				cs.setFont(fontPersonal);

				// Cell style for summary row
				cssPersonal = wbPersonal.createCellStyle();
				fontPersonal = wbPersonal.createFont();
				fontPersonal.setBoldweight(Font.BOLDWEIGHT_BOLD);
				fontPersonal.setFontHeightInPoints((short) 6);
				cssPersonal.setFont(fontPersonal);
				
				// New Sheet
				sheetPersonal = null;
				sheetPersonal = (HSSFSheet) wbPersonal.createSheet("Calculate Corporate Call");

				// Row and column indexes
				// idx = 0;
				// idy = 0;

				// Generate column headings
				rowPersonal = sheetPersonal.createRow(MySharedPrefrence.getIdxPersonal(getApplicationContext()));
				cellPersonal = rowPersonal.createCell(idyp);
				cellPersonal.setCellValue("Contact Name");
				cellPersonal.setCellStyle(cs);
				sheetPersonal.setColumnWidth(idyp, 10 * 300);
				idyp++;
				
				cellPersonal = rowPersonal.createCell(idyp);
				cellPersonal.setCellValue("Phone Number");
				cellPersonal.setCellStyle(cs);
				sheetPersonal.setColumnWidth(idyp, 10 * 300);
				idyp++;
			
				cellPersonal = rowPersonal.createCell(idyp);
				cellPersonal.setCellValue("Call Type");
				cellPersonal.setCellStyle(cs);
				sheetPersonal.setColumnWidth(idyp, 10 * 300);
				idyp++;
				
				cellPersonal = rowPersonal.createCell(idyp);
				cellPersonal.setCellValue("Call Date");
				cellPersonal.setCellStyle(cs);
				sheetPersonal.setColumnWidth(idyp, 10 * 300);
				idyp++;
		
				cellPersonal = rowPersonal.createCell(idyp);
				cellPersonal.setCellValue("Call Duration");
				cellPersonal.setCellStyle(cs);
				sheetPersonal.setColumnWidth(idyp, 10 * 300);
				idyp++;
				
				cellPersonal = rowPersonal.createCell(idyp);
				cellPersonal.setCellValue("Call Cost");
				cellPersonal.setCellStyle(cs);
				sheetPersonal.setColumnWidth(idyp, 10 * 250);
				idyp++;
				
			}
			else
			{
				FileInputStream myInput = new FileInputStream(myFile);

				// Create a POIFSFileSystem object
				 myFileSystem = new POIFSFileSystem(myInput);

				// Create a workbook using the File System
				wbPersonal = new HSSFWorkbook(myFileSystem);

				fontPersonal = wbPersonal.createFont();
				
				// Cell style for summary row
				cssPersonal = wbPersonal.createCellStyle();
				fontPersonal = wbPersonal.createFont();
				fontPersonal.setBoldweight(Font.BOLDWEIGHT_BOLD);
				fontPersonal.setFontHeightInPoints((short) 6);
				cssPersonal.setFont(fontPersonal);
				
				// Get the first sheet from workbook
				sheetPersonal = wbPersonal.getSheetAt(0);
			}
			
			// Skip 1 rows and reset column
			int idxget = MySharedPrefrence.getIdxPersonal(getApplicationContext());
			idxp = idxget + 1;
			MySharedPrefrence.setIdxPeronal(getApplicationContext(), idxp);
			Utils.showToast(getApplicationContext(), "Row Index Number:" + idxp);
			idyp = 0;

			Log.e("Row and Column Index ---------------------", "idx" + idxp + ",,," + "idy" + idyp);
			// Populate detail row data
			// int firstRow = idx + 1;
			
			rowPersonal = sheetPersonal.createRow(MySharedPrefrence.getIdxPersonal(getApplicationContext()));
			cellPersonal = rowPersonal.createCell(idyp);
			cellPersonal.setCellStyle(cssPersonal);
			cellPersonal.setCellValue(callName);
			idyp++;
		
			cellPersonal = rowPersonal.createCell(idyp);
			cellPersonal.setCellStyle(cssPersonal);
			cellPersonal.setCellValue(phNumber);
			idyp++;
			
			cellPersonal = rowPersonal.createCell(idyp);
			cellPersonal.setCellStyle(cssPersonal);
			cellPersonal.setCellValue(typeOutgoing);
			idyp++;
			
			cellPersonal = rowPersonal.createCell(idyp);
			cellPersonal.setCellStyle(cssPersonal);
			cellPersonal.setCellValue(dateTime);
			idyp++;
		
			cellPersonal = rowPersonal.createCell(idyp);
			cellPersonal.setCellStyle(cssPersonal);
			cellPersonal.setCellValue(actualCallDuration);
			idyp++;
		
			cellPersonal = rowPersonal.createCell(idyp);
			cellPersonal.setCellStyle(cssPersonal);
			cellPersonal.setCellValue(totalKyats);
			idyp++;
		

			sheetPersonal.protectSheet("jasadmin");
			FileOutputStream out = new FileOutputStream(myFile);
			wbPersonal.write(out);
			out.close();
			Utils.showToast(getApplicationContext(), "Excel written successfully..");
			System.out.println("Excel written successfully..");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String getDurationString(int seconds)
	{

		int hours = seconds / 3600;
		int minutes = (seconds % 3600) / 60;
		seconds = seconds % 60;

		int hourKyats = Integer.parseInt(twoDigitString(hours)) * 60 * 50;
		int minKyats = Integer.parseInt(twoDigitString(minutes)) * 50;
		int secKyats = Integer.parseInt(twoDigitString(seconds));

		if (secKyats > 0)
		{
			secKyats = 50;
		}
		totalKyats = hourKyats + minKyats + secKyats;
		return twoDigitString(hours) + " : " + twoDigitString(minutes) + " mins" + " : " + twoDigitString(seconds) + " secs";
	}

	private String twoDigitString(int number)
	{

		if (number == 0)
		{
			return "00";
		}

		if (number / 10 == 0)
		{
			return "0" + number;
		}

		return String.valueOf(number);
	}

	public void write(String log)
	{
		try
		{
			out = getApplicationContext().openFileOutput("MyCallLog", Context.MODE_APPEND);
			out.write(log.getBytes());
			out.close();
		} catch (Exception e)
		{
			// no file
			e.printStackTrace();
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

	@Override
	public void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
