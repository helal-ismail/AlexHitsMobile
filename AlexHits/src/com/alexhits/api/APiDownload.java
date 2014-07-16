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

import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.alexhits.core.Constants;
import com.alexhits.model.Song;
import com.alexhits.model.User;
import com.alexhits.ui.AlexHitsActivity;
import com.alexhits.ui.R;
import com.bugsense.trace.BugSenseHandler;

public class ApiDownload extends ApiAbstract{
	

	
	public ApiDownload(AlexHitsActivity activity) {
		super(activity);
		showDialog = false;
		URL = Constants.API_DOWNLOAD;
		dialogText = "Download started ...";
	}

	public void setParams(String songInfo, int user_id){
		paramsList.add(new BasicNameValuePair("song_info", songInfo));
		paramsList.add(new BasicNameValuePair("user_id", user_id+""));
		
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
		
		NotificationManager mNotifyManager;
		NotificationCompat.Builder mBuilder;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mNotifyManager =
			        (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
			mBuilder = new NotificationCompat.Builder(activity);
			mBuilder.setContentTitle("Alexhits Download Service")
			    .setContentText("downloading song ...")
			    .setSmallIcon(R.drawable.thumb);
		}
		
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
	 
	            
	            AlexHitsActivity act = activity;
	            User user = act.cache.currentUser;
	            int userId = activity.cache.currentUser.user_id;
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
	        	BugSenseHandler.sendException(e);
	      //      Log.e("Error: ", e.getMessage());
	        }
	 
	        return null;
	    }
	 
	    @Override
	    protected void onProgressUpdate(String... values) {
	    	super.onProgressUpdate(values);
	    	String val = values[0];
	    	int value = Integer.parseInt(val);
	    	 mBuilder.setProgress(100, value, false);
             mNotifyManager.notify(0, mBuilder.build());
	    }
	    

	   
	    @Override
	    protected void onPostExecute(String songTitle) {

	        mBuilder.setContentText("Download complete").setProgress(0,0,false);
            mNotifyManager.notify(0, mBuilder.build());
            
	    }
	 
	}

}
