package de.dhbw.mwulle.jhelp.impl.view.index;

import de.dhbw.mwulle.jhelp.api.MapId;
import de.dhbw.mwulle.jhelp.api.merge.MergeType;
import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IndexItemParser {

    public List<IndexItem> parse(Element element) {
        List<IndexItem> items = new ArrayList<>();
        ParserUtil.foreachChildrenElement(element, child -> {
            items.add(parseSingle(child));
        });

        return items;
    }

    private IndexItem parseSingle(Element element) {
        String languageText = element.getAttribute("xml:lang");
        String text = element.getAttribute("text");
        MapId target = MapId.fromString(element.getAttribute("target")); // Optional
        MergeType mergeType = MergeType.fromString(element.getAttribute("mergetype")); // Optional
        String expandText = element.getAttribute("expand"); // Optional
        String presentationType = element.getAttribute("presentationtype"); // Optional
        String presentationName = element.getAttribute("presentationname"); // Optional

        Locale language = null;
        if (!languageText.trim().isEmpty()) {
            language = Locale.forLanguageTag(languageText);
        }

        List<IndexItem> items = new ArrayList<>();
        ParserUtil.foreachChildrenElement(element, child -> {
            items.add(parseSingle(child));
        });

        return new IndexItem(language, text, target, mergeType, expandText, presentationType, presentationName, items);
    }
}
