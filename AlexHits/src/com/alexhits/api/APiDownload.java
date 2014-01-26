package com.alexhits.api;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.alexhits.core.AlexHitsActivity;
import com.alexhits.core.Constants;
import com.alexhits.model.Song;

public class APiDownload extends ApiAbstract{
	
	
	
	public APiDownload(AlexHitsActivity activity) {
		super(activity);
		URL = Constants.API_DOWNLOAD;
		dialogText = "Download started ...";
	}

	public void setParams(String songInfo){
		paramsList.add(new BasicNameValuePair("song_info", songInfo));
	}
	
	@Override
	public void customOnPostExecute(JSONObject json) {
		int song_id = json.optInt("song_id");
		String song_title = json.optString("song_title");
		String song_artist = json.optString("song_artist");
		String download_url = json.optString("download_url");
		Song song = new Song(song_id, song_title, song_artist, download_url);
		
	}
	
	private void download(Song song)
	{
		
	}
	
	
	
	private class DownloadFileFromURL extends AsyncTask<String, String, String> {
		 
	    @Override
	    protected String doInBackground(String... f_url) {
	        int count;
	        try {
	            URL url = new URL(f_url[0]);
	            URLConnection conection = url.openConnection();
	            conection.connect();
	            
	            int lenghtOfFile = conection.getContentLength();
	 
	           
	            InputStream input = new BufferedInputStream(url.openStream(), 8192);
	 
	            File user_dir = activity.initUserDir(activity.cache.currentUser.user_id);
	            
	            OutputStream output = new FileOutputStream("/sdcard/downloadedfile.jpg");
	 
	            byte data[] = new byte[1024];
	 
	            long total = 0;
	 
	            while ((count = input.read(data)) != -1) {
	                total += count;
	                // publishing the progress....
	                // After this onProgressUpdate will be called
	                publishProgress(""+(int)((total*100)/lenghtOfFile));
	 
	                // writing data to file
	                output.write(data, 0, count);
	            }
	 
	            // flushing output
	            output.flush();
	 
	            // closing streams
	            output.close();
	            input.close();
	 
	        } catch (Exception e) {
	            Log.e("Error: ", e.getMessage());
	        }
	 
	        return null;
	    }
	 
	   
	    @Override
	    protected void onPostExecute(String file_url) {
	        // dismiss the dialog after the file was downloaded
	        dismissDialog(progress_bar_type);
	 
	        // Displaying downloaded image into image view
	        // Reading image path from sdcard
	        String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
	        // setting downloaded into image view
	        my_image.setImageDrawable(Drawable.createFromPath(imagePath));
	    }
	 
	}

}
