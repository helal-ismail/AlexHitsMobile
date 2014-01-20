package com.alexhits.api;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.alexhits.core.AlexHitsActivity;
import com.alexhits.model.User;

public class ApiRegister extends ApiHelper{

	public ApiRegister(AlexHitsActivity activity, String URL) {
		super(activity, URL);
		
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
		activity.cache.currentUser = new User(user_id, fullname, email);
		
	}
	
	

}
