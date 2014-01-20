package com.alexhits.ui;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alexhits.core.AlexHitsActivity;

public class SignupActivity extends AlexHitsActivity{

	EditText emailField, nameField, passField, passConfirmField;
	Button signUpBtn;
	
	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_signup);
		emailField = (EditText)findViewById(R.id.email_field);
		nameField = (EditText)findViewById(R.id.name_field);
		passField = (EditText)findViewById(R.id.pass_field);
		passConfirmField = (EditText)findViewById(R.id.pass_confirm);
		signUpBtn = (Button)findViewById(R.id.sign_up_btn);
		signUpBtn.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.sign_up_btn:
			String email = emailField.getEditableText().toString();
			String name = nameField.getEditableText().toString();
			String pass = passField.getEditableText().toString();
			String passConfirm = passConfirmField.getEditableText().toString();
			if(pass.equalsIgnoreCase(passConfirm))
			{
				api_register(email, pass, name);
			}
			else
			{
				showToast("Failed to register");
			}
			break;

		default:
			break;
		}
		
	}
}
