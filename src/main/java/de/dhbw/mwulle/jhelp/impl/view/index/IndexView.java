package de.dhbw.mwulle.jhelp.impl.view.index;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.View;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class IndexView implements View {

    private final String name;
    private final String label;
    private final String type;
    private final String mergeType;
    private final Locale language;
    private final List<IndexItem> indexItems;

    public IndexView(String name, String label, String type, String mergeType, Locale language, List<IndexItem> indexItems) {
        this.name = name;
        this.label = label;
        this.type = type;
        this.mergeType = mergeType;
        this.language = language;
        this.indexItems = Collections.unmodifiableList(indexItems);
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

    public List<IndexItem> getIndexItems() {
        return indexItems;
    }

    public IndexItem findIndexIdem(MapId mapId) {
        return findIndexItem(getIndexItems(), mapId.getTarget());
    }

    private IndexItem findIndexItem(List<IndexItem> indexItems, String target) {
        for (IndexItem indexItem : indexItems) {
            if (target.equals(indexItem.getTarget())) {
                return indexItem;
            }
            IndexItem other = findIndexItem(indexItem.getChildren(), target);
            if (other != null) {
                return other;
            }
        }

        return null;
    }
}
