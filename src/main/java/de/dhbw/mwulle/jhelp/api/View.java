package de.dhbw.mwulle.jhelp.api;

public interface View {

    String getMergeType();

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
