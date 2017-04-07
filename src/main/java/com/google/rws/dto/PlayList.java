package com.google.rws.dto;

import java.io.Serializable;

public class PlayList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String title;
	
	public PlayList(String id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayList other = (PlayList) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlayList [id=" + id + ", title=" + title + "]";
	}
	
	
}
