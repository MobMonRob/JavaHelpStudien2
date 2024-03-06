package de.dhbw.mwulle.jhelp.api;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MapIdEntry {

    private final String target;
    private final URL url;
    private final Locale language;
    private final List<MapIdEntry> children;

    public MapIdEntry(String target, URL url, Locale language, List<MapIdEntry> children) {
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

    public List<MapIdEntry> getChildren() {
        return children;
    }
}
