package com.alexhits.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SigninActivity extends AlexHitsActivity {

	EditText emailField, passwordField;
	TextView signUp;
	Button signIn;

	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_signin);

	

		signUp = (TextView) findViewById(R.id.sign_up);
		signUp.setOnClickListener(this);

		signIn = (Button) findViewById(R.id.sign_in_btn);
		signIn.setOnClickListener(this);

		emailField = (EditText) findViewById(R.id.email_field);
		passwordField = (EditText) findViewById(R.id.password_field);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sign_up:
			Intent signUpIntent = new Intent(mContext, SignupActivity.class);
			startActivity(signUpIntent);
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			finish();
			break;

		case R.id.sign_in_btn:
			String email = emailField.getEditableText().toString();
			String password = passwordField.getEditableText().toString();
			api_login(email, password);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		openIntentBackward(LoginActivity.class);
	}
}
