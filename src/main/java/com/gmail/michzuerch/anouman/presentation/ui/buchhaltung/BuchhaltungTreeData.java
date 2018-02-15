package com.gmail.michzuerch.anouman.presentation.ui.buchhaltung;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 18.05.17.
 */
public class BuchhaltungTreeData {
    private List<BuchhaltungTreeData> sub = new ArrayList<>();
    private String type;
    private Long id;
    private String text;

    public BuchhaltungTreeData(Long id, String type, String text) {
        this.type = type;
        this.id = id;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<BuchhaltungTreeData> getSub() {
        return sub;
    }

    public void setSub(List<BuchhaltungTreeData> sub) {
        this.sub = sub;
    }

    @Override
    public String toString() {
        return getText();
    }
}
