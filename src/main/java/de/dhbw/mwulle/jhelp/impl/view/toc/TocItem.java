package de.dhbw.mwulle.jhelp.impl.view.toc;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.merge.MergeData;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.impl.view.item.Item;

import java.util.List;
import java.util.Locale;

public class TocItem extends Item<TocItem> {

    private final MapId image;

    public TocItem(Locale language, String text, MapId target, MapId image, MergeType mergeType, String expand, String presentationType, String presentationName, List<TocItem> children) {
        super(language, text, target, mergeType, expand, presentationType, presentationName, children);
        this.image = image;
    }

    public MapId getImage() {
        return image;
    }

    @Override
    public TocItem merge(TocItem other, MergeType mergeType, MergeData mergeData) {
        MergeType type = mergeType;
        if (other.getMergeType() != null) {
            type = other.getMergeType();
        }

        List<TocItem> otherItems = mergeData.getMergeManager().getMerger(type).mergeList(mergeData, type, getChildren(), other.getChildren(), TocView.TocViewMergeOperation.INSTANCE);

        return new TocItem(getLanguage(), getText(), getTarget(), getImage(), getMergeType(), getExpand(), getPresentationType(), getPresentationName(), otherItems);
    }
}
