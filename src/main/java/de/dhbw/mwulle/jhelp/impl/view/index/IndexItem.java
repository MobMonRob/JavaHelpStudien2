package de.dhbw.mwulle.jhelp.impl.view.index;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.impl.view.item.Item;

import java.util.List;
import java.util.Locale;

public class IndexItem extends Item<IndexItem> {

    public IndexItem(Locale language, String text, MapId target, MergeType mergeType, String expand, String presentationType, String presentationName, List<IndexItem> children) {
        super(language, text, target, mergeType, expand, presentationType, presentationName, children);
    }
}
