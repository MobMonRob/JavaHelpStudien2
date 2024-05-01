package de.dhbw.mwulle.jhelp.api;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HelpSetMap {

    private final MapId homeId;
    private final Locale language;
    private final List<MapIdEntry> mapIdEntries;

    private HelpSetMap(MapId homeId, Locale language, List<MapIdEntry> mapIdEntries) {
        this.homeId = homeId;
        this.language = language;
        this.mapIdEntries = Collections.unmodifiableList(mapIdEntries);
    }

    public static HelpSetMap create(MapId homeId, Locale language, List<MapIdEntry> mapIdEntries) {
        return new HelpSetMap(homeId, language, mapIdEntries);
    }

    public MapId getHomeId() {
        return homeId;
    }

    public Locale getLanguage() {
        return language;
    }

    public List<MapIdEntry> getMapIdEntries() {
        return mapIdEntries;
    }
}
