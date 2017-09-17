package ch.internettechnik.anouman.presentation.ui.templatebuchhaltung;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michzuerch on 18.05.17.
 */
public class TemplateBuchhaltungTreeData {
    private List<TemplateBuchhaltungTreeData> sub = new ArrayList<>();
    private String type;
    private Long id;
    private String text;

    public TemplateBuchhaltungTreeData(Long id, String type, String text) {
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

    public List<TemplateBuchhaltungTreeData> getSub() {
        return sub;
    }

    public void setSub(List<TemplateBuchhaltungTreeData> sub) {
        this.sub = sub;
    }

    @Override
    public String toString() {
        return getText();
    }
}
