package de.dhbw.mwulle.jhelp.impl.view.toc;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.View;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TocView implements View {

    private final String name;
    private final String label;
    private final String type;
    private final String mergeType;
    private final Locale language;
    private final List<TocItem> tocItems;

    public TocView(String name, String label, String type, String mergeType, Locale language, List<TocItem> tocItems) {
        this.name = name;
        this.label = label;
        this.type = type;
        this.mergeType = mergeType;
        this.language = language;
        this.tocItems = Collections.unmodifiableList(tocItems);
    }

    @Override
    public String getMergeType() {
        return mergeType;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getType() {
        return type;
    }

    public List<TocItem> getTocItems() {
        return tocItems;
    }


    public TocItem findTocItem(MapId mapId) {
        return findTocItem(getTocItems(), mapId.getTarget());
    }

    private TocItem findTocItem(List<TocItem> tocItems, String target) {
        for (TocItem tocItem : tocItems) {
            if (target.equals(tocItem.getTarget())) {
                return tocItem;
            }
            TocItem other = findTocItem(tocItem.getChildren(), target);
            if (other != null) {
                return other;
            }
        }

        return null;
    }
}
