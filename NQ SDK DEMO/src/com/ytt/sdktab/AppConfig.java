package com.ytt.sdktab;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nq.enterprise.sdk.AppBridge;
import com.nq.enterprise.sdk.NqAppConfigService;
import com.nq.enterprise.sdk.NqAppConfigServiceListener;
import com.nq.enterprise.sdk.SsoConfig;

public class AppConfig extends Activity {

	private Button getAppConfigButton;
	private TextView configView;
	private NqAppConfigService mNqService = null;
	private String tag="AppConfig";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appconfig);

		initNqService();

		getAppConfigButton = (Button) findViewById(R.id.getAppConfig);
		configView = (TextView) findViewById(R.id.cfgcontent);
		//getAppConfigButton.setEnabled(false);


		getAppConfigButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mNqService != null) {
					String text;
					if (mNqService.getErrorCode() == null) {
						text = mNqService.getAppConfig(); // 获取device info
					} else {
						String errorCode = mNqService.getErrorCode();
						text=ErrorInfo.getErrorInfo(AppConfig.this, errorCode);

					}// 返回错误码
					configView.setText(text);
				} else {
					ErrorInfo.hint(AppConfig.this,"service is null!");
				}
			}
		});
	}

	private void initNqService() {
		AppBridge bridge = new AppBridge(getApplicationContext());
		bridge.initService();
		bridge.registerNqAppConfigServiceListener(nqListener);
	}

	private NqAppConfigServiceListener nqListener = new NqAppConfigServiceListener() {
		

		@Override
		public void onNQAppConfigServiceAvailable(NqAppConfigService mService) {
			mNqService = mService;

			/*if (mService != null) {	
				Log.d(tag, mService.getErrorCode());
				if ("1006".equals(mService.getErrorCode())) {
					Log.d(tag, "error code is 1006");
					ErrorInfo.hint(AppConfig.this,"app config not enabled!");
				}else {
					Log.d(tag, "string compare ok");
					getAppConfigButton.setEnabled(true);
					mNqService = mService;
				}
			}*/
		}
	};

}
