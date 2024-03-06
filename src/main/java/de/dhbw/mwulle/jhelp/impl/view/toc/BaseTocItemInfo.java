package de.dhbw.mwulle.jhelp.impl.view.toc;

import de.dhbw.mwulle.jhelp.api.MapId;

import java.util.Locale;

public class BaseTocItemInfo {

    private final Locale language;
    private final MapId categoryClosedImage;
    private final MapId categoryOpenImage;
    private final MapId topicImage;

    public BaseTocItemInfo(Locale language, MapId categoryClosedImage, MapId categoryOpenImage, MapId topicImage) {
        this.language = language;
        this.categoryClosedImage = categoryClosedImage;
        this.categoryOpenImage = categoryOpenImage;
        this.topicImage = topicImage;
    }

    public Locale getLanguage() {
        return language;
    }

    public MapId getCategoryClosedImage() {
        return categoryClosedImage;
    }

    public MapId getCategoryOpenImage() {
        return categoryOpenImage;
    }

    public MapId getTopicImage() {
        return topicImage;
    }
}
