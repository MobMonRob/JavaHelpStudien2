package de.dhbw.mwulle.jhelp.impl.builder;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.MapIdEntry;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapIdEntryBuilder {

    private final List<MapIdEntry> mapIdEntries = new ArrayList<>();
    private MapId id;
    private URL url;
    private Locale language;

    public MapId getId() {
        return id;
    }

    public void setId(MapId id) {
        this.id = id;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public void addMapId(MapIdEntry mapIdEntry) {
        mapIdEntries.add(mapIdEntry);
    }

    public MapIdEntry build() {
        return new MapIdEntry(id, url, language, mapIdEntries);
    }
}
