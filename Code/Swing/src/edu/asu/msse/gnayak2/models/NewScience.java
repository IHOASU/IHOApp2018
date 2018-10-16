package edu.asu.msse.gnayak2.models;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.UUID;

public class NewScience {
	private String id;
	private String title;
	private String link;
	private Timestamp timestamp;

	public NewScience(String newsTitle, String linkMore) {
		id = UUID.randomUUID().toString().replace("-", "");
		title = newsTitle;
		link = linkMore;
		timestamp = new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public NewScience(JSONObject object) {
		id = object.getString("id");
		title = object.getString("title");
		link = object.getString("link");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String toString() {
		return title;
	}
}
