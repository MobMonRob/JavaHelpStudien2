package de.dhbw.mwulle.jhelp.impl.parser.helpset;

import de.dhbw.mwulle.jhelp.impl.builder.HelpSetBuilder;
import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class RootHelpSetParser implements HelpSetParser {

    private final Map<String, HelpSetParser> helpSetParsers = new HashMap<>();

    @Override
    public void parse(HelpSetBuilder builder, Element element) {
        ParserUtil.foreachChildrenElement(element, child -> {
            HelpSetParser parser = helpSetParsers.get(child.getTagName());
            System.out.println("[RootHelpSetParser] Got tag: " + child.getTagName());

            if (parser == null) {
                // TODO 2023-12-31: log this, we ignore unknown tags -> forwards compatibility
                return;
            }

            parser.parse(builder, child);
        });
    }

    public void registerChildParser(String key, HelpSetParser parser) {
        helpSetParsers.put(key, parser);
    }
}
