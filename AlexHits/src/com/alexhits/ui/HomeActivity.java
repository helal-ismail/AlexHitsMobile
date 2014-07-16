package com.alexhits.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexhits.api.SongInfoHelper;
import com.alexhits.core.Constants;

public class HomeActivity extends AlexHitsActivity {

	ImageView play_pause, subradio, env_rain, env_jungle, env_beach,
			downloadBtn, quickList, downloadsBtn, infoBtn, logoutBtn, shareBtn;
	TextView status_bar, song_info, loading_label;
	// public MediaPlayer mp, env_mp;
	Timer timer;

	// SongInfoHelper songInfoHelper;

	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_home);
		status_bar = (TextView) findViewById(R.id.status_bar);
		song_info = (TextView) findViewById(R.id.song_info);
		loading_label = (TextView) findViewById(R.id.loading_label);

		play_pause = (ImageView) findViewById(R.id.play_pause_btn);
		play_pause.setOnClickListener(this);

		subradio = (ImageView) findViewById(R.id.subradio_btn);
		subradio.setOnClickListener(this);
		env_rain = (ImageView) findViewById(R.id.env_rain);
		env_rain.setOnClickListener(this);

		env_jungle = (ImageView) findViewById(R.id.env_jungle);
		env_jungle.setOnClickListener(this);

		env_beach = (ImageView) findViewById(R.id.env_beach);
		env_beach.setOnClickListener(this);

		downloadBtn = (ImageView) findViewById(R.id.download_btn);
		downloadBtn.setOnClickListener(this);

		downloadsBtn = (ImageView) findViewById(R.id.downloads_btn);
		downloadsBtn.setOnClickListener(this);

		quickList = (ImageView) findViewById(R.id.quicklist_btn);
		quickList.setOnClickListener(this);

		infoBtn = (ImageView) findViewById(R.id.info_btn);
		infoBtn.setOnClickListener(this);

		logoutBtn = (ImageView) findViewById(R.id.logout_btn);
		logoutBtn.setOnClickListener(this);
		
		shareBtn = (ImageView)findViewById(R.id.share_btn);
		shareBtn.setOnClickListener(this);

		cache.homePlayer = new MediaPlayer();

		cache.homePlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
				status_bar.setText("Radio");
				loading_label.setText("Enjoy The Music");
				Handler h = new Handler();
				h.postDelayed(hideLabel, 5000);
				play_pause.setClickable(true);

			}
		});

		try {
			cache.homePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			// mp.prepareAsync();
		} catch (Exception e) {
			showToast("Failed to Connect");
		}

		// === Song Info Timer
		timer = new Timer();
		timer.scheduleAtFixedRate(songInfoTask, 1000, 10000);

	}
	
	public void playFromWeb()
	{
		WebView mWebview  = (WebView)findViewById(R.id.mWeb);

        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            	super.onPageStarted(view, url, favicon);
            	showToast("d :" + url);
            }
        	
        	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            	showToast(description);
            }
        });

        mWebview.loadUrl("http://station.voscast.com/528913997f465/");
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.play_pause_btn:
			playFromWeb();
			break;
		case 100:
			try {

				if (cache.homePlayer == null)
				{
					cache.homePlayer = new MediaPlayer();
					cache.homePlayer.setOnPreparedListener(new OnPreparedListener() {
						@Override
						public void onPrepared(MediaPlayer mp) {
							mp.start();
							status_bar.setText("Radio");
							loading_label.setText("Enjoy The Music");
							Handler h = new Handler();
							h.postDelayed(hideLabel, 5000);
							play_pause.setClickable(true);
						}
					});
					try {
						cache.homePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
						status_bar.setText("Streaming ...");
						//loading_label.setText("A moment of patience\n For a lifetime of great music experience");
						loading_label.setText("Please wait ...");
						
						play_pause.setImageResource(R.drawable.pause_icon);
						play_pause.setClickable(false);
						cache.homePlayer.setDataSource(cache.currentStreamURL);
						cache.homePlayer.prepareAsync();
					}
					catch(Exception e)
					{
						//showToast(e.getMessage()+"");
					}
				}
				
				else if (cache.homePlayer.isPlaying()) {
					status_bar.setText("Paused");
					play_pause.setImageResource(R.drawable.play_icon);
					cache.homePlayer.stop();
				} else {
					status_bar.setText("Streaming ...");
					//loading_label.setText("A moment of patience\n For a lifetime of great music experience");
					loading_label.setText("Please wait ...");

					play_pause.setImageResource(R.drawable.pause_icon);
					play_pause.setClickable(false);
					cache.homePlayer.setDataSource(cache.currentStreamURL);
					cache.homePlayer.prepareAsync();
				}
			} catch (Exception e) {
				//showToast(e.getMessage() + "");
			}

			break;

		case R.id.subradio_btn:
			Intent subRadioIntent = new Intent(mContext, SubRadioActivity.class);
			startActivity(subRadioIntent);
			break;

		case R.id.env_rain:
			dimEnvIcons();
			env_rain.setImageResource(R.drawable.rain);
			playEnvSound(R.raw.rain, 1, env_rain, R.drawable.rainno);
			break;

		case R.id.env_jungle:
			dimEnvIcons();
			env_jungle.setImageResource(R.drawable.tree);
			playEnvSound(R.raw.jungle, 2, env_jungle, R.drawable.treeno);
			break;

		case R.id.env_beach:
			dimEnvIcons();
			env_beach.setImageResource(R.drawable.sea);
			playEnvSound(R.raw.beach, 3, env_beach, R.drawable.seano);
			break;

		case R.id.download_btn:
			if (cache.currentUser == null)
				showToast("You have to register before using this feature");
			else {
				String songInfo = song_info.getText() + "";
				api_download(songInfo);
			}
			break;

		case R.id.downloads_btn:
			if (cache.currentUser == null)
				showToast("You have to register before using this feature");
			else {
				finish();
				Intent downloadsIntent = new Intent(mContext,
						DownloadsActivity.class);
				downloadsIntent.putExtra("dir_name", "downloads");
				startActivity(downloadsIntent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			}
			break;

		case R.id.quicklist_btn:
			if (cache.currentUser == null)
				showToast("You have to register before using this feature");
			else {
				finish();
				Intent quicklistIntent = new Intent(mContext,
						DownloadsActivity.class);
				quicklistIntent.putExtra("dir_name", "quicklist");
				startActivity(quicklistIntent);
				overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			}
			break;

		case R.id.info_btn:
			finish();
			Intent infoIntent = new Intent(mContext, InfoActivity.class);
			startActivity(infoIntent);
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			break;

		case R.id.logout_btn:
			api_logout();

			break;
			
		case R.id.share_btn:
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND); 
		    sharingIntent.setType("text/plain");
		    String shareBody = "Now listening to " + song_info.getText() + " on AlexHits -- get it now on your mobile http://bit.ly/AlexHits";
		    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "AlexHits");
		    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
		    
		startActivity(Intent.createChooser(sharingIntent, "Share via"));
			break;
		default:
			break;
		}
	}

	
	
	


	@Override
	protected void onResume() {
		super.onResume();

		if (cache.homePlayer != null) {

			if (!cache.isChanged) {
				return;
			}

			cache.isChanged = false;
			if (cache.currentStreamURL.equalsIgnoreCase(Constants.RADIO_MAIN))
				status_bar.setText("Alexhits");
			else if (cache.currentStreamURL.equalsIgnoreCase(Constants.RADIO_90s))
				status_bar.setText("90's Hits");
			else if (cache.currentStreamURL.equalsIgnoreCase(Constants.RADIO_ZMAN))
				status_bar.setText("«·“„‰ «·Ã„Ì·");
			else if (cache.currentStreamURL.equalsIgnoreCase(Constants.RADIO_CLASSIC))
				status_bar.setText("Oldies");
			else if (cache.currentStreamURL.equalsIgnoreCase(Constants.RADIO_CHILLOUT))
				status_bar.setText("Chillout");
			else if (cache.currentStreamURL.equalsIgnoreCase(Constants.RADIO_UNDERGROUND))
				status_bar.setText("Underground");
			else if (cache.currentStreamURL.equalsIgnoreCase(Constants.RADIO_TOP))
				status_bar.setText("Top Hits");
			else
			{
				status_bar.setText("Streaming ...");
			}
//			loading_label.setText("A moment of patience\n for a lifetime of great music experience");
			loading_label.setText("Please wait ...");

			play_pause.setImageResource(R.drawable.pause_icon);
			play_pause.setClickable(false);
			cache.homePlayer.stop();
			cache.homePlayer.reset();

			cache.homePlayer = null;
			cache.homePlayer = new MediaPlayer();

			cache.homePlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					mp.start();
					status_bar.setText("Radio");
					play_pause.setClickable(true);
					
					loading_label.setText("Enjoy The Music");
					Handler h = new Handler();
					h.postDelayed(hideLabel, 5000);
				}
			});

			try {
				cache.homePlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				cache.homePlayer.setDataSource(cache.currentStreamURL);
				cache.homePlayer.prepareAsync();
			} catch (Exception e) {
				showToast("Failed to Connect");
			}
		}
		else
		{
			play_pause.setImageResource(R.drawable.play_icon);
		}
	}

	TimerTask songInfoTask = new TimerTask() {
		@Override
		public void run() {

			runOnUiThread(callSongInfo);

		}
	};

	Runnable callSongInfo = new Runnable() {
		@Override
		public void run() {
			SongInfoHelper infoHelper = new SongInfoHelper(HomeActivity.this);
			infoHelper.execute();
		}
	};

	private void dimEnvIcons() {
		env_rain.setImageResource(R.drawable.rainno);
		env_jungle.setImageResource(R.drawable.treeno);
		env_beach.setImageResource(R.drawable.seano);
	}

	private void playEnvSound(int soundResID, int envID, ImageView v,
			int dimResID) {
		if (cache.homeEnvPlayer != null) {
			cache.homeEnvPlayer.stop();
		}

		if (envID != cache.currentEffect) {
			cache.homeEnvPlayer = null;
			cache.homeEnvPlayer = MediaPlayer.create(HomeActivity.this,
					soundResID);
			cache.homeEnvPlayer.start();
			cache.homeEnvPlayer.setLooping(true);
			cache.currentEffect = envID;
		} else {
			cache.homeEnvPlayer = null;
			v.setImageResource(dimResID);
			cache.currentEffect = 0;
		}
	}

	
	Runnable hideLabel = new Runnable() {
		@Override
		public void run() {
			loading_label.setText("");
		}
	};
}
