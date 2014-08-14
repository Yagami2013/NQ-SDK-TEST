package com.ytt.sdktab;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nq.enterprise.sdk.AppBridge;
import com.nq.enterprise.sdk.NqSsoService;
import com.nq.enterprise.sdk.NqSsoServiceListener;
import com.nq.enterprise.sdk.SsoConfig;

public class SSO extends Activity {
	private NqSsoService mSsoService = null;
	private String tag = "SSO";
	private TextView showInfo;

	/**
	 * 初始化AppBridge
	 */
	private void initNqService() {
		AppBridge bridge = new AppBridge(getApplicationContext());
		bridge.initService();
		bridge.registerNqSsoServiceListener(ssoServiceListener);
	}

	/**
	 * 监听函数
	 */
	private NqSsoServiceListener ssoServiceListener = new NqSsoServiceListener() {

		@Override
		public void onNQSsoServiceAvailable(NqSsoService service) {
			// TODO Auto-generated method stub
			if (service != null) {
				mSsoService = service;
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
				if (mSsoService != null) {
					String text;
					if (mSsoService.getErrorCode() == null) {						
						SsoConfig config = mSsoService.getSsoConfig();
						//sso is enabled but no url configured
						if (config==null||config.getSsoUrl()==null||"".equals(config.getSsoUrl())) {
							text="sso config is null!";
						} else {
							text=config.getSsoUrl();
						}
					} else {
						String errorCode = mSsoService.getErrorCode();
						text = ErrorInfo.getErrorInfo(SSO.this, errorCode);

					}// 返回错误码
					showInfo.setText(text);
				} else {
					ErrorInfo.hint(SSO.this, "service is null!");
				}
			}

		});

	}

}
