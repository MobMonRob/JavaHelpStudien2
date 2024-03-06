package de.dhbw.mwulle.jhelp.impl.parser.maps;

import de.dhbw.mwulle.jhelp.impl.builder.MapIdEntryBuilder;
import de.dhbw.mwulle.jhelp.impl.builder.MapsBuilder;
import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import org.w3c.dom.Element;

import java.net.URL;
import java.util.Locale;

public class MapIdEntryMapsParser implements MapsParser {

    @Override
    public void parse(MapsBuilder builder, Element element) {
        MapIdEntryBuilder mapIdEntryBuilder = new MapIdEntryBuilder();
        parse(mapIdEntryBuilder, element, builder.getDirectory());
        builder.addMapIdEntry(mapIdEntryBuilder.build());
    }

    private void parse(MapIdEntryBuilder builder, Element element, URL directory) {
        String target = element.getAttribute("target");
        String url = element.getAttribute("url");
        String language = element.getAttribute("xml:lang");

        if (!language.trim().isEmpty()) {
            builder.setLanguage(Locale.forLanguageTag(language));
        }

        builder.setTarget(target);
        builder.setUrl(ParserUtil.resolve(directory, url));

        ParserUtil.foreachChildrenElement(element, child -> {
            MapIdEntryBuilder mapIdEntryBuilder = new MapIdEntryBuilder();
            parse(mapIdEntryBuilder, child, directory);
            builder.addMapId(mapIdEntryBuilder.build());
        });
    }
}
