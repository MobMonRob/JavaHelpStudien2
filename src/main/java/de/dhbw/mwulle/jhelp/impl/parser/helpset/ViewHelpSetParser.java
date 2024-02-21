package de.dhbw.mwulle.jhelp.impl.parser.helpset;

import de.dhbw.mwulle.jhelp.api.View;
import de.dhbw.mwulle.jhelp.impl.builder.HelpSetBuilder;
import de.dhbw.mwulle.jhelp.impl.builder.ViewBuilder;
import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import de.dhbw.mwulle.jhelp.impl.parser.view.ViewParser;
import de.dhbw.mwulle.jhelp.impl.view.ViewFactory;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ViewHelpSetParser implements HelpSetParser {

    private final Map<String, ViewParser> viewParsers = new HashMap<>();
    private final Map<String, ViewFactory> viewFactories = new HashMap<>();

    @Override
    public void parse(HelpSetBuilder builder, Element element) {
        ViewBuilder viewBuilder = new ViewBuilder(builder.getDirectory());

        String language = element.getAttribute("xml:lang");
        String mergeType = element.getAttribute("mergetype");

        if (!language.trim().isEmpty()) {
            viewBuilder.setLanguage(Locale.forLanguageTag(language));
        }

        if (!mergeType.trim().isEmpty()) {
            viewBuilder.setMergeType(mergeType);
        }

        ParserUtil.foreachChildrenElement(element, child -> {
            ViewParser parser = viewParsers.get(child.getTagName());
            System.out.println("[ViewHelpSetParser] Got tag: " + child.getTagName());

            if (parser == null) {
                // TODO log this, we ignore unknown tags -> forwards compatibility
                return;
            }

            parser.parse(viewBuilder, child);
        });

        Element typeElement = ParserUtil.getElementByTagName("type", element);
        Element dataElement = ParserUtil.getElementByTagName("data", element);

        ViewFactory viewFactory = viewFactories.get(typeElement.getTextContent());
        System.out.println("Got View type Value: " + typeElement.getTextContent());

        if (viewFactory == null) {
            // TODO log this, we ignore unknown factories -> forwards compatibility
            return;
        }

        viewBuilder.setType(typeElement.getTextContent());

        View view = viewFactory.createView(viewBuilder, dataElement);

        builder.addView(view);
    }

    public void registerChildParser(String key, ViewParser parser) {
        viewParsers.put(key, parser);
    }

    public void registerViewFactory(String key, ViewFactory factory) {
        viewFactories.put(key, factory);
    }
}
