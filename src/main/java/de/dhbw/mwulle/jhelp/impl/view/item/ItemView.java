package de.dhbw.mwulle.jhelp.impl.view.item;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.api.merge.MergeData;
import de.dhbw.mwulle.jhelp.api.merge.MergeOperations;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public abstract class ItemView<T extends Item<T>> implements View {

    private final String name;
    private final String label;
    private final String type;
    private final MergeType mergeType;
    private final Locale language;
    private final List<T> items;

    protected ItemView(String name, String label, String type, MergeType mergeType, Locale language, List<T> items) {
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
    public MergeType getMergeType() {
        return mergeType;
    }

    public Locale getLanguage() {
        return language;
    }

    public List<T> getItems() {
        return items;
    }

    public T findItem(MapId mapId) {
        return findItem(getItems(), mapId);
    }

    private T findItem(List<T> items, MapId id) {
        for (T item : items) {
            if (id.equals(item.getTarget())) {
                return item;
            }
            T other = findItem(item.getChildren(), id);
            if (other != null) {
                return other;
            }
        }

        return null;
    }

    @Override
    public View merge(View other, MergeType mergeType, MergeData mergeData) {
        ItemView<T> otherView = (ItemView<T>) other;

        MergeType type = mergeType;
        if (otherView.getMergeType() != null) {
            type = otherView.getMergeType();
        }

        List<T> otherItems = mergeData.getMergeManager().getMerger(type).mergeList(mergeData, type, getItems(), otherView.getItems(), getMergeOperations());

        return merge(otherView, otherItems, mergeData);
    }

    protected abstract ItemView<T> merge(ItemView<T> other, List<T> items, MergeData mergeData);

    protected abstract MergeOperations<T> getMergeOperations();

    protected abstract static class ItemViewMergeOperations<F extends Item<F>> implements MergeOperations<F> {

        @Override
        public int compare(F first, F second) {
            return first.getText().compareTo(second.getText());
        }

        @Override
        public boolean basicEquals(F first, F second) {
            if (!Objects.equals(first.getText(), second.getText())) {
                return false;
            }

            return Objects.equals(first.getTarget(), second.getTarget());
        }
    }
}
