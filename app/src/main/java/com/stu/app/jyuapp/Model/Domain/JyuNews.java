package com.stu.app.jyuapp.Model.Domain;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.List;


/**
 * @author Jack
 * @time 2016/5/18 0018 16:20
 * @des TODO
 */
@AVClassName("JyuNews")
public class JyuNews extends AVObject {
    //    public  JyuNews.Href_entity Href_Entity;

    public String getDate() {
        return getString("Date");
    }

    public void setDate(String date) {
        put("RequestDate",date) ;
    }

    public String getRootHref() {
        return  getString("RootHref");
    }

    public void setRootHref(String rootHref) {

        put("RootHref",rootHref);
    }


    public String getRootTitle() {
        return getString("RootTitle");
    }

    public void setRootTitle(String rootTitle) {
        put("RootTitle",rootTitle);
    }

//    private String RequestDate;
//
//    private long DateID;
//    private String Author;
//    private String From;

    public long getDateID() {
        return getLong("DateID");
    }

    public void setDateID(long dateID) {
        put("DateID",dateID);
    }

    public String getAuthor() {
        return getString("Author");
    }

    public void setAuthor(String author) {
        put("Author",author);
    }

    public String getFrom() {
        return getString("From");
    }

    public void setFrom(String from) {
        put("From",from);
    }

    public String getNewsContent() {
        return getString("NewsContent");
    }

    public void setNewsContent(String newsContent) {
        put("NewsContent",newsContent);
    }

    public String getGuid() {
        return getString("guid");
    }

    public void setGuid(String guid) {
        put("guid",guid);
    }


    public List<String> getNewsImage() {
        return getList("NewsImage");
    }

    public void setNewsImage(List<String> newsImage) {
        put("NewsImage",newsImage);

    }


}
