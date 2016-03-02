package com.system.ia.systemexpert.Entity;

import java.util.List;

/**
 * Created by pira on 13/11/15.
 */
public class SystemEntity {
    private int id;
    private String title;
    private List<ReponseSystem> reponseSystemList;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ReponseSystem> getReponseSystemList() {
        return reponseSystemList;
    }

    public void setReponseSystemList(List<ReponseSystem> reponseSystemList) {
        this.reponseSystemList = reponseSystemList;
    }


}