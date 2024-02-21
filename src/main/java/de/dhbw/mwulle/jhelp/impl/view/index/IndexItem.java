package de.dhbw.mwulle.jhelp.impl.view.index;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class IndexItem {

    private final Locale language;
    private final String text;
    private final String target;
    private final String mergeType;
    private final String expandText;
    private final String presentationType;
    private final String presentationName;
    private final List<IndexItem> children;

    public IndexItem(Locale language, String text, String target, String mergeType, String expandText, String presentationType, String presentationName, List<IndexItem> children) {
        this.language = language;
        this.text = text;
        this.target = target;
        this.mergeType = mergeType;
        this.expandText = expandText;
        this.presentationType = presentationType;
        this.presentationName = presentationName;
        this.children = Collections.unmodifiableList(children);
    }

    public String getPresentationName() {
        return presentationName;
    }

    public String getPresentationType() {
        return presentationType;
    }

    public String getExpandText() {
        return expandText;
    }

    public String getMergeType() {
        return mergeType;
    }

    public String getTarget() {
        return target;
    }

    public String getText() {
        return text;
    }

    public Locale getLanguage() {
        return language;
    }

    public List<IndexItem> getChildren() {
        return children;
    }
}
