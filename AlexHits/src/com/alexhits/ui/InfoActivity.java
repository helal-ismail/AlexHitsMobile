package com.alexhits.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

public class InfoActivity extends AlexHitsActivity {

	ImageView homeBtn, downloadsBtn, quicklistBtn;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.radio_btn:
			openIntentBackward(HomeActivity.class);
			break;

		case R.id.logout_btn:
			api_logout();
			break;

		case R.id.quicklist_btn:

			finish();
			Intent quicklistIntent = new Intent(mContext,
					DownloadsActivity.class);
			quicklistIntent.putExtra("dir_name", "quicklist");
			startActivity(quicklistIntent);
			overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_right);

			break;
			
	case R.id.downloads_btn:
			
			finish();
			quicklistIntent = new Intent(mContext,DownloadsActivity.class);
			quicklistIntent.putExtra("dir_name", "downloads");
			startActivity(quicklistIntent);
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			
			break;



		default:
			break;
		}
	}

	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_info);
		homeBtn = (ImageView) findViewById(R.id.radio_btn);
		homeBtn.setOnClickListener(this);

		downloadsBtn = (ImageView) findViewById(R.id.downloads_btn);
		downloadsBtn.setOnClickListener(this);

		quicklistBtn = (ImageView) findViewById(R.id.quicklist_btn);
		quicklistBtn.setOnClickListener(this);

//		logoutBtn = (ImageView) findViewById(R.id.logout_btn);
//		logoutBtn.setOnClickListener(this);

	}

	

}
