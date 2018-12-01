package main.java.com.asu.delegates;

import main.java.com.asu.models.GalleryModel;

public interface GalleryDelegate {
	public void addGallery(GalleryModel gallery);
	public void deleteGallery(GalleryModel gallery);
}
