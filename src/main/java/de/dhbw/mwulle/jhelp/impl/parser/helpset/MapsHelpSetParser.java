package de.dhbw.mwulle.jhelp.impl.parser.helpset;

import de.dhbw.mwulle.jhelp.impl.builder.HelpSetBuilder;
import de.dhbw.mwulle.jhelp.impl.builder.MapsBuilder;
import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import de.dhbw.mwulle.jhelp.impl.parser.maps.MapsParser;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;

public class MapsHelpSetParser implements HelpSetParser {

    private final Map<String, MapsParser> mapsParsers = new HashMap<>();

    @Override
    public void parse(HelpSetBuilder builder, Element element) {
        MapsBuilder mapsBuilder = new MapsBuilder(builder.getDirectory());

        ParserUtil.foreachChildrenElement(element, child -> {
            MapsParser parser = mapsParsers.get(child.getTagName());
            System.out.println("[MapsHelpSetParser] Got tag: " + child.getTagName());

            if (parser == null) {
                // TODO log this, we ignore unknown tags -> forwards compatibility
                return;
            }

            parser.parse(mapsBuilder, child);
        });

        builder.setHelpSetMap(mapsBuilder.build());
    }

    public void registerChildParser(String key, MapsParser parser) {
        mapsParsers.put(key, parser);
    }
}
