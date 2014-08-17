package com.ytt.sdktab;

import java.io.File;

import android.R.bool;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
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

public class Encryption extends Activity {
	private String tagString = "Encryption";
	private SecurityServer mSecurityServer = null;
	private boolean enabled=false;
	
	private Button enc_string;
	private Button enc_byte;
	private Button enc_txt;
	private Button enc_jpg;
	private Button enc_rar;
	private Button dec_string;
	private Button dec_byte;
	private Button dec_txt;
	private Button dec_jpg;
	private Button dec_rar;
	
	private String text=null;//encrypted string
	private byte[] bytes=null;//encrypted bytes
	private TextView string_encrypted;
	private TextView string_decrypted;
	private TextView byte_encrypted;
	private TextView byte_decrypted;
	

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
					enc_string.setEnabled(enabled);
					enc_byte.setEnabled(enabled);
					enc_txt.setEnabled(enabled);
					enc_jpg.setEnabled(enabled);
					enc_rar.setEnabled(enabled);
					dec_string.setEnabled(enabled);
					dec_byte.setEnabled(enabled);
					dec_txt.setEnabled(enabled);
					dec_jpg.setEnabled(enabled);
					dec_rar.setEnabled(enabled);
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
		
		 enc_string=(Button)findViewById(R.id.btn_enc_string);
		 enc_byte=(Button)findViewById(R.id.btn_enc_byte);
		 enc_txt=(Button)findViewById(R.id.btn_enc_txt);
		 enc_jpg=(Button)findViewById(R.id.btn_enc_jpg);
		 enc_rar=(Button)findViewById(R.id.btn_enc_rar);
		 dec_string=(Button)findViewById(R.id.btn_dec_string);
		 dec_byte=(Button)findViewById(R.id.btn_dec_byte);
		 dec_txt=(Button)findViewById(R.id.btn_dec_txt);
		 dec_jpg=(Button)findViewById(R.id.btn_dec_jpg);
		 dec_rar=(Button)findViewById(R.id.btn_dec_rar);
		
		
		
		enc_string.setEnabled(enabled);
		enc_byte.setEnabled(enabled);
		enc_txt.setEnabled(enabled);
		enc_jpg.setEnabled(enabled);
		enc_rar.setEnabled(enabled);
		dec_string.setEnabled(enabled);
		dec_byte.setEnabled(enabled);
		dec_txt.setEnabled(enabled);
		dec_jpg.setEnabled(enabled);
		dec_rar.setEnabled(enabled);
		
		string_encrypted=(TextView)findViewById(R.id.encryptString);		
		string_decrypted=(TextView)findViewById(R.id.decryptString);
		byte_encrypted=(TextView)findViewById(R.id.encryptByte);
		byte_decrypted=(TextView)findViewById(R.id.decryptByte);
		
		OnClickListener listener=new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {	
				String str_input=((EditText)findViewById(R.id.inputText)).getText().toString();
				byte[] bytes_input=str_input.getBytes();
							
				String[] paths=prepareFile();
				String path_src=paths[0];
				String path_enc=paths[1];
				String path_dec=paths[2];
				
				Button button=(Button)view;
				
				switch (button.getId()) {
				case R.id.btn_enc_string:
					Log.d(tagString, "input string:"+str_input+"input bytes:"+bytes_input.toString());
					text = mSecurityServer.encryptString(str_input);
					string_encrypted.setText(text);
					break;
				case R.id.btn_enc_byte:
					Log.d(tagString, "test if the byte encryption input can be null");
					bytes=mSecurityServer.encryptBytes(bytes_input);
					if (bytes==null) {
						Log.d(tagString, "input bytes is null");
					} else {
						byte_encrypted.setText(new String(bytes));
					}
					
					//byte_encrypted.setText(String.valueOf(bytes));
					break;
				case R.id.btn_enc_txt:
					int flag = mSecurityServer.encryptFile(path_src+"test.txt", path_enc+"test.txt");
					if (flag<0) {
						ErrorInfo.hint(Encryption.this, ErrorInfo.getEncError(Encryption.this, flag));
					}else {
						ErrorInfo.hint(Encryption.this, "Encryption success!");
					}
					break;
				case R.id.btn_enc_jpg:
					int flag1 = mSecurityServer.encryptFile(path_src+"test.jpg", path_enc+"test.jpg");
					if (flag1<0) {
						ErrorInfo.hint(Encryption.this, ErrorInfo.getEncError(Encryption.this, flag1));
					}else {
						ErrorInfo.hint(Encryption.this, "Encryption success!");
					}
					break;
				case R.id.btn_enc_rar:
					int flag2 = mSecurityServer.encryptFile(path_src+"test.rar", path_enc+"test.rar");
					if (flag2<0) {
						ErrorInfo.hint(Encryption.this, ErrorInfo.getEncError(Encryption.this, flag2));
					}else {
						ErrorInfo.hint(Encryption.this, "Encryption success!");
					}
					break;
				case R.id.btn_dec_txt:
					int flag3 = mSecurityServer.decryptFile(path_enc+"test.txt", path_dec+"test.txt");
					if (flag3<0) {
						ErrorInfo.hint(Encryption.this, ErrorInfo.getEncError(Encryption.this, flag3));
					}else {
						ErrorInfo.hint(Encryption.this, "Decryption success!");
					}
					break;
				case R.id.btn_dec_jpg:
					int flag4 = mSecurityServer.decryptFile(path_enc+"test.jpg", path_dec+"test.jpg");
					if (flag4<0) {
						ErrorInfo.hint(Encryption.this, ErrorInfo.getEncError(Encryption.this, flag4));
					}else {
						ErrorInfo.hint(Encryption.this, "Decryption success!");
					}
					break;
				case R.id.btn_dec_rar:
					int flag5=mSecurityServer.decryptFile(path_enc+"test.rar", path_dec+"test.rar");
					if (flag5<0) {
						ErrorInfo.hint(Encryption.this, ErrorInfo.getEncError(Encryption.this, flag5));
					}else {
						ErrorInfo.hint(Encryption.this, "Decryption success!");
					}
					break;
				case R.id.btn_dec_string:
					if (text!=null) {
						String string=mSecurityServer.decryptString(text);
						string_decrypted.setText(string);
					} else {
						ErrorInfo.hint(Encryption.this, "encrypted string is null,please click encryption first!");
					}
					break;
				case R.id.btn_dec_byte:
					if (bytes!=null) {
						byte[] dec_byte=mSecurityServer.decryptBytes(bytes);
						byte_decrypted.setText(new String(dec_byte));
						//byte_decrypted.setText(String.valueOf(dec_byte));
					} else {
						ErrorInfo.hint(Encryption.this, "encrypted bytes is null,please click encryption first!");
					}
					break;
				default:
					ErrorInfo.hint(Encryption.this, "Button id error!");
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
	public void onResume() {
		super.onResume();
		

	}

}
