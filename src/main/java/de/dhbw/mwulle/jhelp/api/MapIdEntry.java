package de.dhbw.mwulle.jhelp.api;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MapIdEntry {

    private final MapId id;
    private final URL url;
    private final Locale language;
    private final List<MapIdEntry> children;

    private MapIdEntry(MapId id, URL url, Locale language, List<MapIdEntry> children) {
        this.id = id;
        this.url = url;
        this.language = language;
        this.children = Collections.unmodifiableList(children);
    }

    public static MapIdEntry create(MapId id, URL url, Locale language, List<MapIdEntry> children) {
        return new MapIdEntry(id, url, language, children);
    }

    public MapId getId() {
        return id;
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
