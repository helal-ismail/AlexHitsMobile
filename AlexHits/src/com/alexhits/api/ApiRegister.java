package com.alexhits.api;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.alexhits.core.Constants;
import com.alexhits.ui.AlexHitsActivity;

public class ApiRegister extends ApiAbstract{

	
	public ApiRegister(AlexHitsActivity activity) {
		super(activity);
		URL = Constants.API_REGISTER;
		dialogText="Registering user info";
		
	}

	public void setParams(String email, String password, String name){
		paramsList.add(new BasicNameValuePair("email", email));
		paramsList.add(new BasicNameValuePair("password", password));
		paramsList.add(new BasicNameValuePair("name", name));

	}
	
	
	
	

	@Override
	public void customOnPostExecute(JSONObject json) {
		
		int user_id = json.optInt("user_id");
		String fullname = json.optString("fullname");
		String email = json.optString("email");
		activity.initUser(user_id, fullname, email);
	}
	
	

}
