package com.alexhits.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.alexhits.model.User;

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

//		facebook = new Facebook(Constants.FB_APP_ID);
//        mAsyncRunner = new AsyncFacebookRunner(facebook);
		
		User user = getStoredUserInfo();
		if (user != null)
			initUser(user.user_id, user.fullname, user.email);

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

//	public void loginToFacebook() {
//		String access_token = prefs.getString("access_token", null);
//		long expires = prefs.getLong("access_expires", 0);
//
//		if (access_token != null) {
//			facebook.setAccessToken(access_token);
//			getProfileInformation();
//		}
//
//		if (expires != 0) {
//			facebook.setAccessExpires(expires);
//		}
//
//		if (!facebook.isSessionValid()) {
//			facebook.authorize(this,
//					new String[] { "email", "publish_stream" },
//					new DialogListener() {
//
//						@Override
//						public void onCancel() {
//							showToast("c");
//						}
//
//						@Override
//						public void onComplete(Bundle values) {
//							// Function to handle complete event
//							// Edit Preferences and update facebook acess_token
//							SharedPreferences.Editor editor = prefs.edit();
//							editor.putString("access_token",
//									facebook.getAccessToken());
//							editor.putLong("access_expires",
//									facebook.getAccessExpires());
//							editor.commit();
//							getProfileInformation();
//							
//						}
//
//						@Override
//						public void onError(DialogError error) {
//							// Function to handle error
//							showToast(error.getMessage()+" ");
//						}
//
//						@Override
//						public void onFacebookError(FacebookError fberror) {
//							// Function to handle Facebook errors
//							showToast(fberror.getMessage() +"");	
//						}
//							
//						
//					});
//		}
//	}
//	
//	
//	public void getProfileInformation() {
//	    mAsyncRunner.request("me", new RequestListener() {
//	        @Override
//	        public void onComplete(String response, Object state) {
//	            Log.d("Profile", response);
//	            String json = response;
//	            try {
//	                JSONObject profile = new JSONObject(json);
//	                final String name = profile.optString("name");
//	                final String email = profile.optString("first_name");
//	                
//	                
//	                runOnUiThread(new Runnable() {
//	 
//	                    @Override
//	                    public void run() {
//	                    	api_social_login("facebook", email, name);
//	                    }
//	 
//	                });
//	 
//	            } catch (JSONException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	 
//	        @Override
//	        public void onIOException(IOException e, Object state) {
//	        }
//	 
//	        @Override
//	        public void onFileNotFoundException(FileNotFoundException e,
//	                Object state) {
//	        }
//	 
//	        @Override
//	        public void onMalformedURLException(MalformedURLException e,
//	                Object state) {
//	        }
//	 
//	        @Override
//	        public void onFacebookError(FacebookError e, Object state) {
//	        }
//	    });
//	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_fb:
	//			loginToFacebook();
			break;

		case R.id.login_tw:

			break;

		case R.id.login_google:

			break;

		case R.id.login_alexhits:
			Intent signinIntent = new Intent(mContext, SigninActivity.class);
			startActivity(signinIntent);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);
			finish();
			break;
		case R.id.login_skip:
			Intent homeIntent = new Intent(mContext, HomeActivity.class);
			startActivity(homeIntent);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_left);
			finish();
			break;
		default:
			break;
		}
	}

}
