package com.alexhits.model;

public class Song {

	public int song_id;
	public String song_title;
	public String song_artist;
	public String download_url;

	public Song(int song_id, String song_title, String song_artist,
			String download_url) {
		super();
		this.song_id = song_id;
		this.song_title = song_title;
		this.song_artist = song_artist;
		this.download_url = download_url;
	}

}
