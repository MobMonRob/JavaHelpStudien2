package de.dhbw.mwulle.jhelp.impl.builder;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;

import java.net.URL;
import java.util.Locale;

public class ViewBuilder {

    private final URL directory;
    private MergeType mergeType;
    private Locale language;
    private String name;
    private String label;
    private String type;
    private MapId image;

    public ViewBuilder(URL directory) {
        this.directory = directory;
    }

    public MergeType getMergeType() {
        return mergeType;
    }

    public void setMergeType(MergeType mergeType) {
        this.mergeType = mergeType;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MapId getImage() {
        return image;
    }

    public void setImage(MapId image) {
        this.image = image;
    }

    public URL getDirectory() {
        return directory;
    }
}
