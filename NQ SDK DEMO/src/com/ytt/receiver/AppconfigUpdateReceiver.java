package com.ytt.receiver;

import com.nq.enterprise.sdk.AppBridge;
import com.nq.enterprise.sdk.NqAppConfigService;
import com.nq.enterprise.sdk.NqAppConfigServiceListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AppconfigUpdateReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		AppBridge bridge = new AppBridge(context);
		bridge.initService();
		bridge.registerNqAppConfigServiceListener(new NqAppConfigServiceListener() {
			
			@Override
			public void onNQAppConfigServiceAvailable(NqAppConfigService arg0) {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
