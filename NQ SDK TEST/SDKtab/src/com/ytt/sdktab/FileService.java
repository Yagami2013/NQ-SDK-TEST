package com.ytt.sdktab;

import java.io.FileOutputStream;

import android.content.Context;

public class FileService {
	private Context context;
	public FileService(Context context) {
		this.context=context;
	}
	public void save(String text) throws Exception{
		FileOutputStream outputStream=context.openFileOutput("internal_test.txt", Context.MODE_PRIVATE);
		outputStream.write(text.getBytes());
		outputStream.close();
	}

}
