package com.alexhits.ui;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.alexhits.core.AlexHitsActivity;

public class SigninActivity extends AlexHitsActivity{

	TextView signUp;
	
	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_signin);
		signUp = (TextView)findViewById(R.id.sign_up);
		signUp.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sign_up:
			Intent signUpIntent = new Intent(mContext, SignupActivity.class);
			startActivity(signUpIntent);
			finish();
			
			break;

		default:
			break;
		}
	}
}
