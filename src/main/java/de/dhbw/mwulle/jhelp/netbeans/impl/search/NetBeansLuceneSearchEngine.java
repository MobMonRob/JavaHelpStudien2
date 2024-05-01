package de.dhbw.mwulle.jhelp.netbeans.impl.search;

import de.dhbw.mwulle.jhelp.api.search.SearchEngine;
import de.dhbw.mwulle.jhelp.impl.search.LuceneSearchEngine;
import org.openide.modules.Places;
import org.openide.util.lookup.ServiceProvider;

import java.io.File;

@ServiceProvider(service = SearchEngine.class)
public class NetBeansLuceneSearchEngine extends LuceneSearchEngine {

    @Override
    protected File getCacheDirectory() {
        return Places.getCacheDirectory();
    }
}
