package de.dhbw.mwulle.jhelp.impl.view.toc;

import de.dhbw.mwulle.jhelp.impl.view.item.Item;
import java.util.List;
import java.util.Locale;

public class TocItem extends Item<TocItem> {

    private final String image;

    public TocItem(Locale language, String text, String target, String image, String mergeType, String expand, String presentationType, String presentationName, List<TocItem> children) {
        super(language, text, target, mergeType, expand, presentationType, presentationName, children);
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
