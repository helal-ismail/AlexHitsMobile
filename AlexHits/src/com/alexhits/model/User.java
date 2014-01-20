package com.alexhits.model;

public class User {
	public int user_id;
	public String fullname = "";
	public String email = "";
	
	public User() {
	}
	
	public User(int user_id, String fullname, String email){
		this.user_id = user_id;
		this.fullname = fullname;
		this.email = email;
	}
}
