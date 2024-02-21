package de.dhbw.mwulle.jhelp.impl.view.toc;

import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import de.dhbw.mwulle.jhelp.impl.view.index.IndexItem;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TocItemParser {

    public List<TocItem> parse(Element element) {
        List<TocItem> items = new ArrayList<>();
        ParserUtil.foreachChildrenElement(element, child -> {
            items.add(parseSingle(child));
        });

        return items;
    }

    private TocItem parseSingle(Element element) {
        String languageText = element.getAttribute("xml:lang");
        String text = element.getAttribute("text");
        String target = element.getAttribute("target"); // Optional
        String image = element.getAttribute("image"); // Optional
        String mergeType = element.getAttribute("mergetype"); // Optional
        String expandText = element.getAttribute("expand"); // Optional
        String presentationType = element.getAttribute("presentationtype"); // Optional
        String presentationName = element.getAttribute("presentationname"); // Optional

        Locale language = null;
        if (!languageText.trim().isEmpty()) {
            language = Locale.forLanguageTag(languageText);
        }

        List<TocItem> items = new ArrayList<>();
        ParserUtil.foreachChildrenElement(element, child -> {
            items.add(parseSingle(child));
        });

        return new TocItem(language, text, target, image, mergeType, expandText, presentationType, presentationName, items);
    }
}
