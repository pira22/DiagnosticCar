package com.system.ia.systemexpert.Entity;

/**
 * Created by pira on 13/11/15.
 */
public class ReponseSystem {
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getProposition() {
        return proposition;
    }

    public void setProposition(String[] proposition) {
        this.proposition = proposition;
    }

    private String text;
    private String[] proposition;
}
