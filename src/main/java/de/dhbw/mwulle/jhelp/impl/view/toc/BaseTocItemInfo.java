package de.dhbw.mwulle.jhelp.impl.view.toc;

import java.util.Locale;

public class BaseTocItemInfo {

    private final Locale language;
    private final String categoryClosedImage;
    private final String categoryOpenImage;
    private final String topicImage;

    public BaseTocItemInfo(Locale language, String categoryClosedImage, String categoryOpenImage, String topicImage) {
        this.language = language;
        this.categoryClosedImage = categoryClosedImage;
        this.categoryOpenImage = categoryOpenImage;
        this.topicImage = topicImage;
    }

    public Locale getLanguage() {
        return language;
    }

    public String getCategoryClosedImage() {
        return categoryClosedImage;
    }

    public String getCategoryOpenImage() {
        return categoryOpenImage;
    }

    public String getTopicImage() {
        return topicImage;
    }
}
