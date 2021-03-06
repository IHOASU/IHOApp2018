package main.java.com.asu.models;

import org.json.JSONObject;

import java.util.UUID;

public class GalleryModel {
	String id;
	String title;
    int order;
    String imageUrl;

	public GalleryModel(String title, int order, String imageUrl) {
		id = UUID.randomUUID().toString().replace("-", "");
		this.title = title;
		this.order = order;
		this.imageUrl = imageUrl;
	}
	
	public GalleryModel(JSONObject object) {
		id = object.getString("id");
	    title = object.getString("title");
		imageUrl = object.getString("imageUrl");
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
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	
	public String toString() {
		return title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
