package com.stu.app.jyuapp.EventOBJ;

import com.stu.app.jyuapp.Domain.JYU_Important_News;

import java.util.List;

/**
 * @author Jack
 * @time 2016/6/3 0003 22:24
 * @des TODO
 */

public class RequestNewsData {
    private String year_month;
    private List<JYU_Important_News> list_sources;

    public String getYear_month() {
        return year_month;
    }

    public void setYear_month(String year_month) {
        this.year_month = year_month;
    }

    public List<JYU_Important_News> getList_sources() {
        return list_sources;
    }

    public void setList_sources(List<JYU_Important_News> list_sources) {
        this.list_sources = list_sources;
    }

    public RequestNewsData(String year_month, List<JYU_Important_News> list_sources) {
        this.year_month = year_month;
        this.list_sources = list_sources;
    }
}
