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

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.alexhits.core.AlexHitsActivity;
import com.alexhits.core.Constants;
import com.alexhits.ui.R;

public abstract class ApiAbstract extends AsyncTask<Void, Void, String>{
	AlexHitsActivity activity;
	String URL;
	String dialogText;
	List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
	ProgressDialog dialog = null;
	
	
	public ApiAbstract(AlexHitsActivity activity) {
		this.activity = activity;
	
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		dialog = new ProgressDialog(activity);
		dialog.setTitle("AlexHits");
		dialog.setIcon(R.drawable.logo);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(false);
		dialog.setMessage(dialogText);
		dialog.show();

		
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
				{
					dialog.dismiss();
					customOnPostExecute(json);
				}
				else
				{
					String msg = json.optString("msg");
					dialog.dismiss();
					activity.showToast(msg);
				}
			}
			catch(Exception e)
			{
				Log.d(Constants.TAG, e.getMessage());
				dialog.dismiss();
				activity.showToast("Request Failed");
			}
		}
	
	public abstract void customOnPostExecute(JSONObject json);
	
}
