package de.dhbw.mwulle.jhelp.impl.parser.maps;

import de.dhbw.mwulle.jhelp.impl.builder.MapIdBuilder;
import de.dhbw.mwulle.jhelp.impl.builder.MapsBuilder;
import de.dhbw.mwulle.jhelp.impl.parser.ParserUtil;
import org.w3c.dom.Element;

import java.net.URL;
import java.util.Locale;

public class MapIdMapsParser implements MapsParser {

    @Override
    public void parse(MapsBuilder builder, Element element) {
        MapIdBuilder mapIdBuilder = new MapIdBuilder();
        parse(mapIdBuilder, element, builder.getDirectory());
        builder.addMapId(mapIdBuilder.build());
    }

    private void parse(MapIdBuilder builder, Element element, URL directory) {
        String target = element.getAttribute("target");
        String url = element.getAttribute("url");
        String language = element.getAttribute("xml:lang");

        if (!language.trim().isEmpty()) {
            builder.setLanguage(Locale.forLanguageTag(language));
        }

        builder.setTarget(target);
        builder.setUrl(ParserUtil.resolve(directory, url));

        ParserUtil.foreachChildrenElement(element, child -> {
            MapIdBuilder mapIdBuilder = new MapIdBuilder();
            parse(mapIdBuilder, child, directory);
            builder.addMapId(mapIdBuilder.build());
        });
    }
}
