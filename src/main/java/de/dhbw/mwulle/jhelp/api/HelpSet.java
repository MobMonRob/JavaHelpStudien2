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

    public MapId findMapId(String target) {
        if (helpSetMap == null) {
            return null;
        }

        return findMapId(helpSetMap.getMapIds(), target);
    }

    private MapId findMapId(List<MapId> mapIds, String target) {
        for (MapId mapId : mapIds) {
            if (target.equals(mapId.getTarget())) {
                return mapId;
            }
            MapId other = findMapId(mapId.getChildren(), target);
            if (other != null) {
                return other;
            }
        }

        return null;
    }
}
