package de.dhbw.mwulle.jhelp.impl.view.index;

import de.dhbw.mwulle.jhelp.api.merge.MergeData;
import de.dhbw.mwulle.jhelp.api.merge.MergeOperations;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.impl.view.item.ItemView;

import java.util.List;
import java.util.Locale;

public class IndexView extends ItemView<IndexItem> {

    public IndexView(String name, String label, String type, MergeType mergeType, Locale language, List<IndexItem> items) {
        super(name, label, type, mergeType, language, items);
    }

    @Override
    protected IndexView merge(ItemView<IndexItem> other, List<IndexItem> items, MergeData mergeData) {
        return new IndexView(getName(), getLabel(), getType(), getMergeType(), getLanguage(), items);
    }

    @Override
    protected MergeOperations<IndexItem> getMergeOperations() {
        return IndexViewMergeOperation.INSTANCE;
    }

    static class IndexViewMergeOperation extends ItemViewMergeOperations<IndexItem> {
        static final IndexViewMergeOperation INSTANCE = new IndexViewMergeOperation();

        @Override
        public IndexItem appendText(IndexItem indexItem, String text) {
            return new IndexItem(indexItem.getLanguage(), indexItem.getText() + text, indexItem.getTarget(), indexItem.getMergeType(), indexItem.getExpand(), indexItem.getPresentationType(), indexItem.getPresentationName(), indexItem.getChildren());
        }
    }
}
