package com.alexhits.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.alexhits.api.ApiRegister;
import com.alexhits.model.User;
import com.alexhits.ui.HomeActivity;

import android.app.Activity;
import android.app.PendingIntent.OnFinished;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View.OnClickListener;
import android.widget.Toast;

public abstract class AlexHitsActivity extends Activity implements
		OnClickListener {

	public Context mContext = this;
	public CacheHelper cache = CacheHelper.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		customOnCreate();
	}

	public abstract void customOnCreate();

	public void showToast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}

	public void openIntent(Class cls) {
		Intent intent = new Intent(mContext, cls);
		startActivity(intent);
		finish();
	}

	public String convertStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public File initUserDir(int user_id)
	{
		File sd = Environment.getExternalStorageDirectory();
		File dir = new File(sd, Constants.DIR_USERS);
		dir.mkdirs();
		File user_dir = new File(dir, "user_"+user_id);
		user_dir.mkdir();
		
		File downloads_dir = new File(user_dir, "downloads");
		downloads_dir.mkdir();
		
		File quicklist_dir = new File(user_dir, "quicklist");
		quicklist_dir.mkdir();
		
		return user_dir;
	}
	
	public void initUser(int user_id,String fullname,String email)
	{
		cache.currentUser = new User(user_id, fullname, email);
		initUserDir(user_id);
		Intent homeIntent = new Intent(mContext, HomeActivity.class);
		startActivity(homeIntent);
		finish();
	}
	
	// ========= API Functions ========
	// Register API
	public void api_register(String email, String password, String name) {
		ApiRegister regTask = new ApiRegister(this);
		regTask.setParams(email, password, name);
		regTask.execute();
	}

}
