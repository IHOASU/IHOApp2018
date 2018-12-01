package main.java.com.asu.delegates;

import main.java.com.asu.models.News;

public interface NewsDelegate {
	public void addNews(News news);
	public void deleteNews(News news);
}
