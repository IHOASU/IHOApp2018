package edu.asu.msse.gnayak2.models;

import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

public class NewScience {
	private String id;
	private String title;
	private String link;
	// String Type instead of TimeStamp for easy serailization/deserialization
	private String timestamp;

	public NewScience(String newsTitle, String linkMore) {
		id = UUID.randomUUID().toString().replace("-", "");
		title = newsTitle;
		link = linkMore;
		timestamp = String.valueOf(new Date().toInstant().toEpochMilli());
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
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
