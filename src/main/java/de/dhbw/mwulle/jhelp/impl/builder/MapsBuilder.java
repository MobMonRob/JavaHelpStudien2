package de.dhbw.mwulle.jhelp.impl.builder;

import de.dhbw.mwulle.jhelp.api.HelpSetMap;
import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.MapIdEntry;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsBuilder {

    private final URL directory;
    private final List<MapIdEntry> mapIdEntries = new ArrayList<>();
    private MapId homeId;
    private Locale language;
    private String version;

    public MapsBuilder(URL directory) {
        this.directory = directory;
    }

    public HelpSetMap build() {
        return HelpSetMap.create(homeId, language, mapIdEntries);
    }

    public MapId getHomeId() {
        return homeId;
    }

    public void setHomeId(MapId homeId) {
        this.homeId = homeId;
    }

    public URL getDirectory() {
        return directory;
    }

    public void addMapIdEntry(MapIdEntry mapIdEntry) {
        mapIdEntries.add(mapIdEntry);
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
