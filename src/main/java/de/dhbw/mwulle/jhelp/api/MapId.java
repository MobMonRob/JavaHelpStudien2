package de.dhbw.mwulle.jhelp.api;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MapId {

    private final String target;
    private final URL url;
    private final Locale language;
    private final List<MapId> children;

    public MapId(String target, URL url, Locale language, List<MapId> children) {
        this.target = target;
        this.url = url;
        this.language = language;
        this.children = Collections.unmodifiableList(children);
    }

    public String getTarget() {
        return target;
    }

    public URL getUrl() {
        return url;
    }

    public Locale getLanguage() {
        return language;
    }

    public List<MapId> getChildren() {
        return children;
    }
}
