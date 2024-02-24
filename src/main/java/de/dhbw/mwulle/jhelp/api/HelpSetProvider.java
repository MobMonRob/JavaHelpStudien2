package de.dhbw.mwulle.jhelp.api;

public interface HelpSetProvider {

    /**
     * The master {@link HelpSet} has all other HelpSets merged into it, which got loaded.
     *
     * @return the master {@link HelpSet}
     */
    HelpSet getMasterHelpSet();
}
