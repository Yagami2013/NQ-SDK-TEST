package com.ytt.sdktab;

import java.io.File;

import android.app.Activity;
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

public class Encryption extends Activity {
	private String tagString = "Encryption";
	private SecurityServer mSecurityServer = null;
	private boolean enabled=false;

	/**
	 * 初始化AppBridge
	 */
	private void initNqService() {
		AppBridge bridge = new AppBridge(getApplicationContext());
		bridge.initService();
		bridge.registerNqSecurityServiceListener(securityServiceListener); // 注册监听函数
	}

	/**
	 * 监听函数
	 */
	private NqSecurityServiceListener securityServiceListener = new NqSecurityServiceListener() {

		@Override
		public void onNQSecurityServiceAvailable(NqSecurityService service) {
			// TODO Auto-generated method stub
			if (service != null) {
				if (service.getErrorCode() == null) {
					enabled=true;
					mSecurityServer = service.getSecurityServer();
				} else {
					String errorCode = service.getErrorCode(); // 返回错误码
					String errorInfo = ErrorInfo.getErrorInfo(Encryption.this, errorCode);					
					ErrorInfo.hint(Encryption.this, errorInfo );
				}
			}
		}
	};
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.security);
		
		initNqService();
		
		Button enc_string=(Button)findViewById(R.id.btn_enc_string);
		Button enc_byte=(Button)findViewById(R.id.btn_enc_byte);
		Button enc_txt=(Button)findViewById(R.id.btn_enc_txt);
		Button enc_jpg=(Button)findViewById(R.id.btn_enc_jpg);
		Button enc_rar=(Button)findViewById(R.id.btn_enc_rar);
		Button dec_string=(Button)findViewById(R.id.btn_dec_string);
		Button dec_byte=(Button)findViewById(R.id.btn_dec_byte);
		Button dec_txt=(Button)findViewById(R.id.btn_dec_txt);
		Button dec_jpg=(Button)findViewById(R.id.btn_dec_jpg);
		Button dec_rar=(Button)findViewById(R.id.btn_dec_rar);
		enc_txt.setEnabled(enabled);
		
		OnClickListener listener=new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {			
							
				String[] paths=prepareFile();
				String path_src=paths[0];
				String path_enc=paths[1];
				String path_dec=paths[2];
				
				Button button=(Button)view;
				
				switch (button.getId()) {
				case R.id.btn_enc_string:					
					break;
				case R.id.btn_enc_byte:					
					break;
				case R.id.btn_enc_txt:
					Log.d(tagString, path_src+"test.txt");
					Log.d(tagString, path_enc+"test.txt");
					mSecurityServer.encryptFile(path_src+"test.txt", path_enc+"test.txt");
					break;
				case R.id.btn_enc_jpg:
					break;
				case R.id.btn_enc_rar:
					break;
				case R.id.btn_dec_txt:
					mSecurityServer.decryptFile(path_enc+"test.txt", path_dec+"test.txt");
					break;
				case R.id.btn_dec_jpg:
					break;
				case R.id.btn_dec_rar:
					break;
				case R.id.btn_dec_string:
					break;
				case R.id.btn_dec_byte:
					break;
				default:
					break;
				}
				
			}
		};
		enc_string.setOnClickListener(listener);
		enc_byte.setOnClickListener(listener);
		enc_txt.setOnClickListener(listener);
		enc_jpg.setOnClickListener(listener);
		enc_rar.setOnClickListener(listener);
		dec_string.setOnClickListener(listener);
		dec_byte.setOnClickListener(listener);
		dec_txt.setOnClickListener(listener);
		dec_jpg.setOnClickListener(listener);
		dec_rar.setOnClickListener(listener);
	}
	public String[] prepareFile() {
		String path_root=Environment.getExternalStorageDirectory().toString();
		String separator = File.separator;								
		
		String path_src=String.format("%s%sNQSDK%ssrc%s", path_root,separator,separator,separator,separator);
		String path_enc=String.format("%s%sNQSDK%sencrypt%s", path_root,separator,separator,separator,separator);
		String path_dec=String.format("%s%sNQSDK%sdecrypt%s", path_root,separator,separator,separator,separator);
		
		/*List<String> mlist = null;
		mlist.add(path_src);
		mlist.add(path_enc);
		mlist.add(path_dec);*/
		String[] paths={path_src,path_enc,path_dec};
		for (String string : paths) {
			File path=new File(string);
			if (!path.exists()) {
				boolean flag=path.mkdirs();
				if (flag) {
					Log.d(tagString, "path created");
				}else {
					Log.d(tagString, "path creating failed");
				}
			}
		}
		return paths;
	}

}
