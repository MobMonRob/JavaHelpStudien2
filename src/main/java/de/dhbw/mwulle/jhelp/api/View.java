package de.dhbw.mwulle.jhelp.api;

import de.dhbw.mwulle.jhelp.api.merge.MergeAble;

public interface View extends MergeAble<View> {

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
