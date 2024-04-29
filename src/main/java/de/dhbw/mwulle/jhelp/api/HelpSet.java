package de.dhbw.mwulle.jhelp.api;

import java.util.Collections;
import java.util.List;

public class HelpSet {

    private final List<HelpSetId> helpSetIds;
    private final String title;
    private final HelpSetMap helpSetMap;
    private final List<View> views;

    public HelpSet(List<HelpSetId> helpSetIds, String title, HelpSetMap helpSetMap, List<View> views) {
        this.helpSetIds = helpSetIds;
        this.title = title;
        this.helpSetMap = helpSetMap;
        this.views = Collections.unmodifiableList(views);
    }

    public String getTitle() {
        return title;
    }

    public HelpSetMap getHelpSetMap() {
        return helpSetMap;
    }

    public List<View> getViews() {
        return views;
    }

    public MapIdEntry findMapIdEntry(MapId id) {
        if (helpSetMap == null) {
            return null;
        }

        return findMapIdEntry(helpSetMap.getMapIdEntries(), id);
    }

    private MapIdEntry findMapIdEntry(List<MapIdEntry> mapIdEntries, MapId id) {
        for (MapIdEntry mapIdEntry : mapIdEntries) {
            if (id.equals(mapIdEntry.getId())) {
                return mapIdEntry;
            }
            MapIdEntry other = findMapIdEntry(mapIdEntry.getChildren(), id);
            if (other != null) {
                return other;
            }
        }

        return null;
    }

    public List<HelpSetId> getHelpSetIds() {
        return helpSetIds;
    }
}
