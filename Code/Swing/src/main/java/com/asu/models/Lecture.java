package main.java.com.asu.models;

import org.json.JSONObject;

import java.util.UUID;

public class Lecture {
	private String id;
	private String name;
	private String bio;
	private String link;
	private String title;
	private String email;
	private String imageUrl;
	private int order;

	public Lecture(String newsTitle,
				   String bio,
				   String linkMore,
				   String title,
				   String imageUrl,
				   String email,
				   int order) {
		id = UUID.randomUUID().toString().replace("-", "");
		name = newsTitle;
		this.bio = bio;
		link = linkMore;
		this.title = title;
		this.imageUrl = imageUrl;
		this.email = email;
		this.order = order;
	}
	
	public Lecture(JSONObject object) {
		id = object.getString("id");
		name = object.getString("name");
		bio = object.getString("bio");
		link = object.getString("link");
		title = object.getString("title");
		imageUrl = object.getString("imageUrl");
		email = object.getString("email");
		order = object.getInt("order");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String toString() {
		return name;
	}
}
