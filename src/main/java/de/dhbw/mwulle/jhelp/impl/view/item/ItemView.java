package de.dhbw.mwulle.jhelp.impl.view.item;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.View;
import java.util.List;
import java.util.Locale;

public abstract class ItemView<T extends Item<T>> implements View {

    private final String name;
    private final String label;
    private final String type;
    private final String mergeType;
    private final Locale language;
    private final List<T> items;

    protected ItemView(String name, String label, String type, String mergeType, Locale language, List<T> items) {
        this.name = name;
        this.label = label;
        this.type = type;
        this.mergeType = mergeType;
        this.language = language;
        this.items = items;
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

    @Override
    public String getMergeType() {
        return mergeType;
    }

    public Locale getLanguage() {
        return language;
    }

    public List<T> getItems() {
        return items;
    }

    public T findItem(MapId mapId) {
        return findItem(getItems(), mapId.getTarget());
    }

    private T findItem(List<T> items, String target) {
        for (T item : items) {
            if (target.equals(item.getTarget())) {
                return item;
            }
            T other = findItem(item.getChildren(), target);
            if (other != null) {
                return other;
            }
        }

        return null;
    }
}
