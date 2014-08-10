package com.ytt.sdktab;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final TabHost tabHost = getTabHost();
		
		String str_encryption_ch=getString(R.string.str_encryption_ch);
		String str_decryption_ch=getString(R.string.str_decryption_ch);
		String str_app_ch=getString(R.string.str_app_ch);
		String str_config_ch=getString(R.string.str_config_ch);
		String str_sso_ch=getString(R.string.str_sso_ch);
		String str_devInfo_ch=getString(R.string.str_devInfo_ch);

		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(str_encryption_ch+str_decryption_ch)
				.setContent(new Intent(this, FileEncryption.class)));
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(str_app_ch+str_config_ch)
				.setContent(new Intent(this, StringEncryption.class)));
		tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator(str_sso_ch)
				.setContent(new Intent(this, AppConfig.class)));
		tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator(str_devInfo_ch)
				.setContent(new Intent(this,DeviceInfo.class)));

	}

}