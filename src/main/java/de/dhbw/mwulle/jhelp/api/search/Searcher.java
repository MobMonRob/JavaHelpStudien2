package de.dhbw.mwulle.jhelp.api.search;

import java.io.Closeable;
import java.util.List;

public interface Searcher extends Closeable {

    List<SearchResult> search(String query, int limit);
}
