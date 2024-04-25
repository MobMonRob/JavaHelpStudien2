package de.dhbw.mwulle.jhelp.api;

import de.dhbw.mwulle.jhelp.api.merge.MergeType;

public interface View {

    MergeType getMergeType();

    String getName();

    String getLabel();

    /**
     * Same as the content of the type tag in the view tag.
     * Example: javax.help.TOCView
     *
     * @return the type of this view
     */
    String getType();
}
