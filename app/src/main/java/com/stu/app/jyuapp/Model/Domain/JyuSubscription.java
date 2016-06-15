package com.stu.app.jyuapp.Model.Domain;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by m1 on 16-6-14.
 */
@AVClassName("JyuSubscription")
public class JyuSubscription extends AVObject {
    private String Link;

    public long getTimeID() {
        return this.getLong("TimeID");

    }

    public void setTimeID(long timeID) {
        this.put("TimeID",timeID);
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        this.put("Link",link);
    }



    public String getTitle() {
        return this.getString("Title");
    }

    public void setTitle(String title) {
        this.put("Title",title);

    }

    public String getGuid() {
        return this.getString("guid");
    }

    public void setGuid(String guid) {
        this.put("guid",guid);
    }

    public String getPubDate() {
        return this.getString("pubDate");
    }

    public void setPubDate(String pubDate) {
        this.put("pubDate",pubDate);
    }

    public String getSubFindID() {
        return this.getString("subFindID");
    }

    public void setSubFindID(String subFindID) {
        this.put("subFindID",subFindID);
    }

    public String getRoot_link() {
        return this.getString("Root_link");
    }

    public void setRoot_link(String root_link) {
        this.put("Root_link",root_link);
    }

    public String getChannel_title() {
        return this.getString("channel_title");
    }

    public void setChannel_title(String channel_title) {
        this.put("channel_title",channel_title);
    }

    public String getChannel_icon() {
        return this.getString("channel_icon");
    }

    public void setChannel_icon(String channel_icon) {
        this.put("channel_icon",channel_icon);
    }


}
