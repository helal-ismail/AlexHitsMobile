package com.alexhits.core;

import android.media.MediaPlayer;

import com.alexhits.model.User;

public class CacheHelper{
	
	private static CacheHelper instance = new CacheHelper();
	public static  CacheHelper getInstance(){
		return instance;
	}
	
	public String currentStreamURL = Constants.RADIO_MAIN;
	public String currentStreamInfo = Constants.INFO_MAIN;
	public int currentEffect = 0; // 0 none - 1 rain - 2 jungle - 3 beach
	
	public User currentUser = null;
	public boolean isChanged = false;
	public MediaPlayer homePlayer, homeEnvPlayer, listPlayer;
	
	
	

}
