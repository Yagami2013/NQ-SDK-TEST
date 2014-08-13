package com.ytt.sdktab;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nq.enterprise.sdk.AppBridge;
import com.nq.enterprise.sdk.NqSecurityService;
import com.nq.enterprise.sdk.NqSecurityServiceListener;
import com.nq.enterprise.sdk.SecurityServer;

public class StringEncryption extends Activity {

	private String TAG = "StringEncryption";

	private SecurityServer mSecurityServer;

	private EditText toBeEncrypt;
	private TextView stringEncrypted;
	private TextView byteEncrypted;
	private Button stringEncButton;
	private Button byteEncButton;
	private Button stringDecButton;
	private Button byteDecButton;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.security_string);

		toBeEncrypt = (EditText) findViewById(R.id.inputText);
		stringEncButton = (Button) findViewById(R.id.encryptStringButton);
		stringEncrypted = (TextView) findViewById(R.id.encryptString);
		byteEncButton = (Button) findViewById(R.id.encryptByteButton);
		byteEncrypted = (TextView) findViewById(R.id.encryptByte);
		stringDecButton = (Button) findViewById(R.id.decryptStringButton);
		byteDecButton = (Button) findViewById(R.id.decryptByteButton);

		stringEncButton.setOnClickListener(stringEncListener);
		byteEncButton.setOnClickListener(byteEncListener);
		OnClickListener l = null;
		stringDecButton.setOnClickListener(l);
		byteDecButton.setOnClickListener(l);
		stringEncButton.setClickable(false);
		byteEncButton.setClickable(false);

		initNqService();

	}

	OnClickListener stringEncListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			String mString = toBeEncrypt.getText().toString();

			Log.d(TAG, "mString=" + mString);
			try {
				Log.d(TAG, "mSecurityServer=" + mSecurityServer.toString());
				String text = mSecurityServer.encryptString(mString);
				stringEncrypted.setText(text);
			} catch (Exception e) {
				e.printStackTrace();
				// stringEncrypted.setText(mNqService.getEncryptErrorCode());
			}

		}
	};

	OnClickListener byteEncListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String mString = toBeEncrypt.getText().toString().trim();
			byte[] mBytes = mString.getBytes();

			try {
				byte[] b = mSecurityServer.encryptBytes(mBytes);
				String text = new String(b);
				byteEncrypted.setText(text);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	};

	private void initNqService() {
		AppBridge bridge = new AppBridge(getApplicationContext());
		bridge.initService();
		bridge.registerNqSecurityServiceListener(nqListener);
	}

	private NqSecurityServiceListener nqListener = new NqSecurityServiceListener() {
		@Override
		public void onNQSecurityServiceAvailable(NqSecurityService nqService) {
			if (nqService != null) {
				if (nqService.getErrorCode() == "1007") {
					Log.d(TAG, "中文");
				} else {
					Log.d(TAG, nqService.getErrorCode());
					mSecurityServer = nqService.getSecurityServer();
					if (mSecurityServer != null) {
						stringEncButton.setClickable(true);
						byteEncButton.setClickable(true);
					}
				}
			} else {
				Log.d(TAG, "nqService is null!");
			}

		}
	};

}
