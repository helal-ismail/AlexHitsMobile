package com.alexhits.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alexhits.core.AlexHitsActivity;
import com.alexhits.core.Constants;

public class SubRadioActivity extends AlexHitsActivity {

	LinearLayout subRadioList;
	LinearLayout stream_main;
	LinearLayout stream_90s;
	LinearLayout stream_zman;
	LinearLayout stream_classic;
	LinearLayout stream_underground;
	LinearLayout stream_chillout;
	LinearLayout stream_top;

	@Override
	public void customOnCreate() {
		setContentView(R.layout.activity_subradio);
		subRadioList = (LinearLayout) findViewById(R.id.subradio_list);
		
		stream_main = (LinearLayout) findViewById(R.id.stream_main);
		stream_main.setOnClickListener(this);
		
		stream_90s = (LinearLayout) findViewById(R.id.stream_90s);
		stream_90s.setOnClickListener(this);
		
		stream_zman = (LinearLayout) findViewById(R.id.stream_zman);
		stream_zman.setOnClickListener(this);
		
		stream_classic = (LinearLayout) findViewById(R.id.stream_classic);
		stream_classic.setOnClickListener(this);
		
		stream_underground = (LinearLayout) findViewById(R.id.stream_underground);
		stream_underground.setOnClickListener(this);
		
		stream_chillout = (LinearLayout) findViewById(R.id.stream_chillout);
		stream_chillout.setOnClickListener(this);
		
		stream_top = (LinearLayout) findViewById(R.id.stream_tophits);
		stream_top.setOnClickListener(this);
		
		checkCurrentSubRadio();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.stream_main:
			cache.currentStreamURL = Constants.RADIO_MAIN;
			cache.currentStreamInfo = Constants.INFO_MAIN;
			finish();
			break;

		case R.id.stream_90s:
			cache.currentStreamURL = Constants.RADIO_90s;
			cache.currentStreamInfo = Constants.INFO_90s;
			finish();
			break;
		case R.id.stream_zman:
			cache.currentStreamURL = Constants.RADIO_ZMAN;
			cache.currentStreamInfo = Constants.INFO_ZMAN;
			finish();
			break;
		case R.id.stream_classic:
			cache.currentStreamURL = Constants.RADIO_CLASSIC;
			cache.currentStreamInfo = Constants.INFO_CLASSIC;
			finish();
			break;
		case R.id.stream_underground:
			cache.currentStreamURL = Constants.RADIO_UNDERGROUND;
			cache.currentStreamInfo = Constants.INFO_UNDERGROUND;
			finish();
			break;
		case R.id.stream_chillout:
			cache.currentStreamURL = Constants.RADIO_CHILLOUT;
			cache.currentStreamInfo = Constants.INFO_CHILLHOUT;
			finish();
			break;
		case R.id.stream_tophits:
			cache.currentStreamURL = Constants.RADIO_TOP;
			cache.currentStreamInfo = Constants.INFO_TOP;
			finish();
			break;
		default:
			break;
		}
	}

	private void checkCurrentSubRadio() {

		int childIndex = 0;

		if (cache.currentStreamURL.equalsIgnoreCase(Constants.RADIO_90s)) {
			childIndex = 2;
		}

		else if (cache.currentStreamURL.equalsIgnoreCase(Constants.RADIO_ZMAN)) {
			childIndex = 4;
		}

		else if (cache.currentStreamURL
				.equalsIgnoreCase(Constants.RADIO_CLASSIC)) {
			childIndex = 6;
		}

		else if (cache.currentStreamURL
				.equalsIgnoreCase(Constants.RADIO_UNDERGROUND)) {
			childIndex = 8;
		}

		else if (cache.currentStreamURL
				.equalsIgnoreCase(Constants.RADIO_CHILLOUT)) {
			childIndex = 10;
		} else if (cache.currentStreamURL.equalsIgnoreCase(Constants.RADIO_TOP)) {
			childIndex = 12;
		}

		LinearLayout l = (LinearLayout) subRadioList.getChildAt(childIndex);
		ImageView musicIcon = (ImageView) l.getChildAt(2);
		musicIcon.setVisibility(View.VISIBLE);
	}
}
