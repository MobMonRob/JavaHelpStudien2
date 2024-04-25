package de.dhbw.mwulle.jhelp.api.merge;

import de.dhbw.mwulle.jhelp.api.HelpSet;

public interface MergeManager {

    HelpSet merge(HelpSet first, HelpSet second);

    void registerMerger(MergeType mergeType, Merger merger);

    Merger getMerger(MergeType mergeType);
}
