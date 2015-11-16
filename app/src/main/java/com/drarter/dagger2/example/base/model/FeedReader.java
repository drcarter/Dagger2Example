package com.drarter.dagger2.example.base.model;

public class FeedReader {

    private int id;
    private String entryId;
    private String title;
    private String subTitle;

    public FeedReader(int id, String entryId, String title, String subTitle) {
        this.id = id;
        this.entryId = entryId;
        this.title = title;
        this.subTitle = subTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
}
