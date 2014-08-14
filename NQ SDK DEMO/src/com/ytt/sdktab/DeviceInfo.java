package com.ytt.sdktab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nq.enterprise.sdk.AppBridge;
import com.nq.enterprise.sdk.NqDeviceInfoService;
import com.nq.enterprise.sdk.NqDeviceInfoServiceListener;
import com.ytt.sdktab.R.string;

public class DeviceInfo extends Activity {

	private NqDeviceInfoService mDeviceInfoService = null;
	private String tag = "DeviceInfo";
	private TextView showInfo;

	/**
	 * 初始化AppBridge
	 */
	private void initNqService() {
		AppBridge bridge = new AppBridge(getApplicationContext());
		bridge.initService();
		bridge.registerNqDeviceInfoServiceListener(deviceInfoServiceListener); // 注册监听函数
	}

	/**
	 * 监听函数
	 */
	private NqDeviceInfoServiceListener deviceInfoServiceListener = new NqDeviceInfoServiceListener() {

		@Override
		public void onNQDeviceInfoServiceAvailable(NqDeviceInfoService service) {
			// TODO Auto-generated method stub
			if (service != null) {
				mDeviceInfoService = service;
				/*
				 * if (service.getErrorCode() == null) { deviceInfo =
				 * service.getUdInfo(); // 获取device info } else { errorCode =
				 * service.getErrorCode(); // 返回错误码 }
				 */
			}
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deviceinfo);

		initNqService();

		Button devInfo = (Button) findViewById(R.id.devInfo);
		showInfo = (TextView) findViewById(R.id.showInfo);
		 
		devInfo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View button) {
				if (mDeviceInfoService != null) {
					String text;
					
					if (mDeviceInfoService.getErrorCode() == null) {
						text = mDeviceInfoService.getUdInfo(); // 获取device info
					} else {
						String errorCode = mDeviceInfoService.getErrorCode();
						text=ErrorInfo.getErrorInfo(DeviceInfo.this, errorCode);

					}// 返回错误码
					showInfo.setText(text);
				} else {
					ErrorInfo.hint(DeviceInfo.this,"service is null!");
				}
			}

		});

	}

}
