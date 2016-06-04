package com.stu.app.jyuapp.Domain;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * @author Jack
 * @time 2016/5/18 0018 16:20
 * @des TODO
 */
public class JYU_Important_News extends BmobObject {
//    public  JYU_Important_News.Href_entity Href_Entity;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHref() {
        return Href;
    }

    public void setHref(String href) {
        Href = href;
    }

    public JYU_Important_News.Href_entity getNews_Etity() {
        return Href_entity;
    }

    public void setNews_Etity(JYU_Important_News.Href_entity news_Etity) {
        Href_entity = news_Etity;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    private String Date;
    private String Href;
    private Href_entity Href_entity;
    private String Title;
    //    public String objectId;
    //    public String updatedAt;
    //    public String createdAt;
    //    public List<News_Etity> Href_entity;



    public String getNews_Author() {
        return Href_entity.News_Author;
    }

    public void setNews_Author(String news_Author) {
        Href_entity.News_Author = news_Author;
    }

    public String getNews_From() {
        return Href_entity.News_From;
    }

    public void setNews_From(String news_From) {
        Href_entity.News_From = news_From;
    }

    public List<String> getNews_Img() {
        return Href_entity.News_Img;
    }

    public void setNews_Img(List<String> news_Img) {
        Href_entity.News_Img = news_Img;
    }

    public String getNews_Main_content() {
        return Href_entity.News_Main_content;
    }

    public void setNews_Main_content(String news_Main_content) {
        Href_entity.News_Main_content = news_Main_content;
    }
   public class Href_entity {
        private String News_Author;
        private String News_From;
        private List<String> News_Img;
        private String News_Main_content;

    }

}
