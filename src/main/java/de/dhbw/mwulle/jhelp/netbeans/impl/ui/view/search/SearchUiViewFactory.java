package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.search;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.api.search.SearchEngine;
import de.dhbw.mwulle.jhelp.impl.view.search.SearchView;
import de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.UiViewFactory;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

import java.awt.*;

@ServiceProvider(service = UiViewFactory.class)
public class SearchUiViewFactory implements UiViewFactory {

    @Override
    public Component createComponent(Lookup.Provider lookupProvider, View view) {
        SearchEngine searchEngine = Lookup.getDefault().lookup(SearchEngine.class);
        HelpSet helpSet = lookupProvider.getLookup().lookup(HelpSet.class);
        return new SearchComponent(lookupProvider, searchEngine.createSearcher(helpSet.getHelpSetIds()));
    }

    @Override
    public Class<? extends View> getViewClass() {
        return SearchView.class;
    }
}
