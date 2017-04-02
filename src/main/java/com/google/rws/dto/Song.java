package com.google.rws.dto;

import java.io.Serializable;

public class Song implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String album;
	private String title;
	private String album_artist;
	private String artist;
	private int track_number;
	private long track_size;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlbum_artist() {
		return album_artist;
	}
	public void setAlbum_artist(String album_artist) {
		this.album_artist = album_artist;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public int getTrack_number() {
		return track_number;
	}
	public void setTrack_number(int track_number) {
		this.track_number = track_number;
	}
	public long getTrack_size() {
		return track_size;
	}
	public void setTrack_size(long track_size) {
		this.track_size = track_size;
	}
	
	
}
