package de.dhbw.mwulle.jhelp.impl.view.toc;

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
}
