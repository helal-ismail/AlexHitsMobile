package com.alexhits.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.alexhits.core.AlexHitsActivity;

public class LoginActivity extends AlexHitsActivity {

	ImageButton fbLogin;
	ImageButton twLogin;
	ImageButton googleLogin;
	ImageButton alexhitsLogin;
	ImageButton skipLogin;
	LinearLayout loginList;
	
	
	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_login);
		fbLogin = (ImageButton) findViewById(R.id.login_fb);
		fbLogin.setOnClickListener(this);

		twLogin = (ImageButton) findViewById(R.id.login_tw);
		twLogin.setOnClickListener(this);

		googleLogin = (ImageButton) findViewById(R.id.login_google);
		googleLogin.setOnClickListener(this);

		alexhitsLogin = (ImageButton) findViewById(R.id.login_alexhits);
		alexhitsLogin.setOnClickListener(this);
		
		skipLogin = (ImageButton) findViewById(R.id.login_skip);
		skipLogin.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_fb:

			break;

		case R.id.login_tw:
			
			break;
			
		case R.id.login_google:
			
			break;

		case R.id.login_alexhits:
			Intent signinIntent = new Intent(mContext, SigninActivity.class);
			startActivity(signinIntent);
			finish();
			break;
		case R.id.login_skip:
			Intent homeIntent = new Intent(mContext, HomeActivity.class);
			startActivity(homeIntent);
			finish();
			break;
		default:
			break;
		}
	}

}
