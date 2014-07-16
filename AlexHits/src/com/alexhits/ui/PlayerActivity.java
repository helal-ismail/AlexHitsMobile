package com.alexhits.ui;

import java.io.File;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayerActivity extends AlexHitsActivity {

	File[] songList;
	ImageView play_pause, subradio, env_rain, env_jungle, env_beach,
			downloadBtn, quickList, downloadsBtn, infoBtn, nextBtn,
			prevBtn;
	String dirName;
	TextView status_bar, song_info, song_index;
	int index = 0;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.next_btn:
			nextSong();
			break;

		case R.id.prev_btn:
			prevSong();
			break;

		case R.id.play_pause_btn:
			if (cache.listPlayer.isPlaying()) {
				play_pause.setImageResource(R.drawable.play_icon);
				cache.listPlayer.stop();
			} else {
				play_pause.setImageResource(R.drawable.pause_icon);
				cache.listPlayer.prepareAsync();
			}
			break;
		}
	}

	@Override
	public void customOnCreate() {
		if (cache.homePlayer != null)
			cache.homePlayer.release();
		if (cache.homeEnvPlayer != null)
			cache.homeEnvPlayer.release();
		cache.homePlayer = null;
		cache.homeEnvPlayer = null;

		setContentView(R.layout.activity_player);
		song_info = (TextView) findViewById(R.id.song_info);
		song_index = (TextView) findViewById(R.id.song_index);

		nextBtn = (ImageView) findViewById(R.id.next_btn);
		nextBtn.setOnClickListener(this);

		prevBtn = (ImageView) findViewById(R.id.prev_btn);
		prevBtn.setOnClickListener(this);

		play_pause = (ImageView) findViewById(R.id.play_pause_btn);
		play_pause.setOnClickListener(this);

		dirName = getIntent().getExtras().getString("dir_name");
		index = getIntent().getExtras().getInt("index");

		File dir = new File(initUserDir(cache.currentUser.user_id), dirName);
		dir.mkdirs();
		songList = dir.listFiles();

		cache.listPlayer = new MediaPlayer();
		cache.listPlayer.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				mp.start();
			}
		});

		try {
			song_info.setText(songList[index].getName());
			song_index.setText((index + 1) + "/" + songList.length);
			cache.listPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			cache.listPlayer.setDataSource(songList[index].getPath());
			cache.listPlayer.prepareAsync();

			cache.listPlayer
					.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.stop();
							index++;
							if (index >= songList.length)
								index = 0;
							try {
								song_index.setText((index + 1) + "/"
										+ songList.length);
								song_info.setText(songList[index].getName());
								mp.reset();
								mp.setDataSource(songList[index].getPath());
								mp.prepareAsync();
							} catch (Exception e) {
								showToast(e.getMessage() + " looper");
							}
						}
					});
		} catch (Exception e) {
			showToast(e.getMessage() + " block");
		}

	}

	private void nextSong() {
		play_pause.setImageResource(R.drawable.pause_icon);
		cache.listPlayer.stop();
		index++;
		if (index >= songList.length)
			index = 0;
		try {
			song_index.setText((index + 1) + "/" + songList.length);
			song_info.setText(songList[index].getName());
			cache.listPlayer.reset();
			cache.listPlayer.setDataSource(songList[index].getPath());
			cache.listPlayer.prepareAsync();
		} catch (Exception e) {
			showToast(e.getMessage() + " looper");
		}
	}

	private void prevSong() {
		play_pause.setImageResource(R.drawable.pause_icon);
		cache.listPlayer.stop();
		index--;
		if (index < 0)
			index = songList.length - 1;
		try {
			song_index.setText((index + 1) + "/" + songList.length);
			song_info.setText(songList[index].getName());
			cache.listPlayer.reset();
			cache.listPlayer.setDataSource(songList[index].getPath());
			cache.listPlayer.prepareAsync();
		} catch (Exception e) {
			showToast(e.getMessage() + " looper");
		}
	}

	@Override
	public void onBackPressed() {
		cache.listPlayer.release();
		if (dirName.equalsIgnoreCase("quicklist")) {
			finish();
			Intent quicklistIntent = new Intent(mContext,
					DownloadsActivity.class);
			quicklistIntent.putExtra("dir_name", "quicklist");
			startActivity(quicklistIntent);
			overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_right);
		} else {
			finish();
			Intent quicklistIntent = new Intent(mContext, DownloadsActivity.class);
			quicklistIntent.putExtra("dir_name", "downloads");
			startActivity(quicklistIntent);
			overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_right);
		}

	}

}
