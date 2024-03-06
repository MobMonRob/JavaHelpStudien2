package de.dhbw.mwulle.jhelp.impl.view.toc;

import org.w3c.dom.Element;

import java.util.Locale;

public class BaseTocItemInfoParser {

    public BaseTocItemInfo parse(Element element) {
        String languageText = element.getAttribute("xml:lang");
        String categoryClosedImage = element.getAttribute("categoryclosedimage");
        String categoryOpenImage = element.getAttribute("categoryopenimage");
        String topicImage = element.getAttribute("topicimage");

        Locale language = null;
        if (!languageText.trim().isEmpty()) {
            language = Locale.forLanguageTag(languageText);
        }

        return new BaseTocItemInfo(language, categoryClosedImage, categoryOpenImage, topicImage);
    }
}
