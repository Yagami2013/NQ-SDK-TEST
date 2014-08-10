package com.ytt.sdktab;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
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
	private TextView ssoContent;
	private NqAppConfigService mNqService = null;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appconfig);

		initNqService();

		getAppConfigButton = (Button) findViewById(R.id.getAppConfig);
		configView = (TextView) findViewById(R.id.cfgcontent);
		// (Button)findViewById(R.id.getSSOConfig);
		ssoContent = (TextView) findViewById(R.id.ssocontent);

		getAppConfigButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String appConfig = mNqService.getAppConfig();
				// SsoConfig mSsoConfig=mNqService.getSsoConfig();
				String errorCode = mNqService.getErrorCode();
				String errorInfo = mNqService.getAppConfig();

				if (appConfig != null) {
					configView.setText(appConfig);
				} else {
					configView.setText(errorInfo);
				}
				/*
				 * if (mSsoConfig!=null) {
				 * ssoContent.setText(mSsoConfig.getSsoUrl()); }else { if
				 * (mNqService.getErrorCode()=="1010") { ssoContent.setText("");
				 * }else { ssoContent.setText(mNqService.getErrorCode()); }
				 * 
				 * }
				 */

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

			if (mService != null) {

				mNqService = mService;
			}
		}
	};

}
