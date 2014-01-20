package com.alexhits.network;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.widget.TextView;

import com.alexhits.core.AlexHitsActivity;
import com.alexhits.ui.R;

public class SongInfoHelper extends AsyncTask<Void, String, String>{
	
	AlexHitsActivity activity;
	
	public SongInfoHelper(AlexHitsActivity activity) {
		this.activity = activity;
	}
	
	
	@Override
	protected String doInBackground(Void... params) {
		try
		{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(activity.cache.currentStreamInfo);
		HttpResponse response;
		
		response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = entity.getContent();
			String result = activity.convertStreamToString(instream);
			return result;
		}
		
		}
		catch(Exception e)
		{
		}
		return "";
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		String songInfo = processInput(result);
		TextView songInfoTV = (TextView)activity.findViewById(R.id.song_info);
		songInfoTV.setText(songInfo);
		
	}
	
	
	private String processInput(String result){
		if (result.equalsIgnoreCase("") || result == null)
			return "N/A";
		String[] arr = result.split("innerHTML = '");
		String[] strArr = arr[1].split("'");
		String res = strArr[0];
		return res;
	}
}
