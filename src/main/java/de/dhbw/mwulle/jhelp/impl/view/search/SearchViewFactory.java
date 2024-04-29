package de.dhbw.mwulle.jhelp.impl.view.search;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.impl.builder.ViewBuilder;
import de.dhbw.mwulle.jhelp.impl.view.ViewFactory;
import org.w3c.dom.Element;

public class SearchViewFactory implements ViewFactory {

    @Override
    public View createView(ViewBuilder viewBuilder, Element dataTag) {
        MergeType mergeType = viewBuilder.getMergeType();
        if (mergeType == null) {
            // From spec, this is default for this view type
            mergeType = MergeType.fromString("javax.help.SortMerge");
        }

        return new SearchView(viewBuilder.getName(), viewBuilder.getLabel(), viewBuilder.getType(), mergeType, viewBuilder.getLanguage());
    }
}
