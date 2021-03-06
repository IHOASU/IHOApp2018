package main.java.com.asu.library;


import main.java.com.asu.models.GalleryModel;
import main.java.com.asu.networking.HTTPConnectionHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class GalleryLibrary {
	static GalleryLibrary galleryLibrary;
	private HashMap<String, GalleryModel> galleryMap;
	
	private GalleryLibrary() {
		galleryMap = new HashMap<>();
		HTTPConnectionHelper helper = new HTTPConnectionHelper();
		String response = "";
		try {
			response = helper.sendGet("galleryobjects");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(response);
		JSONArray json = new JSONArray(response);
		
		for (int i=0;i<json.length();i++) {
			GalleryModel gallery = new GalleryModel((JSONObject)json.get(i));
			galleryMap.put(gallery.getId(), gallery);
		}
		System.out.println("Successful");
	}
	
	public static GalleryLibrary getInstance() {
		if (galleryLibrary == null) {
			galleryLibrary = new GalleryLibrary();
		} else {
			return galleryLibrary;
		}
		return galleryLibrary;
	}
	
	public GalleryModel getGallery(String id) {
		return galleryMap.get(id);
	}
	
	public void deleteGallery(String id) {
		galleryMap.remove(id);
	}
	
	public GalleryModel editGallery(GalleryModel gallery) {
		return null;
	}
	
	public Set<String> getKeySet(){
		return galleryMap.keySet();
	}
	
	public void addToLibrary(GalleryModel gallery) {
		galleryMap.put(gallery.getId(), gallery);
	}
}