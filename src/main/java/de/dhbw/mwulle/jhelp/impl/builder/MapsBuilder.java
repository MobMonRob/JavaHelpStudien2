package de.dhbw.mwulle.jhelp.impl.builder;

import de.dhbw.mwulle.jhelp.api.HelpSetMap;
import de.dhbw.mwulle.jhelp.api.MapId;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapsBuilder {

    private final URL directory;
    private final List<MapId> mapIds = new ArrayList<>();
    private String homeId;
    private Locale language;
    private String version;

    public MapsBuilder(URL directory) {
        this.directory = directory;
    }

    public HelpSetMap build() {
        return new HelpSetMap(homeId, language, mapIds);
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public URL getDirectory() {
        return directory;
    }

    public void addMapId(MapId mapId) {
        mapIds.add(mapId);
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
