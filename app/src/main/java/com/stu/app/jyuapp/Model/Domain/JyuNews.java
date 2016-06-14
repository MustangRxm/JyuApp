package com.stu.app.jyuapp.Model.Domain;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * @author Jack
 * @time 2016/5/18 0018 16:20
 * @des TODO
 */
public class JyuNews extends BmobObject {
    //    public  JyuNews.Href_entity Href_Entity;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getRootHref() {
        return RootHref;
    }

    public void setRootHref(String rootHref) {
        RootHref = rootHref;
    }


    public String getRootTitle() {
        return RootTitle;
    }

    public void setRootTitle(String rootTitle) {
        RootTitle = rootTitle;
    }

    private String Date;

    private long DateID;
    private String Author;
    private String From;

    public long getDateID() {
        return DateID;
    }

    public void setDateID(long dateID) {
        DateID = dateID;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getNewsContent() {
        return NewsContent;
    }

    public void setNewsContent(String newsContent) {
        NewsContent = newsContent;
    }



    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    private String NewsContent;

    public List<String> getNewsImage() {
        return NewsImage;
    }

    public void setNewsImage(List<String> newsImage) {
        NewsImage = newsImage;
    }

    private List<String> NewsImage;
    private String guid;


    private String RootHref;
    private String RootTitle;


}
