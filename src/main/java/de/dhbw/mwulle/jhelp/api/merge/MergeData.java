package de.dhbw.mwulle.jhelp.api.merge;

import de.dhbw.mwulle.jhelp.api.HelpSet;

public class MergeData {

    public static MergeData create(MergeManager mergeManager, HelpSet first, HelpSet second) {
        return new MergeData(mergeManager, first, second);
    }

    private final MergeManager mergeManager;
    private final HelpSet firstHelpSet;
    private final HelpSet secondHelpSet;

    private MergeData(MergeManager mergeManager, HelpSet firstHelpSet, HelpSet secondHelpSet) {
        this.mergeManager = mergeManager;
        this.firstHelpSet = firstHelpSet;
        this.secondHelpSet = secondHelpSet;
    }

    public MergeManager getMergeManager() {
        return mergeManager;
    }

    public HelpSet getFirstHelpSet() {
        return firstHelpSet;
    }

    public HelpSet getSecondHelpSet() {
        return secondHelpSet;
    }
}
