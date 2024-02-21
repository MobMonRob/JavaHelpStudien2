package de.dhbw.mwulle.jhelp.api;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class HelpSetMap {

    private final String homeId;
    private final Locale language;
    private final List<MapId> mapIds;

    public HelpSetMap(String homeId, Locale language, List<MapId> mapIds) {
        this.homeId = homeId;
        this.language = language;
        this.mapIds = Collections.unmodifiableList(mapIds);
    }

    public String getHomeId() {
        return homeId;
    }

    public Locale getLanguage() {
        return language;
    }

    public List<MapId> getMapIds() {
        return mapIds;
    }
}
