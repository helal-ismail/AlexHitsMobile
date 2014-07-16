package com.alexhits.ui;

import java.io.File;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DownloadsActivity extends AlexHitsActivity {

	LinearLayout contentLayout;
	ImageView radioBtn, quickList, downloadsBtn, infoBtn;
	String dirName;
	@Override
	public void customOnCreate() {
		
		dirName = getIntent().getExtras().getString("dir_name");
		if(dirName.equalsIgnoreCase("quicklist"))
			setContentView(R.layout.activity_quicklist);
		else
			setContentView(R.layout.activity_downloads);
		
		contentLayout = (LinearLayout) findViewById(R.id.content_layout);

		radioBtn = (ImageView) findViewById(R.id.radio_btn);
		radioBtn.setOnClickListener(this);

		quickList = (ImageView) findViewById(R.id.quicklist_btn);
		quickList.setOnClickListener(this);
		
		downloadsBtn = (ImageView) findViewById(R.id.downloads_btn);
		downloadsBtn.setOnClickListener(this);

		infoBtn = (ImageView) findViewById(R.id.info_btn);
		infoBtn.setOnClickListener(this);

//		logoutBtn = (ImageView) findViewById(R.id.logout_btn);
//		logoutBtn.setOnClickListener(this);

		
		refresh();
	}

	public void refresh() {
		contentLayout.removeAllViews();
		final File downloads = new File(initUserDir(cache.currentUser.user_id),
				"downloads");
		final File quicklist = new File(initUserDir(cache.currentUser.user_id),
				"quicklist");
		
		File dir = new File(initUserDir(cache.currentUser.user_id),dirName);
		dir.mkdirs();
		downloads.mkdirs();
		quicklist.mkdirs();

		final File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			LinearLayout l = (LinearLayout) inflater.inflate(
					R.layout.custom_download_item, null);
			LinearLayout downloadItem = (LinearLayout)l.getChildAt(0);
			TextView songName = (TextView) downloadItem.getChildAt(1);
			songName.setText(files[i].getName());

			final int index = i;
			ImageView add = (ImageView) downloadItem.getChildAt(3);
			if(dirName.equalsIgnoreCase("quicklist"))
				add.setVisibility(View.GONE);
			add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {

					File src = files[index];
					File dst = new File(quicklist, src.getName());
					showToast(src.getName() + " added to quicklist");

					try {
						copy(src, dst);
					} catch (Exception e) {
						showToast(e.getMessage()+"");
						
					}
				}
			});

			ImageView remove = (ImageView) downloadItem.getChildAt(2);
			remove.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					deleteSong(files[index]);
				}
			});
			
			songName.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent customPlayer = new Intent(mContext, PlayerActivity.class);
					customPlayer.putExtra("index", index);
					customPlayer.putExtra("dir_name", dirName);
					startActivity(customPlayer);
					finish();
				}
			});
			
			contentLayout.addView(l);

		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.radio_btn:
			openIntentBackward(HomeActivity.class);
			break;
		
		case R.id.logout_btn:
			api_logout();
			break;

		case R.id.downloads_btn:
			if(dirName.equalsIgnoreCase("downloads"))
				break;
			
			finish();
			Intent quicklistIntent = new Intent(mContext,DownloadsActivity.class);
			quicklistIntent.putExtra("dir_name", "downloads");
			startActivity(quicklistIntent);
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			
			break;	
			
		case R.id.quicklist_btn:
			if(dirName.equalsIgnoreCase("quicklist"))
				break;
			
			finish();
			quicklistIntent = new Intent(mContext,DownloadsActivity.class);
			quicklistIntent.putExtra("dir_name", "quicklist");
			startActivity(quicklistIntent);
			overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
			
			break;
			
		case R.id.info_btn:
			openIntentForward(InfoActivity.class);
			break;

		default:
			break;
		}
	}

	
	private void deleteSong(final File file){
		new AlertDialog.Builder(this)
	    .setTitle("Alexhits")
	    .setMessage("Are you sure you want to remove this song ?")
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	file.delete();
	        	refresh();
	        }
	     })
	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(R.drawable.thumb)
	     .show();
	}
	

}
