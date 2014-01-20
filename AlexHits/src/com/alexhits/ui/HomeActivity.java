package com.alexhits.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexhits.core.AlexHitsActivity;
import com.alexhits.core.Constants;
import com.alexhits.network.SongInfoHelper;

public class HomeActivity extends AlexHitsActivity {

	ImageView play_pause, subradio, env_rain, env_jungle, env_beach;
	TextView status_bar;
	public MediaPlayer mp, env_mp;
	Timer timer;

	// SongInfoHelper songInfoHelper;

	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_home);
		status_bar = (TextView) findViewById(R.id.status_bar);

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

		mp = new MediaPlayer();

		mp.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
				status_bar.setText("Radio");
				play_pause.setClickable(true);

			}
		});

		try {
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mp.setDataSource(cache.currentStreamURL);
			mp.prepareAsync();
		} catch (Exception e) {
			showToast("Failed to Connect");
		}

		// === Song Info Timer
		timer = new Timer();
		timer.scheduleAtFixedRate(songInfoTask, 1000, 10000);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.play_pause_btn:
			if (mp.isPlaying()) {
				status_bar.setText("Paused");
				play_pause.setImageResource(R.drawable.play_icon);
				mp.stop();
			} else {
				status_bar.setText("Streaming ...");
				play_pause.setImageResource(R.drawable.pause_icon);
				play_pause.setClickable(false);
				mp.prepareAsync();
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

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mp != null) {
			mp.stop();
			mp.release();
		}
		if (env_mp != null) {
			env_mp.stop();
			env_mp.release();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mp != null && mp.isPlaying()) {

			status_bar.setText("Streaming ...");

			mp.stop();
			mp = null;
			mp = new MediaPlayer();

			mp.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					mp.start();
					status_bar.setText("Radio");
					play_pause.setClickable(true);

				}
			});

			try {
				mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mp.setDataSource(cache.currentStreamURL);
				mp.prepareAsync();
			} catch (Exception e) {
				showToast("Failed to Connect");
			}
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
		if (env_mp != null) {
			env_mp.stop();
		}

		if (envID != cache.currentEffect) {
			env_mp = null;
			env_mp = MediaPlayer.create(HomeActivity.this, soundResID);
			env_mp.start();
			env_mp.setLooping(true);
			cache.currentEffect = envID;
		} else {
			env_mp = null;
			v.setImageResource(dimResID);
			cache.currentEffect = 0;
		}
	}

}
