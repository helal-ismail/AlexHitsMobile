package com.alexhits.core;

import com.alexhits.model.User;

public class CacheHelper {
	
	private static CacheHelper instance = new CacheHelper();
	public static  CacheHelper getInstance(){
		return instance;
	}
	
	public String currentStreamURL = Constants.RADIO_MAIN;
	public String currentStreamInfo = Constants.INFO_MAIN;
	public int currentEffect = 0; // 0 none - 1 rain - 2 jungle - 3 beach
	
	public User currentUser = null;
	
	
	
	

}
