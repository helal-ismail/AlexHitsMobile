package com.alexhits.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.alexhits.api.ApiDownload;
import com.alexhits.api.ApiLogin;
import com.alexhits.api.ApiRegister;
import com.alexhits.api.ApiSocialLogin;
import com.alexhits.core.CacheHelper;
import com.alexhits.core.Constants;
import com.alexhits.model.User;
import com.bugsense.trace.BugSenseHandler;
import com.google.analytics.tracking.android.EasyTracker;

public abstract class AlexHitsActivity extends Activity implements
		OnClickListener {
	
	public Context mContext = this;
	public CacheHelper cache = CacheHelper.getInstance();
	public SharedPreferences prefs;
	public LayoutInflater inflater;
//	public Facebook facebook;
//	public AsyncFacebookRunner mAsyncRunner;
	
	@Override
	protected void onStart() {
		super.onStart();
	    EasyTracker.getInstance(this).activityStart(this);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		 EasyTracker.getInstance(this).activityStop(this);
		 BugSenseHandler.closeSession(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    BugSenseHandler.initAndStartSession(this, "5b238014");
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		inflater = LayoutInflater.from(this);
		initAnimation();
		customOnCreate();

	}

	public abstract void customOnCreate();

	public void showToast(String msg) {
		Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
	}

	public void openIntentForward(Class cls) {
		Intent intent = new Intent(mContext, cls);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
		finish();
	}
	
	public void openIntentBackward(Class cls) {
		Intent intent = new Intent(mContext, cls);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
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
		storeUserInfo(cache.currentUser);
		initUserDir(user_id);
		Intent homeIntent = new Intent(mContext, HomeActivity.class);
		startActivity(homeIntent);
		overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
		finish();
	}
	
	
	
	// ========= API Functions ========
	
	public void api_logout(){
		cache.currentUser = null;
		storeUserInfo(null);
		Intent loginIntent = new Intent(mContext, LoginActivity.class);
		finish();
		startActivity(loginIntent);
		overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

	}
	
	public void api_register(String email, String password, String name) {
		ApiRegister regTask = new ApiRegister(this);
		regTask.setParams(email, password, name);
		regTask.execute();
	}
	
	
	public void api_social_login(String provider, String email, String name) {
		ApiSocialLogin loginTask = new ApiSocialLogin(this);
		loginTask.setParams(provider, email, name);
		loginTask.execute();
	}
	
	public void api_login(String email, String password) {
		ApiLogin loginTask = new ApiLogin(this);
		loginTask.setParams(email, password);
		loginTask.execute();
	}
	
	public void api_download(String songInfo) {
		ApiDownload downloadTask = new ApiDownload(this);
		downloadTask.setParams(songInfo, cache.currentUser.user_id);
		downloadTask.execute();
	}
	
	public void storeUserInfo(User user)
	{
		Editor editor = prefs.edit();
		if (user == null)
		{
			editor.putString("email", "");
			editor.putString("name", "");
			editor.putInt("user_id", -1);
		}
		else
		{
			editor.putString("email", user.email);
			editor.putString("name", user.fullname);
			editor.putInt("user_id", user.user_id);
		}
		editor.commit();
		
	}
	
	public User getStoredUserInfo(){
		String email = prefs.getString("email", null);
		String fullname = prefs.getString("name", null);
		int user_id = prefs.getInt("user_id", -1);
		if ( user_id == -1)
			return null;

		User user =  new User(user_id, fullname, email);
		return user;
	}
	
	
	public void copy(File src, File dst) throws IOException {
	    InputStream in = new FileInputStream(src);
	    OutputStream out = new FileOutputStream(dst);

	    // Transfer bytes from in to out
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = in.read(buf)) > 0) {
	        out.write(buf, 0, len);
	    }
	    in.close();
	    out.close();
	}
	
	
	/// ======= Animations ========
	private Animation mSlideInLeft;
	private Animation mSlideOutRight;
	private Animation mSlideInRight;
	private Animation mSlideOutLeft;
	private Animation mFade;
	private Animation mSlideOutBottom;
	private Animation mSlideInBottom;
	private Animation mSlideOutTop;
	private Animation mSlideInTop;
	private void initAnimation() {
		// animation
		mSlideInLeft = AnimationUtils.loadAnimation(mContext,
				R.anim.push_left_in);
		mSlideOutRight = AnimationUtils.loadAnimation(mContext,
				R.anim.push_right_out);
		mSlideInRight = AnimationUtils.loadAnimation(mContext,
				R.anim.push_right_in);
		mSlideOutLeft = AnimationUtils.loadAnimation(mContext,
				R.anim.push_left_out);
		
		mFade = AnimationUtils.loadAnimation(mContext,
				R.anim.fade_in);
		
		mSlideOutBottom = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_out_bottom);
		
		mSlideInBottom = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_in_bottom);
		
		mSlideOutTop = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_out_top);
		
		mSlideInTop = AnimationUtils.loadAnimation(mContext,
				R.anim.slide_in_top);
	}
	
	private void exitApp(){
		new AlertDialog.Builder(this)
	    .setTitle("Turn off Alexhits")
	    .setMessage("Are you sure you want to turn off Alexhits ?")
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	if (cache.homePlayer != null) {
	    			cache.homePlayer.release();
	    		}
	    		if (cache.homeEnvPlayer != null) {
	    			cache.homeEnvPlayer.release();
	    		}
	    		finish();
	        }
	     })
	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(R.drawable.thumb)
	     .show();
	}
	
	@Override
	public void onBackPressed() {
		exitApp();
	}
	
}
