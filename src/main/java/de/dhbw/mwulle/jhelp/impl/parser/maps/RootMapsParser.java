package de.dhbw.mwulle.jhelp.impl.parser.maps;

import de.dhbw.mwulle.jhelp.impl.builder.MapsBuilder;
import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RootMapsParser implements MapsParser {

    private final Map<String, MapsParser> mapsParser = new HashMap<>();

    @Override
    public void parse(MapsBuilder builder, Element element) {
        String language = element.getAttribute("xml:lang");
        String version = element.getAttribute("version");

        if (!language.trim().isEmpty()) {
            builder.setLanguage(Locale.forLanguageTag(language));
        }

        // TODO 2023-12-31: Check version if v2
        builder.setVersion(version);

        ParserUtil.foreachChildrenElement(element, child -> {
            MapsParser parser = mapsParser.get(child.getTagName());

            if (parser == null) {
                // TODO 2023-12-31: log this, we ignore unknown tags -> forwards compatibility
                return;
            }

            parser.parse(builder, child);
        });
    }

    public void registerChildParser(String key, MapsParser parser) {
        mapsParser.put(key, parser);
    }
}
