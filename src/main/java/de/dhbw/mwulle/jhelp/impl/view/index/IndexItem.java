package de.dhbw.mwulle.jhelp.impl.view.index;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.merge.MergeData;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.impl.view.item.Item;

import java.util.List;
import java.util.Locale;

public class IndexItem extends Item<IndexItem> {

    public IndexItem(Locale language, String text, MapId target, MergeType mergeType, String expand, String presentationType, String presentationName, List<IndexItem> children) {
        super(language, text, target, mergeType, expand, presentationType, presentationName, children);
    }

    @Override
    public IndexItem merge(IndexItem other, MergeType mergeType, MergeData mergeData) {
        MergeType type = mergeType;
        if (other.getMergeType() != null) {
            type = other.getMergeType();
        }

        List<IndexItem> otherItems = mergeData.getMergeManager().getMerger(type).mergeList(mergeData, type, getChildren(), other.getChildren(), IndexView.IndexViewMergeOperation.INSTANCE);

        return new IndexItem(getLanguage(), getText(), getTarget(), getMergeType(), getExpand(), getPresentationType(), getPresentationName(), otherItems);
    }
}
