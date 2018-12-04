package com.demo1.ngtszwah.rssreader.models;

import java.util.ArrayList;

public class Feed {

    String title;
    String link;
    String description;
    String pubDate;
    ArrayList<FeedItem> items;

    public Feed(){
        items = new ArrayList<>();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public ArrayList<FeedItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<FeedItem> items) {
        this.items = items;
    }



}
