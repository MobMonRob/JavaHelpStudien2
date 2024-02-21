package de.dhbw.mwulle.jhelp.impl.builder;

import de.dhbw.mwulle.jhelp.api.HelpSet;
import de.dhbw.mwulle.jhelp.api.HelpSetMap;
import de.dhbw.mwulle.jhelp.api.View;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HelpSetBuilder {

    private final URL directory;
    private final List<View> views = new ArrayList<>();
    private String title;
    private HelpSetMap helpSetMap;

    public HelpSetBuilder(URL directory) {
        this.directory = directory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HelpSetMap getHelpSetMap() {
        return helpSetMap;
    }

    public void setHelpSetMap(HelpSetMap helpSetMap) {
        this.helpSetMap = helpSetMap;
    }

    public URL getDirectory() {
        return directory;
    }

    public void addView(View view) {
        views.add(view);
    }

    public HelpSet build() {
        return new HelpSet(title, helpSetMap, views);
    }
}
