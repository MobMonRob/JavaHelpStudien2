package de.dhbw.mwulle.jhelp.impl.builder;

import de.dhbw.mwulle.jhelp.api.MapId;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapIdBuilder {

    private final List<MapId> mapIds = new ArrayList<>();
    private String target;
    private URL url;
    private Locale language;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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

    public void addMapId(MapId mapId) {
        mapIds.add(mapId);
    }

    public MapId build() {
        return new MapId(target, url, language, mapIds);
    }
}
