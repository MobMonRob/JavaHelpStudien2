package de.dhbw.mwulle.jhelp.impl.view.toc;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TocItem {

    private final Locale language;
    private final String text;
    private final String target;
    private final String image;
    private final String mergeType;
    private final String expand;
    private final String presentationType;
    private final String presentationName;
    private final List<TocItem> children;

    public TocItem(Locale language, String text, String target, String image, String mergeType, String expand, String presentationType, String presentationName, List<TocItem> children) {
        this.language = language;
        this.text = text;
        this.target = target;
        this.image = image;
        this.mergeType = mergeType;
        this.expand = expand;
        this.presentationType = presentationType;
        this.presentationName = presentationName;
        this.children = Collections.unmodifiableList(children);
    }

    public Locale getLanguage() {
        return language;
    }

    public String getText() {
        return text;
    }

    public String getTarget() {
        return target;
    }

    public String getImage() {
        return image;
    }

    public String getMergeType() {
        return mergeType;
    }

    public String getExpand() {
        return expand;
    }

    public String getPresentationType() {
        return presentationType;
    }

    public String getPresentationName() {
        return presentationName;
    }

    public List<TocItem> getChildren() {
        return children;
    }
}
