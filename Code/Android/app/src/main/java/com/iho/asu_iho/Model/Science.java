package com.iho.asu_iho.Model;

/**
 * Created by Barathi on 7/4/2014.
 */
public class Science {
    private String id;
    private String title;
    private String link;
    private Long timestamp;

    public String getId() {
        return id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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

    @Override
    public String toString(){
        return title;
    }

}
