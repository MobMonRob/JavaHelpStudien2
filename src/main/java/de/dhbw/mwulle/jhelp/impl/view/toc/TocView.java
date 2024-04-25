package de.dhbw.mwulle.jhelp.impl.view.toc;

import de.dhbw.mwulle.jhelp.api.merge.MergeData;
import de.dhbw.mwulle.jhelp.api.merge.MergeOperations;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.impl.view.item.ItemView;

import java.util.List;
import java.util.Locale;

public class TocView extends ItemView<TocItem> {

    private final BaseTocItemInfo baseTocItemInfo;

    public TocView(String name, String label, String type, MergeType mergeType, Locale language, List<TocItem> items, BaseTocItemInfo baseTocItemInfo) {
        super(name, label, type, mergeType, language, items);
        this.baseTocItemInfo = baseTocItemInfo;
    }

    public BaseTocItemInfo getBaseTocItemInfo() {
        return baseTocItemInfo;
    }

    @Override
    protected TocView merge(ItemView<TocItem> other, List<TocItem> items, MergeData mergeData) {
        TocView otherToc = (TocView) other;

        return new TocView(getName(), getLabel(), getType(), getMergeType(), getLanguage(), items, otherToc.getBaseTocItemInfo());
    }

    @Override
    protected MergeOperations<TocItem> getMergeOperations() {
        return TocViewMergeOperation.INSTANCE;
    }

    static class TocViewMergeOperation extends ItemViewMergeOperations<TocItem> {
        static final TocViewMergeOperation INSTANCE = new TocViewMergeOperation();

        @Override
        public TocItem appendText(TocItem tocItem, String text) {
            return new TocItem(tocItem.getLanguage(), tocItem.getText() + text, tocItem.getTarget(), tocItem.getImage(), tocItem.getMergeType(), tocItem.getExpand(), tocItem.getPresentationType(), tocItem.getPresentationName(), tocItem.getChildren());
        }
    }
}
