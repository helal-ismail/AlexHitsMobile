package com.alexhits.api;

import java.io.BufferedInputStream;
import java.io.File;
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
		download(song);
	}
	
	private void download(Song song)
	{
		new DownloadFileFromURL().execute(song);
	}
	
	
	//http://www.androidhive.info/2012/04/android-downloading-file-by-showing-progress-bar/
	
	
	private class DownloadFileFromURL extends AsyncTask<Song, String, String> {
		 
	    @Override
	    protected String doInBackground(Song... songs) {
	        int count;
	        try {
	        	Song song = songs[0];
	        	String download_url = song.download_url;
	        	
	            URL url = new URL(download_url);
	            URLConnection conection = url.openConnection();
	            conection.connect();
	            
	            int lenghtOfFile = conection.getContentLength();
	 
	           
	            InputStream input = new BufferedInputStream(url.openStream(), 8192);
	 
	            File user_dir = activity.initUserDir(activity.cache.currentUser.user_id);
	            File downloads_dir = new File(user_dir, "downloads");
	            downloads_dir.mkdirs();
	            File songFile = new File(downloads_dir, song.song_title+".mp3");
	            
	            OutputStream output = new FileOutputStream(songFile.getPath());
	 
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
	    protected void onPostExecute(String songTitle) {
	        activity.showToast(songTitle+" was downloaded succesfully"); 
	    }
	 
	}

}
