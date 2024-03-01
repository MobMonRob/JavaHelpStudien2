package de.dhbw.mwulle.jhelp.impl.view.toc;

import de.dhbw.mwulle.jhelp.impl.view.item.ItemView;
import java.util.List;
import java.util.Locale;

public class TocView extends ItemView<TocItem> {

    public TocView(String name, String label, String type, String mergeType, Locale language, List<TocItem> items) {
        super(name, label, type, mergeType, language, items);
    }
}
