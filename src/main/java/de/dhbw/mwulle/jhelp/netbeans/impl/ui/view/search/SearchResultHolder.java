package de.dhbw.mwulle.jhelp.netbeans.impl.ui.view.search;

import de.dhbw.mwulle.jhelp.api.search.SearchResult;

class SearchResultHolder {

    private final SearchResult searchResult;

    SearchResultHolder(SearchResult searchResult) {
        this.searchResult = searchResult;
    }

    public SearchResult getSearchResult() {
        return searchResult;
    }

    @Override
    public String toString() {
        return searchResult.getMapId().getStringId();
    }
}
