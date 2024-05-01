package de.dhbw.mwulle.jhelp.api.search;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.HelpSetId;

import java.util.List;

public interface SearchEngine {

    void indexHelpSet(String buildNumber, HelpSetId helpSetId, HelpSet helpSet);

    Searcher createSearcher(List<HelpSetId> helpSetIds);
}
