package de.dhbw.mwulle.jhelp.impl.view.item;

import de.dhbw.mwulle.jhelp.api.MapId;

import java.util.List;
import java.util.Locale;

public abstract class Item<T extends Item<T>> {

    private final Locale language;
    private final String text;
    private final MapId target;
    private final String mergeType;
    private final String expand;
    private final String presentationType;
    private final String presentationName;
    private final List<T> children;

    protected Item(Locale language, String text, MapId target, String mergeType, String expand, String presentationType, String presentationName, List<T> children) {
        this.language = language;
        this.text = text;
        this.target = target;
        this.mergeType = mergeType;
        this.expand = expand;
        this.presentationType = presentationType;
        this.presentationName = presentationName;
        this.children = children;
    }

    public Locale getLanguage() {
        return language;
    }

    public String getText() {
        return text;
    }

    public MapId getTarget() {
        return target;
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

    public List<T> getChildren() {
        return children;
    }
}
