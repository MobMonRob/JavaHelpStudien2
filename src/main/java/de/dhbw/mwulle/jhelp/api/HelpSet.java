package de.dhbw.mwulle.jhelp.api;

import java.util.Collections;
import java.util.List;

public class HelpSet {

    private final String title;
    private final HelpSetMap helpSetMap;
    private final List<View> views;

    public HelpSet(String title, HelpSetMap helpSetMap, List<View> views) {
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

    public MapIdEntry findMapIdEntry(String target) {
        if (helpSetMap == null) {
            return null;
        }

        return findMapIdEntry(helpSetMap.getMapIdEntries(), target);
    }

    private MapIdEntry findMapIdEntry(List<MapIdEntry> mapIdEntries, String target) {
        for (MapIdEntry mapIdEntry : mapIdEntries) {
            if (target.equals(mapIdEntry.getTarget())) {
                return mapIdEntry;
            }
            MapIdEntry other = findMapIdEntry(mapIdEntry.getChildren(), target);
            if (other != null) {
                return other;
            }
        }

        return null;
    }
}
