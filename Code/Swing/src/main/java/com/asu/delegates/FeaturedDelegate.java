package main.java.com.asu.delegates;

import main.java.com.asu.models.FeaturedNews;

public interface FeaturedDelegate {
	public void addNews(FeaturedNews news);
	public void deleteNews(FeaturedNews news);
}
