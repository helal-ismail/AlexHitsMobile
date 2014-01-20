package com.alexhits.api;

import org.json.JSONObject;

import com.alexhits.core.AlexHitsActivity;

public class ApiLogin extends ApiAbstract{

	public ApiLogin(AlexHitsActivity activity) {
		super(activity);
		URL = "";
		dialogText="";
	}

	
	
	@Override
	public void customOnPostExecute(JSONObject json) {
		
	}
}
