package com.alexhits.api;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.alexhits.core.Constants;
import com.alexhits.ui.AlexHitsActivity;

public class ApiSocialLogin extends ApiAbstract{

	public ApiSocialLogin(AlexHitsActivity activity) {
		super(activity);
		URL = Constants.API_SOCIAL_LOGIN;
		dialogText="Signing In ...";
	}

	public void setParams(String provider, String email, String name){
		paramsList.add(new BasicNameValuePair("provider", provider));
		paramsList.add(new BasicNameValuePair("email", email));
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
