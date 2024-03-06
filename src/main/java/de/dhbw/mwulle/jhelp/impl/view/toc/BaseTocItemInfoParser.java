package de.dhbw.mwulle.jhelp.impl.view.toc;

import de.dhbw.mwulle.jhelp.api.MapId;
import org.w3c.dom.Element;

import java.util.Locale;

public class BaseTocItemInfoParser {

    public BaseTocItemInfo parse(Element element) {
        String languageText = element.getAttribute("xml:lang");
        MapId categoryClosedImage = MapId.fromString(element.getAttribute("categoryclosedimage"));
        MapId categoryOpenImage = MapId.fromString(element.getAttribute("categoryopenimage"));
        MapId topicImage = MapId.fromString(element.getAttribute("topicimage"));

        Locale language = null;
        if (!languageText.trim().isEmpty()) {
            language = Locale.forLanguageTag(languageText);
        }

        return new BaseTocItemInfo(language, categoryClosedImage, categoryOpenImage, topicImage);
    }
}
