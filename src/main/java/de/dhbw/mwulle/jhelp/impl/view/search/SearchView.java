package de.dhbw.mwulle.jhelp.impl.view.search;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.api.merge.MergeData;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;

import java.util.Locale;

public class SearchView implements View {

    private final String name;
    private final String label;
    private final String type;
    private final MergeType mergeType;
    private final Locale language;

    public SearchView(String name, String label, String type, MergeType mergeType, Locale language) {
        this.name = name;
        this.label = label;
        this.type = type;
        this.mergeType = mergeType;
        this.language = language;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public MergeType getMergeType() {
        return mergeType;
    }

    @Override
    public View merge(View other, MergeType mergeType, MergeData mergeData) {
        // Not merge able
        return this;
    }
}
