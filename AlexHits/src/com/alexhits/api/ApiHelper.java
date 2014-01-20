package com.alexhits.api;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.AsyncTask;

import com.alexhits.core.AlexHitsActivity;

public abstract class ApiHelper extends AsyncTask<Void, Void, String>{
	AlexHitsActivity activity;
	String URL;
	List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
	
	
	public ApiHelper(AlexHitsActivity activity, String URL) {
		this.activity = activity;
		this.URL = URL;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		String result = "";
		try
		{
			
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(URL);
			httpPost.setEntity(new UrlEncodedFormEntity(paramsList));

			HttpResponse response;
			response = httpclient.execute(httpPost);
			
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				result = activity.convertStreamToString(instream);
			}
			return result;

		}
		catch(Exception e)
		{
			return result;
		}
	}
	
	

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
				
			try
			{
				JSONObject json = new JSONObject(result);
				boolean success = json.optBoolean("success");
				if(success)
					customOnPostExecute(json);
				else
				{
					String msg = json.optString("msg");
					activity.showToast(msg);
				}
			}
			catch(Exception e)
			{
				activity.showToast("Request Failed");
			}
		}
	
	public abstract void customOnPostExecute(JSONObject json);
	
}
