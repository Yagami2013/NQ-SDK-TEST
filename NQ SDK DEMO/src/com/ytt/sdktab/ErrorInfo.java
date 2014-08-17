package com.ytt.sdktab;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ErrorInfo extends Activity{
	public static String getErrorInfo(Context context,String errorCode){
		if ("".equals(errorCode) || errorCode == null) {
			return "errorCode is null!";
		} else if ("1004".equals(errorCode)) {
			return context.getString(R.string.error_1004);
		} else if ("1005".equals(errorCode)) {
			return context.getString(R.string.error_1005);
		} else if ("1005".equals(errorCode)) {
			return context.getString(R.string.error_1005);
		} else if ("1006".equals(errorCode)) {
			return context.getString(R.string.error_1006);
		} else if ("1007".equals(errorCode)) {
			return context.getString(R.string.error_1007);
		} else if ("1008".equals(errorCode)) {
			return context.getString(R.string.error_1008);
		} else if ("1009".equals(errorCode)) {
			return context.getString(R.string.error_1009);
		} else if ("1010".equals(errorCode)) {
			return context.getString(R.string.error_1010);
		} else if ("1011".equals(errorCode)) {
			return context.getString(R.string.error_1011);
		} else if ("1012".equals(errorCode)) {
			return context.getString(R.string.error_1012);
		} else if ("1013".equals(errorCode)) {
			return context.getString(R.string.error_1013);
		} else if ("1014".equals(errorCode)) {
			return context.getString(R.string.error_1014);
		} else {
			return "unknown error";
		}
	}
	public static void hint(Context context,String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	public static String getEncError(Context context,int errorCode) {
		switch (errorCode) {
		case -1:
			return context.getString(R.string.error_1);
		case -2:
			return context.getString(R.string.error_2);
		case -3:
			return context.getString(R.string.error_3);
		case -4:
			return context.getString(R.string.error_4);
		case -5:
			return context.getString(R.string.error_5);
		default:
			return "unknown error";
		}		
	}
}
