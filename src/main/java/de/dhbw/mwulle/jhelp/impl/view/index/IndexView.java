package de.dhbw.mwulle.jhelp.impl.view.index;

import de.dhbw.mwulle.jhelp.impl.view.item.ItemView;
import java.util.List;
import java.util.Locale;

public class IndexView extends ItemView<IndexItem> {

    public IndexView(String name, String label, String type, String mergeType, Locale language, List<IndexItem> items) {
        super(name, label, type, mergeType, language, items);
    }
}
