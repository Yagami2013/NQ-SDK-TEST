package com.ytt.sdktab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.nq.enterprise.sdk.AppBridge;
import com.nq.enterprise.sdk.NqSecurityService;
import com.nq.enterprise.sdk.NqSecurityServiceListener;
import com.nq.enterprise.sdk.SecurityServer;

public class FileEncryption extends Activity {
	private String TAG = "tabSDK_fileEncryption";
	private SecurityServer mSecurityServer;
	private Button txtEncButton;
	private Button txtDecButton;
	private Button jpgEncButton;
	private Button jpgDecButton;
	private Button rarEncButton;
	private Button rarDecButton;
	private Button interEncButton;
	private Button interDecButton;

	private String Root_Path = Environment.getExternalStorageDirectory()
			.toString() + "/NQSDK";
	private String decrypted;
	private String src;
	private String encrypted;

	private void initNqService() {
		AppBridge bridge = new AppBridge(getApplicationContext());
		bridge.initService();
		bridge.registerNqSecurityServiceListener(nqListener);
	}

	private NqSecurityServiceListener nqListener = new NqSecurityServiceListener() {
		public void onNQSecurityServiceAvailable(NqSecurityService nqService) {
			if (nqService == null)
				Log.d(TAG, "nqService is null");
			try {
				mSecurityServer = nqService.getSecurityServer();
				if (mSecurityServer != null) {
					txtEncButton.setEnabled(true);
					jpgEncButton.setEnabled(true);
					rarEncButton.setEnabled(true);
					interEncButton.setEnabled(true);
					txtDecButton.setEnabled(true);
					jpgDecButton.setEnabled(true);
					rarDecButton.setEnabled(true);
					interDecButton.setEnabled(true);
				} else {
					hint("" + nqService.getErrorCode());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	};

	public void hint(String message) {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				FileEncryption.this);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();

							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private OnClickListener encryptBtnClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			Button button = (Button) v;

			switch (button.getId()) {
			case R.id.encryptTXT:
				src = paths.TXT_SRC;
				encrypted = paths.TXT_ENCRYPTED;
				decrypted = paths.TXT_DECRYPTED;
				Log.d(TAG, "switch case encryptTXT");

				break;
			case R.id.encryptInternalTXT:
				src = paths.Internal_SRC;
				encrypted = paths.Internal_ENCRYPTED;
				decrypted = paths.Internal_DECRYPTED;
				FileService mFileService = new FileService(FileEncryption.this);
				try {
					mFileService.save("Internal file encryption test");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.d(TAG, "switch case encryptTXT");

				break;

			case R.id.encryptJPG:
				src = paths.JPG_SRC;
				encrypted = paths.JPG_ENCRYPTED;
				decrypted = paths.JPG_DECRYPTED;
				Log.d(TAG, "switch case encryptJPG");
				break;

			case R.id.encryptRAR:
				src = paths.RAR_SRC;
				encrypted = paths.RAR_ENCRYPTED;
				decrypted = paths.RAR_DECRYPTED;
				Log.d(TAG, "switch case encryptRAR");
				break;
			}
			if (mSecurityServer == null) {
				Log.d(TAG, "switch case encryptFile function");
			} else {
				mSecurityServer.encryptFile(src, encrypted);
			}

		}
	};
	private OnClickListener dencryptBtnClickListener = new OnClickListener() {
		public void onClick(View v) {
			Button button = (Button) v;
			switch (button.getId()) {
			case R.id.decryptTXT:
				src = paths.TXT_SRC;
				encrypted = paths.TXT_ENCRYPTED;
				decrypted = paths.TXT_DECRYPTED;
				Log.d(TAG, "switch case decryptTXT");
				break;
			case R.id.decryptInternalTXT:
				src = paths.Internal_SRC;
				encrypted = paths.Internal_ENCRYPTED;
				decrypted = paths.Internal_DECRYPTED;
				Log.d(TAG, "switch case encryptTXT");

				break;

			case R.id.decryptJPG:
				src = paths.JPG_SRC;
				encrypted = paths.JPG_ENCRYPTED;
				decrypted = paths.JPG_DECRYPTED;
				Log.d(TAG, "switch case decryptJPG");
				break;

			case R.id.decryptRAR:
				src = paths.RAR_SRC;
				encrypted = paths.RAR_ENCRYPTED;
				decrypted = paths.RAR_DECRYPTED;
				Log.d(TAG, "switch case decryptRAR");
				break;
			}
			if (mSecurityServer != null) {

				mSecurityServer.decryptFile(encrypted, decrypted);
				hint("" + paths.Internal_ENCRYPTED);
			}

		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.security_file);

		txtEncButton = (Button) findViewById(R.id.encryptTXT);
		txtDecButton = (Button) findViewById(R.id.decryptTXT);
		jpgEncButton = (Button) findViewById(R.id.encryptJPG);
		jpgDecButton = (Button) findViewById(R.id.decryptJPG);
		rarEncButton = (Button) findViewById(R.id.encryptRAR);
		rarDecButton = (Button) findViewById(R.id.decryptRAR);
		interEncButton = (Button) findViewById(R.id.encryptInternalTXT);
		interDecButton = (Button) findViewById(R.id.decryptInternalTXT);

		txtEncButton.setOnClickListener(encryptBtnClickListener);
		jpgEncButton.setOnClickListener(encryptBtnClickListener);
		rarEncButton.setOnClickListener(encryptBtnClickListener);
		interEncButton.setOnClickListener(encryptBtnClickListener);

		txtDecButton.setOnClickListener(dencryptBtnClickListener);
		jpgDecButton.setOnClickListener(dencryptBtnClickListener);
		rarDecButton.setOnClickListener(dencryptBtnClickListener);
		interDecButton.setOnClickListener(dencryptBtnClickListener);

		txtEncButton.setEnabled(false);
		jpgEncButton.setEnabled(false);
		rarEncButton.setEnabled(false);
		interEncButton.setEnabled(false);
		txtDecButton.setEnabled(false);
		jpgDecButton.setEnabled(false);
		rarDecButton.setEnabled(false);
		interDecButton.setEnabled(false);

		initNqService();
		Log.d(TAG, Root_Path);
	}

}
