package com.alexhits.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.View;


public class SplashActivity extends AlexHitsActivity {

	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_splash);
		Handler handler = new Handler();
		Runnable splashRunnable = new Runnable() {
			@Override
			public void run() {
				Intent loginIntent = new Intent(mContext, HomeActivity.class);
				startActivity(loginIntent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
				finish();
			}
		};
		handler.postDelayed(splashRunnable, 3000);
	}
	
	@Override
	public void onClick(View v) {
		
	}
	
}
